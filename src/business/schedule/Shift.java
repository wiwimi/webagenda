/**
 * business - Shift.java
 */
package business.schedule;

import java.sql.Time;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class Shift {

	/** When the working shift starts */
	private Time start_time									= null;
	/** When the working shift ends */
	private Time end_time									= null;

	public Shift(Time start, Time end)
	{
		this.start_time = start;
		this.end_time = end;
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

	/**
	 * @param startTime the start_time to set
	 */
	public void setStart_time(Time startTime) {
		start_time = startTime;
	}

	/**
	 * @param endTime the end_time to set
	 */
	public void setEnd_time(Time endTime) {
		end_time = endTime;
	}
	
	
	
}
