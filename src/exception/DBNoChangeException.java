/**
 * exception - DBNoChangeException.java
 */
package exception;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class DBNoChangeException extends DBException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5381735819317615138L;
	
	public DBNoChangeException()
	{
		super();
	}
	
	public DBNoChangeException(String message)
	{
		super(message);
	}

}
