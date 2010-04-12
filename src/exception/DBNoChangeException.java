/**
 * exception - DBNoChangeException.java
 */
package exception;

/**
 * Exception is thrown when an update is made that does not change the original
 * values in the database.
 * 
 * @author Daniel Kettle
 * @version 0.01.00
 * @license GPL 2
 */
public class DBNoChangeException extends DBException {
	private static final long serialVersionUID = 5381735819317615138L;
	
	/**
	 * Empty Constructor
	 */
	public DBNoChangeException()
	{
		super();
	}
	
	/**
	 * Constructor with message that is displayed when thrown
	 * @param message a custom debug message for the exception.
	 */
	public DBNoChangeException(String message)
	{
		super(message);
	}

}
