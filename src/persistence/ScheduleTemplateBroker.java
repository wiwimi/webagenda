/**
 * 
 */
package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * @version 0.1.0
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
	
	@Override
	public boolean create(ScheduleTemplate createObj, Employee caller)
			throws DBException, DBDownException
		{
		if (createObj == null)
			throw new NullPointerException("Can not delete, given schedule template is null.");
		
		DBConnection conn = null;
		try
			{
			//Get connection, disable autocommit.
			conn = this.getConnection();
			conn.getConnection().setAutoCommit(false);
			
			//Create prepared statements for inserts.
			PreparedStatement insSchedTemp = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SCHEDULETEMPLATE` " +
					"(`creatorID`,`name`) " +
					"VALUES " +
					"(?,?)",Statement.RETURN_GENERATED_KEYS);
			
			PreparedStatement insShiftTemp = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SHIFTTEMPLATE` " +
					"(`schedTempID`,`day`,`startTime`,`endTime`) " +
					"VALUES " +
					"(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			PreparedStatement insShiftPos = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SHIFTPOS` " +
					"(`shiftTempID`,`positionName`,`posCount`) " +
					"VALUES " +
					"(?,?,?)");
			
			//Attempt to insert schedule template.
			insSchedTemp.setInt(1, createObj.getCreatorID());
			insSchedTemp.setString(2, createObj.getName());
			if (insSchedTemp.executeUpdate() != 1)
				throw new DBException("Failed to insert schedule template.");
			
			//Save the auto-generated schedule template ID.
			ResultSet temp = insSchedTemp.getGeneratedKeys();
			int schedTempID = -1;
			if (temp.next())
				schedTempID = temp.getInt(1);
			createObj.setSchedTempID(schedTempID);
			
			//Insert each shift template.
			for (ShiftTemplate shiftTemp : createObj.getShiftTemplates().toArray())
				{
				//Add schedule template ID before insert.
				shiftTemp.setSchedTempID(schedTempID);
				
				//Attempt to insert shift template.
				insShiftTemp.setInt(1, shiftTemp.getSchedTempID());
				insShiftTemp.setInt(2, shiftTemp.getDay());
				insShiftTemp.setTime(3, shiftTemp.getStartTime());
				insShiftTemp.setTime(4, shiftTemp.getEndTime());
				insShiftTemp.executeUpdate();
				if (insShiftTemp.getUpdateCount() != 1)
					throw new DBException("Failed to insert shift template");
				
				//Save the auto-generated shift template ID.
				temp = insShiftTemp.getGeneratedKeys();
				int shiftTempID = -1;
				if (temp.next())
					shiftTempID = temp.getInt(1);
				
				//Insert each shift position.
				for (ShiftPosition shiftPos : shiftTemp.getShiftPositions().toArray())
					{
					//Add shift template ID before insert.
					shiftPos.setShiftTempID(shiftTempID);
					
					//Attempt to insert shift position.
					insShiftPos.setInt(1, shiftPos.getShiftTempID());
					insShiftPos.setString(2, shiftPos.getPosName());
					insShiftPos.setInt(3, shiftPos.getPosCount());
					insShiftPos.executeUpdate();
					if (insShiftPos.getUpdateCount() != 1)
						throw new DBException("Failed to insert shift position");
					}
				}
			
			//Create succeeded! Commit all inserts and reset connection.
			conn.getConnection().commit();
			conn.getConnection().setAutoCommit(true);
			insSchedTemp.close();
			insShiftTemp.close();
			insShiftPos.close();
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
	
	@Override
	public boolean delete(ScheduleTemplate deleteObj, Employee caller)
			throws DBException, DBChangeException, DBDownException
		{
		if (deleteObj == null)
			throw new NullPointerException("Can not delete, given schedule template is null.");

		if (deleteObj.getSchedTempID() == null)
			throw new NullPointerException("Can not delete, no schedule template ID in given object.");
		
		// TODO Implement race check method.
		
		try
			{
			DBConnection conn = this.getConnection();
			
			PreparedStatement deleteStmt = conn.getConnection().prepareStatement(
					"DELETE FROM `WebAgenda`.`ScheduleTemplate` WHERE schedTempID = ?");
			
			deleteStmt.setInt(1, deleteObj.getSchedTempID());
			
			if (deleteStmt.executeUpdate() != 1)
				throw new DBException("Failed to delete schedule template.");
			
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete schedule template.", e);
			}
		
		return true;
		}
	
	/*
	 * (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject)
	 */
	@Override
	public ScheduleTemplate[] get(ScheduleTemplate searchTemplate,
			Employee caller) throws DBException, DBDownException
		{
		// Get all schedule templates for the given creator.
		if (searchTemplate == null)
			throw new NullPointerException("Cannot search with null template.");
		
		if (searchTemplate.getCreatorID() == null)
			throw new NullPointerException(
					"Creator ID required for schedule templates.");
		
		String select = String.format(
						"SELECT * FROM `WebAgenda`.`SCHEDULETEMPLATE` WHERE creatorID = %s;",
						searchTemplate.getCreatorID());
		
		ScheduleTemplate[] found;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet schTmpResult = stmt.executeQuery(select);
			found = parseResults(schTmpResult);
			fillSchedTemp(found, conn);
			
			conn.setAvailable(true);
			
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to get schedule templates.", e);
			}
		
		// Return schedule templates that matched search.
		return found;
		}
	
	@Override
	public boolean update(ScheduleTemplate oldObj, ScheduleTemplate updateObj,
			Employee caller) throws DBException, DBChangeException, DBDownException
		{
		// TODO Auto-generated method stub
		return false;
		}
	
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
				"SELECT * FROM `WebAgenda`.`SHIFTTEMPLATE` WHERE schedTempID = ?;");
		PreparedStatement shiftPosStmt = conn.getConnection().prepareStatement(
				"SELECT * FROM `WebAgenda`.`SHIFTPOS` WHERE shiftTempID = ?;");
		
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
	 * Attempts to create shift templates out of a given result set.
	 * 
	 * @param rs the result set returned by a database search.
	 * @return an array of shift templates retrieved from the result set, or null
	 *         if the result set was empty.
	 * @throws SQLException
	 */
	private ShiftTemplate[] parseShiftTemps(ResultSet rs) throws SQLException
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
	private ShiftPosition[] parseShiftPos(ResultSet rs) throws SQLException
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
	
	}
