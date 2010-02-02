/**
 * persistence - Broker.java
 */
package persistence;

/**
 * All brokers should inherit this class
 * 
 * @author peon-dev
 * @version 0.01.00
 *
 */
public abstract class Broker {
	
	/** The database username that contains access to webagenda data */
	private String db_username							= null;
	/** Password for the user found in db_username that connects to the webagenda database */
	private String db_password							= null;
	/** Hostname where the webagenda database is located. This defaults to localhost.*/
	private String db_hostname							= "localhost";
	
	public abstract Object getBrokerObject(Object o);
	
	public abstract int flushCache();
	
	public abstract int clearCache();
	
	public abstract int cache(Cachable cache_obj);
	
	
}
