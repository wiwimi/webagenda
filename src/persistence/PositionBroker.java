/**
 * persistence - PositionBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import application.DBConnection;
import business.Employee;
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
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#create(business.BusinessObject)
	 */
	@Override
	public boolean create(Position createObj, Employee caller) throws DBException,
			DBDownException {
		if (createObj == null)
			throw new NullPointerException("Can not create null Position.");
		if(caller == null) {
			throw new NullPointerException("Caller cannot be null");
		}
		
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
				System.out.println(insert);
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
			
			// create posskill entries
			if(createObj.getPos_skills() != null)
			{
				for(int i = 0; i < createObj.getPos_skills().length; i++) {
					try {
						insert = "INSERT INTO `WebAgenda`.`POSSKILL` " +
								"(`positionName`,`skillName`)" + 
								" VALUES (" + 
								"'" + createObj.getName() + "'," +
								"'" + createObj.getPos_skills()[i] + "')";
						System.out.println(insert);
						stmt = conn.getConnection().createStatement();
						result = stmt.executeUpdate(insert);
					}
					catch(SQLException e) {
						System.out.println("--  " + createObj.getPos_skills()[i] + 
						" not created. May already exist under this Position.");
						conn.getConnection().rollback();
						e.printStackTrace();
					}
				}
				
			}
			conn.getConnection().commit();
			conn.getConnection().setAutoCommit(true);
			conn.setAvailable(true);
			
			if (result != 1)
				throw new DBException(
						"Failed to create position, result count incorrect: " +
								result);
			
			}
		catch (SQLException e)
			{
			// TODO Need additional SQL exception processing here.
			throw new DBException("Failed to create position.", e);
			}
		
		return true;
	}

	/*
	 * ( Important! All Deletions should use internal method object deletePos, not
	 * parameter deleteObj. deleteObj is used to specify the desired Position NAME
	 * whereas deletePos will be the returned results of this broker object for
	 * that position name. A user may not know skills beforehand, and then
	 * we would have to do a skill array check. )
	 * 
	 * (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject)
	 */
	@Override
	public boolean delete(Position deleteObj, Employee caller) throws DBException,
			DBDownException {
		if (deleteObj== null)
			throw new NullPointerException("Can not delete null position.");
		
		if (caller == null)
			throw new NullPointerException("Calling Employee cannot be null");
		
		if (deleteObj.getName() == null)
			throw new DBException("Missing Required Field: Name");
		
		/* Delete goes through three steps:
		 * 1) Grab Position from get(), as we do not know if deleteObj is a true representation
		 * of the Position to delete.
		 * - There can only be one Position deleted at a time
		 * - Skillset that comes with Position may be set to null when delete is requested.
		 * - If no skills exist, we delete Position right away and commit. 
		 * 2) We must grab Skills from SkillBroker to ensure they exist. Skills are found in the
		 * POSSKILL table and compared against those in the parameter.
		 * - If not enough skills are found that are specified, there is something wrong with
		 * Position object and it can be deleted. (or we handle it otherwise)
		 * - If the number of skills required is found, delete and commit.
		 * 
		 * 3) Perform an orphan skill check. Grab all unique skill entries in POSSKILL and
		 * compare against count in SKILL table. If !=, check each SKILL entry for an entry 
		 * in POSSKILL. If one does not exist, delete that SKILL entry. Do for all entries.
		 * 
		 */
		
		String delete = null;
		boolean success;
		try
			{
			Position[] deletePos = PositionBroker.getBroker().get(new Position(deleteObj.getName()),caller);
			if(deletePos.length != 1) {
				throw new DBException("Can only delete one Position object per call.");
			}
			if(!deletePos[0].getName().equals(deleteObj.getName())) {
				throw new DBException("Must specify the exact Position to delete.");
			}
			
			DBConnection conn = this.getConnection();
			conn.getConnection().setAutoCommit(false); // Temporarily while skills are checked for integrity
			Statement stmt = null;
			int result = 0;
			// STEP 1
			// Delete if condition skills are null is met
			if(deletePos[0].getPos_skills() == null) {
				// We can delete this right away
				
				delete = String.format("DELETE FROM `WebAgenda`.`POSITION` WHERE positionName = '%s';",
						deletePos[0].getName());
				System.out.println(delete);
				
				stmt = conn.getConnection().createStatement();
				
				System.out.println("Statement Created");
				//FIXME: Error gets caught here
				result = stmt.executeUpdate(delete);
				System.out.println(result);
				System.out.println("Executed");
				conn.getConnection().commit();
				conn.getConnection().setAutoCommit(true);
				System.out.println("Done delete");
			}
			else {
				// Skills are not null, so we must grab them and utilize them. Step 2.
				Skill[] posSkills = new Skill[deletePos[0].getPos_skills().length];
				int i = 0;
				for(Skill s : deletePos[0].getPos_skills())
				{
					try {
						Skill[] temp = SkillBroker.getBroker().get(s, caller);
						if(temp.length != 1) throw new DBException("Skill does not exist or too many instances retrieved");
						posSkills[i] = temp[0];
						temp = null;
					}
					catch(DBException dbE) {
						conn.getConnection().rollback();
						conn.getConnection().setAutoCommit(true);
						throw new DBException("The Position object pointed to a Skill that does not exist and therefore is invalid.");
						//FIXME: have it skip this skill, maybe init a variable array with skill that don't exist
					}
					catch(Exception E) {
						conn.getConnection().rollback();
						conn.getConnection().setAutoCommit(true);
						conn.setAvailable(true);
						throw new DBException("An Exception occured that will not be dealt with "+ E);
					}
					finally {
						i++;
						// TODO: Determine if following statement is necessary
						deletePos[0].setPos_skills(posSkills); // This sets the Position to utilize the only available skills that
						// exist at this time, enabling the Position to be deleted even if imaginary skills somehow exist. (how they
						// would is beyond me at this time, unless db was modified by outside sources.)
					}
					// At this point, if a variable was to be set that lets method know that a skill doesn't exist that should, we
					// can handle it here.
					
					// ...
				}
				// Skills are valid, delete Position.				
			
				delete = String.format("DELETE FROM `WebAgenda`.`POSITION` WHERE positionName = '%s';",
						deletePos[0].getName());
				stmt = conn.getConnection().createStatement();
				result = stmt.executeUpdate(delete);
				System.out.println(result);
				if(result != 1)
				{
					// roll back and throw exception
					conn.getConnection().rollback();
					conn.getConnection().setAutoCommit(true);
					throw new DBException("Failed to delete position, result count incorrect: " +	result);
				}
				
				conn.getConnection().commit();
				conn.getConnection().setAutoCommit(true);
			
			}

			if (result != 1)
				throw new DBException("Failed to delete position, result count incorrect: " +	result);
			else
				success = true;
			conn.getConnection().setAutoCommit(true); 
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete position.",e);
			}
		
		return success;
	}

	@Override
	public Position[] get(Position searchTemplate, Employee caller) throws DBException,
			DBDownException {
		if (searchTemplate == null)
			throw new NullPointerException("Can not search with null template.");
		
		String select = "SELECT * FROM `WebAgenda`.`POSITION` WHERE ";
		String comp = "";
		
		if (searchTemplate.getName() != null)
			comp = "positionName LIKE '%"+searchTemplate.getName()+"%'";
		if (searchTemplate.getDescription() != null)
			comp = comp + (searchTemplate.getDescription() != null ? (comp.equals("") ? "" : " AND ") +
				"positionDescription LIKE '%" + searchTemplate.getDescription() + "%'" : "");
		
		if (comp.equals(""))
			{
			//Nothing being searched for, return null.
			return null;
			}
		
		// Add comparisons and close select statement.
		select = select + comp + ";";
		
		Position[] foundPositions;
		DBConnection conn = null;
		try
			{
			conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet searchResults = stmt.executeQuery(select);
			
			foundPositions = parseResults(searchResults);
			// Grab all skills associated with all position objects found
			Skill[] skill_check = null; // Initialize for loop
			for(Position p : foundPositions)
			{
				select = String.format(
						"SELECT * FROM `WebAgenda`.`POSSKILL` WHERE positionName = '" + p.getName() + "';");
				stmt = conn.getConnection().createStatement();
				searchResults = stmt.executeQuery(select);
				
				// This statement parses Skills from the Position results then ensures they exist in Skill table.
				// Skills are set to the position in the loop (no exceptions means worked properly) which is returned.
				// in foundPositions object. TA DA
				skill_check = parseSkills(searchResults);
				if(skill_check != null)
					p.setPos_skills(ensureSkillsExist(skill_check,caller));
				searchResults.close();
				stmt.close();
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
				skill = new Skill(rs.getString("skillName"),null);
				posSkill[i] = skill;
			}
			
		}
		
		return posSkill;
	}
	
	/**
	 * Method returns a completed list of skills (adds descriptions, does not throw
	 * exception if executed without error.)
	 * - Checks to ensure that skills exist in skill broker.
	 * @param input Array of skills to check if they exist
	 * @return Skills with descriptions added
	 * @throws DBDownException 
	 * @throws DBException 
	 */
	private Skill[] ensureSkillsExist(Skill[] input,Employee caller) throws DBException, DBDownException {
		if(input == null) return null;
		for(Skill s : input) {
			if(s != null)
				SkillBroker.getBroker().get(s, caller);
		}
		return input;
	}

	/*
	 * Update does not affect position skill relations, only descriptions.
	 * (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject)
	 */
	@Override
	public boolean update(Position oldPos, Position updateObj, Employee caller) throws DBException,
			DBDownException {
		if (updateObj == null)
			throw new NullPointerException("Can not update null position.");
		if(caller == null) throw new NullPointerException("Calling Employee cannot be null");
		if (updateObj.getName() == null)
			throw new NullPointerException(
					"Can not update position without a name.");

		Position[] temp = get(oldPos,caller);
		if(temp.length != 1) throw new DBChangeException("Too many or too few rows for Position to be updated.");
		// Create sql update statement from employee object.
		String update = "SELECT COUNT(*) FROM `WebAgenda`.`POSSKILL` WHERE positionName = '" + temp[0].getName() + "';";
		System.out.println(update);
		// Declare lengths of skills in positions so we don't always have to check for null values.
		int oldPosL = -1, newPosL = -1;
		if(oldPos.getPos_skills() != null) oldPosL = oldPos.getPos_skills().length;
		if(updateObj.getPos_skills() != null) newPosL = updateObj.getPos_skills().length;
		// Get DB connection, send update, and reopen connection for other users.
		try
			{
			DBConnection conn = this.getConnection();
			
			Statement stmt = conn.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(update);
			// Ensure
			if (!rs.last())
				throw new DBException("Position no longer exists, update failed.");
			rs.first();
			int count_value = rs.getInt(1);
			if(temp[0].getPos_skills() != null)
			{
				if(count_value != temp[0].getPos_skills().length) 
				{
					throw new DBChangeException("This Position has been modified and cannot be changed. Please update new Position object");
				}
			}
			stmt = conn.getConnection().createStatement();
			update = "SELECT * FROM `WebAgenda`.`POSSKILL` WHERE positionName = '" + oldPos.getName() + "';";
			System.out.println(update);
			String[] skills = new String[count_value];
			rs = stmt.executeQuery(update);
			conn.getConnection().setAutoCommit(false);
			
			// Check all skills -- in exact order -- from old position and position from db that should BE old
			rs.first();
			int i = 0;
			while(rs.next())
			{
				skills[i] = rs.getString("skillName");
				i++;
			}
			i = 0;
			for(; i < oldPosL; i++) 
			{
				if(!oldPos.getPos_skills()[i].getName().equals(skills[i])) {
					throw new DBChangeException("Skills do not match up with old position for update. Position may have been modified.");
				}
				i++;
			}
			// Race condition Over
			boolean posChanged = false, skillsChanged = false;
			if(oldPos.getDescription() != updateObj.getDescription()) {
				posChanged = true;
			}
			// We are removing skills associated with a Position.
			
			if(oldPosL != newPosL) {
				skillsChanged = true;
			}
			else {
				for(String s : skills)
				{
					// Get all Skill names that old Position has
					for(i = 0; i < updateObj.getPos_skills().length; i++)
					{
						if(!s.equals(updateObj.getPos_skills()[i].getName())) {
							skillsChanged = true;
						}
					}
				}
			}
			if(!posChanged && !skillsChanged)
			{
				conn.getConnection().rollback();
				conn.getConnection().setAutoCommit(true);
				conn.setAvailable(true);
				
				return true;
			}
			if(posChanged)
			{
				update = String.format(
						"UPDATE `WebAgenda`.`POSITION` SET positionName = '%s', positionDescription = %s WHERE positionName = '%s' AND positionDescription %s;",
						updateObj.getName(),
						(updateObj.getDescription() == null ? "NULL" : "'"+updateObj.getDescription()+"'"),
						oldPos.getName(),
						(oldPos.getDescription() == null ? "IS NULL" : "= '"+oldPos.getDescription()+"'"));
				System.out.println(update);
				stmt = conn.getConnection().createStatement();
				i = stmt.executeUpdate(update);
			}
			if(skillsChanged)
			{
				// Delete position from POSSKILL table
				update = String.format(
						"DELETE FROM `WebAgenda`.`POSSKILL` WHERE positionName = '%s';",
						updateObj.getName());
				System.out.println(update);
				stmt = conn.getConnection().createStatement();
				i = stmt.executeUpdate(update);
				if(i != 1) {
					conn.getConnection().rollback();
					conn.getConnection().setAutoCommit(true);
					conn.setAvailable(true);
					throw new DBChangeException("Position not found, may have been changed or deleted by another user.");
				}
				
				update = String.format(
						"DELETE FROM `WebAgenda`.`POSITION` WHERE positionName = '%s';",
						updateObj.getName());
				System.out.println(update);
				stmt = conn.getConnection().createStatement();
				i = stmt.executeUpdate(update);
				if(i != 1) {
					conn.getConnection().rollback();
					conn.getConnection().setAutoCommit(true);
					conn.setAvailable(true);
					throw new DBChangeException("Position not found, may have been changed or deleted by another user.");
				}
				
				update = String.format(
						"INSERT INTO `WebAgenda`.`POSITION` (`positionName`,`positionDescription`) VALUES ('%s','%s');",
						updateObj.getName(),updateObj.getDescription());
				System.out.println(update);
				stmt = conn.getConnection().createStatement();
				i = stmt.executeUpdate(update);
				if(i != 1) {
					conn.getConnection().rollback();
					conn.getConnection().setAutoCommit(true);
					conn.setAvailable(true);
					throw new DBChangeException("Position not found, may have been changed or deleted by another user.");
				}
				if(newPosL > 0) {
				for(i = 0; i < updateObj.getPos_skills().length; i++)
					{
						update = String.format(
								"INSERT INTO `WebAgenda`.`POSSKILL` (`positionName`,`skillName`) VALUES ('%s','%s');",
								updateObj.getPos_skills()[i].getName());
						System.out.println(update);
						stmt = conn.getConnection().createStatement();
						if(stmt.executeUpdate(update) != 1) {
							conn.getConnection().rollback();
							conn.getConnection().setAutoCommit(true);
							conn.setAvailable(true);
							throw new DBChangeException("Could not insert Position/Skill entry as it may already exist.");
						}
						
					}
				}
			}
			conn.getConnection().commit();
			conn.getConnection().setAutoCommit(true);
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to update position.", e);
			}
		
		return true;
	}

}
