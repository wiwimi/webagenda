/**
 * business.schedule - Position.java
 */
package business.schedule;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 * This class represents a Position that an employee can work. It is used extensively in Scheduling for
 * auto-generation; it places employees that can work certain positions in those positions that are
 * required for a completely schedule to be generated. (No errors)
 * 
 */
public class Position {

	/** This attribute represents the name of the position. It is unique and should accurately represent the
	 * the position. */
	private String name								= null;
	
	/** This attribute is a description of the name of the position. It is optional, but may aid in informed
	 * decisions that new users may require when scheduling. */
	private String description						= null;
}
