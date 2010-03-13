/**
 * business.schedule - Position.java
 */
package business.schedule;

import business.BusinessObject;
import business.Skill;

/**
 * This class represents a Position that an employee can work. It is used extensively in Scheduling for
 * auto-generation; it places employees that can work certain positions in those positions that are
 * required for a completely schedule to be generated. (No errors)
 *
 * @author peon-dev
 * @version 0.2.0
 */
public class Position extends BusinessObject {

	/** This attribute represents the name of the position. It is unique and should accurately represent the
	 * the position. */
	private String name								= null;
	
	/** This attribute is a description of the name of the position. It is optional, but may aid in informed
	 * decisions that new users may require when scheduling. */
	private String description						= null;
	private Skill[] pos_skills						= null;
	
	public Position() 
	{
	}
	
	public Position(String pos_name)
	{
		this.name = pos_name;
	}
	
	public Position(String pos_name, Skill[] skills)
	{
		this.name = pos_name;
		this.pos_skills = skills;
	}
	
	public Position(String pos_name, String pos_desc, Skill[] skills)
	{
		this(pos_name, skills);
		this.description = pos_desc;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the pos_skills
	 */
	public Skill[] getPos_skills() {
		return pos_skills;
	}

	/**
	 * @param posSkills the pos_skills to set
	 */
	public void setPos_skills(Skill[] posSkills) {
		pos_skills = posSkills;
	}
	
	@Override
	public String toString()
	{
		String str = name;
		if(pos_skills != null)
			for(Skill s : pos_skills)
				str += s;
		return str;
	}
	
	
}
