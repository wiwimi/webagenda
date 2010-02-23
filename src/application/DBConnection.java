/**
 * 
 */
package application;

import java.sql.Connection;
import java.util.Date;

/**
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class DBConnection
	{
	private Connection	conn;
	private Date			lastAccess	= null;
	private boolean		isAvailable	= false;
	
	public DBConnection(Connection newConn)
		{
		conn = newConn;
		lastAccess = new Date();
		}
	
	public Connection getConnection()
		{
		lastAccess.setTime(System.currentTimeMillis());
		return conn;
		}
	
	public long getAccessTime()
		{
		return lastAccess.getTime();
		}
	
	public boolean isAvailable()
		{
		return isAvailable;
		}
	
	public boolean setAvailable(boolean newAvailable)
		{
		isAvailable = newAvailable;
		return isAvailable;
		}
	
	
	}
