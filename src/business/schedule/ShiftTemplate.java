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
	 * @param shiftTempID int
	 * @param schedTempID int
	 * @param day int
	 * @param startTime Time
	 * @param endTime Time
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
	 * Gets the shift template id
	 * @return int
	 */
	public int getShiftTempID()
		{
		return shiftTempID;
		}

	/**
	 * Sets the shift template id
	 * @param int
	 */
	public void setShiftTempID(int shiftTempID)
		{
		this.shiftTempID = shiftTempID;
		}

	/**
	 * Gets the schedule temp id
	 * @return int
	 */
	public int getSchedTempID()
		{
		return schedTempID;
		}

	/**
	 * Sets the schedule template id
	 * @param int
	 */
	public void setSchedTempID(int schedTempID)
		{
		this.schedTempID = schedTempID;
		}

	/**
	 * Gets the day
	 * @return int
	 */
	public int getDay()
		{
			return day;
		}

	/**
	 * Sets the day
	 * @param int
	 */
	public void setDay(int day)
		{
			this.day = day;
		}

	/**
	 * Gets the start time
	 * @return Time
	 */
	public Time getStartTime()
		{
		return startTime;
		}

	/**
	 * Sets the start time
	 * @param Time
	 */
	public void setStartTime(Time startTime)
		{
		this.startTime = startTime;
		}

	/**
	 * Gets the end time
	 * @return Time
	 */
	public Time getEndTime()
		{
		return endTime;
		}

	/**
	 * Sets the end time
	 * @param Time
	 */
	public void setEndTime(Time endTime)
		{
		this.endTime = endTime;
		}

	/**
	 * Gets the Shift Positions in this template
	 * @return DoubleLinkedList<ShiftPosition>
	 */
	public DoubleLinkedList<ShiftPosition> getShiftPositions()
		{
		return shiftPositions;
		}
	
	/**
	 * Sets the Shift Positions in this template
	 * @param DoubleLinkedList<ShiftPosition>
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
		if (this.day < o.day)
			return -1;
		else if (this.day > o.day)
			return 1;
		
		if (this.startTime.compareTo(o.startTime) != 0)
			return this.startTime.compareTo(o.startTime);
		
		return this.endTime.compareTo(o.endTime);
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
		
		for (ShiftPosition pos : shiftPositions.toArray())
			clonePos.add(pos.clone());
		
		clone.setShiftPositions(clonePos);
		
		return clone;
		}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
		{
		return shiftTempID+";"+schedTempID+";"+day+";"+startTime+";"+endTime;
		}
	}
