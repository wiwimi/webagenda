/**
 * 
 */
package exception;

/**
 * Thrown by brokers when an error occurs within a delete method.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBDeleteException extends Exception
	{
	private static final long	serialVersionUID	= 5401685945167533472L;

	public DBDeleteException()
		{
		super();
		}
	
	public DBDeleteException(String message)
		{
		super(message);
		}
	
	public DBDeleteException(String message, Throwable cause)
		{
		super(message, cause);
		}
	}
