/**
 * persistence - Broker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;
import utilities.*;
import messagelog.Logging;
import business.BusinessObject;
import business.Employee;
import application.ConnectionManager;
import application.DBConnection;

/**
 * Includes database information for multiple db connectivity, BrokerThreads and
 * associated methods for fetching results from the database and managing the
 * threads. This should be inherited by all broker classes.
 * 
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.3.0
 */
public abstract class Broker<E extends BusinessObject>
    {
    /**
     * Double linked list for holding connection objects. List changes
     * dynamically depending on the need of the application, old connections
     * will be closed and removed periodically.
     */
    private DoubleLinkedList<DBConnection> connections         = new DoubleLinkedList<DBConnection>();
    /**
     * Monitor thread for checking broker objects that are inactive, unused and
     * consuming memory needlessly.
     */
    private BrokerConMonitor               bcon_mon            = null;
    /**
     * Minimum connections for Broker object, must be 1 or higher to be valid.
     * This prevents NullPointerExceptions when all connections are removed from
     * the DoubleLinkedList.
     */
    private static final int               int_min_connections = 1;
    /**
     * Boolean used to determine if the BrokerConMonitor should continue to run.
     */
    private boolean                        runConnectionThread = true;
    
    /**
     * Accepts a newly made Business Object, and creates its equivalent record
     * within the database.
     * 
     * @param createObj The object to add to the database.
     * @return true if the create was successful, otherwise false.
     */
    public abstract boolean create(E createObj, Employee caller) throws DBException,
            DBDownException, InvalidPermissionException, PermissionViolationException;
    
    /**
     * Retrieves Business Objects from the database and return them given a set
     * of search parameters.
     * 
     * @param getObj A newly created object used to determine what will be
     *            returned. All non-null attributes will be used as search
     *            criteria. If the primary key is filled in the search object,
     *            only that will be used and all others will be ignored.
     */
    public abstract E[] get(E searchTemplate, Employee caller) throws DBException, DBDownException,
            InvalidPermissionException, PermissionViolationException;
    
    /**
     * Applies all changes to the updated object to its equivalent record within
     * the database. The updated object must have originally been retrieved from
     * the database.
     * 
     * @param updateObj The previously retrieved object that has been updated.
     * @return true if the update was successful, otherwise false.
     */
    public abstract boolean update(E oldObj, E newObj, Employee caller) throws DBChangeException,
            DBException, DBDownException, InvalidPermissionException, PermissionViolationException;
    
    /**
     * Removes the record from the database that is equivalent to the given
     * object. The object to be deleted must have originally been retrieved from
     * the database.
     * 
     * @param deleteObj
     * @return true if the delete was successful, otherwise false.
     */
    public abstract boolean delete(E deleteObj, Employee caller) throws DBChangeException,
            DBException, DBDownException, InvalidPermissionException, PermissionViolationException;
    
    /**
     * Parses a ResultSet returned by a select query back into cachable objects.
     * The original search must have used a "SELECT *" so that full employee
     * objects are in the ResultSet.
     * 
     * @return An array of cachable objects.
     */
    protected abstract E[] parseResults(ResultSet rs) throws SQLException;
    
    /**
     * Grabs a connection in a DBConnection wrapper class that also monitors how
     * many connections this Broker object has retrieved in total; One
     * connection instance is available at a minimum and this will add any and
     * all created connection to a list where they can be managed.
     * 
     * @return DBConnection wrapper class with active connection.
     * @throws DBDownException if the database is unreachable.
     */
    public DBConnection getConnection() throws DBDownException
        {
        DBConnection returnConnection = null;
        
        if (connections.size() == 0)
            {
            // No connections exist yet, get the first one and save it.
            DBConnection newConn = ConnectionManager.getConnection();
            connections.add(newConn);
            returnConnection = newConn;
            }
        else
            {
            // Get next available connection.
            Iterator<DBConnection> it = connections.iterator();
            while (it.hasNext() && returnConnection == null)
                {
                DBConnection newConn = it.next();
                if (newConn.isAvailable())
                    returnConnection = newConn;
                }
            
            if (returnConnection == null)
                {
                // No available connections found, create new one.
                DBConnection newConn = ConnectionManager.getConnection();
                connections.add(newConn);
                returnConnection = newConn;
                }
            }
        
        returnConnection.setAvailable(false);
        return returnConnection;
        }
    
    /**
     * Initializes the thread that manages old and unused connections in the
     * DBConnection-based list. Creates the Broker Connection Monitor object
     * which runs in the background as a thread.
     */
    public void initConnectionThread()
        {
        runConnectionThread = true;
        if (bcon_mon == null)
            {
            bcon_mon = new BrokerConMonitor();
            
            }
        }
    
    /**
     * Prevents the Broker Connection Monitor from running.
     */
    public void stopConnectionThread()
        {
        runConnectionThread = false;
        }
    
    /**
     * Emulates the parsing of level and version from a String object. If fails,
     * will return false, otherwise will return true. This should be used when
     * testing for valid inputs without creating new PermissionLevels which is
     * also where this method's contents are found (duplicated). (This method is
     * called by every PermissionBroker method FIXME: This method expects a
     * blank following the int.
     * 
     * @param str String to parse
     * @return boolean true if valid.
     */
    public boolean isPermissionLevelInputValid(String str)
        {
        boolean b = false;
        try
            {
            if (Character.isLetter(str.charAt(str.length() - 1)))
                {
                // substring is inclusive / exclusive
                int i = Integer.parseInt(str.substring(0, str.length() - 1));
                if (i >= 0)
                    {
                    Character.isLetter(str.charAt(str.length() - 1));
                    b = true;
                    }
                }
            else
                {
                int i = Integer.parseInt(str);
                if (i >= 0)
                    b = true;
                }
            }
        catch (Exception E)
            {
            b = false;
            }
        return b;
        }
    
    /**
     * Class to monitor a list of db connections for each broker, closing them
     * when they are old and unused within a certain time period (default is 5
     * minutes) to prevent overuse of memory.
     * 
     * @author Daniel Kettle
     * @version 0.01.00
     * @license GPL 2
     */
    private class BrokerConMonitor extends Thread
        {
        
        /** Time that thread is put to sleep before re-awaking */
        private long lng_delay         = 5000; // 300 000 is 5 min.
        /**
         * The difference required between last access time and current time, if
         * met will remove the unused connection from the list.
         */
        private long lng_time_to_close = 5000;
        
        /**
         * Constructor that logs the time and message for initializing a Broker
         * Connection
         */
        public BrokerConMonitor()
            {
            Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY,
                    "Broker Connection Monitor Initialized.");
            this.start();
            }
        
        @Override
        public void run()
            {
            long now = -1L;
            DBConnection dbc = null;
            while (runConnectionThread)
                {
                try
                    {
                    // To do when thread wakes up
                    
                    if (connections.size() > int_min_connections)
                        {
                        now = System.currentTimeMillis();
                        for (int i = connections.size() - 1; i >= int_min_connections; i--)
                            {
                            dbc = connections.get(i);
                            // System.out.println("Connection "+i+": "+((dbc.getAccessTime()
                            // - now) + lng_time_to_close) + " time to remove");
                            // System.out.println("Connection "+i+" is available: "+dbc.isAvailable());
                            if (dbc == null)
                                {
                                System.out.println("DBConnection is null");
                                }
                            else if (dbc.isAvailable())
                                {
                                if (dbc.getAccessTime() < now - lng_time_to_close)
                                    {
                                    // System.out.println("Closing connection "
                                    // + i);
                                    connections.remove(dbc);
                                    dbc.getConnection().close();
                                    }
                                }
                            }
                        }
                    
                    dbc = null;
                    now = -1L;
                    Broker.BrokerConMonitor.sleep(lng_delay);
                    
                    }
                catch (InterruptedException e)
                    {
                    
                    System.out.println("Thread woken up");
                    }
                catch (SQLException e)
                    {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    }
                }
            }
        
        }
    
    }
