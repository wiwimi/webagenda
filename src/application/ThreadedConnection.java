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

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class ThreadedConnection extends Thread implements Observer, Runnable {

	private Object connection 					= null;
	private Queue<String> statements			= null;
	
	/**
	 * Constructor that saves the web agenda connection for use in the thread
	 * 
	 * @param wac WaConnection WebAgenda Connectino
	 * @throws SQLException 
	 * @throws InstantiationException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws HeadlessException 
	 */
	public ThreadedConnection(Object o, String name) throws HeadlessException, ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException
	{
		connection = o;
		statements = new LinkedList<String>();
		this.setDaemon(true);
		ConnectionManager.getManager().addObserver(this);
	}
	
	@Override 
	public void run()
	{
		System.out.println("running");
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println(arg.toString());
	}
	
}
