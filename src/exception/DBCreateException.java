/**
 * 
 */
package exception;

/**
 * Thrown by brokers when an error occurs within a create method.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBCreateException extends Exception
	{
	private static final long	serialVersionUID	= 589359863273870950L;

	public DBCreateException()
		{
		super();
		}
	
	public DBCreateException(String message)
		{
		super(message);
		}
	
	public DBCreateException(String message, Throwable cause)
		{
		super(message, cause);
		}
	
	}
