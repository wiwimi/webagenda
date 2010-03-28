/**
 * 
 */
package business.schedule;

import utilities.DoubleLinkedList;
import business.BusinessObject;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
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
	
	public ScheduleTemplate(int schedTempID, int creatorID, String name)
		{
		this.schedTempID = schedTempID;
		this.creatorID = creatorID;
		this.name = name;
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
	 * @return the name
	 */
	public String getName()
		{
			return name;
		}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
		{
			this.name = name;
		}

	/**
	 * @return the shiftTemplates
	 */
	public DoubleLinkedList<ShiftTemplate> getShiftTemplates()
		{
		return shiftTemplates;
		}
	
	/**
	 * @param shiftTemplates the shiftTemplates to set
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
