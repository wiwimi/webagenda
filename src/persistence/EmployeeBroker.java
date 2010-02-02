/**
 * persistence - EmployeeBroker.java
 */
package persistence;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class EmployeeBroker extends Broker {

	/** Collection of Employees to be cached in memory	*/
	private CacheTable employee_cache 								= null;

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
