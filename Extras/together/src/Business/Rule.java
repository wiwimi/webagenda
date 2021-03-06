/* Generated by Together */

package Business;

/**
 * An object that determines the influences that affect Schedule Templates, such as a minimum age rule for example. 
 */
public class Rule implements BusinessObject {
    /**
     * Id of the Rule object 
     */
    private int ruleID;

    /**
     * Shift Template ID that rule applies to 
     */
    private int shiftTempID;

    /**
     * Rule Type (defined elsewhere, such as AGE, POSITION, or otherwise.) 
     */
    private String ruleType;

    /**
     * Rule Value. If AGE is a type, then value can be "> 18" or something. 
     */
    private String ruleValue;

    /**
     * @clientCardinality 1
     * @clientQualifier is limited by
     * @supplierCardinality 0..*
     * @supplierQualifier applies to 
     */
    private ShiftTemplate lnkShiftTemplate;
}
