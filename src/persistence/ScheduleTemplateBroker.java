/**
 * 
 */
package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import exception.DBDownException;
import exception.DBException;
import application.DBConnection;
import business.schedule.ScheduleTemplate;
import business.schedule.ShiftPosition;
import business.schedule.ShiftTemplate;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
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
	 * @return ScheduleTemplateBroker result from the Broker request as its respective
	 *         Broker object.
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
	public boolean create(ScheduleTemplate createObj) throws DBException,
			DBDownException
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public boolean delete(ScheduleTemplate deleteObj) throws DBException,
			DBDownException
		{
		// TODO Auto-generated method stub
		return false;
		}

	/* (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject)
	 */
	@Override
	public ScheduleTemplate[] get(ScheduleTemplate searchTemplate)
			throws DBException, DBDownException
		{
		// Get all schedule templates for the given creator.
		if (searchTemplate == null)
			throw new NullPointerException("Cannot search with null template.");
		
		if (searchTemplate.getCreatorID() == null)
			throw new NullPointerException("Creator ID required for schedule templates.");
		
		String select = String.format(
				"SELECT s.*,e.empID AS 'empID' FROM `WebAgenda`.`SCHEDULETEMPLATE` s JOIN `WebAgenda`.`EMPLOYEE` e ON s.creatorID = e.empRecordID WHERE e.empID = %s;",
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
			throw new DBException("Failed to get schedule templates.",e);
			}
		
		// Return schedule templates that matched search.
		return found;
		}

	@Override
	public boolean update(ScheduleTemplate updateObj) throws DBException,
			DBDownException
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public ScheduleTemplate[] parseResults(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		ScheduleTemplate[] stList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			stList = new ScheduleTemplate[resultCount];
			
			// Return ResultSet to beginning to start retrieving schedule templates.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				ScheduleTemplate st = new ScheduleTemplate(rs.getInt("schedTempID"),
						rs.getInt("empID"),rs.getString("name"));
				
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
	private void fillSchedTemp(ScheduleTemplate[] templates, DBConnection conn) throws SQLException
		{
		//Prepare the select statements to pull additional data.
		PreparedStatement shiftTempStmt = conn.getConnection().prepareStatement(
				"SELECT * FROM `WebAgenda`.`SHIFTTEMPLATE` WHERE schedTempID = ?");
		PreparedStatement shiftPosStmt = conn.getConnection().prepareStatement(
				"SELECT * FROM `WebAgenda`.`SHIFTPOS` WHERE shiftTempID = ?");
		
		for (ScheduleTemplate schedTemp : templates)
			{
			//Get shift templates for matching schedule template.
			shiftTempStmt.setInt(1, schedTemp.getSchedTempID());
			ResultSet shiftRS = shiftTempStmt.executeQuery();
			ShiftTemplate[] shifts = parseShiftTemps(shiftRS);
			
			for (ShiftTemplate shift : shifts)
				{
				//Get shift positions for matching shift template.
				shiftPosStmt.setInt(1, shift.getShiftTempID());
				ResultSet shiftPosRS = shiftPosStmt.executeQuery();
				ShiftPosition[] positions = parseShiftPos(shiftPosRS);
				
				//Add shift positions to shift template.
				for (ShiftPosition pos : positions)
					{
					shift.getShiftPositions().add(pos);
					}
				
				// Add shifts templates to schedule template.
				schedTemp.getShiftTemplates().add(shift);
				}
			}
		
		//Release resources used by prepared statements.
		shiftTempStmt.close();
		shiftPosStmt.close();
		}
	
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
				ShiftTemplate st = new ShiftTemplate(rs.getInt("shiftTempID"),
						rs.getInt("schedTempID"),
						rs.getInt("day"),
						rs.getString("startTime"),
						rs.getString("endTime"));
				
				stList[i] = st;
				}
			
			}
		
		return stList;
		}
	
	/**
	 * Parses a result set into an array of shift positions.
	 * 
	 * @param rs The resultset to parse.
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
				ShiftPosition sp = new ShiftPosition(rs.getInt("shiftTempID"),
						rs.getString("positionName"),
						rs.getInt("posCount"));
				
				spList[i] = sp;
				}
			
			}
		
		return spList;
		}
	
	}
