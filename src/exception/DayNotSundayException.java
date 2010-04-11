/**
 * 
 */
package exception;

/**
 * Thrown if the startDate in a schedule, or the startDate given to the schedule
 * generator is not a sunday.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DayNotSundayException extends DBException
	{
	private static final long	serialVersionUID	= 3298169496731597795L;

	/**
	 * Default constructor.
	 */
	public DayNotSundayException()
		{
		super();
		}
	
	/**
	 * Creates an exception with an assigned message, which should help explain
	 * the cause.
	 * 
	 * @param message the message for the exception.
	 */
	public DayNotSundayException(String message)
		{
		super(message);
		}
	
	}
