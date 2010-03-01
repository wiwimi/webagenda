/**
 * persistence - PermissionBroker.java
 */
package persistence;

import java.security.PermissionCollection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exception.DBException;

import application.DBConnection;
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
	private static volatile PermissionBroker broker_permissions	= null;
	
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
	public boolean create(PermissionLevel createObj) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(PermissionLevel deleteObj) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PermissionLevel[] get(PermissionLevel searchTemplate) throws SQLException {
		
		if(searchTemplate == null)
			throw new NullPointerException();
		
		// Create sql select statement from permission level object.
		String select = "SELECT * FROM `WebAgenda`.`PERMISSIONSET` ";
		String comparisons = "";
		
		if (searchTemplate.getLevel() >= 0)
		{
			// Only non-negative permission levels are valid
			comparisons = comparisons +  "WHERE plevel = '" + searchTemplate.getLevel() + searchTemplate.getVersion() + "'";	
		}
		else {
			
			// Use other values if level is not specified
			Permissions perm = searchTemplate.getLevel_permissions();
			// Check if permissions object is null: find all if that is the case.
			if(perm == null)
			{
				
			}
			else {
				// Can edit schedule
				comparisons = comparisons + "WHERE canEditSched = " + perm.isCanEditSchedule();
				// Can read Schedule
				comparisons = comparisons + " AND canReadSched = " + perm.isCanReadSchedule();
				// Can read an older schedule
				comparisons = comparisons + " AND canReadOldSched = " + perm.isCanReadOldSchedule();
				// Can view resources
				comparisons = comparisons + " AND canViewResources = " + perm.isCanViewResources();
				// Can change permissions (up to current level - 1)
				comparisons = comparisons + " AND canChanagePermissions = " + perm.isCanChangePermissions();
				// Can read log files via a built in viewer (logs hosted on the server)
				comparisons = comparisons + " AND canReadLogs = " + perm.isCanReadLogs();
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
			
		}
		
		// Add comparisons and close select statement.
		select = select + comparisons + ";";
		
		DBConnection conn = this.getConnection();
		Statement stmt = conn.getConnection().createStatement();
		System.out.println(select);
		ResultSet searchResults = stmt.executeQuery(select);
		conn.setAvailable(true);
		
		if(!searchResults.last()) {
			// Results are null
			System.out.println("No results found.");
		}
		PermissionLevel[] foundPermissions = parseResults(searchResults);
		
		return foundPermissions;
	}

	@Override
	public boolean update(PermissionLevel updateObj) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PermissionLevel[] parseResults(ResultSet rs) throws SQLException {
		
		// List will be returned as null if no results are found.
		PermissionLevel[] permList = null;
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same size
			int resultCount = rs.getRow();
			
			// Return ResultSet to beginning.
			rs.beforeFirst();
			
			// Set the size of results returned
			permList = new PermissionLevel[resultCount];
			
			PermissionLevel plevel = null;
			// Loop through results and assign them to the level object
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				// Create a fully-customizable permission object
				Permissions p = PermissionAccess.getAccess().getCustomPermission(
						rs.getBoolean("canEditSched"), rs.getBoolean("canReadSched"), rs.getBoolean("canReadOldSched"), rs.getBoolean("canViewResources"), 
						rs.getBoolean("canChangePermissions"), rs.getBoolean("canReadLogs"), rs.getBoolean("canAccessReports"), 
						rs.getBoolean("canRequestDaysOff"), rs.getInt("maxDaysOff"), rs.getBoolean("canTakeVacations"), rs.getInt("maxVacationDays"), 
						rs.getBoolean("canTakeEmergencyDays"), rs.getBoolean("canViewInactiveEmps"), rs.getBoolean("canSendNotifications"), 
						rs.getInt("trusted"));
				String str = rs.getString("plevel").trim(); // Will remove whitespace (default versions)
				int level = -1;
				char version = ' ';
				if(Character.isLetter(str.charAt(str.length() - 1))) {
					// The last character in the string is a letter (version)
					level = Integer.parseInt(str.substring(0,str.length() - 1));
					version = str.charAt(str.length() -1);
				}
				else {
					try {
						level = Integer.parseInt(str);
						// No versioning.
						
					}
					catch(NumberFormatException nfE)
					{
						nfE.printStackTrace();
					}
				}
				
				plevel = PermissionAccess.getAccess().getLevel(level, version);
				PermissionLevel new_level = PermissionAccess.getAccess().setPermissions(p, plevel);
				// Set values
				
				permList[i] = new_level;
				}
			
			}
		return permList;
	}
	
}
