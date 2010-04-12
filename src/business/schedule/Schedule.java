/**
 * business - Schedule.java
 */
package business.schedule;

import java.sql.Date;
import utilities.DoubleLinkedList;
import business.BusinessObject;

/**
 * Stores all information on a specific schedule, matching the structure of the
 * database. A filled schedule will list what days and times a set of employees
 * are working. May also be used as a template when searching for schedules in
 * the system.
 * 
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.2.0
 */
public class Schedule extends BusinessObject
    {
    /**
     * The internal DB ID of the schedule. This is for broker use only.
     */
    private int                     schedID   = -1;
    
    /**
     * The ID of the employee who created this schedule.
     */
    private int                     creatorID = -1;
    
    /**
     * The first day at which the schedule will be in effect.
     */
    private Date                    startDate = null;
    
    /**
     * The last day at which the schedule will be in effect.
     */
    private Date                    endDate   = null;
    
    /**
     * The list of shift templates that are part of the schedule template.
     */
    private DoubleLinkedList<Shift> shifts    = new DoubleLinkedList<Shift>();
    
    /**
     * Default/Empty constructor.
     */
    public Schedule()
        {
        }
    
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
     * Gets the schedule id
     * 
     * @return int
     */
    public int getSchedID()
        {
        return schedID;
        }
    
    /**
     * Sets the schedule id
     * 
     * @param int
     */
    public void setSchedID(int schedID)
        {
        this.schedID = schedID;
        }
    
    /**
     * Gets the creator id
     * 
     * @return int
     */
    public int getCreatorID()
        {
        return creatorID;
        }
    
    /**
     * Sets the creator id
     * 
     * @param int
     */
    public void setCreatorID(int creatorID)
        {
        this.creatorID = creatorID;
        }
    
    /**
     * Gets the start date
     * 
     * @return java.sql.Date
     */
    public Date getStartDate()
        {
        return startDate;
        }
    
    /**
     * Sets the start date
     * 
     * @param java.sql.Date
     */
    public void setStartDate(Date startDate)
        {
        this.startDate = startDate;
        }
    
    /**
     * Gets the end date
     * 
     * @return java.sql.Date
     */
    public Date getEndDate()
        {
        return endDate;
        }
    
    /**
     * Sets the end date
     * 
     * @param java.sql.Date
     */
    public void setEndDate(Date endDate)
        {
        this.endDate = endDate;
        }
    
    /**
     * Gets the shifts associated with schedule
     * 
     * @return DoubleLinkedList<Shift>
     */
    public DoubleLinkedList<Shift> getShifts()
        {
        return shifts;
        }
    
    /**
     * Sets the shifts
     * 
     * @param DoubleLinkedList<Shift>
     */
    public void setShifts(DoubleLinkedList<Shift> shifts)
        {
        this.shifts = shifts;
        }
    
    /*
     * (non-Javadoc)
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
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return schedID + "," + creatorID + "," + startDate + "," + endDate;
        }
    }
