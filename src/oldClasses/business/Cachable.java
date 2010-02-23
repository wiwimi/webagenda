/**
 * persistence - Cacheable.java
 */
package oldClasses.business;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * Cachable is an abstract class that all CACHABLE business objects extend. The class is required for an object to be cacheable because<br />
 * a) It means that objects that are non-cachable cannot be cached in a broker, so there's datatype security<br />
 * b) Cacheable objects hold the date and time that they were last accessed, so that Brokers can remove them from memory if desired.<br />
 * c) A boolean variable is included that determines if a cached object was modified, forcing it to be re-written to the database. <br />
 * d) Methods defined in Cachable force the business objects to define or inherit those methods. Even though a Broker may retrieve an object, if the user 
 * does not have permission to modify the object, no changes will be applied, no change to the modified boolean if it's false.<br />
 * 
 * @author peon-dev
 * @version 0.01.00
 *
 */
public abstract class Cachable implements Serializable {

	private static final long	serialVersionUID	= 1L;
	private boolean is_modified 						= false;
	private Date date_created							= null;

	/**
	 * TODO
	 * @return boolean true if user can edit this object.
	 */
	public abstract boolean canEdit();
	
	/**
	 * TODO (like canEdit, but even managers cannot delete an employee object. Only disable.)
	 * @return boolean true if user can delete the business object from the cache (henceforth the database)
	 */
	public abstract boolean canDelete();
	
	/**
	 * TODO
	 * @return boolean true if user can read the cachable object
	 */
	public abstract boolean canRead();
	
	
}
