/**
 * business - Shift.java
 */
package business.schedule;

import java.sql.Time;
import business.BusinessObject;
import business.Employee;
import utilities.DoubleLinkedList;

/**
 * Defines the the day of the week, start time and end time of a shift for a set
 * of employees.
 * 
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.2.0
 */
public class Shift extends BusinessObject implements Comparable<Shift>
    {
    
    /**
     * The internal DB ID of the shift. This is for broker use only.
     */
    private int                        shiftID   = -1;
    
    /**
     * The internal DB ID of the schedule the shift belongs to. This is for
     * broker use only.
     */
    private int                        schedID   = -1;
    
    /**
     * The day of the week that the shift is on.
     */
    private int                        day       = -1;
    
    /**
     * The time at which the shift begins.
     */
    private Time                       startTime = null;
    
    /**
     * The time at which the shift ends.
     */
    private Time                       endTime   = null;
    
    /**
     * The employees that will be working during this shift.
     */
    private DoubleLinkedList<Employee> employees = new DoubleLinkedList<Employee>();
    
    /**
     * Default/Empty constructor.
     */
    public Shift()
        {
        }
    
    /**
     * Constructor to create a full shift, as retrieved by the database.
     * 
     * @param shiftID int
     * @param schedID int
     * @param day int
     * @param startTime Time
     * @param endTime Time
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
     * Gets shift id
     * 
     * @return int
     */
    public int getShiftID()
        {
        return shiftID;
        }
    
    /**
     * Sets shift Id
     * 
     * @param int
     */
    public void setShiftID(int shiftID)
        {
        this.shiftID = shiftID;
        }
    
    /**
     * Gets schedule id
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
     * Gets the day
     * 
     * @return int
     */
    public int getDay()
        {
        return day;
        }
    
    /**
     * Sets the day
     * 
     * @param int
     */
    public void setDay(int day)
        {
        this.day = day;
        }
    
    /**
     * Gets the start time
     * 
     * @return Time
     */
    public Time getStartTime()
        {
        return startTime;
        }
    
    /**
     * Sets the start time
     * 
     * @param Time
     */
    public void setStartTime(Time startTime)
        {
        this.startTime = startTime;
        }
    
    /**
     * Gets the end time
     * 
     * @return Time
     */
    public Time getEndTime()
        {
        return endTime;
        }
    
    /**
     * Sets the end time
     * 
     * @param Time
     */
    public void setEndTime(Time endTime)
        {
        this.endTime = endTime;
        }
    
    /**
     * Gets the Employees on this shift
     * 
     * @return DoubleLinkedList<Employee>
     */
    public DoubleLinkedList<Employee> getEmployees()
        {
        return employees;
        }
    
    /**
     * Sets the Employees on this shift
     * 
     * @param DoubleLinkedList<Shift>
     */
    public void setEmployees(DoubleLinkedList<Employee> employees)
        {
        this.employees = employees;
        }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Shift o)
        {
        if (this.day < o.day)
            return -1;
        else if (this.day > o.day)
            return 1;
        
        if (this.startTime.compareTo(o.startTime) != 0)
            return this.startTime.compareTo(o.startTime);
        
        return this.endTime.compareTo(o.endTime);
        }
    
    /*
     * (non-Javadoc)
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
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return shiftID + ";" + schedID + ";" + day + ";" + startTime + ";" + endTime;
        }
    }
