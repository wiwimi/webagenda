/**
 * 
 */
package exception;

/**
 * When thrown, indicates that a delete action was attempting to remove a record
 * that is currently referenced in another table.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBInUseException extends DBException
	{
	private static final long	serialVersionUID	= -3115868541413364222L;

	/**
	 * Default/empty constructor.
	 */
	public DBInUseException()
		{
		super();
		}
	
	/**
	 * Creates the exception with a given message providing additional
	 * information.
	 * 
	 * @param message the exception message.
	 */
	public DBInUseException(String message)
		{
		super(message);
		}
	
	/**
	 * Creates the exception with a given message providing additional
	 * information, and a cause for the exception that caused this exception to
	 * be thrown.
	 * 
	 * @param message the exception message.
	 * @param cause the exception that caused this exception to be thrown.
	 */
	public DBInUseException(String message, Throwable cause)
		{
		super(message, cause);
		}
	
	}
