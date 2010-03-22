/**
 * 
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;
import application.DBConnection;
import business.schedule.Location;
import business.Employee;

/**
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class LocationBroker extends Broker<Location>
	{
	/** Static representation of the broker */
	private static volatile LocationBroker	locationBroker	= null;
	
	/**
	 * Constructor for LocationBroker
	 */
	private LocationBroker()
		{
		super.initConnectionThread(); // Start the connection monitor, checking
		// for old connections.
		}
	
	/**
	 * Returns an object-based Location Broker object.
	 * 
	 * @return LocationBroker result from the Broker request as its respective
	 *         Broker object.
	 */
	public static LocationBroker getBroker()
		{
		if (locationBroker == null)
			{
			locationBroker = new LocationBroker();
			}
		return locationBroker;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#create(business.BusinessObject)
	 */
	@Override
	public boolean create(Location createLocation, Employee caller) throws DBException, DBDownException,  InvalidPermissionException, PermissionViolationException
		{
		if (createLocation == null)
			throw new NullPointerException("Can not create null location.");
		
		if (createLocation.getName() == null)
			throw new DBException("Missing Required Fields: Name");
		
		/*
		 * Create insert string.
		 */
		String insert = String.format(
				"INSERT INTO `WebAgenda`.`LOCATION` " +
				"(`locName`, `locDescription`)" +
				" VALUES ('%s',%s);",
				createLocation.getName(),
				(createLocation.getDesc() == null ? "NULL" : "'" + createLocation.getDesc() + "'"));
				
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
						"Failed to create location, result count incorrect: " +
								result);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to create location.", e);
			}
		
		return true;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(Location deleteLocation, Employee caller) throws DBChangeException, DBException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		if (deleteLocation == null)
			throw new NullPointerException("Can not delete null location.");
		
		if (deleteLocation.getName() == null)
			throw new DBException("Missing Required Field: Name");
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`LOCATION` WHERE locName = '%s' AND locDescription %s;",
				deleteLocation.getName(),
				(deleteLocation.getDesc() == null ? "IS NULL" : "= '"+deleteLocation.getDesc()+"'"));
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(delete);
			conn.setAvailable(true);
			if (result != 1)
				throw new DBChangeException("Location not found, may have been changed or deleted by another user.");
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete location.",e);
			}
		
		return true;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject)
	 */
	@Override
	public Location[] get(Location searchTemplate, Employee caller) throws DBException, DBDownException,  InvalidPermissionException
		{
		if (searchTemplate == null)
			throw new NullPointerException("Can not search with null template.");
		
		String select = "SELECT * FROM `WebAgenda`.`LOCATION` WHERE ";
		String comp = "";
		
		if (searchTemplate.getName() != null)
			comp = "locName LIKE '%"+searchTemplate.getName()+"%'";
		if (searchTemplate.getDesc() != null)
			comp = comp + (searchTemplate.getDesc() != null ? (comp.equals("") ? "" : " AND ") +
				"locDescription LIKE '%" + searchTemplate.getDesc() + "%'" : "");
		
		if (comp.equals(""))
			{
			//Nothing being searched for, return null.
			return null;
			}
		
		// Add comparisons and close select statement.
		select = select + comp + ";";
		
		// Get DB connection, send query, and reopen connection for other users.
		// Parse returned ResultSet into array of locations.
		Location[] foundLocations;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet searchResults = stmt.executeQuery(select);
			conn.setAvailable(true);
			
			foundLocations = parseResults(searchResults);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to complete location search.",e);
			}
		
		// Return locations that matched search.
		return foundLocations;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject)
	 */
	@Override
	public boolean update(Location oldLocation, Location updateLocation, Employee caller) throws DBChangeException, DBException, DBDownException, InvalidPermissionException, PermissionViolationException
		{
		if (updateLocation == null)
			throw new NullPointerException("Update location must not be null.");
		if (oldLocation == null)
			throw new NullPointerException("Old location must not be null.");
		
		if (updateLocation.getName() == null)
			throw new NullPointerException("Update location missing required field: Name");
		if (oldLocation.getName() == null)
			throw new NullPointerException("Old location missing required field: Name");
		
		// Create sql update statement from location object.
		String update = String.format(
				"UPDATE `WebAgenda`.`LOCATION` SET locName = '%s', locDescription = %s WHERE locName = '%s' AND locDescription %s;",
				updateLocation.getName(),
				(updateLocation.getDesc() == null ? "NULL" : "'"+updateLocation.getDesc()+"'"),
				oldLocation.getName(),
				(oldLocation.getDesc() == null ? "IS NULL" : "= '"+oldLocation.getDesc()+"'"));
		
		// Get DB connection, send update, and reopen connection for other users.
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int updateRowCount = stmt.executeUpdate(update);
			conn.setAvailable(true);
			
			// Check if row was updated.
			if (updateRowCount != 1)
				throw new DBChangeException("Location not found, may have been changed or deleted by another user.");
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to update location.", e);
			}
		
		return true;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#parseResults(java.sql.ResultSet)
	 */
	@Override
	protected Location[] parseResults(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		Location[] locList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			locList = new Location[resultCount];
			
			// Return ResultSet to beginning to start retrieving locations.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Location loc = new Location(rs.getString("locName"),rs.getString("locDescription"));
				locList[i] = loc;
				}
			}
		
		return locList;
		}
	
	}
