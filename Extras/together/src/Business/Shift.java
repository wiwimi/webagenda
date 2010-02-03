/* Generated by Together */

package Business;

/**
 * Contains a list of what positions must be filled by employees within a given range of time.
 */
public class Shift {
    /**
     * The time at which work begins for the employees assigned to the shift. 
     */
    private Time startTime;

    /**
     * The time at which work ends for the employees assigned to the shift. 
     */
    private Time endTime;

    /**
     * The positions that are required for the shift. 
     */
    private ShiftPos[] positions;

    /**
     * @clientQualifier contains
     * @supplierCardinality 1..*
     * @supplierQualifier required by
     * @clientCardinality 1 
     */
    private ShiftPos lnkShiftPos;
}
