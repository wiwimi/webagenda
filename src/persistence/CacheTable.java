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
	
	
}
