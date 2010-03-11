/**
 * persistence - PositionBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.DBDownException;
import exception.DBException;
import application.DBConnection;
import business.Skill;
import business.schedule.Position;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class PositionBroker extends Broker<Position> {
	
	// Working on retriving skills along with Positions

	private static volatile PositionBroker pbrok = null;
	
	private PositionBroker()
	{
		super.initConnectionThread();
	}
	
	public static PositionBroker getBroker()
	{
		if(pbrok == null)
			pbrok = new PositionBroker();
		return pbrok;
	}
	
	@Override
	public boolean create(Position createObj) throws DBException,
			DBDownException {
		if (createObj == null)
			throw new NullPointerException("Can not create null Position.");
		
		if (createObj.getName() == null)
			throw new DBException("Missing Required Fields: Name");
		
		/*
		 * Create insert string.
		 */
		String insert = String.format(
				"INSERT INTO `WebAgenda`.`POSITION` " +
				"(`positionName`, `positionDescription`)" +
				" VALUES (%s,%s);",
				"'" + createObj.getName() + "'",
				(createObj.getDescription() == null ? "NULL" : "'" + createObj.getDescription() + "'"));
				
		/*
		 * Send insert to database. SQL errors such as primary key already in use
		 * will be caught, and turned into our own DBAddException, so this method
		 * will only have one type of exception that needs to be caught. If the
		 * insert is successful, return true.
		 */
		try
			{
			DBConnection conn = this.getConnection();
			conn.getConnection().setAutoCommit(false); // Temporarily while skills are checked for integrity
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(insert);
			conn.setAvailable(true);
			
			if (result != 1)
				throw new DBException(
						"Failed to create position, result count incorrect: " +
								result);
			conn.getConnection().setAutoCommit(true);
			}
		catch (SQLException e)
			{
			// TODO Need additional SQL exception processing here.
			throw new DBException("Failed to create position.", e);
			}
		
		return true;
	}

	@Override
	public boolean delete(Position deleteObj) throws DBException,
			DBDownException {
		if (deleteObj== null)
			throw new NullPointerException("Can not delete null position.");
		
		if (deleteObj.getName() == null)
			throw new DBException("Missing Required Field: Name");
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`POSITION` WHERE positionName = '%s';",
				deleteObj.getName());
		
		boolean success;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(delete);
			
			if (result != 1)
				throw new DBException("Failed to delete position, result count incorrect: " +	result);
			else
				success = true;
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete position.",e);
			}
		
		return success;
	}

	@Override
	public Position[] get(Position searchTemplate) throws DBException,
			DBDownException {
		String select;
		
		if (searchTemplate == null)
			{
			select = "SELECT * FROM `WebAgenda`.`POSITION`;";
			}
		else
			{
			if (searchTemplate.getName() == null)
				throw new DBException("Can not search with null name.");
			
			select = String.format(
					"SELECT * FROM `WebAgenda`.`POSITION` WHERE positionName LIKE '%s%%'",
					searchTemplate.getName());
			System.out.println(select);
			}
		
		// Get DB connection, send query, and reopen connection for other users.
		// Parse returned ResultSet into array of positions.
		
		Position[] foundPositions;
		DBConnection conn = null;
		try
			{
			conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet searchResults = stmt.executeQuery(select);
			
			foundPositions = parseResults(searchResults);
			System.out.println("Position[] has " + foundPositions.length + " length");
			Skill[] skills = null;
			for(Position p : foundPositions)
			{
				select = String.format(
						"SELECT * FROM `WebAgenda`.`POSSKILL` WHERE positionName = '" + p.getName() + "';");
				stmt = conn.getConnection().createStatement();
				searchResults = stmt.executeQuery(select);
				p.setPos_skills(parseSkills(searchResults));
			}
			
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to complete position search.",e);
			}
			
		// Return locations that matched search.
		return foundPositions;
	}

	@Override
	public Position[] parseResults(ResultSet rs) throws SQLException {
		// List will be returned as null if no results are found.
		Position[] posList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			posList = new Position[resultCount];
			
			// Return ResultSet to beginning to start retrieving locations.
			rs.beforeFirst();
			Position pos = null;
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				pos = new Position(rs.getString("positionName"),rs.getString("positionDescription"),
						null);
				posList[i] = pos;
				}
			}
		
		return posList;
	}
	
	/**
	 * Method to parse and return all the skills that would be associated to positions that would
	 * be returned.
	 * 
	 * @param rs ResultSet of an executed Statement object to grab positions and skills from the
	 * position-skill table.
	 * @return Skill[] array of Skills that match up with positions being returned.
	 * @throws SQLException
	 */
	private Skill[] parseSkills(ResultSet rs) throws SQLException {
		Skill[] posSkill = null;
		
		if(rs.last())
		{
			int resultCount = rs.getRow();
			posSkill = new Skill[resultCount];
			rs.beforeFirst();
			Skill skill = null;
			for(int i = 0; i < resultCount && rs.next(); i++)
			{
				skill = new Skill(rs.getString("skillName"),rs.getString("skillDescription"));
				posSkill[i] = skill;
			}
			
		}
		
		return posSkill;
	}

	@Override
	public boolean update(Position updateObj) throws DBException,
			DBDownException {
		if (updateObj == null)
			throw new NullPointerException("Can not update null position.");
		
		if (updateObj.getName() == null)
			throw new NullPointerException(
					"Can not update position without a name.");
		
		// Create sql update statement from employee object.
		String update = String.format(
				"UPDATE `WebAgenda`.`POSITION` SET positionDescription = '%s' WHERE positionName = '%s';",
				updateObj.getDescription(),updateObj.getName());
		
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
						"Failed to update position: rowcount incorrect.");
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to update position.", e);
			}
		
		return true;
	}

}
