/* Generated by Together */

package Business;

/**
 * Stores all information on a schedule template, matching the database. Schedule templates match required positions to shifts, and are used to generate new filled schedules that assign work times to specific employees.
 */
public class ScheduleTemplate implements BusinessObject {
    /**
     * The internal DB ID of the schedule template. This is for broker use only. 
     */
    private int schedTempID;

    /**
     * The ID of the employee who created this schedule.
     */
    private int creatorID;

    /**
     * The name given to the template. 
     */
    private String name;

    /**
     * The array or list of shift templates that are part of the schedule template.
     */
    private ShiftTemplate[] shiftTemps;

    /**
     * @clientQualifier contains
     * @supplierCardinality 1..*
     * @supplierQualifier defines
     * @clientCardinality 1 
     */
    private ShiftTemplate lnkShift;

    /**
     * @clientCardinality 0..*
     * @clientQualifier is created by
     * @supplierCardinality 1
     * @supplierQualifier may create 
     */
    private Employee lnkEmployee;
}
