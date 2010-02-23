/**
 * application - SingularThreadControlException.java
 */
package oldClasses.application;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * An exception that is thrown when ConnectionManager allows Brokers to make their own connections
 * to the database. An exception is used because it returns control to the BrokerThread which will
 * allow the continuation of the database connection and request. In which case, it will use its
 * own limits and discretion.<br>
 * If this exception is NOT caught, control is kept within the ConnectionManager and it is dealt
 * with based on C.M. priorities.
 * 
 * 
 * @see oldClasses.persistence.BrokerThread
 * 
 */
public class SingularThreadControlException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7683979141650273497L;

	public SingularThreadControlException()
	{
		super();
	}
	
	public SingularThreadControlException(String message)
	{
		super(message);
	}
	
	public String toString()
	{
		return super.toString();
	}
	
	
	
}
