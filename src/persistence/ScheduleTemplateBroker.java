/**
 * persistence - ScheduleTemplateBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import exception.DBDownException;
import exception.DBException;
import business.schedule.WorkingSchedule;
import business.schedule.WorkingShift;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class ScheduleTemplateBroker extends Broker<WorkingSchedule> {

	@Override
	public boolean create(WorkingSchedule createObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(WorkingSchedule deleteObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WorkingSchedule[] get(WorkingSchedule searchTemplate)
			throws DBException, DBDownException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkingSchedule[] parseResults(ResultSet rs) throws SQLException {
		WorkingSchedule[] schedList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			schedList = new WorkingSchedule[resultCount];
			
			// Return ResultSet to beginning to start retrieving skill.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				WorkingSchedule sched = new WorkingSchedule(rs.getInt("schedTempID"),rs.getInt("creatorID"));
				schedList[i] = sched;
				}
			}
		
		return schedList;
	}

	@Override
	public boolean update(WorkingSchedule updateObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

}
