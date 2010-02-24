/**
 * persistence - Broker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import messagelog.Logging;
import business.BusinessObject;
import application.ConnectionManager;
import application.DBConnection;
import utilities.DoubleLinkedList;
import utilities.Iterator;

/**
 * All brokers should inherit this class. Includes database information for
 * multiple db connectivity, BrokerThreads and associated methods for fetching
 * results from the database and managing the threads.
 * 
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.3.0
 */
public abstract class Broker<E extends BusinessObject>
	{
	
	/** Double linked list for holding connection objects. List changes dynamically depending on
	 * the need of the application, old connections will be closed and removed periodically. */
	private DoubleLinkedList<DBConnection>	connections	= new DoubleLinkedList<DBConnection>();
	/** Monitor thread for checking broker objects that are inactive, unused and consuming 
	 * memory needlessly. */
	private BrokerConMonitor bcon_mon = null;
	/** Minimum connections for Broker object, must be 1 or higher to be valid.
	 * This prevents NullPointerExceptions when all connections are removed
	 * from the DoubleLinkedList. */
	private int int_min_connections = 1;
	
	/**
	 * Accepts a newly made object, and creates its equivalent record within the
	 * database. TODO An exception will be thrown if there is already a record in
	 * the database with the same primary key.
	 * 
	 * @param createObj The object to add to the database.
	 * @return true if the create was successful, otherwise false.
	 */
	public abstract boolean create(E createObj) throws SQLException;
	
	/**
	 * Retrieves data from the database and return them as objects.
	 * 
	 * @param getObj A newly created object used to determine what will be
	 *           returned. All non-null attributes will be used as search
	 *           criteria. If the primary key is filled in the search object,
	 *           only that will be used and all others will be ignored.
	 */
	public abstract E[] get(E getObj) throws SQLException;
	
	/**
	 * Applies all changes to the updated object to its equivalent record within
	 * the database. The updated object must have originally been retrieved from
	 * the database. TODO An exception will be thrown if the record to be updated
	 * does not exist in the database.
	 * 
	 * @param updateObj The previously retrieved object that has been updated.
	 * @return true if the update was successful, otherwise false.
	 */
	public abstract boolean update(E updateObj) throws SQLException;
	
	/**
	 * Removes the record from the database that is equivalent to the given
	 * object. The object to be deleted must have originally been retrieved from
	 * the database. TODO An exception will be thrown if the record to be deleted
	 * does not exist in the database.
	 * 
	 * @param deleteObj
	 * @return true if the delete was successful, otherwise false.
	 */
	public abstract boolean delete(E deleteObj) throws SQLException;
	
	/**
	 * Parses a ResultSet returned by a select query back into cachable objects.
	 * The original search must have used a "SELECT *" so that full employee
	 * objects are in the ResultSet.
	 * 
	 * @return An array of cachable objects.
	 */
	public abstract E[] parseResults(ResultSet rs) throws SQLException;
	
	/**
	 * @return
	 */
	public DBConnection getConnection()
		{
		DBConnection returnConnection = null;
		/*
		 * TODO - This method will return a current available database connection,
		 * or create a new one if needed to support additional load.
		 */
		
		if (connections.size() == 0)
			{
			//No connections exist yet, get the first one and save it.
			DBConnection newConn = ConnectionManager.getConnection();
			connections.add(newConn);
			returnConnection = newConn;
			}
		else
			{
			//Get next available connection.
			Iterator<DBConnection> it = connections.iterator();
			while (it.hasNext() && returnConnection == null)
				{
				DBConnection newConn = it.next();
				if (newConn.isAvailable())
					returnConnection = newConn;
				}
			
			if (returnConnection == null)
				{
				//No available connections found, create new one.
				DBConnection newConn = ConnectionManager.getConnection();
				connections.add(newConn);
				returnConnection = newConn;
				}
			}
		
		returnConnection.setAvailable(false);
		return returnConnection;
		}
	
		public void initConnectionThread()
		{
			if(bcon_mon == null) {
				bcon_mon = new BrokerConMonitor();
				
			}
		}
		
		/**
		 * Returns a list of current open connections that the Broker utilizes.
		 * @return DoubleLinkedList<DBConnection> connections
		 */
		DoubleLinkedList<DBConnection> getConnections()
		{
			return this.connections;
		}
		
		public int getNumberOfConnections()
		{
			return this.connections.size();
		}
	
		/**
		 * Class to monitor a list of db connections for each broker, closing
		 * them when they are old and unused within a certain time period
		 * (default is 5 minutes) to prevent overuse of memory.
		 * 
		 * @author Daniel Kettle
		 * @version 0.01.00
		 * @license GPL 2
		 */
	class BrokerConMonitor extends Thread {
		
		private long lng_delay 				= 1500; // 300 000 is 5 min.
		
		public BrokerConMonitor()
		{
			Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Broker Connection Monitor Initialized.");
			this.start();
		}
		
		@Override 
		public void run()
		{
			long now = -1L;
			DBConnection dbc = null;
			while(true)
			{
				try {
					// To do when thread wakes up
					
					now = new java.util.Date().getTime();
					for(int i = 0; i < getConnections().size(); i++)
					{
						dbc = getConnections().get(i);
						System.out.println(((dbc.getAccessTime() - now) + 3000) + " time to remove");
						System.out.println(dbc.isAvailable() + " is available");
						if(dbc == null) {
							System.out.println("DBConnection is null");
						}
						else if(dbc.isAvailable())
						{
							if(dbc.getAccessTime() < now - 3000)
							{
								System.out.println("Closing connection " + i);
								System.out.println("Total size: " + getConnections().size());
								if(getConnections().size() > int_min_connections)
								{
									getConnections().remove(dbc);
									dbc.getConnection().close();
								}
							}
							else {
								// System is still waiting for the time to pass before removing the connection, and provided it's inactive
								
							}
						}
					}
					
					dbc = null;
					now = -1L;
					Broker.BrokerConMonitor.sleep(lng_delay);
					
				} catch (InterruptedException e) {
				
					System.out.println("Thread woken up");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		
	}
	
	}
