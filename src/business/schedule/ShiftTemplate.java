/**
 * 
 */
package business.schedule;

import java.sql.Time;
import utilities.DoubleLinkedList;
import business.BusinessObject;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class ShiftTemplate extends BusinessObject
	{
	/**
	 * The internal DB ID of the shift template.  This is for broker use only.
	 */
	private Integer shiftTempID = null;
	
	/**
	 * The internal DB ID of the schedule template that the shift belongs to.
	 * This is for broker use only.
	 */
	private Integer schedTempID = null;
	
	/**
	 * The time at which the shift begins.
	 */
	private Time startTime = null;
	
	/**
	 * The time at which the shift ends.
	 */
	private Time endTime = null;
	
	/**
	 * A list of the positions which are needed during the shift.
	 */
	private DoubleLinkedList<ShiftPosition> shiftPositions = new DoubleLinkedList<ShiftPosition>();
	
	/**
	 * Default/Empty Constructor.
	 */
	public ShiftTemplate() {}

	/**
	 * @return the shiftTempID
	 */
	public Integer getShiftTempID()
		{
		return shiftTempID;
		}

	/**
	 * @param shiftTempID the shiftTempID to set
	 */
	public void setShiftTempID(Integer shiftTempID)
		{
		this.shiftTempID = shiftTempID;
		}

	/**
	 * @return the schedTempID
	 */
	public Integer getSchedTempID()
		{
		return schedTempID;
		}

	/**
	 * @param schedTempID the schedTempID to set
	 */
	public void setSchedTempID(Integer schedTempID)
		{
		this.schedTempID = schedTempID;
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
	 * @return the shiftPositions
	 */
	public DoubleLinkedList<ShiftPosition> getShiftPositions()
		{
		return shiftPositions;
		}
	
	
	}
