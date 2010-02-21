/**
 * WebAgenda Connection Class
 * application.WaConnection
 */
package application;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
 * 
 * Class to first initialize connectivity with the database.
 * Once the singular connection communicates with the database, it begins to spawn ThreadedConnections.
 * If only the singular connection is used, the connection object is saved in the ThreadedConnection.
 * WaConnection should only contain the variables used to connect, not the connection itself.
 * 
 */
public class WaConnection {

	private static String wac_database				= "mysql";
	private static String wac_dblocation			= "localhost";
	private static String wac_db_user				= "root"; //FIXME: don't use root for final -- this is just for me (dann) since WebAgenda user doesn't work right
	private static String wac_db_table				= "WebAgenda";
	private static String wac_driver				= "com.mysql.jdbc.Driver";
	private static int wac_dbport					= 3306;
	private static Object wac_con_instnc			= null;
	private static Object wac_connection			= null;
	
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
	public static Object getConnection(InetAddress ip) throws ClassNotFoundException, IllegalAccessException, 
	InstantiationException, HeadlessException, SQLException
	{		
		
		if(wac_con_instnc == null)
		{
			wac_con_instnc = Class.forName (wac_driver).newInstance ();
			//FIXME: Does having objects initialized to a static object interfere with multiple broker connections? 
			wac_connection = (Object) DriverManager.getConnection (wac_db_url, wac_db_user, JOptionPane.showInputDialog("Enter Password"));
			messagelog.Logging.writeToLog(Logging.CONN_LOG,Logging.NORM_ENTRY,"Connection using " + wac_driver + " is established by " + ip + ".");
			
			
		}
		return wac_con_instnc;
	}
	
}
