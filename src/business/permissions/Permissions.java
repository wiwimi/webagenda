/**
 * business - Permissions.java
 */
package business.permissions;

import business.BusinessObject;

/**
 * Permissions are based on the DB_Create_Tables.sql Rev 120 script posted on 1/27/10 (dd/MM/YY)
 * 
 * Only returns copies of variables when using getters.
 * 
 * @author Daniel Kettle
 * @version 0.01.15
 *
 */
public class Permissions extends BusinessObject {

	/** canEditSchedule is a permission that allows a user to call methods in ScheduleBroker successfully that
	 * add/edit/delete shifts and a-e-d employees from those shifts. For Automatic Scheduling to occur, this
	 * must be enabled as well. Changes can be saved with this permission. */
	private boolean canEditSchedule						= false;
	/** canReadSchedule is a permission that allows a user to view the current saved schedule. That schedule is
	 * determined by other classes, but this is true by default as the purpose of WebAgenda is to allow any
	 * user to view their schedule.<br> 
	 * If an employee is contracting outside the company, but is given an account for contacting those in the
	 * business, this will always return a blank schedule unless this permission is set to false. */
	private boolean canReadSchedule						= true; // User should have access to read current sched.
	/** canReadOldSchedule is a dependancy permission for a user to create reports (but not required) and allows viewing
	 * of schedules that are past the current date. For reference and historical value; schedules are not
	 * deleted by default, only 'de-activated' */
	private boolean canReadOldSchedule					= false;
	/** canViewResources is a permission that allows a user to view employees, their hours working, their status whether
	 * on vacation, emergency leave, shift traded, or other defined statuses. It also provides access to their
	 * availability, but no personal data other than name and job-related info. */
	private boolean canViewResources					= false; // Employee res
	/** canChangePermissions is a permission that allows the current employee level to modify permissions of lower
	 * levels. The optimal permission level for this to work is n -2, where n is the user who can change permissions.
	 * Because a user's permission level cannot be elevated to the same level as the elevating user, so a supervisor
	 * cannot make a non-supervisor the same status as them, this will fail if used incorrectly. 
	 * This permission also affects deleting and creating permissions and permission levels. If false, user cannot
	 * manage permissions in any way, regardless of level. */
	private boolean canChangePermissions				= false;
	/** canReadLogs is a permission more for administrators and those interested in viewing program history for 
	 * violations and irregular unexpected behavior. It also lists actions performed by users and the time they occur;
	 * TODO: Implement a way to archive logs (gzip) to store daily log files or plain .txt */
	private boolean canReadLogs							= false; // Access Log Tables
	/** canAccessReports is a permission to allow users to view and create reports. When a report is generated, it 
	 * is saved to the user's account and can be sent via notifications, internal e-mail, external e-mail 
	 * (it is exported first) and of course exporting to file format onto a hard drive.*/
	private boolean canAccessReports					= false;
	/** canRequestDaysOff is a permission that allows a user to request days off from a higher permission level user, 
	 * in most cases this will be based on their job or designated supervisor/authority, and will be dependant on 
	 * their maxDaysOff and will require authentication via a notification to user's authority. */
	private boolean canRequestDaysOff					= true;
	/** maxDaysOff is a permission that represents the number of days off that a user has. This value is automatically
	 * refreshed every n period of days or as defined by the administrator/highest permission level. Setting the
	 * value to -1 will result in unlimited requests allowed. (Not recommended on that setting) */
	private int maxDaysOff								= 0; // Not set
	/** canTakeVacations is a permission that is similar to the canRequestDaysOff. Part of the difference between the
	 * two is that vacations have a more positive political stigma, as some users are allowed to take 2 weeks off work.
	 * To make a request for 14 days straight as a part time or temporary employee without reason looks bad.<br>.
	 * Generally vacations are only allowable for full-time employees. Booked days off do not count towards or against
	 * vacation days and vice versa.*/
	private boolean canTakeVacations					= false;
	/** maxVacationDays ia a permission that represents the number of vacation days. Similar to the maxDaysOff, this
	 * value is regenerated every period of time (assumed to be yearly). Unlike booking days off, the user does not
	 * require confirmation from superiors, but a notification is sent out so that rearrangements can be discussed.
	 * If this method does not appeal to the business, they can add more days off (via manually or by assigning
	 * different permission level variations) that count towards their vacation. */
	private int maxVacationDays							= -0; // Not set
	/** This should always remain true for an employee unless it is abused; canTakeEmergencyDays is a permission
	 * that overrides procedures to book a day off as it is assumed that an emergency situation has occured that
	 * stops a user from coming into work. Reasons are requested when setting an emergency day off, but optional.
	 * Superiors are notified of these events so they can review reasons for the absence especially  */
	private boolean canTakeEmergencyDays				= true; // Emergency situations
	/** canManageEmployees means that a user can modify Employee attributes including Permissions and PermissionLevel
	 * up to their current permission level - 1. In the event that a business would desire having administration
	 * that cannot modify employees, this should be set to false. That way Employee-specific administration
	 * is kept in the hands of those trusted, or those set with the task of such a job. */
	private boolean canManageEmployees					= false;
	/** canViewInactiveEmployees is a permission that allows a user to not only queue inactive employee profiles 
	 * when making requests of the system, but also browse their resource information like a normal employee.
	 * Recommended for creating reports and sys admins. */
	private boolean canViewInactiveEmployees			= false;
	/** canSendNotifications is a permission that allows a user to send their own notification, as opposed to a 
	 * system generated notification, to a user or users. Events can be displayed this way, promotions, and other
	 * items / news. */
	private boolean canSendNotifications				= false;
	/** trusted is a permission that elevates a user's permissions to the elevator's level. However, since checks
	 * look at this permission as a permission, not an actual level, users that naturally have a permission level
	 * equal or higher than the user id recorded in this variable will have superiority. */
	private int trusted									= -1; // Permission Elevation only distributable via a higher level, notification is sent when this is set to true
	
	/**
	 * Constructor will ONLY create a basic default permission set, known as level 0. This is the lowest permission
	 * setting for performing system-related tasks, but has allowances for emergency days off booking and
	 * communication with other employees. Spamming of these privilleges can result in their suspension if
	 * an authority deems it.
	 */
	protected Permissions()
	{
		
	}
	
	// GETTERS //
	
	/**
	 * Returns the canEditSchedule permission
	 * @return boolean
	 */
	public boolean isCanEditSchedule() {
		boolean b = canEditSchedule;
		return b;
	}
	
	/**
	 * Returns the canReadSchedule permission
	 * @return boolean
	 */
	public boolean isCanReadSchedule() {
		boolean b = canReadSchedule;
		return b;
	}
	
	/**
	 * Returns the canReadOldSchedule permission
	 * @return boolean
	 */
	public boolean isCanReadOldSchedule() {
		boolean b = canReadOldSchedule;
		return b;
	}
	
	/**
	 * Returns the canViewResources permission
	 * @return boolean
	 */
	public boolean isCanViewResources() {
		return canViewResources;
	}
	
	/**
	 * Returns the canChangePermissions permission
	 * @return boolean
	 */
	public boolean isCanChangePermissions() {
		return canChangePermissions;
	}
	
	/**
	 * Returns the canReadLogs permission
	 * @return boolean
	 */
	public boolean isCanReadLogs() {
		return canReadLogs;
	}
	
	/**
	 * Returns the canAccessReports permission
	 * @return boolean
	 */
	public boolean isCanAccessReports() {
		return canAccessReports;
	}
	
	/**
	 * Returns the canRequestDaysOff permission
	 * @return boolean
	 */
	public boolean isCanRequestDaysOff() {
		return canRequestDaysOff;
	}
	
	/**
	 * Returns the maxDaysOff permission
	 * @return int
	 */
	public int getMaxDaysOff() {
		return maxDaysOff;
	}
	
	/**
	 * Returns the canTakeVacations permission
	 * @return boolean
	 */
	public boolean isCanTakeVacations() {
		return canTakeVacations;
	}
	
	/**
	 * Returns the maxVacationDays permission
	 * @return int
	 */
	public int getMaxVacationDays() {
		return maxVacationDays;
	}
	
	/**
	 * Returns the canTakeEmergencydays permission
	 * @return boolean
	 */
	public boolean isCanTakeEmergencyDays() {
		return canTakeEmergencyDays;
	}
	
	/**
	 * Returns the canViewInactiveEmployees permission
	 * @return boolean
	 */
	public boolean isCanViewInactiveEmployees() {
		return canViewInactiveEmployees;
	}
	
	/**
	 * Returns the canSendNotifications permission
	 * @return
	 */
	public boolean isCanSendNotifications() {
		return canSendNotifications;
	}
	
	/**
	 * Returns the trusted level of an employee. Trusted works
	 * by having one employee of a higher permission level give
	 * another employee a temporary permission level that is
	 * one level below the bestower's level in order to perform
	 * tasks that they could not originally perform. This is
	 * so that managers can appoint employees to perform
	 * work in the place of other higher level employees.
	 * Actions on a trusted employee will be logged.
	 * @return int
	 */
	public int getTrusted() {
		return trusted;
	}
	/**
	 * Sets the canEditSchedule permission
	 * @param canEditSchedule the canEditSchedule to set
	 */
	protected void setCanEditSchedule(boolean canEditSchedule) {
		this.canEditSchedule = canEditSchedule;
	}
	/**
	 * Sets the canReadSchedule permission
	 * @param canReadSchedule the canReadSchedule to set
	 */
	protected void setCanReadSchedule(boolean canReadSchedule) {
		this.canReadSchedule = canReadSchedule;
	}
	/**
	 * Sets the canReadOldSchedule permission
	 * @param canReadOldSchedule the canReadOldSchedule to set
	 */
	protected void setCanReadOldSchedule(boolean canReadOldSchedule) {
		this.canReadOldSchedule = canReadOldSchedule;
	}
	/**
	 * Sets the canViewResources permission
	 * @param canViewResources the canViewResources to set
	 */
	protected void setCanViewResources(boolean canViewResources) {
		this.canViewResources = canViewResources;
	}
	/**
	 * Sets the canChangePermissions permission
	 * @param canChangePermissions the canChangePermissions to set
	 */
	protected void setCanChangePermissions(boolean canChangePermissions) {
		this.canChangePermissions = canChangePermissions;
	}
	/**
	 * Sets the canReadLogs permission
	 * @param canReadLogs the canReadLogs to set
	 */
	protected void setCanReadLogs(boolean canReadLogs) {
		this.canReadLogs = canReadLogs;
	}
	/**
	 * Sets the canAccessReports permission
	 * @param canAccessReports the canAccessReports to set
	 */
	protected void setCanAccessReports(boolean canAccessReports) {
		this.canAccessReports = canAccessReports;
	}
	/**
	 * Sets the canRequesetDaysOff permission
	 * @param canRequestDaysOff the canRequestDaysOff to set
	 */
	protected void setCanRequestDaysOff(boolean canRequestDaysOff) {
		this.canRequestDaysOff = canRequestDaysOff;
	}
	/**
	 * Sets the maxDaysOff permission
	 * @param maxDaysOff the maxDaysOff to set
	 */
	protected void setMaxDaysOff(int maxDaysOff) {
		this.maxDaysOff = maxDaysOff;
	}
	/**
	 * Sets the canTakeVacations permission
	 * @param canTakeVacations the canTakeVacations to set
	 */
	protected void setCanTakeVacations(boolean canTakeVacations) {
		this.canTakeVacations = canTakeVacations;
	}
	/**
	 * Sets the maxVacationDays permission
	 * @param maxVacationDays the maxVacationDays to set
	 */
	protected void setMaxVacationDays(int maxVacationDays) {
		this.maxVacationDays = maxVacationDays;
	}
	/**
	 * Sets the canTakeEmergencyDays permission
	 * @param canTakeEmergencyDays the canTakeEmergencyDays to set
	 */
	protected void setCanTakeEmergencyDays(boolean canTakeEmergencyDays) {
		this.canTakeEmergencyDays = canTakeEmergencyDays;
	}
	/**
	 * Sets the canViewInactiveEmployees permission
	 * @param canViewInactiveEmployees the canViewInactiveEmployees to set
	 */
	protected void setCanViewInactiveEmployees(boolean canViewInactiveEmployees) {
		this.canViewInactiveEmployees = canViewInactiveEmployees;
	}
	/**
	 * Sets the canSendNotifications permission
	 * @param canSendNotifications the canSendNotifications to set
	 */
	protected void setCanSendNotifications(boolean canSendNotifications) {
		this.canSendNotifications = canSendNotifications;
	}
	/**
	 * Sets the trusted level permission value
	 * @param int trusted the trusted to set
	 */
	protected void setTrusted(int trusted) {
		this.trusted = trusted;
	}

	/**
	 * Sets the canManageEmployees permission
	 * @param canManageEmployees
	 */
	public void setCanManageEmployees(boolean canManageEmployees) {
		this.canManageEmployees = canManageEmployees;
	}

	/**
	 * Gets the canManageEmployees permission
	 * @return boolean 
	 */
	public boolean isCanManageEmployees() {
		return canManageEmployees;
	}
	
	
	
}
