/**
 * persistence - BrokerControl.java
 */
package persistence;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Class to queue up request statements to the database from all the brokers. 
 * Only applicable if Brokers work on a singular connection, otherwise this control
 * is ignored. However, all communications are sent to this class regardless and
 * each broker has its own queue of statements so they can be monitored and limited
 * if necessary.
 */
public class BrokerControl {

	/** Maximum number of statements that can be held in a queue for size and memory usage reasons.
	 * Any requests that max out this queue size 'should' receieve a <i>database busy</i> error. */
	private static int int_max_queue_size					= 500;
	
	
}
