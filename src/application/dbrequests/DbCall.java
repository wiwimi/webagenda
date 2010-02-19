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
 * Is the parent class for all other Cached Broker classes. Used for modularity.
 * 
 */
public abstract class DbCall {

	private String temp = "SELECT * FROM";
	private String table = "`.EMPLOYEE`";
	private String end = ";";
	
	private String database = "`WebAgenda`";

	public String getSelectString()
	{
		return temp + "," + database + table + end;
	}
	
}
