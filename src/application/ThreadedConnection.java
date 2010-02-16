/**
 * application - ThreadedConnection.java
 */
package application;

import java.util.Observable;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class ThreadedConnection extends Observable implements Runnable {

	private Object connection 					= null;
	
	/**
	 * Constructor that saves the web agenda connection for use in the thread
	 * 
	 * @param wac WaConnection WebAgenda Connectino
	 */
	public ThreadedConnection(Object o)
	{
		connection = o;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
