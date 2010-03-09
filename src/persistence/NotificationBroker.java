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

	@Override
	public boolean create(Notification createObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Notification deleteObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Notification[] get(Notification searchTemplate) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return null;
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
			
			// Return ResultSet to beginning to start retrieving locations.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Notification note = new Notification(rs.getInt("notificationID"),rs.getInt("senderID"),
						rs.getInt("recipientID"),rs.getTimestamp("sentTime"),rs.getBoolean("viewed"),
						rs.getString("message"),rs.getString("type"));
				noteList[i] = note;
				}
			}
		
		return noteList;
		
	}

	@Override
	public boolean update(Notification updateObj) throws DBException,
			DBDownException {
		if (updateObj == null)
			throw new NullPointerException("Can not update null notification.");
		
		// Create sql update statement from location object.
		String update = String.format(
				"UPDATE `WebAgenda`.`NOTIFICATION` SET senderID = '%s',recipientID = '%s', sentTime = '%s',  viewed = '%s', " +
				"message = '%s', type = '%s' " +
				"WHERE notificationID = '%s';",
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
