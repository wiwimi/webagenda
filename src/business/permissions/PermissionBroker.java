/**
 * business.permissions - PermissionBroker.java
 */
package business.permissions;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;

import business.Employee;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;

import application.DBConnection;
import persistence.Broker;
import messagelog.Logging;

//TODO: PermissionBroker needs to ensure that employee has a permission set capable of ensuring actions are
// backed up by permission level. get() method may need to ignore the employee parameter, only checking if null to allow this.

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
	
	/**
	 * Method that contains all throwable conditions when attempting to access broker methods of PermissionBroker.
	 * 
	 * 
	 * @param plevel PermissionLevel of desired create, update or delete.
	 * @param caller Employee that invokes the method
	 * 
	 * @throws InvalidPermissionException
	 * @throws DBException
	 * @throws DBDownException
	 */
	private PermissionLevel checkPermission(PermissionLevel plevel, Employee caller) throws InvalidPermissionException, DBException, DBDownException
	{
		PermissionLevel pl = null;
		if(caller.getLevel() < plevel.getLevel()) {
			// Do not allow creation access
			throw new InvalidPermissionException("User cannot create Permission Levels.");
		}
		else if(caller.getLevel() == plevel.getLevel()) {
			pl = get(PermissionAccess.getAccess().getLevel(caller.getLevel(), caller.getVersion()),caller)[0];
			if(pl == null)
				throw new InvalidPermissionException("No matches for caller's Permission Level found");
			if(pl.getLevel_permissions().getTrusted() <= plevel.getLevel()) {
				throw new InvalidPermissionException("User is not trusted to the level required to perform this action");
			}
			else {
				// Trusted to create this Permission
				// TODO: Log this method's results, allow user to continue
			}
		}
		return pl;
	}

	/**
	 * Public method that allows other Brokers to determine what the trusted level of a specified employee
	 * is. 
	 * @param caller
	 * @return int trusted level
	 * @throws DBException
	 * @throws DBDownException
	 */
	public int trustedLevelOf(Employee caller) throws DBException, DBDownException
	{
		return get(PermissionAccess.getAccess().getLevel(caller.getLevel(), caller.getVersion()),caller)[0].getLevel_permissions().getTrusted();
	}
	
	@Override
	public boolean create(PermissionLevel createObj, Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException {
		if (createObj == null)
			throw new NullPointerException("Can not create null permission level.");
		
		if(caller == null)
			throw new DBException("Cannot parse PermissionLevel when invoking Employee is null");
		
		/*
		 * Make sure all "not null" DB fields are filled. Expand this to throw a
		 * DBAddException with the exception message saying exactly what fields
		 * are missing.
		 */
		String nullMsg = "Missing Required Fields:";
		if (createObj.getLevel_permissions() == null)
			nullMsg = nullMsg + " Permissions Object,";
		if (createObj.getLevel() < 0)
			nullMsg = nullMsg + " Valid ID,";
		if ((!Character.isLetter(createObj.getVersion())) && (createObj.getVersion() != ' '))
			nullMsg = nullMsg + " Valid Version,";
		if (!nullMsg.equals("Missing Required Fields:"))
			throw new DBException(nullMsg);
		
		PermissionLevel pl = checkPermission(createObj, caller); // This will throw any exceptions due to invalid permission access
		if(pl.getLevel_permissions().isCanChangePermissions())
			throw new PermissionViolationException("User is not authorized to Create a PermissionLevel");
		
		/*
		 * Create insert string. 
		 */
		String insert = "INSERT INTO `WebAgenda`.`PERMISSIONSET` "
								+ "(`plevel`, `canEditSched`, `canReadSched`, `canReadOldSched`, `canManageEmployee`, `canViewResources`, `canChangePermissions`, " +
										"`canReadLogs`, `canAccessReports`, `canRequestDaysOff`, `maxDaysOff`, `canTakeVacations`, `maxVacationDays`, " +
										"`canTakeEmergencyDays`, `canViewInactiveEmps`,`canSendNotifications`,`trusted`)"
								+ " VALUES ('" + createObj.getLevel() + createObj.getVersion()  + "', " + createObj.getLevel_permissions().isCanEditSchedule() + ", " +
								createObj.getLevel_permissions().isCanReadSchedule() + ", " + createObj.getLevel_permissions().isCanReadOldSchedule() + ", " +
								createObj.getLevel_permissions().isCanManageEmployees() + ", " + 
								createObj.getLevel_permissions().isCanViewResources() + ", " + createObj.getLevel_permissions().isCanChangePermissions() + ", " + 
								createObj.getLevel_permissions().isCanReadLogs() + ", " + createObj.getLevel_permissions().isCanAccessReports() + ", " + 
								createObj.getLevel_permissions().isCanRequestDaysOff() + ", " + createObj.getLevel_permissions().getMaxDaysOff() + ", " + 
								createObj.getLevel_permissions().isCanTakeVacations() + ", " + createObj.getLevel_permissions().getMaxVacationDays() + ", " + 
								createObj.getLevel_permissions().isCanTakeEmergencyDays() + ", " + createObj.getLevel_permissions().isCanViewInactiveEmployees() + ", " +
								createObj.getLevel_permissions().isCanSendNotifications() + ", " + createObj.getLevel_permissions().getTrusted() + ")";
						System.out.println(insert);
		
		/*
		 * Send insert to database. SQL errors such as primary key already in use
		 * will be caught, and turned into our own DBAddException, so this method
		 * will only have one type of exception that needs to be caught. If the
		 * insert is successful, return true.
		 */
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(insert);
			conn.setAvailable(true);
			
			if (result != 1)
				throw new DBException(
						"Failed to create employee, result count incorrect: " +
								result);
			}
		catch (SQLException e)
			{
			// TODO May need additional SQL exception processing here.
			throw new DBException("Failed to create PermissionLevel ", e);
			}
		
		return true;
	}

	/*
	 * By default, this method will not delete the admin permission level (100).
	 * 
	 * (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(PermissionLevel deleteObj, Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException {
		if(deleteObj == null)
			throw new NullPointerException();
		if(caller == null) 
			throw new DBException("Cannot parse PermissionLevel when invoking Employee is null");
		 
		PermissionLevel pl = checkPermission(deleteObj, caller); // This will throw any exceptions due to invalid permission access
		if(pl.getLevel_permissions().isCanChangePermissions())
			throw new PermissionViolationException("User is not authorized to Delete a PermissionLevel");
		
		boolean success = false;
		String delete = "DELETE FROM `WebAgenda`.`PERMISSIONSET` WHERE plevel = " + deleteObj.getLevel() + deleteObj.getVersion() +";";
		try {
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(delete);
			conn.setAvailable(true);
			if (result != 1)
				throw new DBException(
						"Failed to delete permission level " + deleteObj.getLevel() + deleteObj.getVersion() + ": " + 
								result);
			success = true;
		}
		catch (SQLException e)
		{
		// TODO May need additional SQL exception processing here.
		throw new DBException("Failed to delete permission level.", e);
		}
		
		return success;
	}

	@Override
	public PermissionLevel[] get(PermissionLevel searchTemplate, Employee caller) throws DBException, DBDownException {
		
		if(searchTemplate == null)
			throw new NullPointerException();
		if(caller == null) 
			throw new DBException("Cannot parse PermissionLevel when invoking Employee is null");
		// get() requires user to get their own level for other methods.
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
				// Can set employee attributes
				comparisons = comparisons + " AND canManageEmployee = " + perm.isCanManageEmployees();
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
		
		PermissionLevel[] foundPermissions = null;
		try {
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			System.out.println(select);
			ResultSet searchResults = stmt.executeQuery(select);
			conn.setAvailable(true);
			
			if(!searchResults.last()) {
				// Results are null
				System.out.println("No results found.");
			}
			foundPermissions = parseResults(searchResults);
		}
		catch (SQLException e) {
			throw new DBException("Failed to search for permission level.",e);
		}
		
		return foundPermissions;
	}

	/**
	 * Method that finds all permission levels where the level is below the searchTemplate's level.
	 * 
	 * @param int level 
	 * @return PermissionLevel[] Levels below parameter
	 * @throws DBException 
	 */
	public PermissionLevel[] getAllBelow(int level) throws DBException, DBDownException {
		
		if(level <= 0) throw new DBException("Unable to search for Permission Levels with a negative value");
		// Create sql select statement from permission level object.
		String select = "SELECT * FROM `WebAgenda`.`PERMISSIONSET` WHERE plevel='" + (level - 1 + ' ') + "' ORDER BY plevel;";
		
		PermissionLevel[] foundPermissions = null;
		try {
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			System.out.println(select);
			ResultSet searchResults = stmt.executeQuery(select);
			conn.setAvailable(true);
			
			if(!searchResults.last()) {
				// Results are null
				System.out.println("No results found.");
			}
			foundPermissions = parseResults(searchResults);
		}
		catch (SQLException e) {
			throw new DBException("Failed to search for permission level.",e);
		}
		
		
		
		return foundPermissions;
	}
	
	/**
	 * An alterate get method that returns get() results using level and version as opposed to a
	 * permission level object. This is mainly for convenience, as methods that return PermissionLevels
	 * are protected to prevent access.
	 * 
	 * @param level int level
	 * @param version char version (letter or space only)
	 * @param caller
	 * @return results of get() method using level and version attributes
	 * @throws DBException
	 * @throws DBDownException
	 */
	public PermissionLevel[] get(int level, char version, Employee caller) throws DBException, DBDownException
	{
		return get(PermissionAccess.getAccess().getLevel(level, version),caller);
	}
	
	/*
	 * In the very nature of this method call, the primary key or PermissionLevel value itself cannot be changed
	 * once created. It must be deleted and re-created. The front-end can (aka should) do this automatically.
	 * (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject)
	 */
	@Override
	public boolean update(PermissionLevel oldObj, PermissionLevel updateObj, Employee caller) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException {
		if(updateObj == null)
			throw new NullPointerException();
		if(caller == null)
			throw new DBException("Cannot parse PermissionLevel when invoking Employee is null");
		
		PermissionLevel pl = checkPermission(updateObj, caller); // This will throw any exceptions due to invalid permission access
		
		if(pl.getLevel_permissions().isCanChangePermissions())
			throw new PermissionViolationException("User is not authorized to Delete a PermissionLevel");
		
		boolean success = false;
		String update = "UPDATE `WebAgenda`.`PERMISSIONSET` ";
		String set = "SET "; // This string is modified to contain fields being set and their changed values
		String where = " WHERE plevel = " + updateObj.getLevel() + updateObj.getVersion();
		
		Permissions p = updateObj.getLevel_permissions();
		
		set += "plevel = " + updateObj.getLevel() + updateObj.getVersion();
		set += ", canEditSched = " + p.isCanEditSchedule();
		set += ", canReadSched = " + p.isCanReadSchedule();
		set += ", canReadOldSched = " + p.isCanReadOldSchedule();
		set += ", canManageEmployee = " + p.isCanManageEmployees();
		set += ", canViewResources = " + p.isCanViewResources();
		set += ", canChangePermissions = " + p.isCanChangePermissions();
		set += ", canReadLogs = " + p.isCanReadLogs();
		set += ", canAccessReports = " + p.isCanAccessReports();
		set += ", canRequestDaysOff = " + p.isCanRequestDaysOff();
		set += ", maxDaysOff = " + p.getMaxDaysOff();
		set += ", canTakeVacations = " + p.isCanTakeVacations();
		set += ", maxVacationDays = " + p.getMaxVacationDays();
		set += ", canTakeEmergencyDays = " + p.isCanTakeEmergencyDays();
		set += ", canViewInactiveEmps = " + p.isCanViewInactiveEmployees();
		set += ", canSendNotifications = " + p.isCanSendNotifications();
		set += ", trusted = " + p.getTrusted();
		
		// Update all values in SET string
		
		update = update + set + where;
		
		try {
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			System.out.println(update);
			int result = stmt.executeUpdate(update);
			conn.setAvailable(true);
			
			if (result != 1)
				throw new DBException(
						"Failed to update permission level " + updateObj.getLevel() + updateObj.getVersion() + ": " + 
								result);
			success = true;
		}
		catch (SQLException e)
		{
		// TODO May need additional SQL exception processing here.
		throw new DBException("Failed to update permission level.", e);
		}
		
		return success;
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
						rs.getBoolean("canEditSched"), rs.getBoolean("canReadSched"), rs.getBoolean("canReadOldSched"), 
						rs.getBoolean("canManageEmployee"), rs.getBoolean("canViewResources"), 
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
				PermissionLevel new_level = null;
				try {
					// Set values
					new_level = PermissionAccess.getAccess().setPermissions(p, plevel);
					permList[i] = new_level;
				} catch (InvalidPermissionException e) {
					// By having nothing in catch, PermissionLevel that cannot be set (as it is not null) will be ignored.
					// This should not affect the method in a bad way.
				}
				}
			
			}
		return permList;
	}
	
	
}
