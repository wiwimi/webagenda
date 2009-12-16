/* Generated by Together */

package ProblemDomain;

/**
 * A notification is a type of message object that is sent to employees.  These can be sent out automatically to inform an employee of the status of requests they have made, and can also be sent manually if a supervisor wishes to send a message to his/her employees. 
 */
public class Notification {
    /**
     * Creates a new notification and sends it to a specific employee.
     */
    public void sendNotification(Employee p0) {
    }

    /**
     * Creates a new notification and sends it to every employee within a specific workgroup.
     */
    public void sendNotification(Workgroup p0) {
    }

    /**
     * Creates a new notification and sends it to every employee within a specific job type.
     */
    public void sendNotification(JobType p0) {
    }

    /**
     * Creates a new notification and sends it to every employee within a specific schedule.
     */
    public void sendNotification(Schedule p0) {
    }

    /**
     * Shows whether or not the intended recipient of this notification has opened and viewed it. 
     */
    private boolean wasViewed;

    /**
     * Contains the message text of the notification. 
     */
    private String message;

    /**
     * Contains the type of notification that this object represents, such as an accept/reject notification for an employee request, or a general notification sent by the supervisor. 
     */
    private String type;
}
