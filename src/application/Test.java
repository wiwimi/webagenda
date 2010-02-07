/**
 * 
 */
package application;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import exception.InitializedLogFileException;
/**
 * @author dann
 *
 */
public class Test {

	public static void main(String[] args)
	{
		try {
			messagelog.Logging.initializeLogs();
		     InetAddress thisIp =
		         InetAddress.getLocalHost();

			WaConnection.getConnection(thisIp);
			WaConnection.issueQuery("SELECT * FROM EMPLOYEE;");
			
			
			messagelog.Logging.closeAllLogs();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InitializedLogFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
