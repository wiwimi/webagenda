/**
 * persistence - ScheduleBroker.java
 */
package persistence;

import java.sql.ResultSet;
import business.Employee;
import java.sql.SQLException;

import exception.DBException;
import business.schedule.Schedule;
import messagelog.Logging;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class ScheduleBroker extends Broker<Schedule> {

	/** ScheduleBroker object for singleton pattern */
	private static ScheduleBroker broker_schedule				= null;
	
	private ScheduleBroker()
	{
		
	}
	
	public static ScheduleBroker getBroker()
	{
		if(broker_schedule == null)
		{
			Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Schedule Broker initialized");
			broker_schedule = new ScheduleBroker();
		}
		return broker_schedule;
	}

	@Override
	public boolean create(Schedule createObj,Employee caller) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Schedule deleteObj,Employee caller) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Schedule[] get(Schedule searchTemplate,Employee caller) throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Schedule updateObj,Employee caller) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Schedule[] parseResults(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
