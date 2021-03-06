/**
 * business.permissions - PermissionAccess.java
 */
package business.permissions;

import business.Employee;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;

/**
 * @author Daniel Kettle
 * @version 0.01.10
 * @license GPL 2
 */
public class PermissionAccess
    {
    
    /**
     * Class attribute as static in the Singleton Pattern so only one instance
     * is loaded at one time.
     */
    private static PermissionAccess p_access = null;
    
    /**
     * Empty Constructor
     */
    private PermissionAccess()
        {
        
        }
    
    /**
     * Using a singleton pattern to return an access object that restricts the
     * saving and application of Permissions to users. Most setting of
     * permissions requires in itself a permission object. (one should always be
     * included in the database when application is run)
     * 
     * @return PermissionAccecss object
     */
    public static PermissionAccess getAccess()
        {
        if (p_access == null)
            p_access = new PermissionAccess();
        return p_access;
        }
    
    /**
     * Permissions level default 0 is returned. Similar to getDefault, but that
     * method returns a PermissionLevel object using this Permissions object.
     * 
     * @return Permission object of the most basic level
     */
    protected Permissions getRootLevel()
        {
        return new Permissions();
        }
    
    /**
     * Returns the lowest-level default permission level to the user
     * 
     * @return PermissionLevel default level
     */
    protected PermissionLevel getDefault()
        {
        return PermissionLevel.getDefault();
        }
    
    /**
     * Returns a PermissionLevel object with a null Permissions object inside,
     * generally for searching via PermissionBroker or setting up new permission
     * levels.
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
     * Will return a copy of the permissions level if and only if the
     * Permissions is null. Does not modify the original copy.
     * 
     * @param p Permissions to set the PermissionLevel to.
     * @param pl PermissionLevel that is to be modified
     * @return PermissionLevel with permissions in it.
     */
    protected PermissionLevel setPermissions(Permissions p, PermissionLevel pl)
            throws InvalidPermissionException
        {
        // set to blank permissions level with parameter level's level and
        // version
        PermissionLevel new_pl = null;
        // Set permissions if the input permission level is null
        if (pl.getLevel_permissions() == null)
            {
            new_pl = PermissionAccess.getAccess().getLevel(pl.getLevel(), pl.getVersion());
            new_pl.setPermission(p, pl.getLevel(), pl.getVersion());
            }
        return new_pl;
        }
    
    /**
     * Method to generate a Permissions object that is returned. It is just a
     * copy and has no bearing on the system itself, unless either saved to a
     * permission level or used in searching for relevant permission levels in
     * the database.
     * 
     * @param canEditSched
     * @param canReadSched
     * @param canReadOldSched
     * @param canManageEmployee
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
            boolean canReadOldSched, boolean canManageEmployee, boolean canViewResources,
            boolean canChangePermissions,
            boolean canReadLogs, boolean canAccessReports, boolean canRequestDaysOff,
            int maxDaysOff, boolean canTakeVacations, int maxVacationDays,
            boolean canTakeEmergencyDays, boolean canViewInactiveEmps,
            boolean canSendNotifications, int trustedLevel)
        {
        
        Permissions p = new Permissions();
        p.setCanEditSchedule(canEditSched);
        p.setCanReadSchedule(canReadSched);
        p.setCanReadOldSchedule(canReadOldSched);
        p.setCanManageEmployees(canManageEmployee);
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
    
    // ------------------------------------ Methods for manipulating permissions
    // ------------------------------------- //
    
    /**
     * Method that limits access to Permission resources, but allows a user
     * (creator variable) to create a Permission Level as specified by the
     * create variable.
     * 
     * @param PermissionLevel create - Level to create
     * @param Employee creator - Employee requesting creation of Permissionlevel
     * @return boolean true if created successfully
     * @throws DBException
     * @throws InvalidPermissionException if user does not have permission to
     *             create permission level
     * @throws PermissionViolationException
     */
    public boolean createPermissionLevel(PermissionLevel create, Employee creator)
            throws DBException, DBDownException, InvalidPermissionException,
            PermissionViolationException
        {
        return PermissionBroker.getBroker().create(create, creator);
        }
    
    /**
     * Deletes a permission level, redirects the request to the package
     * PermissionBroker which cannot be directly accessed. Security is the
     * reason why it's not public in that class.
     * 
     * @param delete
     * @param requester
     * @return boolean true if successful
     * @throws InvalidPermissionException
     * @throws DBException
     * @throws PermissionViolationException
     */
    public boolean deletePermissionLevel(PermissionLevel delete, Employee requester)
            throws InvalidPermissionException, DBDownException, DBException,
            PermissionViolationException
        {
        return PermissionBroker.getBroker().delete(delete, requester);
        }
    
    /**
     * Updates a PermissionLevel object, redirects the request to the package
     * PermissionBroker. Requires an old and new PermissionLevel object.
     * 
     * @param old
     * @param update
     * @param requester
     * @return
     * @throws InvalidPermissionException
     * @throws DBException
     * @throws DBDownException
     * @throws PermissionViolationException
     */
    public boolean updatePermissionLevel(PermissionLevel old, PermissionLevel update,
            Employee requester) throws InvalidPermissionException, DBException, DBDownException,
            PermissionViolationException
        {
        return PermissionBroker.getBroker().update(old, update, requester);
        }
    }
