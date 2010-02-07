/**
 * persistence - ThreadedConnection.java
 */
package persistence;

import java.util.Observable;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Every Broker has a ThreadedConnection object stored inside it; The ThreadedConnection
 * interacts with the ConnectionManager and works to send requests and retrieve results
 * from the database. ThreadedConnections go through the Manager so that connections are
 * controlled and can be throttled if necessary. 
 * 
 */
public class ThreadedConnection extends Observable implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
