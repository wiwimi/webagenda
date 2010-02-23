/**
 * persistence - PermissionBroker.java
 */
package persistence;

import messagelog.Logging;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class PermissionBroker {

	
	private static PermissionBroker broker_permissions					= null;
	
	private PermissionBroker()
	{
		
	}
	
	public static PermissionBroker getBroker()
	{
		if(broker_permissions == null)
		{
			// Log a database
			broker_permissions = new PermissionBroker();
			Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Permission Broker initialized");
		}
		return broker_permissions;
	}
	
}
