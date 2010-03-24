/**
 * persistence - NotificationBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import application.DBConnection;
import business.Employee;
import business.Notification;
import business.permissions.PermissionLevel;


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
	
	/**
	 * Method that contains all throwable conditions when attempting to access broker methods of EmployeeBroker.
	 * TODO: Do all proper permission checks for broker methods
	 * 
	 * @param target Employee of desired create, update or delete.
	 * @param caller Employee that invokes the method
	 * 
	 * @throws InvalidPermissionException
	 * @throws DBException
	 * @throws DBDownException 
	 */
	private PermissionLevel checkPermissions(Employee target) 
		throws InvalidPermissionException, DBException, DBDownException
	{	
		
		// If item being sent in is a search Employee, this will trigger that it's not exactly valid
		// so it can be dealt with by the program
		try {
			target.getLevel();
			target.getVersion();	
		}
		catch(Exception E) {
			throw new InvalidPermissionException("PermissionLevel not found in target Employee");
		}
		
		PermissionLevel[] pla = persistence.PermissionBroker.getBroker().get(target.getLevel(), target.getVersion(), target);
		if(pla == null) {
			throw new InvalidPermissionException("(Warning) PermissionLevels not found for caller Employee; Ensure Employee exists");
		}
		PermissionLevel pl = pla[0];
		pla = null;
		if(pl == null)
			throw new InvalidPermissionException("No matches for caller's Permission Level found, cannot process");
		if(!pl.getLevel_permissions().isCanSendNotifications()) {
			throw new InvalidPermissionException("User cannot create/delete/update Notifications");
		}
		return pl;
	}
	
	@Override
	public boolean create(Notification createObj, Employee caller) throws DBException,
			DBDownException, InvalidPermissionException {
		if (createObj == null)
			throw new NullPointerException("Can not create null notification.");
		checkPermissions(caller);
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
	public boolean delete(Notification deleteObj, Employee caller) throws DBException,
			DBDownException, InvalidPermissionException {
		if (deleteObj == null)
			throw new NullPointerException("Can not delete null notification.");
		checkPermissions(caller);
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

	/*
	 * Use negative numbers in a searchTemplate object to search all items in that category
	 * 
	 * (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject, business.Employee)
	 */
	@Override
	public Notification[] get(Notification searchTemplate, Employee caller) throws DBException,
			DBDownException {
		if(caller == null) throw new DBException("Cannot get Notifications if user is null");
		
		String select;

		
		if (searchTemplate == null)
			{
			select = "SELECT * FROM `WebAgenda`.`NOTIFICATION`;";
			}
		else
			{
			// we do not factor message contents in (as of current)
			select = String.format(
					"SELECT * FROM `WebAgenda`.`NOTIFICATION` WHERE notificationID LIKE '%s%%' AND " +
					"senderID LIKE '%s%%' AND recipientID LIKE '%s%%' AND type LIKE '%s%%' ORDER BY sentTime;",
					(searchTemplate.getNotificationID() >= 0 ? searchTemplate.getNotificationID() : "%"),
					(searchTemplate.getSenderID() >= 0 ? searchTemplate.getSenderID() : "%" ),
					(searchTemplate.getRecipientID() >= 0 ? searchTemplate.getRecipientID() : "%"),
					(searchTemplate.getType() != null ? searchTemplate.getType() : "%"));
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
	protected Notification[] parseResults(ResultSet rs) throws SQLException {
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
	public boolean update(Notification oldNotification, Notification updateObj, Employee caller) throws DBException,
			DBDownException, InvalidPermissionException {
		if (updateObj == null)
			throw new NullPointerException("Can not update null notification.");
		checkPermissions(caller);
		// Create sql update statement from notification object.
		
		//FIXED: (Daniel Kettle) Removed sentTime from update notification as a) time is restamped upon being written to db, and b) 
		// attempting to set a timeStamp will cause an update to fail because the notification being updated doesn't have one.
		String update = String.format(
				"UPDATE `WebAgenda`.`NOTIFICATION` SET senderID = '%s',recipientID = '%s', viewed = '%s', " +
				"message = '%s', type = '%s' " +
				"WHERE notificationID = %s;",
				updateObj.getSenderID(), updateObj.getRecipientID(), (updateObj.isViewed() == true ? 1 : 0), updateObj.getMessage(),
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
