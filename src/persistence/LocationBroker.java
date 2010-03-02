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
	public boolean create(Location createObj) throws DBException
		{
		// TODO Auto-generated method stub
		return false;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(Location deleteObj) throws DBException
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
			
			select = String.format("SELECT * FROM `WebAgenda`.`LOCATION` WHERE locName LIKE '%s%%'",searchTemplate.getName());
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
	public boolean update(Location updateObj) throws DBException
		{
		// TODO Auto-generated method stub
		return false;
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
