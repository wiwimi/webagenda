/**
 * business.schedule - Location.java
 */
package business.schedule;

import business.BusinessObject;

/**
 * This class represents the locations where employees can work; is used mainly
 * by scheduling and job-related tasks. TODO: Perhaps work this into reporting
 * functions.
 * 
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.2.0
 */
public class Location extends BusinessObject
	{
	/**
	 * The name of the location which is to be used by other components of
	 * WebAgenda. Non-unique location names will cause conflicts so only unique
	 * names can be used.
	 */
	private String	name	= null;
	
	/**
	 * A description of the location being used. If location names are similar, a
	 * description of what goes on at that location or who should/does work at
	 * that location. This is optional.
	 */
	private String	desc	= null;
	
	public Location(String name)
		{
		this.name = name;
		}
	
	/** Produces a blank template of an location */
	public Location()
		{
		}
	
	public Location(String name, String desc)
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
