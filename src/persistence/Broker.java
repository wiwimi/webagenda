/**
 * persistence - Broker.java
 */
package persistence;

import com.mysql.jdbc.Statement;

import business.Cachable;

/**
 * All brokers should inherit this class
 * 
 * @author peon-dev, Daniel Wehr
 * @version 0.2.0
 */
public abstract class Broker<E extends Cachable>
	{
	
	/** The database username that contains access to webagenda data */
	private String					db_username		= null;
	
	/**
	 * Password for the user found in db_username that connects to the webagenda
	 * database
	 */
	private String					db_password		= null;
	
	/**
	 * Hostname where the webagenda database is located. This defaults to
	 * localhost.
	 */
	private String					db_hostname		= "localhost";
	
	/**
	 * Connection that interacts with the ConnectionManager for database requests
	 */
	private ThreadedConnection	threaded_conn	= null;
	
	/**
	 * The max number of threads that the application should use to connect to
	 * the database. Actual MySQL threads are specified in the mysql database,
	 * may be configured by installer but not here.
	 */
	// Default value unless multithreading is specified.
	private int						int_threads		= 1;
	
	/**
	 * Flushes the Cache, writing all changes to the database that are 'dirty',
	 * have been changed from their original values.
	 * 
	 * @return int error code <br>
	 *         0: Successful Flush -1: Error (undefined)
	 */
	public abstract int flushCache();
	
	/**
	 * Clears the Cache, flushing first and then uninitializing data that hasn't
	 * been used past the time specified.
	 * 
	 * @return int error code 0: Successful Clear -1: Error (undefined)
	 */
	public abstract int clearCache();
	
	/**
	 * Place object in the Broker's memory. This will significatly reduce access
	 * time as Broker does not send a ThreadedConnection Request to the
	 * ConnectionManager which will access the database and return the value.
	 * 
	 * @param cache_obj
	 * @return
	 */
	public abstract int cache(E cache_obj);
	
	/**
	 * Accepts a newly made object, and creates its equivalent record within the
	 * database. TODO An exception will be thrown if there is already a record in
	 * the database with the same primary key.
	 * 
	 * @param createObj The object to add to the database.
	 * @return true if the create was successful, otherwise false.
	 */
	public abstract boolean create(E createObj);
	
	/**
	 * Retrieves data from the database and return them as objects.
	 * 
	 * @param getObj A newly created object used to determine what will be
	 *           returned. All non-null attributes will be used as search
	 *           criteria. If the primary key is filled in the search object,
	 *           only that will be used and all others will be ignored.
	 * @return All objects that matched the search criteria. If no matches are
	 *         found, an array with a single null object will be returned.
	 */
	public abstract E[] get(E getObj);
	
	/**
	 * Applies all changes to the updated object to its equivalent record within
	 * the database. The updated object must have originally been retrieved from
	 * the database. TODO An exception will be thrown if the record to be updated
	 * does not exist in the database.
	 * 
	 * @param updateObj The previously retrieved object that has been updated.
	 * @return true if the update was successful, otherwise false.
	 */
	public abstract boolean update(E updateObj);
	
	/**
	 * Removes the record from the database that is equivalent to the given
	 * object. The object to be deleted must have originally been retrieved from
	 * the database. TODO An exception will be thrown if the record to be deleted
	 * does not exist in the database.
	 * 
	 * @param deleteObj
	 * @return
	 */
	public abstract boolean delete(E deleteObj);
	
}
