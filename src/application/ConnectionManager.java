/**
 * application - ConnectionManager.java
 */
package application;

import java.util.Queue;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * This class allows for each Broker class to have its own connection to the database.
 * Each uses the WebAgenda database, but is limited based on the database restrictions
 * placed on a Broker. (A Broker can only access tables and fields that are relevant
 * to it's purpose.)<br>
 * <br>
 * This class also may optionally enforce a 'one request load' rule where only one
 * request may be made to the database by all Brokers combined. This is to reduce stress
 * and load on the database, but may slow the system. <br>
 * <br>
 * 
 * FIXME:
 * 		Writes should have priority over reads (need priority mechanism? One built in?)
 * 		
 * 
 */
public class ConnectionManager {

	/** Manager object where only one instance of it can exist. Is used
	 * to control access to the database. */
	private static ConnectionManager con_manager					= null;
	/** Flag that determines if Brokers are allowed to access the database simultaneously,
	 * even though they are only accessing their own related tables and fields. 
	 * This will not protect against non-WebAgenda connections to the database. */
	private boolean one_request										= false; 
	
	/**
	 * Private Constructor that sets up the ConnectionManager.s
	 */
	private ConnectionManager()
	{
		//TODO: Initialize
	}
	
	/**
	 * Singleton Pattern to retrieve the manager and set it up if uninitialized.
	 * 
	 * 
	 * @return Initialized ConnectionManager
	 */
	public static ConnectionManager getManager()
	{
		if(con_manager==null) {
			con_manager = new ConnectionManager();
		}
		return con_manager;
	}
	
	/**
	 * Returns the boolean flag of whether only one request can be sent to the
	 * database at a time.
	 * 
	 * @return boolean - true if only one request is sent to the database at a time.
	 */
	public boolean isOneRequestOnly()
	{
		return one_request;
	}
	
}
