/**
 * persistence - SkillBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DBConnection;
import business.Skill;
import exception.DBDownException;
import exception.DBException;
import business.Employee;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class SkillBroker extends Broker<Skill> {

	private static volatile SkillBroker sbrok = null;
	
	private SkillBroker()
	{
		super.initConnectionThread();
	}
	
	public static SkillBroker getBroker()
	{
		if(sbrok == null) sbrok = new SkillBroker();
		return sbrok;
	}

	@Override
	public boolean create(Skill createObj,Employee caller) throws DBException, DBDownException {
		if (createObj == null)
			throw new NullPointerException("Can not create null Skill.");
		
		if (createObj.getName() == null)
			throw new DBException("Missing Required Fields: Name");
		
		/*
		 * Create insert string.
		 */
		String insert = String.format(
				"INSERT INTO `WebAgenda`.`SKILL` " +
				"(`skillName`, `skillDescription`)" +
				" VALUES (%s,%s);",
				"'" + createObj.getName() + "'",
				(createObj.getDesc() == null ? "NULL" : "'" + createObj.getDesc() + "'"));
				
		/*
		 * Send insert to database. SQL errors such as primary key already in use
		 * will be caught, and turned into our own DBAddException, so this method
		 * will only have one type of exception that needs to be caught. If the
		 * insert is successful, return true.
		 */
		try
			{
			System.out.println(insert);
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(insert);
			conn.setAvailable(true);
			
			if (result != 1)
				throw new DBException(
						"Failed to create skill, result count incorrect: " +
								result);
			}
		catch (SQLException e)
			{
			// TODO Need additional SQL exception processing here.
			throw new DBException("Failed to create skill.", e);
			}
		
		return true;
	}

	@Override
	public boolean delete(Skill deleteObj,Employee caller) throws DBException, DBDownException {
		if (deleteObj== null)
			throw new NullPointerException("Can not delete null skill.");
		
		if (deleteObj.getName() == null)
			throw new DBException("Missing Required Field: Name");
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`SKILL` WHERE skillName = '%s';",
				deleteObj.getName());
		
		boolean success;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(delete);
			
			if (result != 1)
				throw new DBException("Failed to delete skill, result count incorrect: " +	result);
			else
				success = true;
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete skill.",e);
			}
		
		return success;
	}

	@Override
	public Skill[] get(Skill searchTemplate,Employee caller)
			throws DBException, DBDownException {
		String select;
		
		if (searchTemplate == null)
			{
			select = "SELECT * FROM `WebAgenda`.`SKILL`;";
			}
		else
			{
			if (searchTemplate.getName() == null)
				throw new DBException("Can not search with null name.");
			
			select = String.format(
					"SELECT * FROM `WebAgenda`.`SKILL` WHERE skillName LIKE '%s%%'",
					searchTemplate.getName());
			}
		
		// Get DB connection, send query, and reopen connection for other users.
		// Parse returned ResultSet into array of skills.
		Skill[] foundSkills;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet searchResults = stmt.executeQuery(select);
			conn.setAvailable(true);
			
			foundSkills = parseResults(searchResults);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to complete skill search.",e);
			}
		
		// Return employees that matched search.
		return foundSkills;
	}

	@Override
	public Skill[] parseResults(ResultSet rs) throws SQLException {
		// List will be returned as null if no results are found.
		Skill[] skillList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			skillList = new Skill[resultCount];
			
			// Return ResultSet to beginning to start retrieving skill.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Skill skill = new Skill(rs.getString("skillName"),rs.getString("skillDescription"));
				skillList[i] = skill;
				}
			}
		
		return skillList;
	}

	@Override
	public boolean update(Skill oldSkill, Skill updateObj,Employee caller) throws DBException, DBDownException {
		if (updateObj == null)
			throw new NullPointerException("Can not update null skill.");
		
		if (updateObj.getName() == null)
			throw new NullPointerException(
					"Can not update skill without a name.");
		
		// Create sql update statement from employee object.
		String update = String.format(
				"UPDATE `WebAgenda`.`SKILL` SET skillDescription = '%s' WHERE skillName = '%s';",
				updateObj.getDesc(),updateObj.getName());
		
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
						"Failed to update skill: rowcount incorrect.");
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to update skill.", e);
			}
		
		return true;
	}
	
	
}
