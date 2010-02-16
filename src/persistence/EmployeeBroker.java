/**
 * persistence - EmployeeBroker.java
 */
package persistence;

import messagelog.Logging;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class EmployeeBroker extends Broker {

	/** Collection of Employees to be cached in memory	*/
	private CacheTable employee_cache 								= null;

	private static EmployeeBroker broker_employees					= null;
	
	private EmployeeBroker()
	{
		employee_cache = new CacheTable();
		Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Employee Broker Cache Table initialized");
		
	}
	
	public static EmployeeBroker getBroker()
	{
		if(broker_employees == null) {
			Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Employee Broker initialized");
			broker_employees = new EmployeeBroker();
		}
		return broker_employees;
	}
	
	@Override
	public int cache(Cachable cacheObj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int clearCache() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int flushCache() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getBrokerObject(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
