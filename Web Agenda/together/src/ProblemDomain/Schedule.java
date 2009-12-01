/* Generated by Together */

package ProblemDomain;

/**
 * A collection of shifts of multiple employees, grouped together by the workgroup or supervisor that those employees share. 
 */
public class Schedule {
    /**
     * The createNewSchedule() method is used to create a blank schedule and by default will include any time booked off by any of the employees he is scheduling. This can be turned off if the supervisor does not want to include the dates that the employees are on vacation/sick/absent. 
     */
    public Object createNewSchedule() {
    }

    /**
     * This method is used to actually assign a shift to a schedule for a specific employee. This method can be used for as many shifts as the supervisor wants to have to as many employees as he/she wants. Error checking will take place every time a supervisor tries to create a schedule that goes outside normal bounds of employment. 
     */
    public void addShift() {
    }

    /**
     * This method is used in an action event when the save button is pressed. This will save the schedule however unless the supervisor also chooses to implement the schedule(send the schedule to all employees) then it will just save into the database to be accessed later if any modifications are necessary. 
     */
    public Boolean saveSchedule() {
    }

    /**
     * Implementing the schedule can also be triggered by the same action event as the saveSchedule() method, however this method will be called if the option to implement the schedule is selected. This will begin the implementation process for sending notifications to employees letting them know that a new schedule is up and running. 
     */
    public void implementSchedule() {
    }

    /**
     * Gets the default schedule for this supervisor, showing all employees that the supervisor is in charge of. 
     */
    public Schedule fetchDefaultSchedule(){}

    /**
     * Gets the schedule for a specific workgroup, showing the schedules of only the employees within it rather than all employees that the supervisor has access to. 
     */
    public Schedule fetchWorkgroupSched(){}

    /**
     * The getAvailabilityTemplate is a method that returns a blank schedule: Not blank as in a fresh, untampered date schedule, but it returns a schedule that includes the names of the days of the week and nothing else. It represents a typical week of shifts that a user can populate with shifts they can work; The schedule itself isn't blank either. When a user sets their availability, it is saved to the backend so this method returns what they saved so they can re-edit it. If a user wants to change their schedule, they will send a COPY of it to their supervisor. 
     */
    public Schedule fetchAvailabilityTemplate(){ return null; }

    public void getAllShifts(){}

    /**
     * Holds references to the IDs of all employees that this schedule applies to. 
     */
    private int[] employeeId;

    /**
     * Holds references to all shifts that are active for this schedule. 
     */
    private shift[] employeeShifts;

    /**
     * A schedule is composed of one or more shifts.
     * @link aggregation 
     */
    private Shift lnkShift;
}
