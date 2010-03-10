/**
 * business.schedule - WorkingSchedule.java
 */
package oldClasses.schedule;

import business.BusinessObject;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class WorkingSchedule extends BusinessObject {

	/** Auto-incrementing ID for schedules, forcing them to be unique. This is referenced from template shifts. */
	private int scheduleID		= -1;
	/** ID of the employee who created the template. */
	private int employeeID		= -1;
	
	public WorkingSchedule(int schedule, int employee)
	{
		this.scheduleID = schedule;
		this.employeeID = employee;
	}

	/**
	 * @return the scheduleID
	 */
	public int getScheduleID() {
		return scheduleID;
	}

	/**
	 * @return the employeeID
	 */
	public int getEmployeeID() {
		return employeeID;
	}
	
	
	
}
