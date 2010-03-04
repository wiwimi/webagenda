/* Generated by Together */

package Business;

/**
 * A group of working shifts that apply within a given range of dates.
 */
public class Schedule {
    /**
     * Retrieves an array of all working shifts held by the schedule. 
     */
    public Shift[] getWorkingShifts(){}

    /**
     * The start date for when this schedule will be in effect for all applicable employees.
     */
    private Date startDate;

    /**
     * The end date for when this schedule will no longer be in effect for all applicable employees.
     */
    private Date endDate;

    /**
     * The collection of working shifts that will be active between the dates set by the schedule.
     */
    private Shift[] shifts;

    /**
     * The ID of the employee that created the schedule. 
     */
    private int creatorID;

    /**
     * @clientCardinality 0..*
     * @supplierCardinality 1
     * @supplierQualifier may create
     * @clientQualifier is created by 
     */
    private Employee lnkEmployee;
}
