/**
 * 
 */
package exception;

/**
 * Thrown when an error or exception is encountered while interacting with the
 * database.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBException extends Exception
	{
	private static final long	serialVersionUID	= 4422954236349309804L;

	/**
	 * Empty Constructor.
	 */
	public DBException()
		{
		super();
		}
	
	/**
	 * Constructor with message that is displayed when thrown.
	 * 
	 * @param message a custom debug message for the exception.
	 */
	public DBException(String message)
		{
		super(message);
		}
	
	/**
	 * Constructor with message and throwable cause
	 * @param message a custom debug message for the exception.
	 * @param cause the exception that caused the DBException to be thrown.
	 */
	public DBException(String message, Throwable cause)
		{
		super(message, cause);
		}
	}
