/**
 * persistence - EmployeeBroker.java
 */
package persistence;


import java.awt.HeadlessException;

import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import application.ConnectionManager;
import application.SingularThreadControlException;
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
	 * TODO:
	 * @param getParam Object to initiate the request: May be an Integer (id), Sort request as
	 * a string, or other parameters to be defined in the method itself.
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
	public Employee[] get(Employee getObj)
		{
		// TODO Auto-generated method stub
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
	
	
	
}
