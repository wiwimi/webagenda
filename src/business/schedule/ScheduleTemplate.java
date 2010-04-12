/**
 * 
 */
package business.schedule;

import utilities.DoubleLinkedList;
import business.BusinessObject;

/**
 * Stores all information on a schedule template, matching the database.
 * Schedule templates match required positions to shifts, and are used to
 * generate new filled schedules that assign work times to specific employees.
 * 
 * @author Daniel Wehr, Daniel Kettle
 * @version 0.1.00
 */
public class ScheduleTemplate extends BusinessObject implements Cloneable
	{
	/**
	 * The internal DB ID of the schedule template.  This is for broker use only.
	 */
	private int schedTempID = -1;
	
	/**
	 * The ID of the employee who created this schedule.
	 */
	private int creatorID = -1;
	
	/**
	 * The name given to the template.
	 */
	private String name;
	
	/**
	 * The list of shift templates that are part of the schedule template.
	 */
	private DoubleLinkedList<ShiftTemplate> shiftTemplates = new DoubleLinkedList<ShiftTemplate>();
	
	/**
	 * Default/Empty constructor.
	 */
	public ScheduleTemplate() {}
	
	/**
	 * Constructor for a ScheduleTemplate
	 * @param schedTempID int
	 * @param creatorID int
	 * @param name String
	 */
	public ScheduleTemplate(int schedTempID, int creatorID, String name)
		{
		this.schedTempID = schedTempID;
		this.creatorID = creatorID;
		this.name = name;
		}

	/**
	 * Gets the schedule template id
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
	 * Gets the creator id
	 * @return int
	 */
	public int getCreatorID()
		{
		return creatorID;
		}

	/**
	 * Sets the creator id
	 * @param int
	 */
	public void setCreatorID(int creatorID)
		{
		this.creatorID = creatorID;
		}

	/**
	 * Gets the name of the schedule template
	 * @return String
	 */
	public String getName()
		{
			return name;
		}

	/**
	 * Sets the name of the schedule template
	 * @param String
	 */
	public void setName(String name)
		{
			this.name = name;
		}

	/**
	 * Gets the template shifts of the schedule template
	 * @return DoubleLinkedList<Shift>
	 */
	public DoubleLinkedList<ShiftTemplate> getShiftTemplates()
		{
		return shiftTemplates;
		}
	
	/**
	 * Sets the template shifts of the schedule template
	 * @param shiftTemplates DoubleLinkedList<Shift>
	 */
	public void setShiftTemplates(DoubleLinkedList<ShiftTemplate> shiftTemplates)
		{
		this.shiftTemplates = shiftTemplates;
		}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ScheduleTemplate clone()
		{
		ScheduleTemplate clone = (ScheduleTemplate)super.clone();
		
		DoubleLinkedList<ShiftTemplate> cloneShift = new DoubleLinkedList<ShiftTemplate>();
		
		for (ShiftTemplate shift : shiftTemplates.toArray())
			cloneShift.add(shift.clone());
		
		clone.setShiftTemplates(cloneShift);
		
		return clone;
		}
	}
