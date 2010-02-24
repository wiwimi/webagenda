/**
 * 
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Daniel Wehr, Daniel Kettle
 * @version 0.1.0
 */
public class ConnectionManager
	{
	private static String	db_user				= "WABroker";
	private static String	db_pass				= "WABrokerPass123";
	private static String	db_name				= "WebAgenda";
	private static String	db_driver			= "com.mysql.jdbc.Driver";
	private static Object	db_drv_instance	= null;
	private static String	db_url				= "jdbc:mysql://localhost:3306/" + db_name;
	
	public static DBConnection getConnection()
		{
		Connection conn = null;
		try
			{
			if (db_drv_instance == null)
				{
				db_drv_instance = Class.forName(db_driver).newInstance();
				}
			conn = DriverManager.getConnection(db_url, db_user, db_pass);
			}
		catch (InstantiationException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		catch (IllegalAccessException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		catch (ClassNotFoundException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		catch (SQLException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		return new DBConnection(conn);
		}
	}
