/**
 * persistence - EmployeeBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exception.InvalidLoginException;
import application.DBConnection;
import business.Employee;
import messagelog.Logging;

/**
 * @author peon-dev, Daniel Wehr
 * @version 0.2.0
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
		super.initConnectionThread(); // Start the connection monitor, checking for old connections. 
		// Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY,
		// "Employee Broker Initialized");
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
			// Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY,
			// "Employee Broker initialized");
			}
		return employeeBroker;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#create(business.BusinessObject)
	 */
	@Override
	public boolean create(Employee createObj)
		{
		// TODO Auto-generated method stub
		return false;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(Employee deleteObj)
		{
		// TODO Auto-generated method stub
		return false;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject)
	 */
	@Override
	public Employee[] get(Employee searchTemplate) throws SQLException
		{
		// Create sql select statement from employee object.
		String select = "SELECT * FROM `WebAgenda`.`EMPLOYEE` WHERE ";
		String comparisons = "";
		
		/*
		 * NOTE: Depending on how the sql statement is parsed, new lines may need
		 * to be included to prevent errors.
		 */

		if (searchTemplate.getEmployee_id() != null)
			{
			// If an employee ID is given, use only that for search.
			comparisons = "empID = " + searchTemplate.getEmployee_id();
			}
		else
			{
			// Use all other non-null fields for search if no employee ID is given.
			// Supervisor ID
			comparisons = comparisons +
					(searchTemplate.getSupervisor() != null ? "supervisorID = " + searchTemplate.getSupervisor() : "");
			// Given Name
			comparisons = comparisons +
					(searchTemplate.getGivenName() != null ? (comparisons.equals("") ? "" : " AND ") + "givenName = '" +
							searchTemplate.getGivenName()+"'" : "");
			// Family Name
			comparisons = comparisons +
					(searchTemplate.getFamilyName() != null ? (comparisons.equals("") ? "" : " AND ") + "familyName = '" +
							searchTemplate.getFamilyName()+"'" : "");
			// Email
			comparisons = comparisons +
					(searchTemplate.getEmail() != null ? (comparisons.equals("") ? "" : " AND ") + "email = '" +
							searchTemplate.getEmail()+"'" : "");
			// Username
			comparisons = comparisons +
					(searchTemplate.getUsername() != null ? (comparisons.equals("") ? "" : " AND ") + "username = '" +
							searchTemplate.getUsername()+"'" : "");
			// Username
			comparisons = comparisons +
					(searchTemplate.getPassword() != null ? (comparisons.equals("") ? "" : " AND ") + "password = '" +
							searchTemplate.getPassword()+"'" : "");
			// Preferred Position
			comparisons = comparisons +
					(searchTemplate.getPreferred_position() != null ? (comparisons.equals("") ? "" : " AND ") +
							"prefPosition = '" + searchTemplate.getPreferred_position()+"'" : "");
			// Preferred Location
			comparisons = comparisons +
					(searchTemplate.getPreferred_location() != null ? (comparisons.equals("") ? "" : " AND ") +
							"prefLocation = '" + searchTemplate.getPreferred_location()+"'" : "");
			// Active State. FIXME may need to send 0/1 bit instead of
			// "false"/"true".
			comparisons = comparisons +
					(searchTemplate.getActive() != null ? (comparisons.equals("") ? "" : " AND ") + "active = " +
							searchTemplate.getActive() : "");
			}
		
		// Add comparisons and close select statement.
		select = select + comparisons + ";";
		System.out.println(select);
		
		// Get open DB connection, send query, and reopen connection for other
		// users.
		DBConnection conn = this.getConnection();
		Statement stmt = conn.getConnection().createStatement();
		System.out.println(select);
		ResultSet searchResults = stmt.executeQuery(select);
		conn.setAvailable(true);
		
		// Parse returned ResultSet into array of employees.
		Employee[] foundEmployees = parseResults(searchResults);
		
		// Return employees that matched search.
		return foundEmployees;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject)
	 */
	@Override
	public boolean update(Employee updateObj)
		{
		// TODO Auto-generated method stub
		return false;
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
	 * @throws SQLException if there was an issue with the database connection or search query.
	 */
	public Employee tryLogin(String username, String password) throws InvalidLoginException, SQLException
		{
		Employee loginEmp = new Employee();
		loginEmp.setUsername(username);
		loginEmp.setPassword(password);
		Employee[] results = get(loginEmp);
		
		if (results == null)
			throw new InvalidLoginException("Username or password invalid.");
		
		if (results.length > 1)
			throw new InvalidLoginException("Duplicate users found.");
		
		return results[0];
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
			
			// Return ResultSet to beginning.
			rs.beforeFirst();
			empList = new Employee[resultCount];
			
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
				emp.setLastLogin(rs.getDate("lastLogin"));
				emp.setPreferred_position(rs.getString("prefPosition"));
				emp.setPermission_level(rs.getString("plevel"));
				emp.setActive(rs.getBoolean("active"));
				empList[i] = emp;
				}
			
			}
		
		return empList;
		}
	}
