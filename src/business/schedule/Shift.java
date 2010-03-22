/**
 * business - Shift.java
 */
package business.schedule;

import java.sql.Time;
import business.Employee;
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
	 * The day of the week that the shift is on.
	 */
	private Integer day = null;
	
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
	private DoubleLinkedList<Employee> employees = new DoubleLinkedList<Employee>();
	
	/**
	 * Default/Empty constructor.
	 */
	public Shift() {}
	
	/**
	 * Constructor to create a full shift, as retrieved by the database.
	 * 
	 * @param shiftID
	 * @param schedID
	 * @param day
	 * @param startTime
	 * @param endTime
	 */
	public Shift(Integer shiftID, Integer schedID, Integer day, Time startTime, Time endTime)
		{
		this.shiftID = shiftID;
		this.schedID = schedID;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		}

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
	 * @return the day
	 */
	public Integer getDay()
		{
			return day;
		}

	/**
	 * @param day the day to set
	 */
	public void setDay(Integer day)
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
	 * @return the employees
	 */
	public DoubleLinkedList<Employee> getEmployees()
		{
		return employees;
		}
	
	
	
	
	
	}
