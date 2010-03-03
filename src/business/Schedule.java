/**
 * business - Schedule.java
 */
package business;

import java.util.Date;

import business.schedule.WorkingShift;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class Schedule extends BusinessObject {

	/** Date when the schedule starts. */
	private Date start_date									= null;
	/** Date when schedule ends */
	private Date end_date									= null;
	/** An array of shifts that are required for the schedule to be complete. */
	private WorkingShift[] scheduled_shifts				= null;

	public Date getStart_date() {
		return start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public WorkingShift[] getScheduled_shifts() {
		return scheduled_shifts;
	}
	
	
	
}
