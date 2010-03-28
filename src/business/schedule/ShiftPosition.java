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
public class ShiftPosition extends BusinessObject implements Comparable<ShiftPosition>, Cloneable
	{
	/**
	 * The internal DB ID of the shift template that the shift position belongs
	 * to.
	 */
	private int shiftTempID = -1;
	
	/**
	 * The name of the position that is required on the shift.
	 */
	private String posName = null;
	
	/**
	 * The number of people required to be working the position on the shift.
	 */
	private int posCount = -1;
	
	/**
	 * Default/Empty constructor.
	 */
	public ShiftPosition() {}

	public ShiftPosition(int shiftTempID, String posName, int posCount)
		{
		this.shiftTempID = shiftTempID;
		this.posName = posName;
		this.posCount = posCount;
		}
	
	/**
	 * @return the shiftTempID
	 */
	public int getShiftTempID()
		{
		return shiftTempID;
		}

	/**
	 * @param shiftTempID the shiftTempID to set
	 */
	public void setShiftTempID(int shiftTempID)
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
	public int getPosCount()
		{
		return posCount;
		}

	/**
	 * @param posCount the posCount to set
	 */
	public void setPosCount(int posCount)
		{
		this.posCount = posCount;
		}

	@Override
	public int compareTo(ShiftPosition o)
		{
		if (shiftTempID < o.getShiftTempID())
			return -1;
		else if (shiftTempID > o.getShiftTempID())
			return 1;
		
		if (posName.compareToIgnoreCase(o.getPosName()) != 0)
			return posName.compareToIgnoreCase(o.getPosName());
		
		if (posCount < o.getPosCount())
			return -1;
		else if (posCount > o.getPosCount())
			return 1;
		
		return 0;
		}
	
	/* (non-Javadoc)
	 * @see business.BusinessObject#clone()
	 */
	@Override
	public ShiftPosition clone()
		{
		return (ShiftPosition)super.clone();
		}

	@Override
	public String toString()
		{
		return shiftTempID+";"+posName+";"+posCount; 
		}
	
	}
