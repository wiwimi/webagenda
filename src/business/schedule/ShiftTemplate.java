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
public class ShiftTemplate extends BusinessObject implements Comparable<ShiftTemplate>, Cloneable
	{
	/**
	 * The internal DB ID of the shift template.  This is for broker use only.
	 */
	private int shiftTempID = -1;
	
	/**
	 * The internal DB ID of the schedule template that the shift belongs to.
	 * This is for broker use only.
	 */
	private int schedTempID = -1;
	
	/**
	 * The day of the week that the shift is on.<br>
	 * 1 = Sunday.<br>
	 * 2 = Monday.<br>
	 * 3 = Tuesday.<br>
	 * 4 = Wednesday.<br>
	 * 5 = Thursday.<br>
	 * 6 = Friday.<br>
	 * 7 = Saturday.
	 */
	private int day = -1;
	
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
	 * Creates a new shiftTemplate holding a record from the database.
	 * 
	 * @param shiftTempID
	 * @param schedTempID
	 * @param startTime
	 * @param endTime
	 */
	public ShiftTemplate(int shiftTempID, int schedTempID, int day, Time startTime, Time endTime)
		{
		this.shiftTempID = shiftTempID;
		this.schedTempID = schedTempID;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		}

	/**
	 * @return the shiftTempID
	 */
	public int getShiftTempID()
		{
		return shiftTempID;
		}

	/**
	 * @param shiftTempID the shiftTempID to set
	 */
	public void setShiftTempID(int shiftTempID)
		{
		this.shiftTempID = shiftTempID;
		}

	/**
	 * @return the schedTempID
	 */
	public int getSchedTempID()
		{
		return schedTempID;
		}

	/**
	 * @param schedTempID the schedTempID to set
	 */
	public void setSchedTempID(int schedTempID)
		{
		this.schedTempID = schedTempID;
		}

	/**
	 * @return the day
	 */
	public int getDay()
		{
			return day;
		}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day)
		{
			this.day = day;
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
	
	/**
	 * @param shiftPositions the shiftPositions to set
	 */
	public void setShiftPositions(DoubleLinkedList<ShiftPosition> shiftPositions)
		{
		this.shiftPositions = shiftPositions;
		}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ShiftTemplate o)
		{
		if (this.getShiftTempID() < o.getShiftTempID())
			return -1;
		else if (this.getShiftTempID() > o.getShiftTempID())
			return 1;
		
		return 0;
		}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ShiftTemplate clone()
		{
		ShiftTemplate clone = (ShiftTemplate)super.clone();
		clone.startTime = (Time)this.startTime.clone();
		clone.endTime = (Time)this.endTime.clone();
		
		DoubleLinkedList<ShiftPosition> clonePos = new DoubleLinkedList<ShiftPosition>();
		ShiftPosition[] origPos = shiftPositions.toArray();
		
		for (ShiftPosition pos : origPos)
			{
			clonePos.add(pos.clone());
			}
		
		clone.setShiftPositions(clonePos);
		
		return clone;
		}

	@Override
	public String toString()
		{
		return shiftTempID+";"+schedTempID+";"+day+";"+startTime+";"+endTime;
		}
	}
