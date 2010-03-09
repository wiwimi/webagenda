/**
 * business - Notification.java
 */
package business;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class Notification extends BusinessObject {

	/** Primary key of the notification */
	private int notificationID				= -1;
	/** ID of the sender user */
	private int senderID					= -1;
	/** ID of the recipient of the notification */
	private int recipientID					= -1;
	/** Date that the notification was sent at */
	private Timestamp sentTime				= null;
	/** Whether the recipient has viewed the notification */
	private boolean viewed					= false;
	/** Message that the notification contains */
	private String message					= null;
	/** Type of notification being sent */
	private String type						= null;
	
	// FIXME: I'm a little unsure of how notifications are supposed to be
	// constructed. Modify if wrong.
	
	public Notification(int notificationID,int senderID,int recipientID,
			boolean viewed, String message, String type)
	{
		this.notificationID = notificationID;
		this.senderID = senderID;
		this.recipientID = recipientID;
		this.viewed = viewed;
		this.message = message;
		this.type = type;
	}
	
	
	/**
	 * @return the notificationID
	 */
	public int getNotificationID() {
		return notificationID;
	}
	/**
	 * @return the senderID
	 */
	public int getSenderID() {
		return senderID;
	}
	/**
	 * @return the recipientID
	 */
	public int getRecipientID() {
		return recipientID;
	}
	/**
	 * @return the sentTime
	 */
	public Timestamp getSentTime() {
		return sentTime;
	}
	/**
	 * @return the viewed
	 */
	public boolean isViewed() {
		return viewed;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
	
}
