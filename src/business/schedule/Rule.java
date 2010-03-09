/**
 * business.schedule - Rule.java
 */
package business.schedule;

import business.BusinessObject;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class Rule extends BusinessObject {

	private int ruleID				= -1;
	private int shiftTempID			= -1;
	private String ruleType			= null;
	private String ruleValue		= null;
	
	public Rule(int ruleID,int shiftTempID, String ruleType, String ruleValue)
	{
		this.ruleID = ruleID;
		this.shiftTempID = shiftTempID;
		this.ruleType = ruleType;
		this.ruleValue = ruleValue;
	}

	/**
	 * @return the ruleID
	 */
	public int getRuleID() {
		return ruleID;
	}

	/**
	 * @return the shiftTempID
	 */
	public int getShiftTempID() {
		return shiftTempID;
	}

	/**
	 * @return the ruleType
	 */
	public String getRuleType() {
		return ruleType;
	}

	/**
	 * @return the ruleValue
	 */
	public String getRuleValue() {
		return ruleValue;
	}

	/**
	 * @param ruleID the ruleID to set
	 */
	public void setRuleID(int ruleID) {
		this.ruleID = ruleID;
	}

	/**
	 * @param shiftTempID the shiftTempID to set
	 */
	public void setShiftTempID(int shiftTempID) {
		this.shiftTempID = shiftTempID;
	}

	/**
	 * @param ruleType the ruleType to set
	 */
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	/**
	 * @param ruleValue the ruleValue to set
	 */
	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}
	
	
	
}
