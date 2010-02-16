/**
 * application - ConnectionManager.java
 */
package application;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.LinkedList;

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
public class ConnectionManager implements ConnectionManagerInterface {
	
	/** List of connections that are stored and retrieved.  */
	private LinkedList<Connection> connections = new LinkedList<Connection>();

	/** Manager object where only one instance of it can exist. Is used
	 * to control access to the database. */
	private static ConnectionManager con_manager					= null;
	/** Flag that determines if Brokers are allowed to access the database simultaneously,
	 * even though they are only accessing their own related tables and fields. 
	 * This will not protect against non-WebAgenda connections to the database. 
	 * <br>
	 * Default is false -- multiple connections are allowed. */
	private static boolean one_request										= false; 
	
	/**
	 * Private Constructor that sets up the ConnectionManager.
	 * If only one connection is allowed, one connection is added.
	 * If more than one connection are allowed, then every broker will get its own connection.
	 * 
	 * @throws SQLException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws HeadlessException 
	 */
	private ConnectionManager() throws HeadlessException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
	{

	}
	
	/**
	 * Singleton Pattern to retrieve the manager and set it up if uninitialized.
	 * 
	 * @return Initialized ConnectionManager
	 * @throws SQLException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws HeadlessException 
	 */
	public static ConnectionManager getManager() throws HeadlessException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
	{
		if(con_manager==null) {
			con_manager = new ConnectionManager();
		}
		return con_manager;
	}
	
	/**
	 * Singleton Pattern to retrieve the manager and set it up if uninitialized. Method only sets the one_instance boolean if the
	 * manager currently isn't initialized.
	 * 
	 * @param one_instance boolean true if manager only uses one thread for the application to connect to the database.
	 * @return Initialized ConnectionManager 
	 * @throws HeadlessException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException
	 */
	public static ConnectionManager getManager(boolean one_instance) throws HeadlessException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
	{
		if(con_manager == null) {
			one_request = one_instance;
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

	@Override
	public Connection addConnection(Connection c, InetAddress ip) {
		return null;
	}

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConnected(Connection getConn) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
