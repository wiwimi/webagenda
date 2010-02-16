/**
 * persistence - Broker.java
 */
package persistence;

import business.Cachable;

/**
 * All brokers should inherit this class
 * 
 * @author peon-dev, Daniel Wehr
 * @version 0.2.0
 *
 */
public abstract class Broker<E extends Cachable> {
	
	/** The database username that contains access to webagenda data */
	private String db_username							= null;
	/** Password for the user found in db_username that connects to the webagenda database */
	private String db_password							= null;
	/** Hostname where the webagenda database is located. This defaults to localhost.*/
	private String db_hostname							= "localhost";
	/** Connection that interacts with the ConnectionManager for database requests */
	private ThreadedConnection threaded_conn			= null;
	/** The max number of threads that the application should use to connect to the database.
	 * Actual MySQL threads are specified in the mysql database, may be configured by installer
	 * but not here. */
	private int int_threads								= 1; // Default value, unless multithreading is specified
	
	/**
	 * Send a request through the ConnectionManager to the database
	 * 
	 * @param An SQL query to send to the database
	 * @return String[][] - results from query as a 2D String array (table)
	 */
	protected String[][] query(String query)
	{
		
		return null;
	}
	
	/**
	 * Flushes the Cache, writing all changes to the database that are 'dirty', have
	 * been changed from their original values.
	 * 
	 * @return int error code <br>
	 * 0: Successful Flush
	 * -1: Error (undefined) 
	 */
	public abstract int flushCache();
	
	/**
	 * Clears the Cache, flushing first and then uninitializing data that hasn't
	 * been used past the time specified.
	 * 
	 * @return int error code
	 * 0: Successful Clear
	 * -1: Error (undefined)
	 */
	public abstract int clearCache();
	
	/**
	 * Place object in the Broker's memory. This will significatly reduce access time
	 * as Broker does not send a ThreadedConnection Request to the ConnectionManager which
	 * will access the database and return the value.
	 * 
	 * @param cache_obj
	 * @return
	 */
	public abstract int cache(E cache_obj);
	
	public abstract E[] get(E getObj);
	
	public abstract boolean update(E updateObj);
	
	public abstract boolean create(E createObj);
	
	public abstract boolean delete(E deleteObj);
	
	
}
