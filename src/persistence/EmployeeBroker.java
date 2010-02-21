/**
 * persistence - EmployeeBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import utilities.DoubleLinkedList;


import application.CachableResult;
import application.dbrequests.EmployeeCall;
import business.Cachable;
import business.Employee;
import messagelog.Logging;

/**
 * @author peon-dev, Daniel Wehr
 * @version 0.2.0
 */
public class EmployeeBroker extends Broker<Employee> implements Observer
	{
	
	/** Collection of Employees to be cached in memory */
	private CacheTable				employee_cache		= null;
	/** Static representation of the broker */
	private static EmployeeBroker	broker_employees	= null;
	/** Thread created in the CacheTable object that flushes this broker's data. Not static because
	 * thread must be instantiated by each Broker for its own purpose. So don't go creating hundreds
	 * of cachebrokers if you want an efficient system. ;) */
	private Thread	flush_employee						= null; 
	/** Class that contains methods to return Sql commands (as strings) that can be inputted into a 
	 * search for Cachable objects.  */
	private EmployeeCall emp_call						= new EmployeeCall();
	
	public static final int empID						= 1;
	public static final int supervisorID				= 2;
	public static final int givenName					= 3;
	public static final int familyName					= 4;
	public static final int birthDate					= 5;
	public static final int email						= 6;
	public static final int username					= 7;
	public static final int lastLogin					= 8;
	public static final int password					= 9;
	public static final int prefPosition				= 10;
	public static final int prefLocation				= 11;
	public static final int plevel						= 12;
	public static final int active						= 13;
	/** This variable specifies the last table entry in the database that can be retrieved */
	private static final int last_table_entry			= active;
	
	/**
	 * Constructor for EmployeeBroker
	 */
	private EmployeeBroker()
		{
		
		Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY,
				"Employee Broker Cache Table initialized");
		}
	
	/**
	 * Returns an object-based Employee Broker object.
	 * 
	 * 
	 * @return EmployeeBroker result from the Broker request as its respective Broker object.
	 */
	public static EmployeeBroker getBroker()
		{
		if (broker_employees == null)
			{
			Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Employee Broker initialized");
			broker_employees = new EmployeeBroker();
			broker_employees.initCacheTable();
			}
		return broker_employees;
		}
	
	private void initCacheTable()
	{
		employee_cache = new CacheTable(broker_employees);
		flush_employee = employee_cache.getFlushThread();
		flush_employee.start(); // Start your engines
	}
	
	@Override
	public int cache(Employee cacheObj)
		{
		// TODO Auto-generated method stub
		return 0;
		}

	@Override
	public int clearCache()
		{
		// TODO Auto-generated method stub
		return 0;
		}

	@Override
	public boolean create(Employee createObj)
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public boolean delete(Employee deleteObj)
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public int flushCache()
		{
		// TODO Auto-generated method stub
		return 0;
		}

	@Override
	public Employee[] get(Employee template)
		{
		if(template.getEmployee_id() >= 0)
		{
			// Returns only one employee
			CachableResult cres = new CachableResult(template,emp_call.getAllEmployees());
		}
		else {
			// May Return multiples
		}
		return null;
		
		}

	@Override
	public boolean update(Employee updateObj)
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public void update(Observable o, Object arg) {
		
	}

	@Override
	public Cachable getCachableObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cachable[] getCachableObjects(Cachable c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Cachable[] translateResultSet(ResultSet rs) throws SQLException {
		DoubleLinkedList<Employee> dll = new DoubleLinkedList<Employee>();
		rs.next();
		Employee emp = new Employee();
		for(int i = 0; i < last_table_entry; i++)
		{
			emp.setEmployee_id(rs.getInt(empID));
			emp.setSupervisor(rs.getInt(supervisorID));
			emp.setGivenName(rs.getString(givenName));
			emp.setFamilyName(rs.getString(familyName));
			emp.setBirth_date(rs.getDate(birthDate));
			emp.setEmail(rs.getString(email));
			emp.setUsername(rs.getString(username));
			emp.setLastLogin(rs.getDate(lastLogin));
			emp.setPassword(rs.getString(password));
			emp.setPreferred_position(rs.getString(prefPosition));
			emp.setPermission_level(rs.getString(plevel));
			emp.setActive(rs.getBoolean(active));
		}
		
		//employees.next(); // Must be positioned to first (next) item before it can be read
		//int i = employees.getInt(1);
		//String str = employees.getString(3);
		//
		//System.out.println("Employee " + i + " has a first name of " + str);
		
		return null;
	}
	
	
	
}
