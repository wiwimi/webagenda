/**
 * application - AppDriver.java
 */
package application;

/*
 * TODO: Priority in Queue -- when adding, have multiple queues. Make sure one queue works first
 */

import java.awt.HeadlessException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import application.ThreadedConnection;

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
			
			
			
			// ---- Set up Logs ----
			
				// Logs first, since ConnectionManager uses log files
			messagelog.Logging.initializeLogs();

				// Determine IP address (this will be an args item in the installer or command line specification)
		    InetAddress thisIp = InetAddress.getLocalHost();
			
			// ---- Set up Connection Manager ----
			
			con_man = ConnectionManager.getManager(true); // Only have one connection into the db
			
			con_man.addConnection(WaConnection.getConnection(thisIp),"Singular"); // Grab the connection object that is produced.
			
			/* In the event that multiple connections are used, ConnectionManager still has the singular threaded connection
			 * object saved at position 0 in the linked list of connections. 
			 * TODO: After all connections (if multiple connections are chosen) are initialized, remove the original singular
			 * threaded connection.  */
			
			// ---- Set up Brokers ----
			
				// The setting up of FlushThreads is in the constructor called in getBroker()
			EmployeeBroker brok_emp = EmployeeBroker.getBroker();
			PermissionBroker brok_perm = PermissionBroker.getBroker();
			ScheduleBroker brok_sched = ScheduleBroker.getBroker();
			
			
			// ---- Observe Connection Threads ----
			
			// These threads are daemons, so they will exit when program exits.
			//TODO: Implement proper shutdown procedure
			
			ThreadedConnection tc = null; // Prepare for creating ThreadedConnection using Singular Connector
			for(int i = 0; i < con_man.numberOfConnections(); i++) {
				tc = con_man.getConnection(i); // Begin thread, get singular thread connector
			}
			
			
			con_man.notifyObservers("Notifying Manager to send this to all ThreadedConnections");
			con_man.notifyObservers(new SqlStatement(null,null));
			con_man.notifyObservers(new SqlStatement(null,null));
			con_man.notifyObservers(new SqlStatement(null,null));
			
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
