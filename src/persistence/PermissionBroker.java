/**
 * persistence - PermissionBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exception.DBCreateException;
import exception.DBDeleteException;

import application.DBConnection;
import business.Employee;
import business.permissions.*;
import messagelog.Logging;
import business.permissions.*;

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
	public boolean delete(PermissionLevel deleteObj) throws DBDeleteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PermissionLevel[] get(PermissionLevel searchTemplate) throws SQLException {
		
		if(searchTemplate == null)
			throw new NullPointerException();
		
		// Create sql select statement from permission level object.
		String select = "SELECT * FROM `WebAgenda`.`PERMISSIONSET` WHERE ";
		String comparisons = "";
		
		if (searchTemplate.getLevel() >= 0)
		{
			// Only non-negative permission levels are valid
			comparisons = "plevel = " + searchTemplate.getLevel() + searchTemplate.getVersion();
			
				
		}
		else if(searchTemplate.getLevel() < 0) throw new SQLException("Invalid plevel Identifier value: No negative values allowed.");
		else {
			// Use other values if level is not specified
			Permissions perm = searchTemplate.getLevel_permissions();
			// Can edit schedule
			comparisons = comparisons + " canEditSched = " + perm.isCanEditSchedule();
			// Can read Schedule
			comparisons = comparisons + " AND canReadSched = " + perm.isCanReadSchedule();
			// Can read an older schedule
			comparisons = comparisons + " AND canReadOldSched = " + perm.isCanReadOldSchedule();
			// Can view resources
			comparisons = comparisons + " AND canViewResources = " + perm.isCanViewResources();
			// Can change permissions (up to current level - 1)
			comparisons = comparisons + " AND canChanagePermissions = " + perm.isCanChangePermissions();
			// Can read log files via a built in viewer (logs hosted on the server)
			comparisons = comparisons + " canReadLogs = " + perm.isCanReadLogs();
			// Can access reports
			comparisons = comparisons + " AND canAccessReports = " + perm.isCanAccessReports();
			// Max days off
			comparisons = comparisons +
				(perm.getMaxDaysOff() >= 0 ? " AND maxDaysoff = " + perm.getMaxDaysOff() : "");
			// Can Take Vacations (if applicable)
			comparisons = comparisons + " AND canTakeVacations = " + perm.isCanTakeVacations();
			// Max Vacation Days
			comparisons = comparisons + 
				(perm.getMaxVacationDays() >= 0 ? " AND maxVacationDays = " + perm.getMaxVacationDays() : "");
			// Can Take Emergency Days Off
			comparisons = comparisons + " AND canTakeEmergencyDays = " + perm.isCanTakeEmergencyDays();
			// Can View Inactive Employees
			comparisons = comparisons + " AND canViewInactiveEmps = " + perm.isCanViewInactiveEmployees();
			// Can Send Notifications
			comparisons = comparisons + " AND canSendNotifications = " + perm.isCanSendNotifications();
			// Trusted Employee
			comparisons = comparisons + " AND trusted = " + perm.getTrusted();
			
			
		}
		
		// Add comparisons and close select statement.
		select = select + comparisons + ";";
		
		DBConnection conn = this.getConnection();
		Statement stmt = conn.getConnection().createStatement();
		System.out.println(select);
		ResultSet searchResults = stmt.executeQuery(select);
		conn.setAvailable(true);
		
		PermissionLevel[] foundPermissions = parseResults(searchResults);
		
		return foundPermissions;
	}

	@Override
	public boolean update(PermissionLevel updateObj) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PermissionLevel[] parseResults(ResultSet rs) throws SQLException {
		
		// List will be returned as null if no results are found.
		PermissionLevel[] permList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			
			// Return ResultSet to beginning.
			rs.beforeFirst();
			permList = new PermissionLevel[resultCount];
			
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				PermissionLevel plevel = new PermissionLevel();
				
				Permissions p = new Permissions();
				
				
				// Set values
				
				permList[i] = plevel;
				}
			
			}
		return permList;
	}
	
}
