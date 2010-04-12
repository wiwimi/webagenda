/**
 * 
 */
package business.schedule;

import business.BusinessObject;

/**
 * Holds information on a position required by a shift template, and the number
 * of employees that are required for that position.
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

	/**
	 * Constructor to create a shift position
	 * @param shiftTempID int
	 * @param posName String 
	 * @param posCount int
	 */
	public ShiftPosition(int shiftTempID, String posName, int posCount)
		{
		this.shiftTempID = shiftTempID;
		this.posName = posName;
		this.posCount = posCount;
		}
	
	/**
	 * Gets the shift template id
	 * @return int
	 */
	public int getShiftTempID()
		{
		return shiftTempID;
		}

	/**
	 * Sets the shift template id
	 * @param int
	 */
	public void setShiftTempID(int shiftTempID)
		{
		this.shiftTempID = shiftTempID;
		}

	/**
	 * Gets the position name
	 * @return String
	 */
	public String getPosName()
		{
		return posName;
		}

	/**
	 * Sets the position name
	 * @param String
	 */
	public void setPosName(String posName)
		{
		this.posName = posName;
		}

	/**
	 * Gets the position count
	 * @return int
	 */
	public int getPosCount()
		{
		return posCount;
		}

	/**
	 * Sets the position count
	 * @param int
	 */
	public void setPosCount(int posCount)
		{
		this.posCount = posCount;
		}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
		{
		return shiftTempID+";"+posName+";"+posCount; 
		}
	
	}
