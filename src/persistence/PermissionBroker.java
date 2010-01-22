/**
 * persistence - PermissionBroker.java
 */
package persistence;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class PermissionBroker extends Broker {

	/** Collection of Permissions to be cached in memory	*/
	private CacheTable permission_cache									= null;
	
	private static PermissionBroker broker_permissions					= null;
	
	private PermissionBroker()
	{
		
	}
	
	public static Broker getBroker()
	{
		if(broker_permissions == null)
		{
			// Log a database
			broker_permissions = new PermissionBroker();
		}
		return broker_permissions;
	}

	@Override
	public Object getBrokerObject(Object o) {
		// TODO Auto-generated method stub
		return null;
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
	
	
	
}
