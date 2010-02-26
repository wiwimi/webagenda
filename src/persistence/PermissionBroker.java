/**
 * persistence - PermissionBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import exception.DBCreateException;

import business.permissions.*;
import messagelog.Logging;

//TODO: PermissionBroker needs access to permission tables and permission level tables

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class PermissionBroker extends Broker<PermissionLevel> {

	/** Permission Broker that is returned when the getBroker() method is called.
	 * Only one instance of this Broker can exist at one time. */
	private static volatile PermissionBroker broker_permissions					= null;
	
	/**
	 * PermissionsBroker constructor 
	 */
	private PermissionBroker()
	{
		
	}
	
	/**
	 * Returns the only instance of PermissionBroker that exists to return read-only copies
	 * of permission and permission level objects.<br>
	 * <br>
	 * Actual setting of permissions require a Validation object that is determined 
	 * to be acceptable or not (thrown exception if invalid) to actually set permissions.
	 * It also applies to getting permission levels, but even if permissions are returned
	 * as read-only, it's not so much a security risk.
	 * 
	 * @return PermissionBroker broker for permissions
	 */
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

	@Override
	public boolean create(PermissionLevel createObj) throws DBCreateException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(PermissionLevel deleteObj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PermissionLevel[] get(PermissionLevel getObj) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(PermissionLevel updateObj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PermissionLevel[] parseResults(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
