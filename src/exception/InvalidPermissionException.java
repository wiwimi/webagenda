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

	/**
	 * 
	 */
	private static final long serialVersionUID = 3559826756270573552L;

	public InvalidPermissionException()
	{
		this("User does not have permission to attempt this action");
	}
	
	public InvalidPermissionException(String message)
	{
		super(message);
		Logging.writeToLog(Logging.PERM_LOG,Logging.ERR_ENTRY, message);
	}
	
}
