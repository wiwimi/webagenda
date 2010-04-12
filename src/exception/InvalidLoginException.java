/**
 * 
 */
package exception;

/**
 * Thrown when an attempt is made to log into the system, and the username or
 * password are incorrect.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class InvalidLoginException extends Exception
	{
	private static final long	serialVersionUID	= 8048540449803829654L;
	
	/**
	 * Empty Constructor
	 */
	public InvalidLoginException()
		{
		super();
		}
	
	/**
	 * Constructor with message that is displayed when thrown
	 * @param message a custom debug message for the exception.
	 */
	public InvalidLoginException(String message)
		{
		super(message);
		}
	}
