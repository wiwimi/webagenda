/**
 * business - Skill.java
 */
package business;


/**
 * Skill is an object that is applied to an employee that determines
 * what positions they can work in a schedule.
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
	
	public Skill(String name)
		{
		this.name = name;
		}
	
	/** Produces a blank template of an skill */
	public Skill()
		{
		
		}
	
	public Skill(String name, String desc)
		{
		this.name = name;
		this.desc = desc;
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

	@Override
	public String toString()
		{
		return name;
		}
}
