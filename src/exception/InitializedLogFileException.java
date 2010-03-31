/**
 * exception - InitializedLogFileException.java
 */
package exception;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class InitializedLogFileException extends Exception {
	
	/** Automatically generated serial version uid */
	private static final long serialVersionUID = -3896812803637911425L;

	/**
	 * Empty Constructor
	 */
	public InitializedLogFileException()
	{
		super();
	}
	
	/**
	 * Constructor that displays message when exception is thrown.
	 * @param str
	 */
	public InitializedLogFileException(String str)
	{
		super(str);
	}
	
}
