/**
 * persistence - ShiftTemplateBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import oldClasses.schedule.WorkingShift;

import exception.DBDownException;
import exception.DBException;
import business.Skill;
import business.schedule.*;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class ShiftTemplateBroker extends Broker<WorkingShift> {

	@Override
	public boolean create(WorkingShift createObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(WorkingShift deleteObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WorkingShift[] get(WorkingShift searchTemplate) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkingShift[] parseResults(ResultSet rs) throws SQLException {
		// List will be returned as null if no results are found.
		WorkingShift[] shiftList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			shiftList = new WorkingShift[resultCount];
			
			// Return ResultSet to beginning to start retrieving skill.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				WorkingShift shift = new WorkingShift(rs.getInt("shiftTempID"),rs.getInt("schedTempID"),(Time) rs.getObject("startTime"),
						(Time) rs.getObject("endTime"));
				shiftList[i] = shift;
				}
			}
		
		return shiftList;
	}

	@Override
	public boolean update(WorkingShift updateObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
