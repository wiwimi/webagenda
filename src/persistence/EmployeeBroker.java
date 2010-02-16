/**
 * persistence - EmployeeBroker.java
 */
package persistence;


import business.Employee;
import messagelog.Logging;

/**
 * @author peon-dev, Daniel Wehr
 * @version 0.2.0
 */
public class EmployeeBroker extends Broker<Employee>
	{
	
	/** Collection of Employees to be cached in memory */
	private CacheTable				employee_cache		= null;
	
	private static EmployeeBroker	broker_employees	= null;
	
	private EmployeeBroker()
		{
		employee_cache = new CacheTable();
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
			}
		return broker_employees;
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
	
	}
