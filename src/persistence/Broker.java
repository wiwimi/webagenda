/**
 * persistence - Broker.java
 */
package persistence;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observer;

import application.ThreadedConnection;
import business.Cachable;
import persistence.BrokerThread;
import utilities.DoubleLinkedList;

/**
 * All brokers should inherit this class.
 * Includes database information for multiple db connectivity, BrokerThreads and associated methods for fetching results from the
 * database and managing the threads.
 * 
 * @author dann, Daniel Wehr
 * @version 0.2.0
 */
public abstract class Broker<E extends Cachable> implements Observer 
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
	
	private DoubleLinkedList<Cachable> queue		= null;
	
	private ThreadedConnection threadc			= null;
	
	/**
	 * The max number of threads that the application should use to fetch resultsets from
	 * sqlstatements simultaneously. The method that calls issueStatement(SqlStatement) will
	 * then wait until their results are retrieved. That way, if one statement must call the
	 * db and the next has all data in cache, the cache can quickly retrieve it and the db
	 * can continue to fetch.
	 */
	// Default value unless multithreading is specified.
	protected int						int_threads		= 5;
	
	/**
	 * The number of available threads that grab queue items and return data.
	 */
	private BrokerThread[] request_threads				= new BrokerThread[int_threads];
	
	/**
	 * Flushes the Cache, writing all changes to the database that are 'dirty',
	 * have been changed from their original values.
	 * 
	 * @return int error code <br>
	 *         0: Successful Flush -1: Error (undefined)
	 */
	abstract int flushCache();
	
	/**
	 * Clears the Cache, flushing first and then uninitializing data that hasn't
	 * been used past the time specified.
	 * 
	 * @return int error code 0: Successful Clear -1: Error (undefined)
	 */
	abstract int clearCache();
	
	/**
	 * Place object in the Broker's memory. This will significatly reduce access
	 * time as Broker does not send a ThreadedConnection Request to the
	 * ConnectionManager which will access the database and return the value.
	 * 
	 * @param cache_obj
	 * @return
	 */
	abstract int cache(E cache_obj);
	
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
	 */
	public abstract void get(E getObj) throws HeadlessException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException;
	
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
	
	/**
	 * Returns the Cachable object (a business object) that has the same identifier 
	 * as the one specified. Will return null if target does not exist.
	 * 
	 * @param id int identification number
	 * @return Cachable object, or null if not found.
	 */
	public abstract Cachable getCachableObject(int id);
	
	/**
	 * Returns an array of cachable (business) objects that meet criteria of the
	 * specified Cachable object. In this method, all attributes are taken into
	 * account when looking for matching objects (except when null or wildcard values,
	 * see comments on overriden methods for more information) and a list of
	 * those are returned.<br>
	 * <br>
	 * For id numbers, -1 specifies any id numbers are a match.
	 * 
	 * @param c Cachable object template
	 * @return Cachable[] of matching objects. Can be null if no matches are found.
	 */
	public abstract Cachable[] getCachableObjects(Cachable c);
	
	/**
	 * Method to return the next free space in the BrokerThread array. 
	 * 
	 * @return int next free position in BrokerThread array
	 * <br>
	 * Returns -1 if no free space is found.
	 */
	public int nextFreeBrokerThread()
	{
		for(int i = 0; i < int_threads; i++)
		{
			if(request_threads[i] == null) return i;
		}
		return -1;
	}
	
	/**
	 * Method to take a result set and (based on the columns of the appropriate data table) return
	 * the right results through an array of cachable objects relating to the broker.<br>
	 * This method is used on objects that are returned from the database, not the actual broker
	 * cache.
	 * 
	 * @param rs ResultSet
	 * @return Cachable array of objects
	 */
	protected abstract Cachable[] translateResultSet(ResultSet rs) throws SQLException;
	

	/**
	 * @return the db_username
	 */
	protected String getDb_username() {
		return db_username;
	}

	/**
	 * @param dbUsername the db_username to set
	 */
	protected void setDb_username(String dbUsername) {
		db_username = dbUsername;
	}

	/**
	 * @return the db_password
	 */
	protected String getDb_password() {
		return db_password;
	}

	/**
	 * @param dbPassword the db_password to set
	 */
	protected void setDb_password(String dbPassword) {
		db_password = dbPassword;
	}

	/**
	 * @return the db_hostname
	 */
	protected String getDb_hostname() {
		return db_hostname;
	}

	/**
	 * @param dbHostname the db_hostname to set
	 */
	protected void setDb_hostname(String dbHostname) {
		db_hostname = dbHostname;
	}
	
	/**
	 * Method that should be overriden by Broker object. Can only be set once.
	 * 
	 * @param tc ThreadedConnection connection to database
	 */
	void setConnection(ThreadedConnection tc)
	{
		if(threadc == null)
			threadc = tc;
		
	}
	
	protected ThreadedConnection getConnection()
	{
		return threadc;
	}
	
	/**
	 * Returns a whole integer representing the position in a list of threadconnections found in ConnectionManager
	 * that is owned by the broker.
	 * 
	 * @return int position 0 if ConnectionManager's isSingular() method returns true, otherwise a dynamic integer
	 * is returned.
	 * @see application.ConnectionManager
	 */
	public abstract int getBrokerConnectionPosition();
	
	
}
