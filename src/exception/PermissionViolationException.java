/**
 * exception - PermissionViolationException.java
 */
package exception;

/**
 * @author Daniel Kettle
 * @version 0.01.00
 * @license GPL 2
 */
public class PermissionViolationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4836671660353035485L;

	/**
	 * Empty Constructor
	 */
	public PermissionViolationException() {
		super();
	}

	/**
	 * Constructor with message that is displayed when thrown
	 * @param String
	 */
	public PermissionViolationException(String arg0) {
		super(arg0);
	}

	/**
	 * Constructor with throwable cause
	 * @param Throwable
	 */
	public PermissionViolationException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor with message and throwable cause
	 * @param arg0 String 
	 * @param arg1 Throwable
	 */
	public PermissionViolationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
