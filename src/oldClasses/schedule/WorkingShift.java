/**
 * business - WorkingShift.java
 */
package oldClasses.schedule;

import java.sql.Time;

import business.BusinessObject;
import business.schedule.Shift;


/**
 * @author peon-dev
 * @version 0.01.00
 *
 * @see business.schedule.Shift
 */
public class WorkingShift extends BusinessObject {
	
	
	private int shift_id									= -1;
	private int schedule_id									= -1;
	private Time start_time									= null;
	private Time end_time									= null;
	
	public WorkingShift(int schedule, int shift)
		{
		this.schedule_id = schedule;
		this.shift_id = shift;
		// TODO: Make default time difference (15 min)

		}

	public WorkingShift(int schedule, int shift, Time start, Time end)
	{
		this(schedule, shift);
		
		// Need to check to make sure start and end aren't in reverse order, or produce a negative.
		// Get absolute value of the two?
		
		
		this.start_time = start;
		this.end_time = end;
	}

	/**
	 * @return the shift_id
	 */
	public int getShift_id() {
		return shift_id;
	}

	/**
	 * @return the schedule_id
	 */
	public int getSchedule_id() {
		return schedule_id;
	}

	/**
	 * @return the start_time
	 */
	public Time getStart_time() {
		return start_time;
	}

	/**
	 * @return the end_time
	 */
	public Time getEnd_time() {
		return end_time;
	}
	
	
}
