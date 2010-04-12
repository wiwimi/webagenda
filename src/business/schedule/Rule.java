/**
 * business.schedule - Rule.java
 */
package business.schedule;

import business.BusinessObject;

/**
 * An object that determines the influences that affect Shift templates, such as
 * a minimum age rule for example.
 * 
 * @author Daniel Kettle
 * @version 0.01.00
 * @license GPL 2
 */
public class Rule extends BusinessObject {

	/** Id of the Rule object */
	private int ruleID				= -1;
	/** Shift Template ID that rule applies to */
	private int shiftTempID			= -1;
	/** Rule Type (defined elsewhere, such as AGE, POSITION, or otherwise.) */
	private String ruleType			= null;
	/** Rule Value. If AGE is a type, then value can be "> 18" or something. */
	private String ruleValue		= null;
	
	/**
	 * Constructor for a Rule
	 * @param ruleID
	 * @param shiftTempID
	 * @param ruleType
	 * @param ruleValue
	 */
	public Rule(int ruleID,int shiftTempID, String ruleType, String ruleValue)
	{
		this.ruleID = ruleID;
		this.shiftTempID = shiftTempID;
		this.ruleType = ruleType;
		this.ruleValue = ruleValue;
	}

	/**
	 * Gets the rule id
	 * @return int
	 */
	public int getRuleID() {
		return ruleID;
	}

	/**
	 * gets the shift template id
	 * @return int
	 */
	public int getShiftTempID() {
		return shiftTempID;
	}

	/**
	 * Gets the rule type
	 * @return String
	 */
	public String getRuleType() {
		return ruleType;
	}

	/**
	 * Gets the value of the rule
	 * @return String
	 */
	public String getRuleValue() {
		return ruleValue;
	}

	/**
	 * Sets the rule id number
	 * @param int
	 */
	public void setRuleID(int ruleID) {
		this.ruleID = ruleID;
	}

	/**
	 * Sets the shift template id
	 * @param int
	 */
	public void setShiftTempID(int shiftTempID) {
		this.shiftTempID = shiftTempID;
	}

	/**
	 * Sets the rule type
	 * @param String
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	/**
	 * Sets the rule value
	 * @param String
	 */
	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}
	
	
	
}
