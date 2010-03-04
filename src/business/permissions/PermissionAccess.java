/**
 * business.permissions - PermissionAccess.java
 */
package business.permissions;

import business.Employee;

import exception.DBDownException;
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
	 * Permissions level default 0 is returned
	 * 
	 * @return
	 */
	protected Permissions getRootLevel()
	{
		return new Permissions();
	}
	
	/**
	 * Returns the lowest-level default permission level to the user
	 * @return PermissionLevel default
	 */
	protected PermissionLevel getDefault()
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
	protected PermissionLevel getLevel(int level, char version)
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
	protected PermissionLevel setPermissions(Permissions p,PermissionLevel pl) throws InvalidPermissionException
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
	protected Permissions getCustomPermission(boolean canEditSched, boolean canReadSched,
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
	
	/**
	 * Method that limits access to Permission resources, but allows a user (creator variable) to create a Permission Level as
	 * specified by the create variable.
	 * 
	 * @param PermissionLevel create - Level to create
	 * @param Employee creator - Employee requesting creation of Permissionlevel
	 * @return boolean true if created successfully
	 * @throws DBException 
	 * @throws InvalidPermissionException if user does not have permission to create permission level
	 */
	public boolean createPermissionLevel(PermissionLevel create, Employee creator) throws DBException, DBDownException , InvalidPermissionException
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
			throw new InvalidPermissionException("Employee " + creator.getEmpID() + " attempted to create a PermissionLevel " + 
					create.getLevel() + " outside their access " + level + version + ".");
		}
		
		// FIXME: Check permissions to ensure that user can create a permission level
		create.setDescription("Test Data Permission Level");
		// ...
		
		return PermissionBroker.getBroker().create(create);
	}
	
	/**
	 * TODO: canModifyUser permission also checks
	 * 
	 * @param delete
	 * @param requester
	 * @return
	 * @throws InvalidPermissionException 
	 * @throws DBException 
	 */
	public boolean deletePermissionLevel(PermissionLevel delete, Employee requester) throws InvalidPermissionException, DBDownException, DBException
	{
		// Retrieve permission level from employee
		int level = -1;
		char version = ' ';
		String str = requester.getPLevel();
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
		// Determine whether employee permission level is higher than the set they want to delete
		if(level <= delete.getLevel()) {
			// If the employee has a lower level than the permission they want to delete, deny it
			throw new InvalidPermissionException("Employee " + requester.getEmpID() + " attempted to delete a PermissionLevel " + 
					delete.getLevel() + " outside their access " + level + version + ".");
		}
		// FIXME: Check permissions to ensure that user can delete a permission level
		
		// ...
		
		
		return PermissionBroker.getBroker().delete(delete);
	}
	
	public boolean updatePermissionLevel(PermissionLevel update, Employee requester) throws InvalidPermissionException, DBException, DBDownException
	{
		int level = -1;
		char version = ' ';
		String str = requester.getPLevel();
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
		// Determine whether employee permission level is higher than the set they want to delete
		if(level <= update.getLevel()) {
			// If the employee has a lower level than the permission they want to delete, deny it
			throw new InvalidPermissionException("Employee " + requester.getEmpID() + " attempted to update a PermissionLevel " + 
					update.getLevel() + " outside their access " + level + version + ".");
		}
		
		return PermissionBroker.getBroker().update(update);
	}
}
