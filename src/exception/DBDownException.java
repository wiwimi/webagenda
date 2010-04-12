/**
 * 
 */
package exception;

/**
 * Thrown when a connection is created or a statement is sent through a
 * connection, but the database service/daemon/process is not found.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBDownException extends Exception
	{
	private static final long	serialVersionUID	= -7883072133466689940L;

	/**
	 * Empty constructor. 
	 */
	public DBDownException()
		{
		super();
		}
	
	/**
	 * Constructor with message displayed when thrown.
	 * 
	 * @param message a custom debug message for the exception.
	 */
	public DBDownException(String message)
		{
		super(message);
		}
	
	/**
	 * Constructor with message and throwable cause.
	 * 
	 * @param message a custom debug message for the exception.
	 * @param cause the exception that caused the DBDownException to be thrown.
	 */
	public DBDownException(String message, Throwable cause)
		{
		super(message, cause);
		}
	}
