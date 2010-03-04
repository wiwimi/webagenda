/**
 * 
 */
package exception;

/**
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBDownException extends Exception
	{
	private static final long	serialVersionUID	= -7883072133466689940L;

	public DBDownException()
		{
		super();
		}
	
	public DBDownException(String message)
		{
		super(message);
		}
	
	public DBDownException(String message, Throwable cause)
		{
		super(message, cause);
		}
	}
