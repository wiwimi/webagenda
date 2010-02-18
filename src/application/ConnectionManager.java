/**
 * application - ConnectionManager.java
 */
package application;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import com.mysql.jdbc.Statement;

import messagelog.Logging;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * This class allows for each Broker class to have its own connection to the database.
 * Each uses the WebAgenda database, but is limited based on the database restrictions
 * placed on a Broker. (A Broker can only access tables and fields that are relevant
 * to it's purpose.) An account should be made for each broker. It can read other tables,
 * but not write. <br>
 * <br>
 * This class also may optionally enforce a 'one request load' rule where only one
 * request may be made to the database by all Brokers combined. This is to reduce stress
 * and load on the database, but may slow the system. <br>
 * <br>
 * Also queues up request statements to the database from all the brokers using
 * a control mechanism (only applicable) if Brokers work on a singular connection.
 * Otherwise this control is ignored. However, all communications are sent to this 
 * class regardless and each broker has its own queue of statements so they can be 
 * monitored and limited if necessary.
 * 
 * FIXME:
 * 		Writes should have priority over reads (need priority mechanism? One built in?)
 * 		
 * 
 */
public class ConnectionManager extends Observable {
	

	/** Manager object where only one instance of it can exist. Is used
	 * to control access to the database. */
	private static ConnectionManager con_manager					= null;
	/** Flag that determines if Brokers are allowed to access the database simultaneously,
	 * even though they are only accessing their own related tables and fields. 
	 * This will not protect against non-WebAgenda connections to the database. 
	 * <br>
	 * Default is false -- multiple connections are allowed. */
	private static boolean one_request										= false;
	
	/** A list of connections that reside in a thread that access the database  */
	private LinkedList<ThreadedConnection> connections						= null;
	
	/**
	 * Private Constructor that sets up the ConnectionManager and initializes internal lists.
	 * 
	 * @throws SQLException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws HeadlessException 
	 */
	private ConnectionManager() throws HeadlessException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
	{		
		connections = new LinkedList<ThreadedConnection>();
		
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
			Logging.writeToLog(Logging.ACCESS_LOG, Logging.NORM_ENTRY, "Connection Manager Rule: Multiple Connections");
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
			if(one_request) {
				Logging.writeToLog(Logging.ACCESS_LOG, Logging.NORM_ENTRY, "Connection Manager Rule: Singular Connection");
			}
			else {
				Logging.writeToLog(Logging.ACCESS_LOG, Logging.NORM_ENTRY, "Connection Manager Rule: Multiple Connections");
			}
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
	
	/**
	 * Method that works twofold:<br>
	 * <br>
	 * 1) Singular Thread: When a request is made from a broker, the thread drops the desirable statement into ConnectionManager's
	 * queue. Singular Thread will 
	 * 
	 * 
	 * @param state
	 * @return
	 */
	protected ResultSet issueStatement(Statement state)
	{
		
		return null;
	}
	
	/**
	 * Adds a connection to a thread which may represent the entire database connection or one broker's connection.
	 * 
	 * @param o Object connection object
	 * @return Object connection
	 */
	Object addConnection(Object o, String name)
	{
		try {
			connections.add(new ThreadedConnection(o,name));
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Logging.writeToLog(Logging.CONN_LOG, Logging.NORM_ENTRY, "Added a Connection " + connections.size() + " to a thread");
		return o;
	}

	/**
	 * Returns a ThreadedConnection object.
	 * Thread 0 will always be the Singular Thread. All other threads are broker threads if they exist.
	 * 
	 * @param pos int position in array of threaded connections
	 * @return ThreadedConnection object
	 */
	ThreadedConnection getConnection(int pos)
	{
		return connections.get(pos);
	}
	
	/**
	 * Returns the number of database connection threads. 
	 * @return
	 */
	public int numberOfConnections()
	{
		return connections.size();
	}
	
	@Override
	public void notifyObservers(Object arg)
	{
		this.setChanged();
		super.notifyObservers(arg);
	}
	
	/**
	 * Getter method for determining if application allows multiple database connections to exist, or whether to use only the
	 * singular thread for the db connection.
	 * 
	 * @return boolean true if only one thread connects to the database. False if multiples do.
	 */
	public static boolean isSingular()
	{
		return ConnectionManager.one_request;
	}
}
