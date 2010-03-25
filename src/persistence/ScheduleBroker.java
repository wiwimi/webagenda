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
import java.util.Arrays;
import utilities.DoubleLinkedList;
import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import business.schedule.Schedule;
import business.schedule.Shift;
import messagelog.Logging;

/**
 * @author Daniel Wehr
 * @version 0.3.0
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
			createObj.setSchedID(null);
			throw new DBException("Failed to get schedules.", e);
			}
		
		notifyScheduleEmps(createObj, "A new schedule has been added for you.");
		
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
		
		raceCheck(deleteObj, caller);
		
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
	
	/**
	 * Gets all schedules in the database that match the given schedule. Only one
	 * attribute from the given schedule will be used for the search, in the
	 * following order:<br>
	 * <br>
	 * A) Schedule ID (schedTempID)<br>
	 * B) Creator ID (creatorID)<br>
	 * C) Start Date & End Date (startDate, endDate)<br>
	 * <br>
	 * Searches by start and end date will return all schedules that start or end
	 * within the given dates.
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
	public Schedule[] get(Schedule searchTemplate, Employee caller)
			throws DBException, DBChangeException, DBDownException
		{
		// Get all schedules for the given creator.
		if (searchTemplate == null)
			throw new NullPointerException("Cannot search with null template.");
		
		Schedule[] found;
		try
			{
			DBConnection conn = this.getConnection();
			
			PreparedStatement select = null;
			if (searchTemplate.getSchedID() != null)
				{
				select = conn.getConnection().prepareStatement(
					"SELECT * FROM `WebAgenda`.`SCHEDULE` WHERE schedID = ? ORDER BY schedID");
				select.setInt(1, searchTemplate.getSchedID());
				}
			else if (searchTemplate.getCreatorID() != null)
				{
				select = conn.getConnection().prepareStatement(
					"SELECT * FROM `WebAgenda`.`SCHEDULE` WHERE creatorID = ? ORDER BY schedID");
				select.setInt(1, searchTemplate.getCreatorID());
				}
			else if (searchTemplate.getStartDate() != null && searchTemplate.getEndDate() != null)
				{
				select = conn.getConnection().prepareStatement(
					"SELECT * FROM `WebAgenda`.`SCHEDULE` WHERE (`startDate` BETWEEN ? AND ?) OR (`endDate` BETWEEN ? AND ?) ORDER BY schedID");
				select.setDate(1, searchTemplate.getStartDate());
				select.setDate(2, searchTemplate.getEndDate());
				select.setDate(3, searchTemplate.getStartDate());
				select.setDate(4, searchTemplate.getEndDate());
				}
			
			//If nothing is being searched for, return null.
			if (select == null)
				{
				conn.setAvailable(true);
				return null;
				}
			
			ResultSet schResults = select.executeQuery();
			found = parseResults(schResults);
			fillSched(found, conn);
			
			conn.setAvailable(true);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to get schedule(s).", e);
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
	
	/**
	 * Steps through a schedule and sorts all shifts, as well as all employees
	 * within them, to match the order that would be returned by the database.
	 * 
	 * @param toSort the schedule to sort.
	 * @return true when the sort is complete.
	 */
	public boolean sortSchedule(Schedule toSort)
		{
		//Get the list of shifts.
		DoubleLinkedList<Shift> shifts = toSort.getShifts();
		
		//Sort employees within shifts first.
		for (int i = 0; i < shifts.size(); i++)
			{
			//Get employees and sort.
			DoubleLinkedList<Employee> emps = shifts.get(i).getEmployees(); 
			Employee[] sortedEmps = emps.toArray();
			Arrays.sort(sortedEmps);
			
			//Add sorted employees back to list.
			emps.clear();
			for (int j = 0; j < sortedEmps.length; j++)
				emps.add(sortedEmps[j]);
			}
		
		//Employees sorted, now sort shifts.
		Shift[] sortedShifts = shifts.toArray();
		Arrays.sort(sortedShifts);
		
		//Add sorted shifts back to list.
		shifts.clear();
		for (int k = 0; k < sortedShifts.length; k++)
			shifts.add(sortedShifts[k]);
		
		return true;
		}
	
	public boolean notifyScheduleEmps(Schedule sched, String customMessage)
		{
		//TODO send notification to all employees in the system.
		
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
						rs.getInt("creatorID"), rs.getDate("startDate"), rs.getDate("endDate"));
				
				schedList[i] = st;
				}
			
			}
		
		return schedList;
		}
	
	/**
	 * Attempts to create shift templates out of a given result set.
	 * 
	 * @param rs the result set returned by a database search.
	 * @return an array of shift templates retrieved from the result set, or null
	 *         if the result set was empty.
	 * @throws SQLException
	 */
	protected Shift[] parseShifts(ResultSet rs) throws SQLException
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
				"SELECT * FROM `WebAgenda`.`SHIFT` WHERE schedID = ? ORDER BY shiftID;");
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
	private boolean raceCheck(Schedule old, Employee caller) throws DBChangeException, DBException, DBDownException
		{
		if (old == null || old.getSchedID() == null)
			throw new DBException("Unable to validate old schedule template, is null or has no schedTempID.");
		
		//Get schedule from DB with matching scheduleID.
		Schedule[] fromDB = this.get(old, caller);
		
		//If no schedule template returned, throw exception.
		if (fromDB == null)
			throw new DBChangeException("No matching record found, schedule may have been deleted.");
		
		Schedule fetched = fromDB[0];
		
		//Sort schedule before starting compares.
		sortSchedule(old);
		
		//Compare dates of old/fetched. SchedID and CreatorID do not need to be checked.
		if (!old.getStartDate().equals(fetched.getStartDate()) ||
				!old.getEndDate().equals(fetched.getEndDate()))
			throw new DBChangeException("Schedule dates have been modified.");
		
		//Compare number of shifts.
		if (old.getShifts().size() != fetched.getShifts().size())
			throw new DBChangeException("Schedule num of shifts modified.");
		
		//Compare shift templates individually between old/fetched.
		for (int i = 0; i < old.getShifts().size(); i++)
			{
			Shift sh1 = old.getShifts().get(i);
			Shift sh2 = fetched.getShifts().get(i);
			
			//Compare shift template attributes.
			if (sh1.getShiftID() != sh2.getShiftID() ||
					sh1.getDay() != sh2.getDay() ||
					!sh1.getStartTime().equals(sh2.getStartTime()) ||
					!sh1.getEndTime().equals(sh2.getEndTime()))
				throw new DBChangeException("Shift changed.");
			
			//Compare number of shift employees.
			if (sh1.getEmployees().size() != sh2.getEmployees().size())
				throw new DBChangeException("Shift employees changed.");
			
			//Compare shift employees individually between old/fetched.
			for (int j = 0; j < sh1.getEmployees().size(); j++)
				{
				Employee emp1 = sh1.getEmployees().get(j);
				Employee emp2 = sh2.getEmployees().get(j);
				
				//Only ID numbers need to be compared.
				if (!emp1.getEmpID().equals(emp2.getEmpID()))
					throw new DBChangeException("Shift employees changed: "+emp1.getEmpID()+" vs "+emp2.getEmpID());
				}
			}
		
		return true;
		}
	}
