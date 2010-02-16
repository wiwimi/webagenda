/**
 * WebAgenda Connection Class
 * application.WaConnection
 */
package application;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JOptionPane;

import messagelog.Logging;

import com.mysql.jdbc.*;

/**
 * @author dann
 * @version 00.01.00
 * @license GPL 2
 */
public class WaConnection extends Connection {

	private static String wac_database				= "mysql";
	private static String wac_dblocation			= "localhost";
	private static String wac_db_user				= "root"; //FIXME: don't use root for final -- this is just for me (dann) since WebAgenda user doesn't work right
	private static String wac_db_table				= "WebAgenda";
	private static String wac_driver				= "com.mysql.jdbc.Driver";
	private static int wac_dbport					= 3306;
	private static Object wac_con_instnc			= null;
	private static Connection wac_connection		= null;
	
	private static String wac_db_url				= "jdbc:" + wac_database + "://" + wac_dblocation + ":" + wac_dbport + "/" + wac_db_table;
	
	private WaConnection()
	{
		
	}
	
	/**
	 * Attempts to store a database connection into an object. Will throw exceptions
	 * if connection fails or will return null. May return an object that is already
	 * initialized if it was done so previously. 
	 * 
	 * @return Connection a connection that can be manipulated. The connection should exist purely for holding 
	 * the connection until the database needs to be disconnected (a very unlikely scenario in the real-world use) 
	 * and then it should be able to be assigned to a null value after db is closed.
	 * <br>
	 * Object is essentially an anchor for holding a connection as long as needed 
	 * @throws ClassNotFoundException - This exception is thrown if the mysql jdbc library is not found.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SQLException 
	 * @throws HeadlessException 
	 */
	static Connection getConnection(InetAddress ip) throws ClassNotFoundException, IllegalAccessException, 
	InstantiationException, HeadlessException, SQLException
	{
		if(wac_con_instnc == null)
		{
			wac_con_instnc = Class.forName (wac_driver).newInstance ();
			wac_connection = (Connection) DriverManager.getConnection (wac_db_url, wac_db_user, JOptionPane.showInputDialog("Enter Password"));
			messagelog.Logging.writeToLog(Logging.CONN_LOG,Logging.NORM_ENTRY,"Connection using " + wac_driver + " is established by " + ip + ".");
		}
		return (Connection) wac_con_instnc;
	}
	
	/**
	 * Returns the ResultSet that is found from the query. Result set can be used
	 * to retrieve information requested from the database.
	 * 
	 * 
	 * @return ResultSet query result
	 * @param The Query (as a string)
	 * <br>Example: SELECT * FROM EMPLOYEE; 
	 */
	static ResultSet issueQuery(String query)
	{
		try {
			// This is the beauty of abstraction layers.
			Statement db_state = (Statement) ((java.sql.Connection) wac_connection).createStatement();
			return db_state.executeQuery(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
