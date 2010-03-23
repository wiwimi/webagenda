/**
 * persistence - ScheduleBroker.java
 */
package persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import application.DBConnection;
import business.Employee;
import java.sql.SQLException;
import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import business.schedule.Schedule;
import business.schedule.Shift;
import messagelog.Logging;

/**
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.2.0
 */
public class ScheduleBroker extends Broker<Schedule>
	{
	/** ScheduleBroker object for singleton pattern */
	private static ScheduleBroker	broker_schedule	= null;
	
	private ScheduleBroker()
		{
		
		}
	
	public static ScheduleBroker getBroker()
		{
		if (broker_schedule == null)
			{
			Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY,
					"Schedule Broker initialized");
			broker_schedule = new ScheduleBroker();
			}
		return broker_schedule;
		}
	
	@Override
	public boolean create(Schedule createObj, Employee caller)
			throws DBException, DBDownException
		{
		if (createObj == null)
			throw new NullPointerException("Can not create, given schedule is null.");
		
		DBConnection conn = null;
		try
			{
			//Get connection, disable autocommit.
			conn = this.getConnection();
			conn.getConnection().setAutoCommit(false);
			
			//Create prepared statements for inserts.
			PreparedStatement insSched = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SCHEDULE` " +
					"(`startDate`,`endDate`,`creatorID`) " +
					"VALUES " +
					"(?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			PreparedStatement insShift = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SHIFT` " +
					"(`schedID`,`day`,`startTime`,`endTime`) " +
					"VALUES " +
					"(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
			
			PreparedStatement insShiftEmp = conn.getConnection().prepareStatement(
					"INSERT INTO `WebAgenda`.`SHIFTEMP` " +
					"(`shiftID`,`empID`) " +
					"VALUES " +
					"(?,?)");
			
			//Attempt to insert schedule template.
			insSched.setDate(1, createObj.getStartDate());
			insSched.setDate(2, createObj.getEndDate());
			insSched.setInt(3, createObj.getCreatorID());
			if (insSched.executeUpdate() != 1)
				throw new DBException("Failed to insert schedule template.");
			
			//Save the auto-generated schedule ID.
			ResultSet temp = insSched.getGeneratedKeys();
			if (temp.next())
				createObj.setSchedID(temp.getInt(1));
			
			//Insert each shift template.
			for (Shift shift : createObj.getShifts().toArray())
				{
				//Add schedule template ID before insert.
				shift.setSchedID(createObj.getSchedID());
				
				//Attempt to insert shift template.
				insShift.setInt(1, shift.getSchedID());
				insShift.setInt(2, shift.getDay());
				insShift.setTime(3, shift.getStartTime());
				insShift.setTime(4, shift.getEndTime());
				if (insShift.executeUpdate() != 1)
					throw new DBException("Failed to insert shift");
				
				//Save the auto-generated shift template ID.
				temp = insShift.getGeneratedKeys();
				if (temp.next())
					shift.setShiftID(temp.getInt(1));
				
				//Insert each employee into SHIFTEMP.
				for (Employee emp : shift.getEmployees().toArray())
					{
					//Attempt to insert ShiftEmp record.
					insShiftEmp.setInt(1, shift.getShiftID());
					insShiftEmp.setInt(2, emp.getEmpID());
					if (insShiftEmp.executeUpdate() != 1)
						throw new DBException("Failed to insert ShiftEmp.");
					}
				}
			
			//Create succeeded! Commit all inserts and reset connection.
			conn.getConnection().commit();
			conn.getConnection().setAutoCommit(true);
			insSched.close();
			insShift.close();
			insShiftEmp.close();
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
			throw new DBException("Failed to get schedules.", e);
			}
		
		return true;
		}
	
	@Override
	public boolean delete(Schedule deleteObj, Employee caller)
			throws DBException, DBDownException
		{
		if (deleteObj == null)
			throw new NullPointerException("Can not delete, given schedule is null.");

		if (deleteObj.getSchedID() == null)
			throw new NullPointerException("Can not delete, no schedule ID in given object.");
		
		// TODO Implement race check method.
		
		try
			{
			DBConnection conn = this.getConnection();
			
			PreparedStatement deleteStmt = conn.getConnection().prepareStatement(
					"DELETE FROM `WebAgenda`.`SCHEDULE` WHERE schedID = ?");
			
			deleteStmt.setInt(1, deleteObj.getSchedID());
			
			if (deleteStmt.executeUpdate() != 1)
				throw new DBException("Failed to delete schedule.");
			
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete schedule.", e);
			}
		
		return true;
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject, business.Employee)
	 */
	@Override
	public Schedule[] get(Schedule searchTemplate, Employee caller)
			throws DBException, DBChangeException, DBDownException
		{
		// Get all schedules for the given creator.
		if (searchTemplate == null)
			throw new NullPointerException("Cannot search with null template.");
		
		String select = String.format(
						"SELECT * FROM `WebAgenda`.`SCHEDULE` WHERE creatorID = %s;",
						searchTemplate.getCreatorID());
		
		Schedule[] found;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet schResult = stmt.executeQuery(select);
			found = parseResults(schResult);
			fillSched(found, conn);
			
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
	public boolean update(Schedule oldSched, Schedule updateObj, Employee caller)
			throws DBException, DBChangeException, DBDownException
		{
		// TODO Auto-generated method stub
		return false;
		}
	
	@Override
	protected Schedule[] parseResults(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		Schedule[] schedList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			schedList = new Schedule[resultCount];
			
			// Return ResultSet to beginning to start retrieving schedules.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Schedule st = new Schedule(rs.getInt("schedID"),
						rs.getDate("startDate"), rs.getDate("endDate"), rs.getInt("creatorID"));
				
				schedList[i] = st;
				}
			
			}
		
		return schedList;
		}
	
	/**
	 * Fetches all shift templates and shift position objects from the database
	 * for each shiftTemplate.
	 * 
	 * @param templates
	 * @param conn
	 */
	private void fillSched(Schedule[] schedules, DBConnection conn)
			throws SQLException
		{
		// Prepare the select statements to pull additional data.
		PreparedStatement shiftStmt = conn.getConnection().prepareStatement(
				"SELECT * FROM `WebAgenda`.`SHIFT` WHERE schedID = ?;");
		PreparedStatement shiftEmpStmt = conn.getConnection().prepareStatement(
				"SELECT e.* FROM `WebAgenda`.`EMPLOYEE` e JOIN `WebAgenda`.`SHIFTEMP` se ON e.empID = se.empID WHERE se.shiftID = ?;");
		
		for (Schedule sched : schedules)
			{
			// Get shifts for matching schedule.
			shiftStmt.setInt(1, sched.getSchedID());
			ResultSet shiftRS = shiftStmt.executeQuery();
			Shift[] shifts = parseShifts(shiftRS);
			
			for (Shift shift : shifts)
				{
				// Get shift employees for matching shift.
				shiftEmpStmt.setInt(1, shift.getShiftID());
				ResultSet shiftEmpRS = shiftEmpStmt.executeQuery();
				Employee[] shiftEmps = EmployeeBroker.getBroker().parseResults(shiftEmpRS);
				
				// Add employees to shift.
				for (Employee emp : shiftEmps)
					{
					shift.getEmployees().add(emp);
					}
				
				// Add shifts templates to schedule template.
				sched.getShifts().add(shift);
				}
			}
		
		// Release resources used by prepared statements.
		shiftStmt.close();
		shiftEmpStmt.close();
		}
	
	/**
	 * Attempts to create shift templates out of a given result set.
	 * 
	 * @param rs the result set returned by a database search.
	 * @return an array of shift templates retrieved from the result set, or null
	 *         if the result set was empty.
	 * @throws SQLException
	 */
	private Shift[] parseShifts(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		Shift[] stList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			stList = new Shift[resultCount];
			
			// Return ResultSet to beginning to start retrieving shift templates.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Shift st = new Shift(rs.getInt("shiftID"), rs.getInt("schedID"),
						rs.getInt("day"), rs.getTime("startTime"), rs.getTime("endTime"));
				
				stList[i] = st;
				}
			
			}
		
		return stList;
		}
	
	}
