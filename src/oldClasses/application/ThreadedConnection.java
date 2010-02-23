/**
 * application - ThreadedConnection.java
 */
package oldClasses.application;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import java.sql.Connection;

import messagelog.Logging;

import com.mysql.jdbc.Statement;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class ThreadedConnection extends Thread implements Observer, Runnable {

	private Object connection 							= null;
	private Queue<CachableResult> statements			= null;
	
	/**
	 * Constructor that saves the web agenda connection for use in the thread
	 * 
	 * @param wac WaConnection WebAgenda Connection
	 * @throws SQLException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws HeadlessException 
	 */
	public ThreadedConnection(Object o, String name) throws HeadlessException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
	{
		connection = o;
		statements = new LinkedList<CachableResult>();
		this.setDaemon(false);
		ConnectionManager.getManager().addObserver(this);
	}
	
	@Override 
	public void run()
	{
		// Declare Variables here to reduce memory consumption in loop
		ResultSet results = null;
			Statement st = null;
		// Get connection from object
		Connection con = (Connection) connection;
		System.out.println("Starting Sql Database loop");
		
		while(true)
		{
			// Get sql statement from queue
			CachableResult c_result = statements.poll();
			if(c_result == null) {
				// No items in queue, can exit
				Logging.writeToLog(Logging.ACCESS_LOG, Logging.WARN_ENTRY, "A Null CachableResult object was received, discarded.");
				break;
			}
			// Send request to database
			try {
				st = (Statement) con.createStatement();
				System.out.println("SQL: " + c_result.getSql());
				results = st.executeQuery(c_result.getSql());
				c_result.setResults(results, c_result.getSql());
				c_result.printResults();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				results = null;
				System.err.println("Execution of SqlStatement failed. Check Synatx of Query.");
				e1.printStackTrace();
			}
			
			
			
			
			
			try {
				Thread.sleep(1000); // Temporary, to emulate the time it takes to process db request (not accurate)
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Statements Left: " + statements.size());
			c_result.notifyObservers(results);
		}
		
		System.out.println("Finished Sql Manage Loop, now exiting");
		results = null;
		st = null;
		con = null; // Since connection is the actual connection, this can be nulled without fear of disconnecting
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String)
			System.out.println(arg.toString());
		else if(arg instanceof CachableResult)
		{
			System.out.println("Recieved a CacheResult object, now running thread on it");
			// We only want it to start when the list goes from empty to non-empty.
			statements.add((CachableResult) arg);
			if(statements.size() == 1) {
				start(); // Was previously 0 
			}
		}
		else return;
	}
}
