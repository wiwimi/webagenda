/**
 * application - ThreadedConnection.java
 */
package application;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

import com.mysql.jdbc.Statement;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class ThreadedConnection extends Thread implements Observer, Runnable {

	private Object connection 						= null;
	private Queue<SqlStatement> statements			= null;
	
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
		statements = new LinkedList<SqlStatement>();
		this.setDaemon(false);
		ConnectionManager.getManager().addObserver(this);
	}
	
	@Override 
	public void run()
	{
		System.out.println("Starting Sql Database loop");
		while(true)
		{
			SqlStatement sqlstatement = statements.poll();
			if(sqlstatement == null) {
				// No items in queue, can exit
				break;
			}
			// Send request to database
			
			try {
				Thread.sleep(1000); // Temporary, to emulate the time it takes to process db request (not accurate)
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Finished statement: " + statements.size());
			
		}
		
		System.out.println("Finished Sql Manage Loop, now exiting");
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String)
			System.out.println(arg.toString());
		else if(arg instanceof SqlStatement)
		{
			statements.add((SqlStatement) arg);
			if(statements.size() == 1) {
				start(); // Was previously 0 
			}
		}
		else return;
	}
	
}
