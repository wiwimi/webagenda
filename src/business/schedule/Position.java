/**
 * business.schedule - Position.java
 */
package business.schedule;

import business.BusinessObject;
import business.Skill;

/**
 * Represents a Position that an employee can work. It is used extensively in
 * Scheduling for auto-generation; it places employees that can work certain
 * positions in those positions that are required for a completely schedule to
 * be generated.
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
	/** An array of Skills that are associated with the position */
	private Skill[] pos_skills						= null;
	
	/**
	 * Empty constructor
	 */
	public Position() 
	{
	}
	
	/**
	 * Constructor that takes a name for the position
	 * @param pos_name String
	 */
	public Position(String pos_name)
	{
		this.name = pos_name;
	}
	
	/**
	 * Constructor that takes a name for the position as well as a list
	 * of skills as a Skill array
	 * @param pos_name String
	 * @param skills Skill[]
	 */
	public Position(String pos_name, Skill[] skills)
	{
		this.name = pos_name;
		this.pos_skills = skills;
	}
	
	/**
	 * Constructor that takes a Position name and description as well as
	 * a list of skills for that position.
	 * @param pos_name String
	 * @param pos_desc String
	 * @param skills Skill[]
	 */
	public Position(String pos_name, String pos_desc, Skill[] skills)
	{
		this(pos_name, skills);
		this.description = pos_desc;
	}

	/**
	 * Gets the position name
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the name
	 * @param String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the description
	 * @param String
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the skills associated with the position
	 * @return Skill[]
	 */
	public Skill[] getPos_skills() {
		return pos_skills;
	}

	/**
	 * Sets the skills associated with the position 
	 * @param Skills[]
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
				str += ";" + s;
		return str;
	}
	
	
}
