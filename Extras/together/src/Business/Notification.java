/* Generated by Together */

package Business;

/**
 * A notification is a type of message object that is sent to employees.  These can be sent out automatically to inform an employee of the status of requests they have made, or changes to their schedules, and can also be sent manually if a supervisor wishes to send a message to his/her employees.
 */
public class Notification {
    /**
     * The ID of the employee that sent the notification, or 0 if sent by the system. 
     */
    private int notificationID;

    /**
     * The ID of the employee that sent the notification, or -1 if sent by the system. 
     */
    private int senderID;

    /**
     * The ID of the employee that the notification is being sent to. 
     */
    private int recipientID;

    /**
     * The exact time that the notification was sent.
     */
    private Timestamp sentTime;

    /**
     * Shows whether or not the intended recipient of this notification has opened and viewed it. 
     */
    private boolean viewed;

    /**
     * Contains the message text of the notification. 
     */
    private String message;

    /**
     * Contains the type of notification that this object represents, such as an accept/reject notification for an employee request, an automatic notificaiton for schedule changes or a general notification sent by the supervisor.
     */
    private String type;
}
