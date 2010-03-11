/**
 * 
 */
package business.schedule;

import business.BusinessObject;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class ShiftPosition extends BusinessObject
	{
	/**
	 * The internal DB ID of the shift template that the shift position belongs
	 * to.
	 */
	private Integer shiftTempID = null;
	
	/**
	 * The name of the position that is required on the shift.
	 */
	private String posName = null;
	
	/**
	 * The number of people required to be working the position on the shift.
	 */
	private Integer posCount = null;
	
	/**
	 * Default/Empty constructor.
	 */
	public ShiftPosition() {}

	public ShiftPosition(Integer shiftTempID, String posName, Integer posCount)
		{
		this.shiftTempID = shiftTempID;
		this.posName = posName;
		this.posCount = posCount;
		}
	
	/**
	 * @return the shiftTempID
	 */
	public Integer getShiftTempID()
		{
		return shiftTempID;
		}

	/**
	 * @param shiftTempID the shiftTempID to set
	 */
	public void setShiftTempID(Integer shiftTempID)
		{
		this.shiftTempID = shiftTempID;
		}

	/**
	 * @return the posName
	 */
	public String getPosName()
		{
		return posName;
		}

	/**
	 * @param posName the posName to set
	 */
	public void setPosName(String posName)
		{
		this.posName = posName;
		}

	/**
	 * @return the posCount
	 */
	public Integer getPosCount()
		{
		return posCount;
		}

	/**
	 * @param posCount the posCount to set
	 */
	public void setPosCount(Integer posCount)
		{
		this.posCount = posCount;
		}
	
	
	
	}
