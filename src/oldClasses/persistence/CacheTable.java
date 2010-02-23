/**
 * persistence - CacheTable.java
 */
package oldClasses.persistence;


import java.util.Date;
import oldClasses.business.Cachable;
import persistence.Broker;

import messagelog.Logging;
import utilities.DoubleLinkedList;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class CacheTable {

	/** <div> 
	 * Double Linked List --<br /><br /> 
	 * Worst Case Scenario for finding a sorted ID in the cache table: (O) n / 2 <br />
	 * Faster Inserting and Removing of objects, optimizing cache response time. </div> */
	private DoubleLinkedList<Cachable> cached_data 						= null;
	
	/** Number of milliseconds for flush to wait in a regular flushing cycle -- 15 seconds default if not specified in arguments. 
	 * FlushThread will be activated and run after the wait time, then put back to sleep. */
	protected static long flush_wait_time								= 15000; 
	
	/** Date when last cache flush occured. If greater than the specified routine flush time, Broker knows
	 * that an error has occured such as a power failure upon request to flush. This should be
	 * logged to an error log. */
	private Date flush_stamp											= null;
	
	private FlushThread flusher											= null;
	
	/**
	 * Broker parameter is purposefully unchecked because attempting to introduce strict rules prevents this from running.
	 * FIXME: ?
	 * 
	 * @param broker Any Broker object
	 */
	@SuppressWarnings("unchecked")
	CacheTable(Broker broker)
	{
		cached_data = new DoubleLinkedList<Cachable>();
		flush_stamp = new Date();// Place current date into the flush stamp -- this is the last 'flush' recorded.
		flusher = new FlushThread(broker);
	}
	
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
	
	protected void flush()
	{
		flusher.forceFlush();
	}
	
	/**
	 * TODO: Write a modification to cache. Will check against all different parts to the business object
	 * so an edited object won't be overwritten with another edited object while discarding changes of the first.
	 * Conflicts will lean towards highest permission level
	 * @param cache_obj
	 * @return
	 */
	private int persist(Cachable cache_obj)
	{
		return -1;
	}
	
	/**
	 * TODO: Write data to the database. This method call must check permissions before doing so
	 * @param cache_obj
	 * @return
	 */
	private int writeToDatabase(Cachable cache_obj)
	{
		return -1;
	}
	
	/**
	 * Overloaded method to loop through cachable array and write changes to database
	 * @param cache_objects
	 * @return
	 */
	private int[] writeToDatabase(Cachable[] cache_objects)
	{
		return new int[]{-1,-1,-1,-1};
	}
	
	/**
	 * Returns all the objects required to flush with each call. Should be called via a thread in each Broker
	 * object. 
	 * @return Array of objects to save in backend. 
	 */
	private Cachable[] getCachablesToFlush()
	{
		return null;
	}
	
	FlushThread getFlushThread()
	{
		return flusher;
	}
	
	/**
	 * 
	 * @author dann
	 * @version 0.01.00
	 * @license GPL 2
	 * 
	 * Class that flushes data periodically back to the database. Flushes are database writes and therefore given higher priority than retrievals.
	 * As this class extends the Thread class, it is a thread that sleeps for a defined period of time (recommended 3 - 30 seconds in between flush
	 * negotiations) then wakes up to save modified data in a broker. Brokers can avoid making database calls with every edit in this manner.
	 * All these threads are daemons.
	 * FIXME: Upon exit of program, all FlushThreads must be run once (set as non-daemon?) so that data can be written before db is closed.
	 * Of course, this does not check for immediate shutdowns aka failures, outages.
	 */
	public class FlushThread extends Thread {
		
		/** Remembers what Broker called this flushing thread so it can communicate with broker if required. (Remove if not) TODO */
		private Broker<Cachable> brok_monitor							= null;
		
		/**
		 * Constructor that creates a thread to navigate a specific Broker's cache table as a daemon thread that will
		 * detect data that is modified and hasn't been saved, then saving it.
		 * 
		 * @param brok Broker object to monitor
		 */
		public FlushThread(Broker<Cachable> brok)
		{
			brok_monitor = brok;
			this.setDaemon(true);
		}
		
		protected void forceFlush()
		{
			this.interrupt();
		}
		
		@Override
		public void run()
		{
			if(brok_monitor == null)
				Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Flushing Thread started for Broker (Not yet initialized)");
			else
				Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Flushing Thread started for " + brok_monitor.getClass().getSimpleName());

			
			while(true)
			{
				try {
					System.out.println("Thread going to sleep");
					this.sleep(CacheTable.flush_wait_time);
					
				} catch (InterruptedException e) {
				
					System.out.println("Thread woken up");
				}
			}
				
		}
		
		
		
		
	}
	
}
