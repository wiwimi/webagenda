/**
 * business - Shift.java
 */
package business.schedule;

import java.sql.Time;
import business.BusinessObject;
import business.Employee;
import utilities.DoubleLinkedList;

/**
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.2.0
 */
public class Shift extends BusinessObject implements Comparable<Shift>
	{
	
	/**
	 * The internal DB ID of the shift. This is for broker use only.
	 */
	private int shiftID = -1;
	
	/**
	 * The internal DB ID of the schedule the shift belongs to. This is for
	 * broker use only.
	 */
	private int schedID = -1;
	
	/**
	 * The day of the week that the shift is on.
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
	public Shift(int shiftID, int schedID, int day, Time startTime, Time endTime)
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
	public int getShiftID()
		{
		return shiftID;
		}

	/**
	 * @param shiftID the shiftID to set
	 */
	public void setShiftID(int shiftID)
		{
		this.shiftID = shiftID;
		}

	/**
	 * @return the schedID
	 */
	public int getSchedID()
		{
		return schedID;
		}

	/**
	 * @param schedID the schedID to set
	 */
	public void setSchedID(int schedID)
		{
		this.schedID = schedID;
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
	 * @return the employees
	 */
	public DoubleLinkedList<Employee> getEmployees()
		{
		return employees;
		}
	
	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(DoubleLinkedList<Employee> employees)
		{
		this.employees = employees;
		}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Shift o)
		{
		if (this.getShiftID() < o.getShiftID())
			return -1;
		else if (this.getShiftID() > o.getShiftID())
			return 1;
		
		return 0;
		}

	/* (non-Javadoc)
	 * @see business.BusinessObject#clone()
	 */
	@Override
	public Shift clone()
		{
		Shift clone = (Shift)super.clone();
		clone.startTime = (Time)this.startTime.clone();
		clone.endTime = (Time)this.endTime.clone();
		
		DoubleLinkedList<Employee> cloneEmp = new DoubleLinkedList<Employee>();
		
		for (Employee emp : this.employees.toArray())
			cloneEmp.add(emp.clone());
		
		clone.setEmployees(cloneEmp);
		
		return clone;
		}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
		{
		return shiftID+";"+schedID+";"+day+";"+startTime+";"+endTime;
		}
	}
