/**
 * business.schedule - Location.java
 */
package business.schedule;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 * This class represents the locations where employees can work; is used mainly by scheduling and
 * job-related tasks.
 * 
 * TODO: Perhaps work this into reporting functions.
 */
public class Location {

	/** The name of the location which is to be used by other components of WebAgenda. Non-unique location
	 * names will cause conflicts so only unique names can be used. */
	private String name								= null;
	/** A description of the location being used. If location names are similar, a description of what
	 * goes on at that location or who should/does work at that location. This is optional. */
	private String description						= null;
	
	public Location(String str)
	{
		name = str;
	}
	
}
