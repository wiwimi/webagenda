/**
 * persistence - NotificationBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.DBDownException;
import exception.DBException;
import application.DBConnection;
import business.Notification;


/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * FIXME: Need fuzzy search for type? other fields?
 */
public class NotificationBroker extends Broker<Notification> {

	private static volatile NotificationBroker nbrok = null;
	
	private NotificationBroker()
	{
		
	}
	
	public static NotificationBroker getBroker()
	{
		if(nbrok == null) nbrok = new NotificationBroker();
		return nbrok;
	}
	
	@Override
	public boolean create(Notification createObj) throws DBException,
			DBDownException {
		if (createObj == null)
			throw new NullPointerException("Can not create null notification.");
		
		/*
		 * Create insert string.
		 */
		String insert = String.format(
				"INSERT INTO `WebAgenda`.`NOTIFICATION` " +
				"(`notificationID`, `senderID`,`recipientID`,`viewed`,`message`,`type`)" +
				" VALUES (%s,%s,%s,%s,%s,%s);",
				createObj.getNotificationID(), createObj.getSenderID(), createObj.getRecipientID(),
				createObj.isViewed(),
				(createObj.getMessage() == null ? "NULL" : "'" + createObj.getMessage() + "'"),
				(createObj.getType() == null ? "NULL" : "'" + createObj.getType() + "'"));
		/*
		 * Send insert to database. SQL errors such as primary key already in use
		 * will be caught, and turned into our own DBAddException, so this method
		 * will only have one type of exception that needs to be caught. If the
		 * insert is successful, return true.
		 */
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(insert);
			conn.setAvailable(true);
			
			if (result != 1)
				throw new DBException(
						"Failed to create notification, result count incorrect: " +
								result);
			}
		catch (SQLException e)
			{
			// TODO Need additional SQL exception processing here.
			throw new DBException("Failed to create notification.", e);
			}
		
		return true;
	}

	@Override
	public boolean delete(Notification deleteObj) throws DBException,
			DBDownException {
		if (deleteObj == null)
			throw new NullPointerException("Can not delete null notification.");
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`NOTIFICATION` WHERE notificationID = '%s';",
				deleteObj.getNotificationID());
		
		boolean success;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(delete);
			
			if (result != 1)
				throw new DBException("Failed to delete notification, result count incorrect: " +	result);
			else
				success = true;
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete notification.",e);
			}
		
		return success;
	}

	@Override
	public Notification[] get(Notification searchTemplate) throws DBException,
			DBDownException {
		String select;
		
		if (searchTemplate == null)
			{
			select = "SELECT * FROM `WebAgenda`.`NOTIFICATION`;";
			}
		else
			{
			select = String.format(
					"SELECT * FROM `WebAgenda`.`NOTIFICATION` WHERE notificationID LIKE '%s%%'",
					searchTemplate.getNotificationID());
			}
		
		// Get DB connection, send query, and reopen connection for other users.
		// Parse returned ResultSet into array of notifications.
		Notification[] foundNotifications;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet searchResults = stmt.executeQuery(select);
			conn.setAvailable(true);
			
			foundNotifications = parseResults(searchResults);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to complete location search.",e);
			}
		
		// Return notifications that matched search.
		return foundNotifications;
	}

	@Override
	public Notification[] parseResults(ResultSet rs) throws SQLException {
		// List will be returned as null if no results are found.
		Notification[] noteList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			noteList = new Notification[resultCount];
			
			// Return ResultSet to beginning to start retrieving notifications.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Notification note = new Notification(rs.getInt("notificationID"),rs.getInt("senderID"),
						rs.getInt("recipientID"),rs.getBoolean("viewed"),
						rs.getString("message"),rs.getString("type"));
				noteList[i] = note;
				}
			}
		
		return noteList;
		
	}

	@Override
	public boolean update(Notification oldUpdate, Notification updateObj) throws DBException,
			DBDownException {
		if (updateObj == null)
			throw new NullPointerException("Can not update null notification.");
		
		// Create sql update statement from notification object.
		String update = String.format(
				"UPDATE `WebAgenda`.`NOTIFICATION` SET senderID = '%s',recipientID = '%s', sentTime = '%s',  viewed = '%s', " +
				"message = '%s', type = '%s' " +
				"WHERE notificationID = %s;",
				updateObj.getSenderID(), updateObj.getRecipientID(), updateObj.getSentTime(), updateObj.isViewed(), updateObj.getMessage(),
				updateObj.getType(), updateObj.getNotificationID());
		
		// Get DB connection, send update, and reopen connection for other users.
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int updateRowCount = stmt.executeUpdate(update);
			conn.setAvailable(true);
			
			// Ensure
			if (updateRowCount != 1)
				throw new DBException(
						"Failed to update notification: rowcount incorrect.");
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to update notification.", e);
			}
		
		return true;
	}

}
