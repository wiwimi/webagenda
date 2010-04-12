/**
 * business - Notification.java
 */
package business;

import java.sql.Timestamp;

/**
 * Holds all information for a notification, matching a record in the database.
 * 
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
	
	/**
	 * Constructor that creates a Notification with all required parameters.
	 * viewed boolean must be false to have it pop-up on a user's screen.
	 */
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
	 * Constructor with required parameters as well as a Timestamp attribute that
	 * determines when message was sent. If you want a notification to appear
	 * in the future, this constructor should be used.
	 * 
	 * @param notificationID id of this notification. Should be auto-set by database
	 * so any value can be used
	 * @param senderID id of employee who is sending notification
	 * @param recipientID id of employee who is receiving notification
	 * @param time time that notification should be seen as sent.
	 * @param viewed boolean if the notification has been viewed
	 * @param message String message of the notification
	 * @param type String type of notification
	 */
	public Notification(int notificationID,int senderID,int recipientID,
			Timestamp time, boolean viewed, String message, String type)
	{
		this(notificationID,senderID,recipientID,viewed,message,type);
		this.sentTime = time;
	}
	
	/**
	 * Constructor used for system notifications.
	 * 
	 * @param recipientID id of employee who is receiving notification.
	 * @param message String message of the notification.
	 * @param type String type of notification.
	 */
	public Notification(int recipientID, String message, String type)
	{
		this.recipientID = recipientID;
		this.message = message;
		this.type = type;
	}
	
	/**
	 * Constructor that is meant for easy deletions of notifications.
	 * 
	 * @param notificationID
	 */
	public Notification(int notificationID)
	{
		this.notificationID = notificationID;
	}
	
	
	/**
	 * Gets the notification identification number
	 * @return the notificationID
	 */
	public int getNotificationID() {
		return notificationID;
	}
	/**
	 * Gets the sender Employee's id number
	 * @return the senderID
	 */
	public int getSenderID() {
		return senderID;
	}
	/**
	 * Gets the recipients's id number
	 * @return the recipientID
	 */
	public int getRecipientID() {
		return recipientID;
	}
	/**
	 * Gets the time notification was sent (as Timestamp)
	 * @return the sentTime
	 */
	public Timestamp getSentTime() {
		return sentTime;
	}
	/**
	 * Gets whether notification was viewed.
	 * @return the viewed
	 */
	public boolean isViewed() {
		return viewed;
	}
	/**
	 * Gets the mesage of the notification
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * Gets the type of notification
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/*
	 * Actual message will not be shown to protect privacy
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return notificationID + ";" + senderID + ";" + recipientID + ";" + sentTime + ";" + viewed + ";" + type;
	}
	
}
