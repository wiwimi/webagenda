/**
 * persistence - Broker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	private DoubleLinkedList<DBConnection>	connections	= new DoubleLinkedList<DBConnection>();
	
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
	
	}
