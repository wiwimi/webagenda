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
public class ScheduleTemplate extends BusinessObject
	{
	/**
	 * The internal DB ID of the schedule template.  This is for broker use only.
	 */
	private Integer schedTempID = null;
	
	/**
	 * The ID of the employee who created this schedule.
	 */
	private Integer creatorID = null;
	
	/**
	 * The list of shift templates that are part of the schedule template.
	 */
	private DoubleLinkedList<ShiftTemplate> shiftTemplates = new DoubleLinkedList<ShiftTemplate>();
	
	/**
	 * Default/Empty constructor.
	 */
	public ScheduleTemplate() {}

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
	 * @return the creatorID
	 */
	public Integer getCreatorID()
		{
		return creatorID;
		}

	/**
	 * @param creatorID the creatorID to set
	 */
	public void setCreatorID(Integer creatorID)
		{
		this.creatorID = creatorID;
		}

	/**
	 * @return the shiftTemplates
	 */
	public DoubleLinkedList<ShiftTemplate> getShiftTemplates()
		{
		return shiftTemplates;
		}
	
	
	
	}
