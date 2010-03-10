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
import application.DBConnection;
import business.Employee;

/**
 * @author peon-dev, Daniel Wehr
 * @version 0.3.0
 */
public class EmployeeBroker extends Broker<Employee>
	{
	/** Static representation of the broker */
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
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#create(business.BusinessObject)
	 */
	@Override
	public boolean create(Employee createEmp) throws DBException, DBDownException
		{
		if (createEmp == null)
			throw new NullPointerException("Can not create null employee.");
		
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
		if (createEmp.getPLevel() == null)
			nullMsg = nullMsg + " PermissionLevel";
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
			
			cs.setString(11, createEmp.getPLevel());
			
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
	
	/**
	 * Disables the employee in the system rather than fully deleting it.
	 * 
	 * @param disableEmp 
	 * @return
	 * @throws DBException
	 */
	public boolean disable(Employee disableEmp) throws DBException, DBDownException
		{
		if (disableEmp == null)
			throw new NullPointerException("Can not disable null employee.");
		
		//Set emp to disabled and pass to update method.
		disableEmp.setActive(false);
		
		return update(disableEmp);
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(Employee deleteEmp) throws DBException, DBDownException
		{
		if (deleteEmp == null)
			throw new NullPointerException("Can not delete null employee.");
		
		if (deleteEmp.getEmpID() == null)
			throw new DBException("Missing Required Field: EmpID");
		
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
	public Employee[] get(Employee searchTemplate) throws DBException, DBDownException
		{
		if (searchTemplate == null)
			throw new NullPointerException(
					"Can not search with null employee template.");
		
		// Create sql select statement from employee object.
		String select = "SELECT emp.*,sup.empID AS 'supID' FROM `WebAgenda`.`EMPLOYEE` emp LEFT JOIN `WebAgenda`.`EMPLOYEE` sup ON emp.supRecordID = sup.empRecordID WHERE ";
		String comp = "";
		
		if (searchTemplate.getEmpID() != null)
			{
			// If an employee ID is given, use only that for search.
			comp = "emp.empID = " + searchTemplate.getEmpID();
			}
		else
			{
			// Use all other non-null fields for search if no employee ID is given.
			// Supervisor ID
			comp = comp + (searchTemplate.getSupervisorID() != null ? "sup.empID = " + searchTemplate.getSupervisorID() : "");
			// Given Name
			comp = comp + (searchTemplate.getGivenName() != null ? (comp.equals("") ? "" : " AND ") +
					"emp.givenName LIKE '" + searchTemplate.getGivenName() + "%'" : "");
			// Family Name
			comp = comp + (searchTemplate.getFamilyName() != null ? (comp.equals("") ? "" : " AND ") +
					"emp.familyName LIKE '" + searchTemplate.getFamilyName() + "%'" : "");
			// Email
			comp = comp + (searchTemplate.getEmail() != null ? (comp.equals("") ? "" : " AND ") +
					"emp.email LIKE '" + searchTemplate.getEmail() + "%'" : "");
			// Username
			comp = comp + (searchTemplate.getUsername() != null ? (comp.equals("") ? "" : " AND ") +
					"emp.username = '" + searchTemplate.getUsername() + "'" : "");
			// Password
			comp = comp + (searchTemplate.getPassword() != null ? (comp.equals("") ? "" : " AND ") +
					"emp.password = '" + searchTemplate.getPassword() + "'" : "");
			// Preferred Position
			comp = comp + (searchTemplate.getPrefPosition() != null ? (comp.equals("") ? "" : " AND ") +
					"emp.prefPosition LIKE '" + searchTemplate.getPrefPosition() + "%'" : "");
			// Preferred Location
			comp = comp + (searchTemplate.getPrefLocation() != null ? (comp.equals("") ? "" : " AND ") +
					"emp.prefLocation LIKE '" + searchTemplate.getPrefLocation() + "%'" : "");
			// Active State.
			comp = comp + (searchTemplate.getActive() != null ? (comp.equals("") ? "" : " AND ") +
					"emp.active = " + searchTemplate.getActive() : "");
			}
		
		if (comp.equals(""))
			{
			//Nothing being searched for, return array with a single empty employee.
			Employee[] empArr = new Employee[1];
			empArr[0] = new Employee();
			return empArr;
			}
		
		// Add comparisons and close select statement.
		select = select + comp + ";";
		System.out.println(select);
		
		// Get DB connection, send query, and reopen connection for other users.
		// Parse returned ResultSet into array of employees.
		Employee[] foundEmployees;
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
	public boolean update(Employee updateEmployee) throws DBException, DBDownException
		{
		if (updateEmployee == null)
			throw new NullPointerException("Can not update null employee.");
		
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
		if (updateEmployee.getPLevel() == null)
			nullMsg = nullMsg + " PermissionLevel";
		if (!nullMsg.equals("Missing Required Fields:"))
			throw new DBException(nullMsg);
		
		// Create sql update statement from employee object.
		String update = String.format(
				"UPDATE `WebAgenda`.`EMPLOYEE` SET supRecordID = %s, givenName = '%s', familyName = '%s', email = %s, username = '%s', password = '%s', lastLogin = %s, prefPosition = %s, prefLocation = %s, active = %s WHERE empID = %s;",
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
	 * @throws SQLException if there was an issue with the database connection or
	 *            search query.
	 * @throws NullPointerException
	 */
	public Employee tryLogin(String username, String password)
			throws InvalidLoginException, DBException, DBDownException
		{
		if (username == null || password == null)
			throw new InvalidLoginException(
					"Username and password must not be null.");
		
		Employee loginEmp = new Employee();
		loginEmp.setUsername(username);
		loginEmp.setPassword(password);
		loginEmp.setActive(true);
		Employee[] results = get(loginEmp);
		
		if (results == null)
			throw new InvalidLoginException("Username or password invalid.");
		
		Employee loggedIn = results[0];
		
		// Update employee record in DB with new lastLogin time.
		Timestamp time = new Timestamp(System.currentTimeMillis());
		loggedIn.setLastLogin(time);
		updateLastLoginTime(loggedIn.getEmpID(), time);
		
		// TODO Pull full permissions object into this employee as well.
		
		return loggedIn;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#parseResults(java.sql.ResultSet)
	 */
	@Override
	public Employee[] parseResults(ResultSet rs) throws SQLException
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
				Employee emp = new Employee();
				emp.setEmpID(rs.getInt("empID"));
				emp.setSupervisorID(rs.getInt("supID"));
				emp.setGivenName(rs.getString("givenName"));
				emp.setFamilyName(rs.getString("familyName"));
				emp.setBirthDate(rs.getDate("birthDate"));
				emp.setEmail(rs.getString("email"));
				emp.setUsername(rs.getString("username"));
				emp.setLastLogin(rs.getTimestamp("lastLogin"));
				emp.setPrefPosition(rs.getString("prefPosition"));
				emp.setPrefLocation(rs.getString("prefLocation"));
				emp.setPLevel(rs.getString("plevel"));
				emp.setActive(rs.getBoolean("active"));
				
				if (emp.getSupervisorID() == 0)
					emp.setSupervisorID(null);
				
				empList[i] = emp;
				}
			
			}
		
		return empList;
		}
	}
