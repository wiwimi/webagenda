/**
 * business - Position.java
 */
package business;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class Position extends BusinessObject {

	/** The name of the position that is being filled */
	private String position_name = null;
	/** A description of the position's purpose, optional */
	private String position_desc = null;
	
	public Position(String pos_name)
	{
		this.position_name = pos_name;
	}
	
	public Position(String pos_name, String pos_desc)
	{
		this.position_desc = pos_desc;
		this.position_name = pos_name;
	}

	/**
	 * @return the position_name
	 */
	public String getPosition_name() {
		return position_name;
	}

	/**
	 * @return the position_desc
	 */
	public String getPosition_desc() {
		return position_desc;
	}

	/**
	 * @param positionName the position_name to set
	 */
	public void setPosition_name(String positionName) {
		position_name = positionName;
	}

	/**
	 * @param positionDesc the position_desc to set
	 */
	public void setPosition_desc(String positionDesc) {
		position_desc = positionDesc;
	}
	
	
	
}
