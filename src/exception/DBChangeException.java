/**
 * 
 */
package exception;

/**
 * When thrown, indicates that an attempt has been made to update or delete a
 * record within the database, but that record has already been updated or
 * deleted by another user.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBChangeException extends DBException
	{
	private static final long	serialVersionUID	= -7973661398854206917L;
	
	/**
	 * Default/empty constructor.
	 */
	public DBChangeException()
		{
		super();
		}
	
	/**
	 * Creates the exception with a given message providing additional
	 * information.
	 * 
	 * @param message the exception message.
	 */
	public DBChangeException(String message)
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
	public DBChangeException(String message, Throwable cause)
		{
		super(message, cause);
		}
	
	}
