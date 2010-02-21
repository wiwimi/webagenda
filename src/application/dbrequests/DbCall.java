/**
 * application.dbrequests - DbCall.java
 */
package application.dbrequests;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Abstract class to specifiy database request statements in a uniformed and consistent manner.
 * Is the parent class for all other Cached Broker classes. Used for modularity. Contains
 * variables that prevent hardcoding of sql statements.
 * 
 */
public abstract class DbCall {

	private String database = "`WebAgenda`.";
	private String select = "SELECT";
	private String update = "UPDATE";
	private String delete = "DELETE";
	private String insert = "INSERT";
	
}
