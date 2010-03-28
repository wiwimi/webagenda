/**
 * business - Schedule.java
 */
package business.schedule;

import java.sql.Date;
import utilities.DoubleLinkedList;
import business.BusinessObject;

/**
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.2.0
 */
public class Schedule extends BusinessObject
	{
	
	/**
	 * The internal DB ID of the schedule.  This is for broker use only.
	 */
	private int schedID = -1;
	
	/**
	 * The ID of the employee who created this schedule.
	 */
	private int creatorID = -1;
	
	/**
	 * The first day at which the schedule will be in effect.
	 */
	private Date startDate = null;
	
	/**
	 * The last day at which the schedule will be in effect.
	 */
	private Date endDate = null;
	
	/**
	 * The list of shift templates that are part of the schedule template.
	 */
	private DoubleLinkedList<Shift> shifts = new DoubleLinkedList<Shift>();
	
	/**
	 * Default/Empty constructor.
	 */
	public Schedule() {}

	/**
	 * Constructor to create a full schedule as retrieved by the database.
	 * 
	 * @param schedID
	 * @param creatorID
	 * @param startDate
	 * @param endDate
	 */
	public Schedule(int schedID, int creatorID, Date startDate, Date endDate)
		{
		this.schedID = schedID;
		this.creatorID = creatorID;
		this.startDate = startDate;
		this.endDate = endDate;
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
	 * @return the creatorID
	 */
	public int getCreatorID()
		{
		return creatorID;
		}

	/**
	 * @param creatorID the creatorID to set
	 */
	public void setCreatorID(int creatorID)
		{
		this.creatorID = creatorID;
		}

	/**
	 * @return the startDate
	 */
	public Date getStartDate()
		{
		return startDate;
		}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate)
		{
		this.startDate = startDate;
		}

	/**
	 * @return the endDate
	 */
	public Date getEndDate()
		{
		return endDate;
		}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate)
		{
		this.endDate = endDate;
		}

	/**
	 * @return the shifts
	 */
	public DoubleLinkedList<Shift> getShifts()
		{
		return shifts;
		}
	
	/**
	 * @param shifts the shifts to set
	 */
	public void setShifts(DoubleLinkedList<Shift> shifts)
		{
		this.shifts = shifts;
		}

	/* (non-Javadoc)
	 * @see business.BusinessObject#clone()
	 */
	@Override
	public Schedule clone()
		{
		Schedule clone = (Schedule)super.clone();
		clone.startDate = (Date)this.startDate.clone();
		clone.endDate = (Date)this.endDate.clone();
		
		DoubleLinkedList<Shift> cloneShift = new DoubleLinkedList<Shift>();
		
		for (Shift shift : this.shifts.toArray())
			cloneShift.add(shift.clone());
		
		clone.setShifts(cloneShift);
		
		return clone;
		}
	}
