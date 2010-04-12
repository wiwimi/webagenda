/**
 * exception - InvalidPermissionException.java
 */
package exception;

import messagelog.Logging;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class InvalidPermissionException extends Exception {
	private static final long serialVersionUID = 3559826756270573552L;

	/**
	 * Empty Constructor with predefined message sent to parent
	 */
	public InvalidPermissionException()
	{
		this("User does not have permission to attempt this action");
	}
	
	/**
	 * Constructor with message that is displayed when thrown.
	 * Message is also logged.
	 * @param message The custom debug message for the exception.
	 */
	public InvalidPermissionException(String message)
	{
		super(message);
		Logging.writeToLog(Logging.PERM_LOG,Logging.ERR_ENTRY, message);
	}
	
}
