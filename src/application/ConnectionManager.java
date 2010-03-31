/**
 * 
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import exception.DBDownException;

/**
 * @author Daniel Wehr, Daniel Kettle
 * @version 0.1.0
 */
public class ConnectionManager
	{
	private static String	db_user				= "WABroker";
	private static String	db_pass				= "password";
	private static String	db_name				= "WebAgenda";
	private static String	db_driver			= "com.mysql.jdbc.Driver";
	private static Object	db_drv_instance	= null;
	private static String	db_url				= "jdbc:mysql://localhost:3306/" + db_name;
	
	public static DBConnection getConnection() throws DBDownException
		{
		Connection conn = null;
		try
			{
			if (db_drv_instance == null)
				{
				db_drv_instance = Class.forName(db_driver).newInstance();
				}
			// +"&noAccessToProcedureBodies=true"
			conn = DriverManager.getConnection(db_url+"?user="+db_user+"&password="+db_pass);
			}
		catch (CommunicationsException e)
			{
			throw new DBDownException("Database Down",e);
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

	/**
	 * @param dbUser the db_user to set
	 */
	protected static void setDb_user(String dbUser) {
		db_user = dbUser;
	}

	/**
	 * @param dbPass the db_pass to set
	 */
	protected static void setDb_pass(String dbPass) {
		db_pass = dbPass;
	}

	/**
	 * @param dbName the db_name to set
	 */
	protected static void setDb_name(String dbName) {
		db_name = dbName;
	}

	/**
	 * @param dbDriver the db_driver to set
	 */
	protected static void setDb_driver(String dbDriver) {
		db_driver = dbDriver;
	}

	/**
	 * @param dbDrvInstance the db_drv_instance to set
	 */
	protected static void setDb_drv_instance(Object dbDrvInstance) {
		db_drv_instance = dbDrvInstance;
	}

	/**
	 * @param dbUrl the db_url to set
	 */
	protected static void setDb_url(String dbUrl) {
		db_url = dbUrl;
	}

	/**
	 * @return the db_user
	 */
	protected static String getDb_user() {
		return db_user;
	}

	/**
	 * @return the db_pass
	 */
	protected static String getDb_pass() {
		return db_pass;
	}

	/**
	 * @return the db_name
	 */
	protected static String getDb_name() {
		return db_name;
	}

	/**
	 * @return the db_driver
	 */
	protected static String getDb_driver() {
		return db_driver;
	}

	/**
	 * @return the db_drv_instance
	 */
	protected static Object getDb_drv_instance() {
		return db_drv_instance;
	}

	/**
	 * @return the db_url
	 */
	protected static String getDb_url() {
		return db_url;
	}
	
	protected static void printTest() 
	{
		System.out.println("User: " + (getDb_user().equals("WABroker")));
		System.out.println("Pass: " + (getDb_pass().equals("password")));
		System.out.println("Name: " + (getDb_name().equals("WebAgenda")));
		System.out.println("Driver: " + (getDb_driver().equals("com.mysql.jdbc.Driver")));
		System.out.println("URL: " + (getDb_url().equals("jdbc:mysql://localhost:3306/" + db_name)));
	}
	}
