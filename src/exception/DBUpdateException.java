/**
 * 
 */
package exception;

/**
 * Thrown by brokers when an error occurs within an update method.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBUpdateException extends Exception
	{
	private static final long	serialVersionUID	= -5691037638537780764L;

	public DBUpdateException()
		{
		super();
		}
	
	public DBUpdateException(String message)
		{
		super(message);
		}
	
	public DBUpdateException(String message, Throwable cause)
		{
		super(message, cause);
		}
	}
