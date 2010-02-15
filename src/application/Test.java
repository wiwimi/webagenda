/**
 * 
 */
package application;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
			ResultSet employees = WaConnection.issueQuery("SELECT * FROM EMPLOYEE;");
			employees.next(); // Must be positioned to first (next) item before it can be read
			int i = employees.getInt(1);
			String str = employees.getString(3);
			
			System.out.println("Employee " + i + " has a first name of " + str);
			
			
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
