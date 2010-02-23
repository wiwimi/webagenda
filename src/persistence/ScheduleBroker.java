/**
 * persistence - ScheduleBroker.java
 */
package persistence;

import messagelog.Logging;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class ScheduleBroker {

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
	
	
}
