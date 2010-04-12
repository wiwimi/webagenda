/**
 * business - Skill.java
 */
package business;


/**
 * Holds all information for a skill, matching the structure of skills in the
 * database.  Skills are used to record what skills an employee has both within
 * and outside of their position, and positions may have a set of skills
 * that are required.
 * 
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class Skill extends BusinessObject {
	/**
	 * The name of the skill which is to be attributed to the employees of
	 * WebAgenda. Non-unique skill names will cause conflicts so only unique
	 * names can be used.
	 */
	private String	name	= null;
	
	/**
	 * A description of the skill being used. If skill names are similar, a
	 * description of what the skill is or how it is applied should be
	 * included. This is optional.
	 */
	private String	desc	= null;
	
	/** Produces a blank template of an skill */
	public Skill()
		{
		}

	/**
	 * Constructor for a Skill with a set name
	 * @param name String name of skill
	 */
	public Skill(String name)
		{
		this.name = name;
		}
	
	/**
	 * Constructor for a skill with a name and description
	 * @param name String name of skill
	 * @param desc String description of skill 
	 */
	public Skill(String name, String desc)
		{
		this.name = name;
		this.desc = desc;
		}
	
	/**
	 * Gets the Skill name
	 * @return the name
	 */
	public String getName()
		{
		return name;
		}

	/**
	 * Sets the skill name
	 * @param name the name to set
	 */
	public void setName(String name)
		{
		this.name = name;
		}

	/**
	 * Gets the skill description
	 * @return the desc
	 */
	public String getDesc()
		{
		return desc;
		}

	/**
	 * Sets the skill description
	 * @param desc the desc to set
	 */
	public void setDesc(String desc)
		{
		this.desc = desc;
		}

	@Override
	public String toString()
		{
		return name;
		}
}
