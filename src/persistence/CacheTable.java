/**
 * persistence - CacheTable.java
 */
package persistence;


import java.util.Date;
import utilities.DoubleLinkedList;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class CacheTable {

	/** Double Linked List --<br /><br /> 
	 * Worst Case Scenario for finding a sorted ID in the cache table: (O) n / 2 <br />
	 * Faster Inserting and Removing of objects, optimizing cache response time. */
	private DoubleLinkedList<Cachable> cached_data 						= null;
	
	/** Date when last cache flush occured. If > than the specified routine flush time, Broker knows
	 * that an error has occured such as a power failure upon request to flush. This should be
	 * logged to an error log. */
	private Date flush_stamp											= null;
	
	
	/**
	 * Method to remove an item from cache. Probably won't be used while the system is in a work environment because it's
	 * undesirable to not have items cached when they are used.
	 * 
	 * @param id int id to remove from cache
	 * @return error code int<br />
	 * -1: Method failed, Error occured.
	 * 0 : Item successfully removed from cache
	 * 1 : ID specified not found
	 * 
	 */
	private int removeFromCache(int id)
	{
		return -1;
	}
	
	/**
	 * 
	 * @param cache_obj
	 * @return
	 */
	private int persist(Cachable cache_obj)
	{
		return -1;
	}
	
}
