/**
 * application - AppDriver.java
 */
package application;

import java.awt.HeadlessException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;

import persistence.*;

import messagelog.*;
import exception.*;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * AppDriver is a class to launch the backend, setup all the brokers, establish
 * connections, and wait for input. Any errors in setup should be outputted
 * and logged.
 */
public class AppDriver {

	/** Connection Manager for setting up Broker connections. */
	private static ConnectionManager con_man = null;
	
	
	/**
	 * Method to setup the backend
	 * 
	 * @param args String[] command line arguments
	 */
	public static void main(String[] args)
	{
		try {
			
			// ---- Set up Connection Manager ----
			con_man = ConnectionManager.getManager();
			
			// ---- Set up Logs ----
			
			messagelog.Logging.initializeLogs();

		    InetAddress thisIp = InetAddress.getLocalHost();
		    
			WaConnection.getConnection(thisIp);
			
			// ---- Set up Brokers ----
			
			EmployeeBroker brok_emp = EmployeeBroker.getBroker();
			PermissionBroker brok_perm = PermissionBroker.getBroker();
			ScheduleBroker brok_sched = ScheduleBroker.getBroker();
			
			// ---- 
			
			
			
			
//			ResultSet employees = WaConnection.issueQuery("SELECT * FROM EMPLOYEE;");
//			employees.next(); // Must be positioned to first (next) item before it can be read
//			int i = employees.getInt(1);
//			String str = employees.getString(3);
//			
//			System.out.println("Employee " + i + " has a first name of " + str);
			
			
			
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
		finally {
			// This statement should only be accessed if the application is shutdown on the server or errors cause application to fail.
			messagelog.Logging.closeAllLogs();
		}
		
	}
	
}
