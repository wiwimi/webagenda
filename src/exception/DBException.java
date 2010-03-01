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

	public DBException()
		{
		super();
		}
	
	public DBException(String message)
		{
		super(message);
		}
	
	public DBException(String message, Throwable cause)
		{
		super(message, cause);
		}
	}
