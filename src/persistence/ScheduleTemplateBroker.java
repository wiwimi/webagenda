/**
 * 
 */
package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import utilities.DoubleLinkedList;
import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import application.DBConnection;
import business.schedule.ScheduleTemplate;
import business.schedule.ShiftPosition;
import business.schedule.ShiftTemplate;
import business.Employee;

/**
 * @author Daniel Wehr
 * @version 0.2.0
 */
public class ScheduleTemplateBroker extends Broker<ScheduleTemplate>
	{
	/** Static representation of the broker */
	private static volatile ScheduleTemplateBroker	stb	= null;
	
	/**
	 * Constructor for ScheduleTemplateBroker
	 */
	private ScheduleTemplateBroker()
		{
		// Start the connection monitor, checking for old connections.
		super.initConnectionThread();
		}
	
	/**
	 * Returns an object-based Schedule Template Broker object.
	 * 
	 * @return ScheduleTemplateBroker result from the Broker request as its
	 *         respective Broker object.
	 */
	public static ScheduleTemplateBroker getBroker()
		{
		if (stb == null)
			{
			stb = new ScheduleTemplateBroker();
			}
		return stb;
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#create(business.BusinessObject, business.Employee)
	 */
	@Override
	public boolean create(ScheduleTemplate createObj, Employee caller)
			throws DBException, DBDownException
		{
		if (createObj == null)
			throw new NullPointerException("Can not create, given schedule template is null.");
		
		DBConnection conn = null;
		try
			{
			//Get connection, disable auto-commit.
			conn = this.getConnection();
			conn.getConnection().setAutoCommit(false);
			
			//Create prepared statements for inserts.
			PreparedStatement createSchedTemp = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SCHEDULETEMPLATE` " +
					"(`creatorID`,`name`) " +
					"VALUES " +
					"(?,?)",Statement.RETURN_GENERATED_KEYS);
			
			PreparedStatement createShiftTemp = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SHIFTTEMPLATE` " +
					"(`schedTempID`,`day`,`startTime`,`endTime`) " +
					"VALUES " +
					"(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			PreparedStatement createShiftPos = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SHIFTPOS` " +
					"(`shiftTempID`,`positionName`,`posCount`) " +
					"VALUES " +
					"(?,?,?)");
			
			//Attempt to insert schedule template.
			createSchedTemp.setInt(1, createObj.getCreatorID());
			createSchedTemp.setString(2, createObj.getName());
			if (createSchedTemp.executeUpdate() != 1)
				throw new DBException("Failed to insert schedule template.");
			
			//Save the auto-generated schedule template ID.
			ResultSet temp = createSchedTemp.getGeneratedKeys();
			if (temp.next())
				createObj.setSchedTempID(temp.getInt(1));
			
			//Insert each shift template.
			for (ShiftTemplate shiftTemp : createObj.getShiftTemplates().toArray())
				{
				insertShiftTemplate(shiftTemp, createObj.getSchedTempID(),
						createShiftTemp, createShiftPos);
				}
			
			//Create succeeded! Commit all inserts and reset connection.
			conn.getConnection().commit();
			conn.getConnection().setAutoCommit(true);
			createSchedTemp.close();
			createShiftTemp.close();
			createShiftPos.close();
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			try
				{
				conn.getConnection().rollback();
				conn.getConnection().setAutoCommit(true);
				}
			catch (SQLException e1)
				{
				throw new DBException("Failed to rollback connection.",e1);
				}
			conn.setAvailable(true);
			throw new DBException("Failed to get schedule templates.", e);
			}
		
		return true;
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#delete(business.BusinessObject, business.Employee)
	 */
	@Override
	public boolean delete(ScheduleTemplate deleteObj, Employee caller)
			throws DBException, DBChangeException, DBDownException
		{
		if (deleteObj == null || deleteObj.getSchedTempID() == -1)
			throw new NullPointerException("Can not delete, schedule template with ID required.");
		
		raceCheck(deleteObj,caller);
		
		try
			{
			DBConnection conn = this.getConnection();
			
			PreparedStatement deleteStmt = conn.getConnection().prepareStatement(
					"DELETE FROM `WebAgenda`.`SCHEDULETEMPLATE` WHERE schedTempID = ?");
			
			deleteStmt.setInt(1, deleteObj.getSchedTempID());
			
			if (deleteStmt.executeUpdate() != 1)
				throw new DBChangeException("Failed to delete schedule template. May have been changed or removed by another user.");
			
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete schedule template.", e);
			}
		
		return true;
		}
	
	/**
	 * Gets all schedule templates in the database that match the given schedule
	 * template. Only one attribute from the given schedule template will be used
	 * for the search, in the following order:<br>
	 * <br>
	 * A) Schedule Template ID (schedTempID)<br>
	 * B) Creator ID (creatorID)<br>
	 * C) Name (name)<br>
	 * <br>
	 * Searches by name will be a fuzzy search, returning all schedules that
	 * include the contents of the search object, rather than an exact search.
	 * 
	 * @param searchTemplate the schedule template holding the attribute to be
	 *           used for the search.
	 * @param caller The employee currently logged into the system, used for
	 *           permissions checks.
	 * @throws DBException
	 * @throws DBDownException
	 * @see persistence.Broker#get(business.BusinessObject)
	 */
	@Override
	public ScheduleTemplate[] get(ScheduleTemplate searchTemplate,
			Employee caller) throws DBException, DBDownException
		{
		// Get all schedule templates for the given creator.
		if (searchTemplate == null)
			throw new NullPointerException("Cannot search with null template.");
		
		ScheduleTemplate[] found = null;
		try
			{
			DBConnection conn = this.getConnection();
			
			PreparedStatement select = null;
			if (searchTemplate.getSchedTempID() != -1)
				{
				select = conn.getConnection().prepareStatement(
					"SELECT * FROM `WebAgenda`.`SCHEDULETEMPLATE` WHERE schedTempID = ? ORDER BY schedTempID");
				select.setInt(1, searchTemplate.getSchedTempID());
				}
			else if (searchTemplate.getCreatorID() != -1)
				{
				select = conn.getConnection().prepareStatement(
					"SELECT * FROM `WebAgenda`.`SCHEDULETEMPLATE` WHERE creatorID = ? ORDER BY schedTempID");
				select.setInt(1, searchTemplate.getCreatorID());
				}
			else if (searchTemplate.getName() != null)
				{
				select = conn.getConnection().prepareStatement(
					"SELECT * FROM `WebAgenda`.`SCHEDULETEMPLATE` WHERE name LIKE ? ORDER BY schedTempID");
				select.setString(1, "%"+searchTemplate.getName()+"%");
				}
			
			//If nothing is being searched for, return null.
			if (select == null)
				{
				conn.setAvailable(true);
				return null;
				}
			
			ResultSet schTmpResult = select.executeQuery();
			found = parseResults(schTmpResult);
			fillSchedTemp(found, conn);
			
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to get schedule template(s).", e);
			}
		
		// Return schedule templates that matched search.
		return found;
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#update(business.BusinessObject, business.BusinessObject, business.Employee)
	 */
	@Override
	public boolean update(ScheduleTemplate oldObj, ScheduleTemplate updateObj,
			Employee caller) throws DBException, DBChangeException, DBDownException
		{
		//Ensure Old/Update objects aren't null.
		if (oldObj == null || oldObj.getSchedTempID() == -1)
			throw new NullPointerException("Old schedule template with ID required for updates.");
		if (updateObj == null || updateObj.getSchedTempID() == -1)
			throw new NullPointerException("Update schedule template with ID required for updates.");
		
		//Ensure old/update objects refer to the same schedule template.
		if (oldObj.getSchedTempID() != updateObj.getSchedTempID())
			throw new DBException("Old and Update schedule templates do not have the same ID.");
		
		//Run raceCheck on old object. Pass DBChangeException out of method if applicable.
		raceCheck(oldObj, caller);
		
		DBConnection conn = null;
		try
			{
			//Get DB connection, disable auto-commit.
			conn = this.getConnection();
			conn.getConnection().setAutoCommit(false);
			
			//Prepare all statements used during update.
			PreparedStatement updateSchedTemp = conn.getConnection().prepareStatement(
					"UPDATE `WebAgenda`.`SCHEDULETEMPLATE` SET name = ? WHERE schedTempID = ? AND creatorID = ? AND name = ?");
			
			PreparedStatement createShiftTemp = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SHIFTTEMPLATE` " +
					"(`schedTempID`,`day`,`startTime`,`endTime`) " +
					"VALUES " +
					"(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			PreparedStatement updateShiftTemp = conn.getConnection().prepareStatement(
					"UPDATE `WebAgenda`.`SHIFTTEMPLATE` " +
					"SET day = ?, startTime = ?, endTime = ? " +
					"WHERE shiftTempID = ? AND schedTempID = ? AND day = ? " +
							"AND startTime = ? AND endTime = ?");
			
			PreparedStatement deleteShiftTemp = conn.getConnection().prepareStatement(
					"DELETE FROM `WebAgenda`.`SHIFTTEMPLATE` WHERE shiftTempID = ?");
			
			PreparedStatement createShiftPos = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SHIFTPOS` " +
					"(`shiftTempID`,`positionName`,`posCount`) " +
					"VALUES " +
					"(?,?,?)");
			
			PreparedStatement deleteMultiShiftPos = conn.getConnection().prepareStatement(
					"DELETE FROM `WebAgenda`.`SHIFTPOS` WHERE shiftTempID = ?");
			
			//Compare old/update schedule template attributes. If not equal, update DB.
			if (!oldObj.getName().equals(updateObj.getName()))
				{
				updateSchedTemp.setString(1, updateObj.getName());
				updateSchedTemp.setInt(2, oldObj.getSchedTempID());
				updateSchedTemp.setInt(3, oldObj.getCreatorID());
				updateSchedTemp.setString(4, oldObj.getName());
				
				//Attempt update.
				if (updateSchedTemp.executeUpdate() != 1)
					throw new DBChangeException("Failed to update schedule template name. May have been changed or deleted by another user.");
				
				updateSchedTemp.close();
				}
			
			//Convert old/new shift templates to array.
			ShiftTemplate[] oldShiftArr = oldObj.getShiftTemplates().toArray();
			ShiftTemplate[] updShiftArr = updateObj.getShiftTemplates().toArray();
			
			//Check for old shift templates that are not used, and can be deleted.
			for (int i = 0; i < oldShiftArr.length; i++)
				{
				boolean found = false;
				for (int j = 0; j < updShiftArr.length && !found; j++)
					{
					//If old shift exists in new shift array, set found.
					if (oldShiftArr[i].getShiftTempID() == updShiftArr[j].getShiftTempID())
						found = true;
					}
				
				//Old shift not in update, delete from database.
				if (!found)
					{
					deleteShiftTemp.setInt(1, oldShiftArr[i].getShiftTempID());
					if (deleteShiftTemp.executeUpdate() != 1)
						throw new DBException("Failed to delete shift template during update.");
					}
				}
			deleteShiftTemp.close();
			
			//Check updated shifts against old shifts, adding and updating as necessary.
			for (int i = 0; i < updShiftArr.length; i++)
				{
				ShiftTemplate updShift = updShiftArr[i];
				
				//If update shift doesn't have an ID, it is new.
				if (updShift.getShiftTempID() == -1)
					{
					//Add new shift.
					insertShiftTemplate(updShift, updateObj.getSchedTempID(),
								createShiftTemp, createShiftPos);
					}
				else
					{
					/*
					 * Confirm that shift template has schedule template ID.  If not,
					 * shift ID was added in the front-end, which should not be done.
					 */
					if (updShift.getSchedTempID() == -1)
						throw new DBException("Shift ID was not assigned by backend.");
					
					//Shift template has an ID should match an old shift template.
					int oldShiftIdx = -1;
					for (int j = 0; j < oldShiftArr.length && oldShiftIdx == -1; j++)
						{
						if (oldShiftArr[j].getSchedTempID() == updShift.getSchedTempID() &&
								oldShiftArr[j].getShiftTempID() == updShift.getShiftTempID())
							{
							//Matching shift template found.
							oldShiftIdx = j;
							}
						}
					
					if (oldShiftIdx != -1)
						{
						ShiftTemplate oldShift = oldShiftArr[oldShiftIdx];
						
						//If shift attributes different, update DB.
						if (oldShift.getDay() != updShift.getDay() ||
								oldShift.getStartTime() != updShift.getStartTime() ||
								oldShift.getEndTime() != updShift.getEndTime())
							{
							//Add new parameters.
							updateShiftTemp.setInt(1, updShift.getDay());
							updateShiftTemp.setTime(2, updShift.getStartTime());
							updateShiftTemp.setTime(3, updShift.getEndTime());
							
							//Add old parameters.
							updateShiftTemp.setInt(4, oldShift.getShiftTempID());
							updateShiftTemp.setInt(5, oldShift.getSchedTempID());
							updateShiftTemp.setInt(6, oldShift.getDay());
							updateShiftTemp.setTime(7, oldShift.getStartTime());
							updateShiftTemp.setTime(8, oldShift.getEndTime());
							
							if (updateShiftTemp.executeUpdate() != 1)
								throw new DBChangeException("Failed to update shift template. May have been changed or deleted by another user.");
							}
						
						//Check if shifts have same positions.
						ShiftPosition[] oldShiftPosArr = oldShift.getShiftPositions().toArray();
						ShiftPosition[] updShiftPosArr = updShift.getShiftPositions().toArray();
						
						boolean shiftPosChanged = false;
						if (oldShiftPosArr.length == updShiftPosArr.length)
							{
							for (int j = 0; j < oldShiftPosArr.length && !shiftPosChanged; j++)
								{
								if (oldShiftPosArr[j].getShiftTempID() != updShiftPosArr[j].getShiftTempID() ||
										!oldShiftPosArr[j].getPosName().equals(updShiftPosArr[j].getPosName()) ||
										oldShiftPosArr[j].getPosCount() != updShiftPosArr[j].getPosCount())
									shiftPosChanged = true;
								}
							}
						else
							shiftPosChanged = true;
						
						if (shiftPosChanged)
							{
							//Shift positions changed.  Delete old shift positions.
							deleteMultiShiftPos.setInt(1, updShift.getShiftTempID());
							if (deleteMultiShiftPos.executeUpdate() < 1)
								throw new DBChangeException("Failed to delete old shift positions for: "+updShift);
							
							//Create new shift positions.
							insertShiftPositions(updShiftPosArr, updShift.getShiftTempID(), createShiftPos);
							}
						}
					else
						{
						//Shift was given incorrect IDs, no matches found.
						throw new DBException("Shift template has non-matching ID's, can not update. "+updShift);
						}
					}
				}
			
			//Update succeeded! Commit all changes and reset connection.
			conn.getConnection().commit();
			conn.getConnection().setAutoCommit(true);
			updateSchedTemp.close();
			createShiftTemp.close();
			updateShiftTemp.close();
			deleteShiftTemp.close();
			createShiftPos.close();
			deleteMultiShiftPos.close();
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			try
				{
				conn.getConnection().rollback();
				conn.getConnection().setAutoCommit(true);
				}
			catch (SQLException e1)
				{
				throw new DBException("Failed to rollback connection.",e1);
				}
			conn.setAvailable(true);
			throw new DBException("Failed to get schedule templates.", e);
			}
		
		return true;
		}
	
	/**
	 * Steps through a schedule templates and sorts all shift templates, as well
	 * as all shift positions within them, to match the order that would be
	 * returned by the database.
	 * 
	 * @param toSort the schedule template to sort.
	 * @return true when the sort is complete.
	 */
	public static boolean sortScheduleTemplate(ScheduleTemplate toSort)
		{
		//Get the list of shift templates.
		DoubleLinkedList<ShiftTemplate> shiftTemps = toSort.getShiftTemplates();
		
		//Sort shift positions within shifts templates.
		for (int i = 0; i < shiftTemps.size(); i++)
			{
			//Get shift positions and sort.
			DoubleLinkedList<ShiftPosition> shiftPositions = shiftTemps.get(i).getShiftPositions(); 
			ShiftPosition[] sortedShiftPos = shiftPositions.toArray();
			Arrays.sort(sortedShiftPos);
			
			//Add sorted shift positions back to list.
			shiftPositions.clear();
			for (int j = 0; j < sortedShiftPos.length; j++)
				shiftPositions.add(sortedShiftPos[j]);
			}
		
		//Employees sorted, now sort shifts.
		ShiftTemplate[] sortedShiftTemps = shiftTemps.toArray();
		Arrays.sort(sortedShiftTemps);
		
		//Add sorted shifts back to list.
		shiftTemps.clear();
		for (int k = 0; k < sortedShiftTemps.length; k++)
			shiftTemps.add(sortedShiftTemps[k]);
		
		return true;
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#parseResults(java.sql.ResultSet)
	 */
	@Override
	protected ScheduleTemplate[] parseResults(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		ScheduleTemplate[] stList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			stList = new ScheduleTemplate[resultCount];
			
			// Return ResultSet to beginning to start retrieving schedule
			// templates.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				ScheduleTemplate st = new ScheduleTemplate(
						rs.getInt("schedTempID"), rs.getInt("creatorID"),
						rs.getString("name"));
				
				stList[i] = st;
				}
			
			}
		
		return stList;
		}
	
	/**
	 * Attempts to create shift templates out of a given result set.
	 * 
	 * @param rs the result set returned by a database search.
	 * @return an array of shift templates retrieved from the result set, or null
	 *         if the result set was empty.
	 * @throws SQLException
	 */
	protected ShiftTemplate[] parseShiftTemps(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		ShiftTemplate[] stList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			stList = new ShiftTemplate[resultCount];
			
			// Return ResultSet to beginning to start retrieving shift templates.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				ShiftTemplate st = new ShiftTemplate(rs.getInt("shiftTempID"), rs
						.getInt("schedTempID"), rs.getInt("day"), rs
						.getTime("startTime"), rs.getTime("endTime"));
				
				stList[i] = st;
				}
			
			}
		
		return stList;
		}
	
	/**
	 * Parses a result set into an array of shift positions.
	 * 
	 * @param rs The result set to parse.
	 * @return the array of shift positions, or null if the result set was empty.
	 * @throws SQLException if there was an error during parsing.
	 */
	protected ShiftPosition[] parseShiftPos(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		ShiftPosition[] spList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			spList = new ShiftPosition[resultCount];
			
			// Return ResultSet to beginning to start retrieving shift positions.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				ShiftPosition sp = new ShiftPosition(rs.getInt("shiftTempID"), rs
						.getString("positionName"), rs.getInt("posCount"));
				
				spList[i] = sp;
				}
			
			}
		
		return spList;
		}
	
	/**
	 * Fetches all shift templates and shift position objects from the database
	 * for each shiftTemplate.
	 * 
	 * @param templates
	 * @param conn
	 */
	private void fillSchedTemp(ScheduleTemplate[] templates, DBConnection conn)
			throws SQLException
		{
		// Prepare the select statements to pull additional data.
		PreparedStatement shiftTempStmt = conn.getConnection().prepareStatement(
				"SELECT * FROM `WebAgenda`.`SHIFTTEMPLATE` WHERE schedTempID = ? ORDER BY shiftTempID;");
		PreparedStatement shiftPosStmt = conn.getConnection().prepareStatement(
				"SELECT * FROM `WebAgenda`.`SHIFTPOS` WHERE shiftTempID = ? ORDER BY shiftTempID;");
		
		for (ScheduleTemplate schedTemp : templates)
			{
			// Get shift templates for matching schedule template.
			shiftTempStmt.setInt(1, schedTemp.getSchedTempID());
			ResultSet shiftRS = shiftTempStmt.executeQuery();
			ShiftTemplate[] shifts = parseShiftTemps(shiftRS);
			
			for (ShiftTemplate shift : shifts)
				{
				// Get shift positions for matching shift template.
				shiftPosStmt.setInt(1, shift.getShiftTempID());
				ResultSet shiftPosRS = shiftPosStmt.executeQuery();
				ShiftPosition[] positions = parseShiftPos(shiftPosRS);
				
				// Add shift positions to shift template.
				for (ShiftPosition pos : positions)
					{
					shift.getShiftPositions().add(pos);
					}
				
				// Add shifts templates to schedule template.
				schedTemp.getShiftTemplates().add(shift);
				}
			}
		
		// Release resources used by prepared statements.
		shiftTempStmt.close();
		shiftPosStmt.close();
		}

	/**
	 * Compares a given schedule template against the database, ensuring that it
	 * has not been changed by another user.  This is used check for race conditions
	 * when updating or deleting schedule templates in the database.
	 * 
	 * @param old The schedule template that was previously retrieved
	 * 			from the database.
	 * @param caller The employee that is logged into the system.
	 * @return
	 * @throws DBChangeException
	 * @throws DBException
	 * @throws DBDownException 
	 */
	private boolean raceCheck(ScheduleTemplate old, Employee caller) throws DBChangeException, DBException, DBDownException
		{
		if (old == null || old.getSchedTempID() == -1)
			throw new DBException("Unable to validate old schedule template, is null or has no schedTempID.");
		
		//Get schedule template from DB with matching scheduleTemplateID.
		ScheduleTemplate[] fromDB = this.get(old, caller);
		
		//If no schedule template returned, throw exception.
		if (fromDB == null)
			throw new DBChangeException("No matching record found, schedule template may have been deleted.");
		
		ScheduleTemplate fetched = fromDB[0];
		
		//Sort schedule template before starting compares.
		sortScheduleTemplate(old);
		
		//Compare name of old/fetched. SchedTempID and CreatorID do not need to be checked.
		if (!old.getName().equals(fetched.getName()))
			throw new DBChangeException("Schedule Template name has been modified.");
		
		//Compare number of shift templates.
		if (old.getShiftTemplates().size() != fetched.getShiftTemplates().size())
			throw new DBChangeException("Schedule Template num of shifts modified.");
		
		//Compare shift templates individually between old/fetched.
		for (int i = 0; i < old.getShiftTemplates().size(); i++)
			{
			ShiftTemplate st1 = old.getShiftTemplates().get(i);
			ShiftTemplate st2 = fetched.getShiftTemplates().get(i);
			
			//Compare shift template attributes.
			if (st1.getShiftTempID() != st2.getShiftTempID() ||
					st1.getDay() != st2.getDay() ||
					!st1.getStartTime().equals(st2.getStartTime()) ||
					!st1.getEndTime().equals(st2.getEndTime()))
				throw new DBChangeException("Shift Template changed."+st1+" vs "+st2);
			
			//Compare number of shift positions.
			if (st1.getShiftPositions().size() != st2.getShiftPositions().size())
				throw new DBChangeException("Shift Positions changed.");
			
			//Compare shift positions individually between old/fetched.
			for (int j = 0; j < st1.getShiftPositions().size(); j++)
				{
				ShiftPosition sp1 = st1.getShiftPositions().get(j);
				ShiftPosition sp2 = st2.getShiftPositions().get(j);
				
				//Compare shift position attributes.
				if (sp1.getShiftTempID() != sp2.getShiftTempID() ||
						!sp1.getPosName().equals(sp2.getPosName()) ||
						sp1.getPosCount() != sp2.getPosCount())
					throw new DBChangeException("Shift Positions changed. "+sp1+" vs "+sp2);
				}
			}
		
		return true;
		}
	
	/**
	 * Inserts the given shift template, using the given connection, resultSet,
	 * and schedule ID.
	 * 
	 * @param shiftTemp The shift template to insert into the database.
	 * @param createShiftTemp The resultSet defining the insert for a shift template.
	 * @param createShiftPos The resultSet defining the insert for a shift position.
	 * @param schedTempID The ID number of the schedule the shift belongs to
	 * @throws SQLException if there was an error with the insert statements.
	 * @throws DBException if there was an error with executing the statements.
	 */
	private void insertShiftTemplate(ShiftTemplate shiftTemp, Integer schedTempID, 
			PreparedStatement createShiftTemp, PreparedStatement createShiftPos) throws SQLException, DBException
		{
		//Add schedule template ID before insert.
		shiftTemp.setSchedTempID(schedTempID);
		
		//Attempt to insert shift template.
		createShiftTemp.setInt(1, shiftTemp.getSchedTempID());
		createShiftTemp.setInt(2, shiftTemp.getDay());
		createShiftTemp.setTime(3, shiftTemp.getStartTime());
		createShiftTemp.setTime(4, shiftTemp.getEndTime());
		if (createShiftTemp.executeUpdate() != 1)
			throw new DBException("Failed to insert shift template");
		
		//Save the auto-generated shift template ID.
		ResultSet temp = createShiftTemp.getGeneratedKeys();
		if (temp.next())
			shiftTemp.setShiftTempID(temp.getInt(1));
		
		//Insert each shift position.
		insertShiftPositions(shiftTemp.getShiftPositions().toArray(), shiftTemp.getShiftTempID(), createShiftPos);
		}
	
	/**
	 * Attempts to insert an array of shift positions into the database.
	 * 
	 * @param shiftPosArr The array to insert.
	 * @param shiftTempID The shift template ID to use for all shift positions.
	 * @param createShiftPos The prepared statement used to execute the inserts.
	 */
	private void insertShiftPositions(ShiftPosition[] shiftPosArr, Integer shiftTempID,
			PreparedStatement createShiftPos) throws DBException, SQLException
		{
		for (ShiftPosition shiftPos : shiftPosArr)
			{
			//Add shift template ID before insert.
			shiftPos.setShiftTempID(shiftTempID);
			
			//Attempt to insert shift position.
			createShiftPos.setInt(1, shiftPos.getShiftTempID());
			createShiftPos.setString(2, shiftPos.getPosName());
			createShiftPos.setInt(3, shiftPos.getPosCount());
			if (createShiftPos.executeUpdate() != 1)
				throw new DBException("Failed to insert shift position");
			}
		
		}
	}
