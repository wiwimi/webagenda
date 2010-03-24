/**
 * persistence - EmployeeBroker.java
 */
package persistence;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidLoginException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;
import application.DBConnection;
import business.Employee;
import business.permissions.PermissionLevel;

/**
 * @author peon-dev, Daniel Wehr
 * @version 0.3.0
 */
public class EmployeeBroker extends Broker<Employee>
	{
	/** Static representation of the broker. Initialized the first time called. */
	private static volatile EmployeeBroker	employeeBroker	= null;
	
	/**
	 * Constructor for EmployeeBroker
	 */
	private EmployeeBroker()
		{
		super.initConnectionThread(); // Start the connection monitor, checking
		// for old connections.
		}
	
	/**
	 * Returns an object-based Employee Broker object.
	 * 
	 * @return EmployeeBroker result from the Broker request as its respective
	 *         Broker object.
	 */
	public static EmployeeBroker getBroker()
		{
		if (employeeBroker == null)
			{
			employeeBroker = new EmployeeBroker();
			}
		return employeeBroker;
		}
	
	
	/**
	 * Method that contains all throwable conditions when attempting to access broker methods of EmployeeBroker.
	 * TODO: Do all proper permission checks for broker methods
	 * 
	 * @param target Employee of desired create, update or delete.
	 * @param caller Employee that invokes the method
	 * 
	 * @throws InvalidPermissionException
	 * @throws DBException
	 * @throws DBDownException 
	 */
	private PermissionLevel checkPermissions(Employee target, Employee caller) 
		throws InvalidPermissionException, DBException, DBDownException
	{	
		
		// If item being sent in is a search Employee, this will trigger that it's not exactly valid
		// so it can be dealt with by the program
		try {
			target.getLevel();
			target.getVersion();	
		}
		catch(Exception E) {
			throw new InvalidPermissionException("PermissionLevel not found in target Employee");
		}
		
		PermissionLevel[] pla = persistence.PermissionBroker.getBroker().get(caller.getLevel(), caller.getVersion(), caller);
		if(pla == null) {
			throw new InvalidPermissionException("(Warning) PermissionLevels not found for caller Employee");
		}
		PermissionLevel pl = pla[0];
		pla = null;
		if(pl == null)
			throw new InvalidPermissionException("No matches for caller's Permission Level found, cannot process");
		if(pl.getLevel() < target.getLevel()) {
			// Do not allow creation access
			throw new InvalidPermissionException("User cannot create Employees.");
		}
		else if(caller.getLevel() == target.getLevel()) {
			if(pl.getLevel_permissions().getTrusted() <= caller.getLevel()) {
				throw new InvalidPermissionException("User is not trusted to the level required to perform this action");
				
			}
			else {
				// Trusted to create this Permission
				// TODO: Log this method's results, allow user to continue
			}
		}
		return pl;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#create(business.BusinessObject)
	 */
	@Override
	public boolean create(Employee createEmp, Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		if (createEmp == null)
			throw new NullPointerException("Can not create null employee.");
		if(caller == null)
			throw new DBException("Cannot parse PermissionLevel when invoking Employee is null");
		
		PermissionLevel pl = checkPermissions(createEmp,caller); // will throw exceptions if permission 'levels' are invalid (doesn't detect individual ones)
		System.out.println(pl + " permission level");
		System.out.println("canManageEmployees: "  + pl.getLevel_permissions().isCanManageEmployees() + " "  + caller.getGivenName());
		if(!pl.getLevel_permissions().isCanManageEmployees()) {
			
			throw new PermissionViolationException("User is not authorized to Create an Employee");
		}
		
		/*
		 * Make sure all "not null" DB fields are filled. Expand this to throw a
		 * DBAddException with the exception message saying exactly what fields
		 * are missing.
		 */
		String nullMsg = "Missing Required Fields:";
		if (createEmp.getEmpID() == null)
			nullMsg = nullMsg + " EmpID";
		if (createEmp.getGivenName() == null)
			nullMsg = nullMsg + " GivenName";
		if (createEmp.getFamilyName() == null)
			nullMsg = nullMsg + " FamilyName";
		if (createEmp.getUsername() == null)
			nullMsg = nullMsg + " Username";
		if (createEmp.getPassword() == null)
			nullMsg = nullMsg + " Password";
		if (createEmp.getLevel() < 0)
			nullMsg = nullMsg + " [negative] PermissionLevel";
		if (!nullMsg.equals("Missing Required Fields:"))
			throw new DBException(nullMsg);
		
		boolean result = false;
		try
			{
			//Get connection.
			DBConnection conn = this.getConnection();
			
			//Get callable statement.
			String call = "CALL createEmployee(?,?,?,?,?,?,?,?,?,?,?,?);";
			CallableStatement cs = conn.getConnection().prepareCall(call);
			
			//Assign variables of new user.
			cs.registerOutParameter(12, java.sql.Types.BOOLEAN);
			cs.setInt(1, createEmp.getEmpID());
			
			if (createEmp.getSupervisorID() == null)
				cs.setNull(2, java.sql.Types.INTEGER);
			else
				cs.setInt(2, createEmp.getSupervisorID());
			
			cs.setString(3, createEmp.getGivenName());
			cs.setString(4, createEmp.getFamilyName());
			
			if (createEmp.getBirthDate() == null)
				cs.setNull(5, java.sql.Types.DATE);
			else
				cs.setDate(5, createEmp.getBirthDate());
			
			if (createEmp.getEmail() == null)
				cs.setNull(6, java.sql.Types.VARCHAR);
			else
				cs.setString(6, createEmp.getEmail());
			
			cs.setString(7, createEmp.getUsername());
			cs.setString(8, createEmp.getPassword());
			
			if (createEmp.getPrefPosition() == null)
				cs.setNull(9, java.sql.Types.VARCHAR);
			else
				cs.setString(9, createEmp.getPrefPosition());
			
			if (createEmp.getPrefLocation() == null)
				cs.setNull(10, java.sql.Types.VARCHAR);
			else
				cs.setString(10, createEmp.getPrefLocation());
			
			cs.setString(11, createEmp.getLevel() + "" + createEmp.getVersion());
			
			//Run procedure.
			cs.execute();
			
			//Get result boolean from procedure.
			result = cs.getBoolean("result");
			cs.close();
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			// TODO May need additional SQL exception processing here.
			throw new DBException("Failed to create employee.", e);
			}
		
		// TODO Inserts for employee skills as well, once that broker is up.
		
		return result;
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(Employee deleteEmp, Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		if (deleteEmp == null)
			throw new NullPointerException("Can not delete null employee.");
		
		if (deleteEmp.getEmpID() == null)
			throw new DBException("Missing Required Field: EmpID");
		
		PermissionLevel pl = checkPermissions(deleteEmp,caller); /// will throw exceptions if permission 'levels' are invalid (doesn't detect individual ones)
		if(!pl.getLevel_permissions().isCanManageEmployees()) {
			throw new PermissionViolationException("User is not authorized to Delete [Disable] an Emplyoee");
		}
		
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`EMPLOYEE` WHERE empID = %s;",
				deleteEmp.getEmpID()+"");
		
		boolean success;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(delete);
			
			if (result != 1)
				throw new DBException("Failed to delete employee, result count incorrect: " +	result);
			else
				success = true;
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete employee.",e);
			}
		
		return success;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject)
	 */
	@Override
	public Employee[] get(Employee searchTemplate,Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		if (searchTemplate == null)
			throw new NullPointerException(
					"Can not search with null employee template.");
		if(caller == null)
			throw new DBException("Cannot parse PermissionLevel when invoking Employee is null");
		PermissionLevel pl = null;
		try
			{
			pl = checkPermissions(searchTemplate, caller); /// will throw exceptions if permission 'levels' are invalid (doesn't detect individual ones)
			}
		catch (InvalidPermissionException ipe)	{ ipe.printStackTrace(); }
		
		try {
			if(!searchTemplate.getActive() && !pl.getLevel_permissions().isCanViewInactiveEmployees())
				throw new PermissionViolationException("User cannot view Inactive Employee data");
		}
		catch(NullPointerException npE) {
			// Employee is a search template and does not have a permission set.
			if(!pl.getLevel_permissions().isCanViewInactiveEmployees()) {
				throw new PermissionViolationException("User cannot view Inactive Employee data");
			}
		}
		
		// Create sql select statement from employee object.
		String select = "SELECT * FROM `WebAgenda`.`EMPLOYEE` WHERE ";
		String comp = "";
		
		if (searchTemplate.getEmpID() != null)
			{
			// If an employee ID is given, use only that for search.
			comp = "empID = " + searchTemplate.getEmpID();
			}
		else
			{
			// Use all other non-null fields for search if no employee ID is given.
			// Supervisor ID
			comp = comp + (searchTemplate.getSupervisorID() != null ? "supID = " + searchTemplate.getSupervisorID() : "");
			// Given Name
			comp = comp + (searchTemplate.getGivenName() != null ? (comp.equals("") ? "" : " AND ") +
					"givenName LIKE '%" + searchTemplate.getGivenName() + "%'" : "");
			// Family Name
			comp = comp + (searchTemplate.getFamilyName() != null ? (comp.equals("") ? "" : " AND ") +
					"familyName LIKE '%" + searchTemplate.getFamilyName() + "%'" : "");
			// Email
			comp = comp + (searchTemplate.getEmail() != null ? (comp.equals("") ? "" : " AND ") +
					"email LIKE '%" + searchTemplate.getEmail() + "%'" : "");
			// Username
			comp = comp + (searchTemplate.getUsername() != null ? (comp.equals("") ? "" : " AND ") +
					"username LIKE '%" + searchTemplate.getUsername() + "%'" : "");
			// Preferred Position
			comp = comp + (searchTemplate.getPrefPosition() != null ? (comp.equals("") ? "" : " AND ") +
					"prefPosition LIKE '%" + searchTemplate.getPrefPosition() + "%'" : "");
			// Preferred Location
			comp = comp + (searchTemplate.getPrefLocation() != null ? (comp.equals("") ? "" : " AND ") +
					"prefLocation LIKE '%" + searchTemplate.getPrefLocation() + "%'" : "");
			// Active State.
			comp = comp + (searchTemplate.getActive() != null ? (comp.equals("") ? "" : " AND ") +
					"active = " + searchTemplate.getActive() : "");
			}	
		
		if (comp.equals(""))
			{
			//Nothing being searched for, return null.
			return null;
			}
		
		// Add comparisons and close select statement.
		select = select + comp + ";";
		System.out.println(select);
		
		// Get DB connection, send query, and reopen connection for other users.
		// Parse returned ResultSet into array of employees.
		Employee[] foundEmployees = null;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet searchResults = stmt.executeQuery(select);
			conn.setAvailable(true);
			
			foundEmployees = parseResults(searchResults);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to complete employee search.",e);
			}
		
		// Return employees that matched search.
		return foundEmployees;
		}
	
	/**
	 * Gets the current total number of active employees stored within the system.
	 * 
	 * @return the number of active employees in the system.
	 * @throws DBException if there is an error when querying the database.
	 */
	public int getEmpCount() throws DBException, DBDownException
		{
		String count = "SELECT COUNT(*) FROM `WebAgenda`.`EMPLOYEE` WHERE active = TRUE;";
		
		// Get DB connection, send update, and reopen connection for other users.
		int numEmps = 0;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet countResult = stmt.executeQuery(count);
			conn.setAvailable(true);
			
			countResult.beforeFirst();
			countResult.next();
			numEmps = countResult.getInt(1);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to count employees", e);
			}
		
		return numEmps;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject)
	 */
	@Override
	public boolean update(Employee oldEmployee, Employee updateEmployee, Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		if (updateEmployee == null)
			throw new NullPointerException("Can not update null employee.");
		if(caller == null)
			throw new DBException("Cannot parse PermissionLevel when invoking Employee is null");
		
		PermissionLevel pl = checkPermissions(updateEmployee,caller); /// will throw exceptions if permission 'levels' are invalid (doesn't detect individual ones)
		if(!pl.getLevel_permissions().isCanManageEmployees()) {
			throw new PermissionViolationException("User is not authorized to Update an Emplyoee");
		}
		
		/*
		 * Make sure all "not null" DB fields are filled. Expand this to throw a
		 * DBAddException with the exception message saying exactly what fields
		 * are missing.
		 */
		String nullMsg = "Missing Required Fields:";
		if (updateEmployee.getEmpID() == null)
			nullMsg = nullMsg + " EmpID";
		if (updateEmployee.getGivenName() == null)
			nullMsg = nullMsg + " GivenName";
		if (updateEmployee.getFamilyName() == null)
			nullMsg = nullMsg + " FamilyName";
		if (updateEmployee.getUsername() == null)
			nullMsg = nullMsg + " Username";
		if (updateEmployee.getPassword() == null)
			nullMsg = nullMsg + " Password";
		if (updateEmployee.getLevel() < 0)
			nullMsg = nullMsg + " PermissionLevel [ < 0 ]";
		if (!nullMsg.equals("Missing Required Fields:"))
			throw new DBException(nullMsg);
		
		// Create sql update statement from employee object.
		String update = String.format(
				"UPDATE `WebAgenda`.`EMPLOYEE` SET empID = %s, supID = %s, givenName = '%s', familyName = '%s', email = %s, username = '%s', password = '%s', lastLogin = %s, prefPosition = %s, prefLocation = %s, active = %s WHERE empID = %s;",
				updateEmployee.getEmpID(),
				(updateEmployee.getSupervisorID() != null ? updateEmployee.getSupervisorID() + "" : "NULL"),
				updateEmployee.getGivenName(),
				updateEmployee.getFamilyName(),
				(updateEmployee.getEmail() != null ? "'"+updateEmployee.getEmail()+"'" : "NULL"),
				updateEmployee.getUsername(),
				updateEmployee.getPassword(),
				(updateEmployee.getLastLogin() != null ? "'"+updateEmployee.getLastLogin()+"'" : "NULL"),
				(updateEmployee.getPrefPosition() != null ? "'"+updateEmployee.getPrefPosition()+"'" : "NULL"),
				(updateEmployee.getPrefLocation() != null ? "'"+updateEmployee.getPrefLocation()+"'" : "NULL"),
				updateEmployee.getActive(),
				updateEmployee.getEmpID() + "");
		
		// Get DB connection, send update, and reopen connection for other users.
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int updateRowCount = stmt.executeUpdate(update);
			conn.setAvailable(true);
			
			if (updateRowCount != 1)
				throw new DBException(
						"Failed to update employee: rowcount incorrect.");
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to update employee", e);
			}
		
		return true;
		}
	
	/**
	 * Updates the last login time of the given employee to the time contained
	 * within the given timestamp.
	 * 
	 * @param empID The ID of the employee to update.
	 * @param time The time to update the employee to.
	 * @return True if the update was successful, otherwise false.
	 * @throws DBException If there was an error with executing the update in the
	 *            database.
	 * @throws DBDownException If the communication with the database has failed
	 *            and it is likely down.
	 */
	public boolean updateLastLoginTime(int empID, Timestamp time) throws DBException, DBDownException
		{
		String update = String.format("UPDATE `WebAgenda`.`EMPLOYEE` SET lastLogin = '%s' WHERE empID = %s",
				time.toString(),
				empID);
		
		// Get DB connection, send update, and reopen connection for other users.
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int updateRowCount = stmt.executeUpdate(update);
			conn.setAvailable(true);
			
			if (updateRowCount != 1)
				throw new DBException(
						"Failed to update employee: rowcount incorrect.");
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to update login time.", e);
			}
		
		return true;
		}
	
	/**
	 * Validates the given username and password by attempting to retrieve a
	 * matching employee from the database. If found, the employee object will be
	 * returned, otherwise an exception will be thrown.
	 * 
	 * @param username The username of the employee that is logging in.
	 * @param password The password of the employee that is logging in.
	 * @return The employee object for the employee that has logged in.
	 * @throws InvalidLoginException when the username or password does not match
	 *            a record in the database.
	 * @throws PermissionViolationException 
	 * @throws InvalidPermissionException 
	 * @throws SQLException if there was an issue with the database connection or
	 *            search query.
	 * @throws NullPointerException
	 */
	public Employee tryLogin(String username, String password)
			throws InvalidLoginException, DBException, DBDownException, PermissionViolationException
		{
		if (username == null || password == null)
			throw new InvalidLoginException(
					"Username and password must not be null.");
		
		Employee loginEmp = new Employee();
		loginEmp.setUsername(username);
		loginEmp.setPassword(password);
		loginEmp.setActive(true);
		// get() method does not check for permisison levels, so loginEmp can call it
		Employee[] results = null;
		try
			{
			results = get(loginEmp,loginEmp);
			}
		catch (InvalidPermissionException ipe) {
		ipe.printStackTrace();
		}
		
		
		if (results == null)
			throw new InvalidLoginException("Username or password invalid.");
		
		Employee loggedIn = results[0];
		
		// Update employee record in DB with new lastLogin time.
		Timestamp time = new Timestamp(System.currentTimeMillis());
		loggedIn.setLastLogin(time);
		updateLastLoginTime(loggedIn.getEmpID(), time);
		
		/*
		 * TODO Pull a full permissions object out of the DB and add it to
		 * the "loggedIn" employee object.
		 */
		
		return loggedIn;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#parseResults(java.sql.ResultSet)
	 */
	@Override
	protected Employee[] parseResults(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		Employee[] empList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			empList = new Employee[resultCount];
			
			// Return ResultSet to beginning to start retrieving employees.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Employee emp;
				
				try {
					emp = new Employee(
						rs.getInt("empID"),
						rs.getString("givenName"),
						rs.getString("familyName"),
						rs.getString("username"),
						null,
						rs.getString("plevel")
					);
				} catch (DBException e) {
					// TODO Auto-generated catch block
					throw new SQLException("Attempting to create an Employee with an Invalid Permission Level");
				}
				
				emp.setSupervisorID(rs.getInt("supID"));
				emp.setBirthDate(rs.getDate("birthDate"));
				emp.setEmail(rs.getString("email"));
				emp.setActive(rs.getBoolean("active"));
				emp.setLastLogin(rs.getTimestamp("lastLogin"));
				emp.setPrefPosition(rs.getString("prefPosition"));
				emp.setPrefLocation(rs.getString("prefLocation"));
				
				if (emp.getSupervisorID() == 0)
					emp.setSupervisorID(null);
				
				empList[i] = emp;
				}
			
			}
		
		return empList;
		}
	}
