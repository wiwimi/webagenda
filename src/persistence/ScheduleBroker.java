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
			throws DBException
		{
		// TODO Auto-generated method stub
		return false;
		}
	
	@Override
	public boolean delete(Schedule deleteObj, Employee caller)
			throws DBException
		{
		// TODO Auto-generated method stub
		return false;
		}
	
	/* (non-Javadoc)
	 * @see persistence.Broker#get(business.BusinessObject, business.Employee)
	 */
	@Override
	public Schedule[] get(Schedule searchTemplate, Employee caller)
			throws DBException, DBDownException
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
			throws DBException
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
				Employee[] shiftEmps = parseShiftEmps(shiftEmpRS);
				
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
	
	/**
	 * Parses a result set into an array of shift positions.
	 * 
	 * @param rs The result set to parse.
	 * @return the array of shift positions, or null if the result set was empty.
	 * @throws SQLException if there was an error during parsing.
	 */
	private Employee[] parseShiftEmps(ResultSet rs) throws SQLException
		{
		// List will be returned as null if no results are found.
		Employee[] empList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			empList = new Employee[resultCount];
			
			// Return ResultSet to beginning to start retrieving employees.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Employee emp;
				
				try {
					emp = new Employee(
						rs.getInt("empID"),
						rs.getString("givenName"),
						rs.getString("familyName"),
						rs.getString("username"),
						null,
						rs.getString("plevel")
					);
				} catch (DBException e) {
					// TODO Auto-generated catch block
					throw new SQLException("Attempting to create an Employee with an Invalid Permission Level");
				}
				
				emp.setSupervisorID(rs.getInt("supID"));
				emp.setBirthDate(rs.getDate("birthDate"));
				emp.setEmail(rs.getString("email"));
				emp.setActive(rs.getBoolean("active"));
				emp.setLastLogin(rs.getTimestamp("lastLogin"));
				emp.setPrefPosition(rs.getString("prefPosition"));
				emp.setPrefLocation(rs.getString("prefLocation"));
				
				if (emp.getSupervisorID() == 0)
					emp.setSupervisorID(null);
				
				empList[i] = emp;
				}
			
			}
		
		return empList;
		}
	}
