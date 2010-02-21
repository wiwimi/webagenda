/**
 * exception - FullBrokerQueueException.java
 */
package exception;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * An exception that is thrown when the Broker attempts to start a BrokerThread to retrieve data, but
 * the maximum thread limit has been reached and no free threads are available. <br>
 * 
 * When this type of exception is thrown, it should force the request to wait until a thread is free by
 * using an observer / observable and a queue option. Every BrokerThread should return a ResultSet when
 * called based on a request method (issueRequest(...) method for example).
 */
public class FullBrokerQueueException extends Exception {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -621989508955301705L;

	public FullBrokerQueueException()
	{
		super();
	}
	
	public FullBrokerQueueException(String message)
	{
		super(message);
	}
}
