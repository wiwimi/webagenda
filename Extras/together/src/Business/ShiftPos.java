/* Generated by Together */

package Business;

/**
 * Holds information on a position required by a shift template, and the number of employees that are required for that position.
 */
public class ShiftPos implements BusinessObject {
    /**
     * The ID of the shift template that the shift position belongs to. 
     */
    private int shiftTempID;

    /**
     * The name of the position that is required on the shift.
     */
    private String positionName;

    /**
     * The number of people required to be working the position on the shift.
     */
    private int posCount;

    /**
     * @clientQualifier contains
     * @supplierCardinality 1
     * @supplierQualifier required by
     * @clientCardinality 0..* 
     */
    private Position lnkPosition;
}
