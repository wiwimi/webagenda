/**
 * business.permissions - PermissionAccess.java
 */
package business.permissions;

import messagelog.Logging;
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
	 * Static method to edit a permission level. 
	 * 
	 * @param to_change PermissionLevel that is to be changed.
	 * @param user_permissions PermissionLevel of user attempting to change permissions
	 * @return PermissionLevel changed level
	 */
	public static PermissionLevel editPermissionLevel
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
	 * Returns the lowest-level default permission level to the user
	 * @return PermissionLevel default
	 */
	public PermissionLevel getDefault()
	{	
		return PermissionLevel.getDefault();
	}
	
}
