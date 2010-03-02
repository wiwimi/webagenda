/**
 * persistence - EmployeeBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
	public boolean create(Employee createEmp) throws DBException
		{
		if (createEmp == null)
			throw new NullPointerException("Can not create null employee.");
		
		/*
		 * Make sure all "not null" DB fields are filled. Expand this to throw a
		 * DBAddException with the exception message saying exactly what fields
		 * are missing.
		 */
		String nullMsg = "Missing Required Fields:";
		if (createEmp.getEmployee_id() == null)
			nullMsg = nullMsg + " EmpID";
		if (createEmp.getGivenName() == null)
			nullMsg = nullMsg + " GivenName";
		if (createEmp.getFamilyName() == null)
			nullMsg = nullMsg + " FamilyName";
		if (createEmp.getUsername() == null)
			nullMsg = nullMsg + " Username";
		if (createEmp.getPassword() == null)
			nullMsg = nullMsg + " Password";
		if (createEmp.getPermission_level() == null)
			nullMsg = nullMsg + " PermissionLevel";
		if (!nullMsg.equals("Missing Required Fields:"))
			throw new DBException(nullMsg);
		
		/*
		 * Create insert string. Employee will always start with an empty
		 * lastLogin value, and true for active state.
		 */
		String insert = String
				.format(
						"INSERT INTO `WebAgenda`.`EMPLOYEE` "
								+ "(`empID`, `supervisorID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `active`)"
								+ " VALUES (%s,%s,'%s','%s',%s,%s,'%s','%s',NULL,%s,%s,'%s',true)",
						createEmp.getEmployee_id(),
						(createEmp.getSupervisor() == null ? "NULL" : createEmp
								.getSupervisor()), createEmp.getGivenName(), createEmp
								.getFamilyName(),
						(createEmp.getBirth_date() == null ? "NULL" : "'" +
								createEmp.getBirth_date() + "'"),
						(createEmp.getEmail() == null ? "NULL" : "'" +
								createEmp.getEmail() + "'"), createEmp.getUsername(),
						createEmp.getPassword(),
						(createEmp.getPreferred_position() == null ? "NULL" : "'" +
								createEmp.getPreferred_position() + "'"), (createEmp
								.getPreferred_location() == null ? "NULL" : "'" +
								createEmp.getPreferred_location() + "'"), createEmp
								.getPermission_level());
		
		/*
		 * Send insert to database. SQL errors such as primary key already in use
		 * will be caught, and turned into our own DBAddException, so this method
		 * will only have one type of exception that needs to be caught. If the
		 * insert is successful, return true.
		 */
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(insert);
			conn.setAvailable(true);
			
			if (result != 1)
				throw new DBException(
						"Failed to create employee, result count incorrect: " +
								result);
			}
		catch (SQLException e)
			{
			// TODO May need additional SQL exception processing here.
			throw new DBException("Failed to create employee.", e);
			}
		
		// TODO Inserts for employee skills as well, once that broker is up.
		
		return true;
		}
	
	/**
	 * Disables the employee in the system rather than fully deleting it.
	 * 
	 * @param disableEmp 
	 * @return
	 * @throws DBException
	 */
	public boolean disable(Employee disableEmp) throws DBException
		{
		if (disableEmp == null)
			throw new NullPointerException("Can not delete null employee.");
		
		if (disableEmp.getEmployee_id() == null)
			throw new DBException("Missing Required Field: EmpID");
		
		/*
		 * Construct new employee that will only update the active state.
		 */
		Employee newDisableEmp = new Employee();
		newDisableEmp.setEmployee_id(disableEmp.getEmployee_id());
		newDisableEmp.setActive(false);
		
		return update(newDisableEmp);
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(Employee deleteEmp) throws DBException
		{
		if (deleteEmp == null)
			throw new NullPointerException("Can not delete null employee.");
		
		if (deleteEmp.getEmployee_id() == null)
			throw new DBException("Missing Required Field: EmpID");
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`Employee` WHERE empID = %i;",
				deleteEmp.getEmployee_id());
		
		boolean success;
		try
			{
			DBConnection conn = employeeBroker.getConnection();
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
	public Employee[] get(Employee searchTemplate) throws DBException
		{
		if (searchTemplate == null)
			throw new NullPointerException(
					"Can not search with null employee template.");
		
		// Create sql select statement from employee object.
		String select = "SELECT * FROM `WebAgenda`.`EMPLOYEE` WHERE ";
		String comp = "";
		
		if (searchTemplate.getEmployee_id() != null)
			{
			// If an employee ID is given, use only that for search.
			comp = "empID = " + searchTemplate.getEmployee_id();
			}
		else
			{
			// Use all other non-null fields for search if no employee ID is given.
			// Supervisor ID
			comp = comp +
					(searchTemplate.getSupervisor() != null ? "supervisorID = " +
							searchTemplate.getSupervisor() : "");
			// Given Name
			comp = comp +
					(searchTemplate.getGivenName() != null ? (comp.equals("") ? ""
							: " AND ") +
							"givenName = '" + searchTemplate.getGivenName() + "'" : "");
			// Family Name
			comp = comp +
					(searchTemplate.getFamilyName() != null ? (comp.equals("") ? ""
							: " AND ") +
							"familyName = '" + searchTemplate.getFamilyName() + "'"
							: "");
			// Email
			comp = comp +
					(searchTemplate.getEmail() != null ? (comp.equals("") ? ""
							: " AND ") +
							"email = '" + searchTemplate.getEmail() + "'" : "");
			// Username
			comp = comp +
					(searchTemplate.getUsername() != null ? (comp.equals("") ? ""
							: " AND ") +
							"username = '" + searchTemplate.getUsername() + "'" : "");
			// Password
			comp = comp +
					(searchTemplate.getPassword() != null ? (comp.equals("") ? ""
							: " AND ") +
							"password = '" + searchTemplate.getPassword() + "'" : "");
			// Preferred Position
			comp = comp +
					(searchTemplate.getPreferred_position() != null ? (comp
							.equals("") ? "" : " AND ") +
							"prefPosition = '" +
							searchTemplate.getPreferred_position() + "'" : "");
			// Preferred Location
			comp = comp +
					(searchTemplate.getPreferred_location() != null ? (comp
							.equals("") ? "" : " AND ") +
							"prefLocation = '" +
							searchTemplate.getPreferred_location() + "'" : "");
			// Active State.
			comp = comp +
					(searchTemplate.getActive() != null ? (comp.equals("") ? ""
							: " AND ") +
							"active = " + searchTemplate.getActive() : "");
			}
		
		// Add comparisons and close select statement.
		select = select + comp + ";";
		
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
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject)
	 */
	@Override
	public boolean update(Employee updateEmployee) throws DBException
		{
		if (updateEmployee == null)
			throw new NullPointerException("Can not update null employee.");
		
		if (updateEmployee.getEmployee_id() == null)
			throw new NullPointerException(
					"Can not update employee without emp ID.");
		
		// Construct update string.
		
		// Create sql update statement from employee object.
		String update = "UPDATE `WebAgenda`.`EMPLOYEE` SET ";
		String set = "";
		
		// Use all non-null fields for update.
		// Supervisor ID
		set = set +
				(updateEmployee.getSupervisor() != null ? "supervisorID = " +
						updateEmployee.getSupervisor() : "");
		// Given Name
		set = set +
				(updateEmployee.getGivenName() != null ? (set.equals("") ? ""
						: ", ") +
						"givenName = '" + updateEmployee.getGivenName() + "'" : "");
		// Family Name
		set = set +
				(updateEmployee.getFamilyName() != null ? (set.equals("") ? ""
						: ", ") +
						"familyName = '" + updateEmployee.getFamilyName() + "'" : "");
		// Email
		set = set +
				(updateEmployee.getEmail() != null ? (set.equals("") ? "" : ", ") +
						"email = '" + updateEmployee.getEmail() + "'" : "");
		// Username
		set = set +
				(updateEmployee.getUsername() != null ? (set.equals("") ? "" : ", ") +
						"username = '" + updateEmployee.getUsername() + "'"
						: "");
		// Password
		set = set +
				(updateEmployee.getPassword() != null ? (set.equals("") ? "" : ", ") +
						"password = '" + updateEmployee.getPassword() + "'"
						: "");
		// LastLogin
		set = set +
				(updateEmployee.getLastLogin() != null ? (set.equals("") ? ""
						: ", ") +
						"lastLogin = NOW()" : "");
		// Preferred Position
		set = set +
				(updateEmployee.getPreferred_position() != null ? (set.equals("") ? ""
						: ", ") +
						"prefPosition = '" +
						updateEmployee.getPreferred_position() +
						"'"
						: "");
		// Preferred Location
		set = set +
				(updateEmployee.getPreferred_location() != null ? (set.equals("") ? ""
						: ", ") +
						"prefLocation = '" +
						updateEmployee.getPreferred_location() +
						"'"
						: "");
		// Active State.
		set = set +
				(updateEmployee.getActive() != null ? (set.equals("") ? "" : ", ") +
						"active = " + updateEmployee.getActive() : "");
		
		// TODO if set is empty, throw error as nothing is being updated.
		
		update = update + set + " WHERE empID = " +
				updateEmployee.getEmployee_id() + ";";
		System.out.println(update);
		
		// Get DB connection, send update, and reopen connection for other users.
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int updateRowCount = stmt.executeUpdate(update);
			conn.setAvailable(true);
			
			// Ensure
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
			throws InvalidLoginException, DBException
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
		Employee updateLoginTime = new Employee();
		updateLoginTime.setEmployee_id(loggedIn.getEmployee_id());
		updateLoginTime.setLastLogin(new Timestamp(System.currentTimeMillis()));
		boolean successful = update(updateLoginTime);
		if (!successful)
			throw new DBException("Failed to update login time.");
		
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
				emp.setEmployee_id(rs.getInt("empID"));
				emp.setSupervisor(rs.getInt("supervisorID"));
				emp.setGivenName(rs.getString("givenName"));
				emp.setFamilyName(rs.getString("familyName"));
				emp.setBirth_date(rs.getDate("birthDate"));
				emp.setEmail(rs.getString("email"));
				emp.setUsername(rs.getString("username"));
				emp.setLastLogin(rs.getTimestamp("lastLogin"));
				emp.setPreferred_position(rs.getString("prefPosition"));
				emp.setPreferred_location(rs.getString("prefLocation"));
				emp.setPermission_level(rs.getString("plevel"));
				emp.setActive(rs.getBoolean("active"));
				
				if (emp.getSupervisor() == 0)
					emp.setSupervisor(null);
				
				empList[i] = emp;
				}
			
			}
		
		return empList;
		}
	}
