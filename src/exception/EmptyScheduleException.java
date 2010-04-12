/**
 * 
 */
package exception;

/**
 * Thrown when an attempt is made to create a schedule in the database that
 * has no assigned employees.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class EmptyScheduleException extends DBException
	{
	private static final long	serialVersionUID	= -8694173262481785545L;

	/**
	 * Default constructor.
	 */
	public EmptyScheduleException()
		{
		super();
		}
	
	/**
	 * Constructor including a debug message.
	 * 
	 * @param message the debug message for the exception.
	 */
	public EmptyScheduleException(String message)
		{
		super(message);
		}
	
	}
