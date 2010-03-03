/**
 * business.permissions - PermissionAccess.java
 */
package business.permissions;

import persistence.PermissionBroker;
import business.Employee;
import messagelog.Logging;
import exception.DBException;
import exception.InvalidPermissionException;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class PermissionAccess {

	private static PermissionAccess p_access = null;
	
	
	private PermissionAccess()
	{
		
	}
	
	/**
	 * Using a singleton pattern to return an access object that restricts the saving and 
	 * application of Permissions to users.
	 * Most setting of permissions requires in itself a permission object. (one should always
	 * be included in the database when application is run)
	 * 
	 * @return PermissionAccecss object
	 */
	public static PermissionAccess getAccess()
	{	
		if(p_access == null)
			p_access = new PermissionAccess();
		return p_access;
	}
	
	/**
	 * Method to edit a permission level. 
	 * 
	 * @param to_change PermissionLevel that is to be changed.
	 * @param user_permissions PermissionLevel of user attempting to change permissions
	 * @return PermissionLevel changed level
	 */
	public PermissionLevel editPermissionLevel
		(PermissionLevel to_change, PermissionLevel user_permissions) throws InvalidPermissionException
	{
		// Make sure that user changing the permissions is at least higher
		if(user_permissions.getLevel() < to_change.getLevel()) {
			System.out.println("User is above specified level and can perform action");
		}
		// Make sure that user, if the same level as the changing permission, is trusted to that level + 1
		else if((user_permissions.getLevel() == to_change.getLevel()) && (user_permissions.getLevel_permissions().getTrusted() > to_change.getLevel())) {
			System.out.println("User is trusted and can perform action");
		}
		// If user just doesn't have proper permissions to change. This will log an error to permissions.log file.
		else {
			Logging.writeToLog(Logging.PERM_LOG, Logging.ERR_ENTRY, "User with permission level " + 
					user_permissions.getLevel() + " tried to modify permission level " + to_change.getLevel());
			System.out.println("user cannot perform action");
		}
		return null;
	}
	
	/**
	 * Permissions level default 0 is returned
	 * 
	 * @return
	 */
	public Permissions getRootLevel()
	{
		return new Permissions();
	}
	
	/**
	 * Returns the lowest-level default permission level to the user
	 * @return PermissionLevel default
	 */
	public PermissionLevel getDefault()
	{	
		return PermissionLevel.getDefault();
	}
	
	/**
	 * Returns a PermissionLevel object with a null Permissions object inside, generally for searching via
	 * PermissionBroker or setting up new permission levels.
	 * 
	 * @param level int level
	 * @param version char version 
	 * @return PermissionLevel with level and version.
	 */
	public PermissionLevel getLevel(int level, char version)
	{
		PermissionLevel p_level = getDefault();
		p_level.setLevel(level);
		p_level.setVersion(version);
		p_level.invalidatePermission(); // Sets permission object to null
		return p_level;
	}
	
	/**
	 * Will return a copy of the permissions level if and only if the Permissions is null.
	 * Does not modify the original copy.
	 * 
	 * @param p Permissions to set the PermissionLevel to.
	 * @param pl PermissionLevel that is to be modified
	 * @return PermissionLevel with permissions in it.
	 */
	public PermissionLevel setPermissions(Permissions p,PermissionLevel pl)
	{
		// set to blank permissions level with parameter level's level and version
		PermissionLevel new_pl = null; 
		// Set permissions if the input permission level is null
		if(pl.getLevel_permissions() == null) {
			new_pl = PermissionAccess.getAccess().getLevel(pl.getLevel(), pl.getVersion());
			new_pl.setPermission(p, pl.getLevel(), pl.getVersion());
		}
		return new_pl;
	}
	
	/**
	 * Method to generate a Permissions object that is returned.
	 * It is just a copy and has no bearing on the system itself,
	 * unless either saved to a permission level or used in
	 * searching for relevant permission levels in the database.
	 * 
	 * @param canEditSched
	 * @param canReadSched
	 * @param canReadOldSched
	 * @param canViewResources
	 * @param canChangePermissions
	 * @param canReadLogs
	 * @param canAccessReports
	 * @param canRequestDaysOff
	 * @param maxDaysOff
	 * @param canTakeVacations
	 * @param maxVacationDays
	 * @param canTakeEmergencyDays
	 * @param canViewInactiveEmps
	 * @param canSendNotifications
	 * @param trustedLevel
	 * @return Permissions object with assigned permissions.
	 */
	public Permissions getCustomPermission(boolean canEditSched, boolean canReadSched,
			boolean canReadOldSched, boolean canViewResources, boolean canChangePermissions,
			boolean canReadLogs,boolean canAccessReports, boolean canRequestDaysOff,
			int maxDaysOff, boolean canTakeVacations, int maxVacationDays,
			boolean canTakeEmergencyDays, boolean canViewInactiveEmps,
			boolean canSendNotifications, int trustedLevel)
	{
		
		Permissions p = new Permissions();
		p.setCanEditSchedule(canEditSched);
		p.setCanReadSchedule(canReadSched);
		p.setCanReadOldSchedule(canReadOldSched);
		p.setCanViewResources(canViewResources);
		p.setCanChangePermissions(canChangePermissions);
		p.setCanReadLogs(canReadLogs);
		p.setCanAccessReports(canAccessReports);
		p.setCanRequestDaysOff(canRequestDaysOff);
		p.setMaxDaysOff(maxDaysOff);
		p.setCanTakeVacations(canTakeVacations);
		p.setMaxVacationDays(maxVacationDays);
		p.setCanTakeEmergencyDays(canTakeEmergencyDays);
		p.setCanViewInactiveEmployees(canViewInactiveEmps);
		p.setCanSendNotifications(canSendNotifications);
		p.setTrusted(trustedLevel);
		
		
		return p;
	}
	
	
	
	// ------------------------------------ Methods for manipulating permissions ------------------------------------- //
	
	public boolean createPermissionLevel(PermissionLevel create, Employee creator) throws DBException, InvalidPermissionException
	{
		// Retrieve permission level from employee
		int level = -1;
		char version = ' ';
		String str = creator.getPLevel();
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
		// Determine whether employee permission level is higher than the set they want to create
		if(level <= create.getLevel()) {
			// If the employee has a lower level than the permission they want to create, deny it
			throw new InvalidPermissionException("Employee " + creator.getEmpID() + " attempted to create a PermissionLevel " + level + " outside their access.");
		}
		
		// FIXME: Check permissions to ensure that user can create a permission level
		create.setDescription("Test Data Permission Level");
		System.out.println(create.getLevel_permissions());
		// ...
		
		return PermissionBroker.getBroker().create(create);
	}
	
}
