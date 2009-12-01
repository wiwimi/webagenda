/* Generated by Together */

package ProblemDomain;

/**
 * Contains information on an employee within the system including username and passwords to log in, and basic employee information such as first and last name. 
 */
public class Employee {
    /**
     * This method checks the employees availbility in case the supervisor does not have it viewed by default. If the return value is true then the supervisor can create the shift. If the return value is false then an error will be displayed and the supervisor will be notified of the reason the user is not able to accept that shift that they created. 
     */
    public Boolean checkAvailability() {
    }

    /**
     * Returns the current credentials of the employee so their permissions within the system can be viewed. 
     */
    public Object accessCredentials() {
    }

    /**
     * After the user makes modifications to their blank schedule, they can choose to save it. A user cannot submit a new availability if it has not been saved, to prevent spamming.  
     * The newly saved schedule does not overwrite their current schedule. 
     */
    public void saveAvailability(Schedule p0) {
    }

    /**
     * Sends a notification to the specified employee that their availaility has been changed. 
     */
    public void sendNotification(Notification p0, Employee p1) {
    }

    /**
     * This method will get the vacation/absent time that an employee either requests or is given. This method will loop for the number of employees that the supervisor is scheduling under the permission class of the supervisor. The method returns an array of Dates that the employee is going to be absent for any reason. 
     */
    public Object[] fetchVacationTime() {
    }

    /**
     * This method will actually add the individual schedules to the employees. When this happens the schedule can then be accessed by the employees in their accounts. 
     */
    public void addSchedule() {
    }

    /**
     * The username that the employee uses to log into the system. 
     */
    private String username;

    /**
     * The password that the employee uses to log into the system. 
     */
    private String password;

    /**
     * The last name of the employee. 
     */
    private String lName;

    /**
     * The first name of the employee. 
     */
    private String fName;

    /**
     * The ID number of the employee within the system. 
     */
    private int employeeId;

    /**
     * Stores the set of permission settings for this employee that determines what features of the system they are and are not allowed to access. 
     */
    private Object credentials;

    /**
     * Holds information regarding what times and days the employee is currently working, which can be used to determine what days and times the employee is available for additional shifts.
     * @label
     * @undirected 
     */
    private Object currentAvailability;

    /**
     * The supervisor that the employee works for.
     */
    private Object supervisor;

    /**
     * Employees will receive and view notifications that are sent automatically, or manually sent by their supervisor. 
     */
    private Notification lnkNotification;

    /**
     * Multiple employees can work the same shift, and an employee can work multiple shifts. 
     */
    private Shift lnkShift;
}
