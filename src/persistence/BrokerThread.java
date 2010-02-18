/**
 * persistence - BrokerThread.java
 */
package persistence;

import java.util.Observable;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Every Broker has a BrokerThread object stored inside it; The BrokerThread
 * interacts with the ConnectionManager and works to send requests and retrieve results
 * from the database via the ConnectionManager. BrokerThreads go through the Manager so 
 * that connections are controlled and can be throttled if necessary by using
 * a maximum queue size limiter. 
 * 
 */
public class BrokerThread extends Observable implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
