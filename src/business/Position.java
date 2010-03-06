/**
 * business.schedule - Position.java
 */
package business;


/**
 * This class represents a Position that an employee can work. It is used
 * extensively in Scheduling for auto-generation; it places employees that can
 * work certain positions in those positions that are required for a completely
 * schedule to be generated. (No errors)
 * 
 * @author peon-dev, Daniel Wehr
 * @version 0.2.0
 */
public class Position extends BusinessObject {

	/**
	 * This attribute represents the name of the position. It is unique and
	 * should accurately represent the the position.
	 */
	private String name	= null;
	
	/**
	 * This attribute is a description of the name of the position. It is
	 * optional, but may aid in informed decisions that new users may require
	 * when scheduling.
	 */
	private String desc	= null;
	
	/**
	 * Creates an empty position with no name or description.
	 */
	public Position()
		{
		}
	
	/**
	 * Creates a position with a name and no description.
	 * 
	 * @param newName The name of the position.
	 */
	public Position(String newName)
		{
		name = newName;
		}
	
	/**
	 * Creates a position with a name and description.
	 * 
	 * @param newName The name of the position.
	 * @param newDesc The description of the position.
	 */
	public Position(String newName, String newDesc)
		{
		name = newName;
		desc = newDesc;
		}

	/**
	 * @return the name
	 */
	public String getName()
		{
		return name;
		}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
		{
		this.name = name;
		}

	/**
	 * @return the desc
	 */
	public String getDesc()
		{
		return desc;
		}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc)
		{
		this.desc = desc;
		}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
		{
		return name;
		}
}
