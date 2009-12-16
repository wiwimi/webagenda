/* Generated by Together */

package ProblemDomain;

/**
 * Contains information on an employee within the system including username and passwords to log in, and basic employee information such as first and last name. 
 */
public class Employee {
    /**
     * Gets a collection of shifts that the employee is currently assigned to. 
     */
    public Shift[] getShifts(){}

    /**
     * Returns the last several times at which the employee logged into the system.  Primarily used when generating usage reports. 
     */
    public void getLoginTimes(){}

    /**
     * Returns the last several times at which the employee viewed their schedules, which can be used to verify that an employee is aware of and has seen any chances that may have occurred.  Primarily used for usage reports. 
     */
    public void getViewTimes(){}

    /**
     * Returns the current availability status of the employee. 
     */
    public String getAvailability(){}

    /**
     * Returns the permissions of an employee, used to determine what they have access to within the system. 
     */
    public String getCredentials(){}

    /**
     * The ID number of the employee within the system. 
     */
    private int empId;

    /**
     * The first name of the employee. 
     */
    private String fName;

    /**
     * The last name of the employee. 
     */
    private String lName;

    /**
     * The username that the employee uses to log into the system. 
     */
    private String username;

    /**
     * The password that the employee uses to log into the system. 
     */
    private String password;

    /**
     * The active state of the employee.  When active, an employee can be included in shifts and schedules.
     * 
     * An inactive employee is equivalent to one that has been deleted, and is kept in the system so they may still be included in reports and auditing procedures. 
     */
    private boolean active;

    /**
     * The current permission level of the employee, tied to a permission set. 
     */
    private PermissionSet plevel;

    /**
     * Employees will receive and view notifications that are sent automatically, or manually sent by their supervisor. 
     * @clientCardinality 0..1
     * @supplierCardinality 0..*
     * @clientQualifier is sent to
     * @supplierQualifier receives
     */
    private Notification lnkNotification;

    /**
     * Multiple employees can work the same shift, and an employee can work multiple shifts. 
     * @clientCardinality 1
     * @supplierCardinality 0..*
     * @clientQualifier belongs to
     * @supplierQualifier has
     */
    private Shift lnkShift;

    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @clientQualifier applies to
     * @supplierQualifier has
     */
    private JobType lnkEmployeeType;

    /**
     * All employees have an assigned permission set that determines employee-specific permissions within the system regarding what they can and can't access, as well as numerical values relating to time off.
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @supplierQualifier has
     * @clientQualifier applies to 
     */
    private PermissionSet lnkPermissionLevel;
}
