/**
 * 
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exception.DBException;
import application.DBConnection;
import business.schedule.Location;

/**
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class LocationBroker extends Broker<Location>
	{

	/* (non-Javadoc)
	 * @see persistence.Broker#create(business.BusinessObject)
	 */
	@Override
	public boolean create(Location createLocation) throws DBException
		{
		if (createLocation == null)
			throw new NullPointerException("Can not create null employee.");
		
		if (createLocation.getName() == null)
			throw new DBException("Missing Required Fields: Name");
		
		/*
		 * Create insert string.
		 */
		String insert = String.format(
				"INSERT INTO `WebAgenda`.`LOCATION` " +
				"(`locName`, `locDescription`)" +
				" VALUES (%s,%s);",
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
						"Failed to create employee, result count incorrect: " +
								result);
			}
		catch (SQLException e)
			{
			// TODO Need additional SQL exception processing here.
			throw new DBException("Failed to create employee.", e);
			}
		
		return true;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(Location deleteLocation) throws DBException
		{
		// TODO Auto-generated method stub
		return false;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject)
	 */
	@Override
	public Location[] get(Location searchTemplate) throws DBException
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
					"SELECT * FROM `WebAgenda`.`LOCATION` WHERE locName LIKE '%s%%'",
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
		
		// Return employees that matched search.
		return foundLocations;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject)
	 */
	@Override
	public boolean update(Location updateLocation) throws DBException
		{
		if (updateLocation == null)
			throw new NullPointerException("Can not update null employee.");
		
		if (updateLocation.getName() == null)
			throw new NullPointerException(
					"Can not update location without a name.");
		
		// Create sql update statement from employee object.
		String update = String.format(
				"UPDATE `WebAgenda`.`LOCATION` SET locDescription = '%s' WHERE locName = '%s';",
				updateLocation.getDesc(),updateLocation.getName());
		
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
						"Failed to update location: rowcount incorrect.");
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
			
			// Return ResultSet to beginning to start retrieving employees.
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
