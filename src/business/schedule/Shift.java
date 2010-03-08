/**
 * business - Shift.java
 */
package business.schedule;

import java.sql.Time;
import utilities.DoubleLinkedList;

/**
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.2.0
 */
public class Shift
	{
	
	/**
	 * The internal DB ID of the shift. This is for broker use only.
	 */
	private Integer shiftID = null;
	
	/**
	 * The internal DB ID of the schedule the shift belongs to. This is for
	 * broker use only.
	 */
	private Integer schedID = null;
	
	/**
	 * The time at which the shift begins.
	 */
	private Time startTime = null;
	
	/**
	 * The time at which the shift ends.
	 */
	private Time endTime = null;
	
	/**
	 * The employees that will be working during this shift.
	 */
	private DoubleLinkedList<String> employees = new DoubleLinkedList<String>();
	
	/**
	 * Default/Empty constructor.
	 */
	public Shift() {}

	/**
	 * @return the shiftID
	 */
	public Integer getShiftID()
		{
		return shiftID;
		}

	/**
	 * @param shiftID the shiftID to set
	 */
	public void setShiftID(Integer shiftID)
		{
		this.shiftID = shiftID;
		}

	/**
	 * @return the schedID
	 */
	public Integer getSchedID()
		{
		return schedID;
		}

	/**
	 * @param schedID the schedID to set
	 */
	public void setSchedID(Integer schedID)
		{
		this.schedID = schedID;
		}

	/**
	 * @return the startTime
	 */
	public Time getStartTime()
		{
		return startTime;
		}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Time startTime)
		{
		this.startTime = startTime;
		}

	/**
	 * @return the endTime
	 */
	public Time getEndTime()
		{
		return endTime;
		}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Time endTime)
		{
		this.endTime = endTime;
		}

	/**
	 * @return the employees
	 */
	public DoubleLinkedList<String> getEmployees()
		{
		return employees;
		}
	
	
	
	
	
	}
