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
	/** Database user that accesses WebAgenda data. Defaults to WABroker. */
	private static String	db_user				= "WABroker";
	/** Password that is used for database user. */
	private static String	db_pass				= "password";
	/** Database table that holds WebAgenda data. Methods will fail if this is inaccurate. */
	private static String	db_name				= "WebAgenda";
	/** Driver for accessing tables and data. Defaults to MySQL driver included.  */
	private static String	db_driver			= "com.mysql.jdbc.Driver";
	/** Driver instance, holds an instance of the driver so memory isn't wasted re-creating
	 * the instance whenever a connection is required. */
	private static Object	db_drv_instance	= null;
	/** Driver information string that determines port and host and  table name used in the
	 * connectin */
	private static String	db_url				= "jdbc:mysql://localhost:3306/" + db_name;
	
	/**
	 * Returns an initialized connection instance wrapped in WebAgenda's
	 * DBConnection object for use with Broker objects for connecting to database
	 * and performing statements. Instances are controlled by Brokers so memory
	 * usage doesn't spiral out of control when the database is it with many
	 * connection requests.
	 * 
	 * @return DBConnection object -- holds a connection with availability
	 *         settings.
	 * @throws DBDownException If the database information is incorrect or
	 *            connection cannot be made (Database daemon / process / service
	 *            is not running
	 */
	public static DBConnection getConnection() throws DBDownException
		{
		Connection conn = null;
		try
			{
			if (db_drv_instance == null)
				{
				db_drv_instance = Class.forName(db_driver).newInstance();
				}
			conn = DriverManager.getConnection(db_url+"?user="+db_user+"&password="+db_pass);
			}
		catch (CommunicationsException e)
			{
			throw new DBDownException("Database Down",e);
			}
		catch (InstantiationException e)
			{
			e.printStackTrace();
			}
		catch (IllegalAccessException e)
			{
			e.printStackTrace();
			}
		catch (ClassNotFoundException e)
			{
			e.printStackTrace();
			}
		catch (SQLException e)
			{
			e.printStackTrace();
			}
		
		return new DBConnection(conn);
		}

	/**
	 * Set the database user
	 * @param dbUser the db_user to set
	 */
	protected static void setDb_user(String dbUser) {
		db_user = dbUser;
	}

	/**
	 * Set the database user password
	 * @param dbPass the db_pass to set
	 */
	protected static void setDb_pass(String dbPass) {
		db_pass = dbPass;
	}

	/**
	 * Set the database instance name
	 * @param dbName the db_name to set
	 */
	protected static void setDb_name(String dbName) {
		db_name = dbName;
	}

	/**
	 * Set the driver library name to use 
	 * @param dbDriver the db_driver to set
	 */
	protected static void setDb_driver(String dbDriver) {
		db_driver = dbDriver;
	}

	/**
	 * Sets the database url (hostname, port, and database instance; should use the 
	 * getDb_name() method for db instance name appended to url)
	 * @param dbUrl the db_url to set
	 */
	protected static void setDb_url(String dbUrl) {
		db_url = dbUrl;
	}

	/**
	 * Gets the Database user
	 * @return the db_user
	 */
	protected static String getDb_user() {
		return db_user;
	}

	/**
	 * Gets the Database user's password 
	 * @return the db_pass
	 */
	protected static String getDb_pass() {
		return db_pass;
	}

	/**
	 * Gets the database instance name
	 * @return the db_name
	 */
	protected static String getDb_name() {
		return db_name;
	}

	/**
	 * Gets the database connection driver name 
	 * @return the db_driver
	 */
	protected static String getDb_driver() {
		return db_driver;
	}

	/**
	 * Gets the url of the database (includes hostname, port, and db instance name)
	 * @return the db_url
	 */
	protected static String getDb_url() {
		return db_url;
	}
	
	/**
	 * Simple system.out test depicting whether Properties file meets the default
	 * values set. A false printout will determine that the value has been changed (is custom)
	 * so functionality may vary if a value is incorrect.
	 */
	protected static void printTest() 
	{
		System.out.println("User: " + (getDb_user().equals("WABroker")));
		System.out.println("Pass: " + (getDb_pass().equals("password")));
		System.out.println("Name: " + (getDb_name().equals("WebAgenda")));
		System.out.println("Driver: " + (getDb_driver().equals("com.mysql.jdbc.Driver")));
		System.out.println("URL: " + (getDb_url().equals("jdbc:mysql://localhost:3306/" + db_name)));
	}
	}
