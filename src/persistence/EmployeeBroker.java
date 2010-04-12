/**
 * persistence - EmployeeBroker.java
 */
package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidLoginException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;
import application.DBConnection;
import business.Employee;
import business.permissions.PermissionLevel;

/**
 * Provides functionality for adding, removing and changing employee data in the
 * database.
 * 
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.3.0
 */
public class EmployeeBroker extends Broker<Employee>
	{
	/** Static representation of the broker. Initialized the first time called. */
	private static volatile EmployeeBroker	employeeBroker	= null;
	
	/**
	 * Constructor for EmployeeBroker, initializes the Broker Connection
	 * Monitor.
	 */
	private EmployeeBroker()
		{
		super.initConnectionThread(); // Start the connection monitor, checking
		// for old connections.
		}
	
	/**
	 * Returns an Employee Broker object.
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
		if (createEmp.getEmpID() == -1)
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
		
		//Get connection.
		DBConnection conn = this.getConnection();
		try
			{
			//Create insert statement.
			PreparedStatement insert = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`EMPLOYEE`" +
					"(`empID`,`supID`,`givenName`,`familyName`,`birthDate`,`email`," +
					"`username`,`password`,`prefPosition`,`prefLocation`,`plevel`,`pversion`) " +
					"VALUES " +
					"(?,?,?,?,?,?,?,?,?,?,?,?)");
			
			//Assign variables of new user.
			insert.setInt(1, createEmp.getEmpID());
			
			if (createEmp.getSupervisorID() == -1)
				insert.setNull(2, java.sql.Types.INTEGER);
			else
				insert.setInt(2, createEmp.getSupervisorID());
			
			insert.setString(3, createEmp.getGivenName());
			insert.setString(4, createEmp.getFamilyName());
			
			if (createEmp.getBirthDate() == null)
				insert.setNull(5, java.sql.Types.DATE);
			else
				insert.setDate(5, createEmp.getBirthDate());
			
			if (createEmp.getEmail() == null)
				insert.setNull(6, java.sql.Types.VARCHAR);
			else
				insert.setString(6, createEmp.getEmail());
			
			insert.setString(7, createEmp.getUsername());
			insert.setString(8, createEmp.getPassword());
			
			if (createEmp.getPrefPosition() == null)
				insert.setNull(9, java.sql.Types.VARCHAR);
			else
				insert.setString(9, createEmp.getPrefPosition());
			
			if (createEmp.getPrefLocation() == null)
				insert.setNull(10, java.sql.Types.VARCHAR);
			else
				insert.setString(10, createEmp.getPrefLocation());
			
			insert.setInt(11, createEmp.getLevel());
			insert.setString(12, createEmp.getVersion()+"");
			//Run procedure and get result.
			int resultNum = insert.executeUpdate();
			insert.close();
			conn.setAvailable(true);
			
			if (resultNum != 1)
				throw new DBException("Failed to create employee.");
			}
		catch (SQLException e)
			{
			conn.setAvailable(true);
			throw new DBException("Failed to create employee.", e);
			}
		
		return true;
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject, business.Employee)
	 */
	@Override
	public boolean delete(Employee deleteEmp, Employee caller) throws DBException, DBChangeException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		if (deleteEmp == null)
			throw new NullPointerException("Can not delete null employee.");
		
		if (deleteEmp.getEmpID() == -1)
			throw new DBException("Missing Required Field: EmpID");
		
		PermissionLevel pl = checkPermissions(deleteEmp,caller); /// will throw exceptions if permission 'levels' are invalid (doesn't detect individual ones)
		if(!pl.getLevel_permissions().isCanManageEmployees()) {
			throw new PermissionViolationException("User is not authorized to Delete [Disable] an Emplyoee");
		}
		
		if (!raceCheck(deleteEmp, caller))
			throw new DBChangeException("Delete failed. Target employee may have been changed by another user.");
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`EMPLOYEE` WHERE empID = %s;",
				deleteEmp.getEmpID()+"");
		
		DBConnection conn = this.getConnection();
		try
			{
			Statement stmt = conn.getConnection().createStatement();
			int resultNum = stmt.executeUpdate(delete);
			stmt.close();
			conn.setAvailable(true);
			
			if (resultNum != 1)
				throw new DBException("Failed to delete employee. No match for empID.");
			}
		catch (SQLException e)
			{
			conn.setAvailable(true);
			throw new DBException("Failed to delete employee.",e);
			}
		
		return true;
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
		catch (InvalidPermissionException ipe)	{}
		
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
		
		if (searchTemplate.getEmpID() != -1)
			{
			// If an employee ID is given, use only that for search.
			comp = "empID = " + searchTemplate.getEmpID();
			}
		else
			{
			// Use all other non-null fields for search if no employee ID is given.
			// Supervisor ID
			comp = comp + (searchTemplate.getSupervisorID() != -1 ? "supID = " + searchTemplate.getSupervisorID() : "");
			// Given Name
			comp = comp + (searchTemplate.getGivenName() != null ? (comp.equals("") ? "" : " AND ") +
					"givenName LIKE '%" + searchTemplate.getGivenName() + "%'" : "");
			// Family Name
			comp = comp + (searchTemplate.getFamilyName() != null ? (comp.equals("") ? "" : " AND ") +
					"familyName LIKE '%" + searchTemplate.getFamilyName() + "%'" : "");
			// Birthdate
			comp = comp + (searchTemplate.getBirthDate() != null ? (comp.equals("") ? "" : " AND ") +
					"birthDate <= '" + searchTemplate.getBirthDate() + "'" : "");
			// Email
			comp = comp + (searchTemplate.getEmail() != null ? (comp.equals("") ? "" : " AND ") +
					"email LIKE '%" + searchTemplate.getEmail() + "%'" : "");
			// Username
			comp = comp + (searchTemplate.getUsername() != null ? (comp.equals("") ? "" : " AND ") +
					"username LIKE '%" + searchTemplate.getUsername() + "%'" : "");
			// Password
			comp = comp + (searchTemplate.getPassword() != null ? (comp.equals("") ? "" : " AND ") +
					"password = '" + searchTemplate.getPassword() + "'" : "");
			// Preferred Position
			comp = comp + (searchTemplate.getPrefPosition() != null ? (comp.equals("") ? "" : " AND ") +
					"prefPosition LIKE '%" + searchTemplate.getPrefPosition() + "%'" : "");
			// Preferred Location
			comp = comp + (searchTemplate.getPrefLocation() != null ? (comp.equals("") ? "" : " AND ") +
					"prefLocation LIKE '%" + searchTemplate.getPrefLocation() + "%'" : "");
			// Active State.
			comp = comp + (comp.equals("") ? "" : " AND ") +
					"active = " + searchTemplate.getActive();
			}	
		
		
		// Add comparisons and close select statement.
		select = select + comp + ";";
		System.out.println(select);
		
		// Get DB connection, send query, and reopen connection for other users.
		// Parse returned ResultSet into array of employees.
		Employee[] foundEmployees = null;
		DBConnection conn = this.getConnection();
		try
			{
			Statement stmt = conn.getConnection().createStatement();
			ResultSet searchResults = stmt.executeQuery(select);
			foundEmployees = parseResults(searchResults);
			stmt.close();
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			conn.setAvailable(true);
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
		// Get DB connection, send update, and reopen connection for other users.
		int numEmps = 0;
		DBConnection conn = this.getConnection();
		try
			{
			PreparedStatement count = conn.getConnection().prepareStatement(
					"SELECT COUNT(*) FROM `WebAgenda`.`EMPLOYEE` WHERE active = TRUE;");
			
			ResultSet countResult = count.executeQuery();
			countResult.beforeFirst();
			countResult.next();
			numEmps = countResult.getInt(1);
			
			count.close();
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			conn.setAvailable(true);
			throw new DBException("Failed to count employees", e);
			}
		
		return numEmps;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject)
	 */
	@Override
	public boolean update(Employee oldEmp, Employee updateEmp, Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		if (updateEmp == null)
			throw new NullPointerException("Can not update null employee.");
		if(caller == null)
			throw new DBException("Cannot parse PermissionLevel when invoking Employee is null");
		
		PermissionLevel pl = checkPermissions(updateEmp,caller); /// will throw exceptions if permission 'levels' are invalid (doesn't detect individual ones)
		if(!pl.getLevel_permissions().isCanManageEmployees()) {
			throw new PermissionViolationException("User is not authorized to Update an Emplyoee");
		}

		if (!raceCheck(oldEmp, caller))
			throw new DBChangeException("Delete failed. Target employee may have been changed by another user.");
		
		/*
		 * Make sure all "not null" DB fields are filled. Expand this to throw a
		 * DBAddException with the exception message saying exactly what fields
		 * are missing.
		 */
		String nullMsg = "Missing Required Fields:";
		if (updateEmp.getEmpID() == -1)
			nullMsg = nullMsg + " EmpID";
		if (updateEmp.getGivenName() == null)
			nullMsg = nullMsg + " GivenName";
		if (updateEmp.getFamilyName() == null)
			nullMsg = nullMsg + " FamilyName";
		if (updateEmp.getUsername() == null)
			nullMsg = nullMsg + " Username";
		if (updateEmp.getLevel() < 0)
			nullMsg = nullMsg + " PermissionLevel [ < 0 ]";
		if (!nullMsg.equals("Missing Required Fields:"))
			throw new DBException(nullMsg);
		
		
		
		// Create sql update statement from employee object.
		String update = String.format(
				"UPDATE `WebAgenda`.`EMPLOYEE` SET empID = %s, supID = %s, givenName = '%s', familyName = '%s', email = %s, username = '%s', lastLogin = %s, prefPosition = %s, prefLocation = %s, active = %s, passChanged = %s WHERE empID = %s;",
				updateEmp.getEmpID(),
				(updateEmp.getSupervisorID() != -1 ? updateEmp.getSupervisorID() + "" : "NULL"),
				updateEmp.getGivenName(),
				updateEmp.getFamilyName(),
				(updateEmp.getEmail() != null ? "'"+updateEmp.getEmail()+"'" : "NULL"),
				updateEmp.getUsername(),
				(updateEmp.getLastLogin() != null ? "'"+updateEmp.getLastLogin()+"'" : "NULL"),
				(updateEmp.getPrefPosition() != null ? "'"+updateEmp.getPrefPosition()+"'" : "NULL"),
				(updateEmp.getPrefLocation() != null ? "'"+updateEmp.getPrefLocation()+"'" : "NULL"),
				updateEmp.getActive(),
				updateEmp.getPassChanged(),
				updateEmp.getEmpID() + "");
		
		System.out.println(update);
		
		// Get DB connection, send update, and reopen connection for other users.
		DBConnection conn = this.getConnection();
		try
			{
			Statement stmt = conn.getConnection().createStatement();
			int updateRowCount = stmt.executeUpdate(update);
			stmt.close();
			conn.setAvailable(true);
			
			if (updateRowCount != 1)
				throw new DBException(
						"Failed to update employee: rowcount incorrect.");
			}
		catch (SQLException e)
			{
			conn.setAvailable(true);
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
		DBConnection conn = this.getConnection();
		try
			{
			Statement stmt = conn.getConnection().createStatement();
			int updateRowCount = stmt.executeUpdate(update);
			stmt.close();
			conn.setAvailable(true);
			
			if (updateRowCount != 1)
				throw new DBException("Failed to update employee. EmpID invalid.");
			}
		catch (SQLException e)
			{
			conn.setAvailable(true);
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
		
		Employee loginEmp = new Employee(-1,null,null,username,password, 2, 'a');
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
	
	/**
	 * Attempts to change the password of the currently logged in user.
	 * 
	 * @param oldPass The old password, matching what is currently in the database.
	 * @param newPass The new password to change to.
	 * @param caller The user who is currently logged in.
	 * @return true if the password is changed, false otherwise.
	 * @throws DBDownException 
	 * @throws DBException 
	 */
	public boolean changePassword(String oldPass, String newPass, Employee caller) throws DBDownException, DBChangeException, DBException
		{
		if (oldPass == null)
			throw new NullPointerException("Old password can not be null.");
		if (newPass == null)
			throw new NullPointerException("New password can not be null.");
		if (caller == null)
			throw new NullPointerException("Caller can not be null.");
		
		DBConnection conn = this.getConnection();
		
		try
			{
			PreparedStatement changePass = conn.getConnection().prepareStatement(
					"UPDATE `WebAgenda`.`EMPLOYEE` SET password = ? WHERE username = ? AND password = ?");
			
			changePass.setString(1, newPass);
			changePass.setString(2, caller.getUsername());
			changePass.setString(3, oldPass);
			
			int resultNum = changePass.executeUpdate();
			changePass.close();
			conn.setAvailable(true);
			
			if (resultNum != 1)
				throw new DBChangeException("Failed to update password. Old password incorrect or changed by another user.");
			}
		catch (SQLException e)
			{
			conn.setAvailable(true);
			throw new DBException("Failed to change password.",e);
			}
		
		return true;
		}
	
	/**
	 * Resets the password of a target username to a new value, regardless of
	 * what the old password was.  This method should only be used by supervisors
	 * or admins.
	 * 
	 * @param username the user who's password will be reset.
	 * @param newPassword the new password to give to the user.
	 * @param caller The user currently logged into the system, who should have
	 * 		the necessary permissions to reset passwords.
	 * @return true if the target user's password was reset, otherwise false.
	 * @throws DBDownException if the database is currently down.
	 * @throws DBException if there was an error updating the password.
	 */
	public boolean resetPassword(String username, String newPassword, Employee caller) throws DBDownException, DBException
		{
		if (username == null)
			throw new NullPointerException("Username can not be null.");
		if (newPassword == null)
			throw new NullPointerException("Username can not be null.");
		if (caller == null)
			throw new NullPointerException("Username can not be null.");
		
		//TODO Verify that caller has permissions to reset the passwords of other users.
		
		DBConnection conn = this.getConnection();
		
		try
			{
			PreparedStatement changePass = conn.getConnection().prepareStatement(
					"UPDATE `WebAgenda`.`EMPLOYEE` SET password = ? WHERE username = ?");
			
			changePass.setString(1, newPassword);
			changePass.setString(2, username);
			
			int resultNum = changePass.executeUpdate();
			changePass.close();
			conn.setAvailable(true);
			
			if (resultNum != 1)
				throw new DBChangeException("Failed to reset password. Username invalid.");
			}
		catch (SQLException e)
			{
			conn.setAvailable(true);
			throw new DBException("Failed to change password.",e);
			}
		
		return true;
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
						rs.getInt("plevel"),
						rs.getString("pversion").charAt(0));
				} catch (DBException e) {
					throw new SQLException("Attempting to create an Employee with an Invalid Permission Level");
				}
				
				emp.setSupervisorID(rs.getInt("supID"));
				emp.setBirthDate(rs.getDate("birthDate"));
				emp.setEmail(rs.getString("email"));
				emp.setActive(rs.getBoolean("active"));
				emp.setPassChanged(rs.getBoolean("passChanged"));
				emp.setLastLogin(rs.getTimestamp("lastLogin"));
				emp.setPrefPosition(rs.getString("prefPosition"));
				emp.setPrefLocation(rs.getString("prefLocation"));
				
				if (emp.getSupervisorID() == 0)
					emp.setSupervisorID(-1);
				
				empList[i] = emp;
				}
			
			}
		
		return empList;
		}
	
	/**
	 * Returns an array of Employee objects by inputting a parameter ResultSet
	 * that will use this Broker's built in parsing method to get the data.
	 * 
	 * @param rs ResultSet
	 * @return Employee[] array
	 * @throws SQLException
	 */
	public static Employee[] parseResultsStatic(ResultSet rs) throws SQLException
		{
		EmployeeBroker eb = EmployeeBroker.getBroker();
		return eb.parseResults(rs);
		}
	
	/**
	 * Checks if an employee object is an exact match with an employee record
	 * in the database.
	 * 
	 * @param oldEmp The employee object to compare to the database.
	 * @param caller The Employee who is currently logged into the system.
	 * @return true if the given employee object matches a database record, false otherwise.
	 * @throws PermissionViolationException 
	 * @throws InvalidPermissionException 
	 * @throws DBDownException if the database is own and the race check can not be done. 
	 * @throws DBException if an sql statement failed to execute in the database. 
	 */
	private boolean raceCheck(Employee oldEmp, Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		//Race check fails if null parameters are used.
		if (oldEmp == null || caller == null)
			return false;
		
		//Get matching record from DB.
		Employee[] results = get(oldEmp, caller); 
		
		//No employee matching employee ID returned, race check fails.
		if (results == null)
			return false;
		
		//Extract employee.
		Employee dbEmp = results[0];
		
		return dbEmp.equals(oldEmp);
		}
	}
