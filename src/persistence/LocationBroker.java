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
import exception.DBInUseException;
import application.DBConnection;
import business.schedule.Location;

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
	public boolean create(Location createLocation) throws DBException, DBDownException
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
	public boolean delete(Location deleteLocation) throws DBException, DBChangeException, DBDownException
		{
		if (deleteLocation == null)
			throw new NullPointerException("Can not delete null location.");
		
		if (deleteLocation.getName() == null)
			throw new DBException("Missing Required Field: Name");
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`LOCATION` WHERE locName = '%s' AND locDescription %s;",
				deleteLocation.getName(),
				(deleteLocation.getDesc() == null ? "IS NULL" : "= '"+deleteLocation.getDesc()+"'"));
		
		boolean success;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(delete);
			
			if (result != 1)
				throw new DBChangeException("Location not found, likely changed or deleted by another user.");
			else
				success = true;
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete location.",e);
			}
		
		return success;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject)
	 */
	@Override
	public Location[] get(Location searchTemplate) throws DBException, DBDownException
		{
		String select;
		
		if (searchTemplate == null)
			{
			select = "SELECT * FROM `WebAgenda`.`LOCATION`;";
			}
		else
			{
			if (searchTemplate.getName() == null)
				throw new DBException("Can not search with null name.");
			
			select = String.format(
					"SELECT * FROM `WebAgenda`.`LOCATION` WHERE locName LIKE '%%%s%%'",
					searchTemplate.getName());
			}
		
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
	public boolean update(Location oldLocation, Location updateLocation) throws DBException, DBChangeException, DBDownException
		{
		if (updateLocation == null)
			throw new NullPointerException("Can not update null location.");
		
		if (updateLocation.getName() == null)
			throw new NullPointerException("Missing Required Field: Name");
		
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
				throw new DBChangeException("Location changed or deleted by another user.");
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
	public Location[] parseResults(ResultSet rs) throws SQLException
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
