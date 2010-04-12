/**
 * 
 */
package application;

import java.sql.Connection;
import java.util.Date;

/**
 * Wrapper for JDBC database connections, used to provide additional
 * functionality for connection pooling.
 * 
 * @author Daniel Wehr, Daniel Kettle
 * @version 0.1.0
 */
public class DBConnection
    {
    /**
     * Database connection object that statements and queries are sent through.
     */
    private Connection conn        = null;
    /**
     * Determines when connection was last accessed so unused connections can be
     * closed and their memory returned.
     */
    private long       lastAccess  = -1;
    /**
     * True if this connection object is in use and cannot be closed.
     * Connections are only closed when isAvailable is true and hasn't been
     * accessed for a specific duration.
     */
    private boolean    isAvailable = false;
    
    /**
     * Constructor that takes a database connection instance and wraps it with
     * values that add usage information.
     * 
     * @param newConn Database connection object to wrap in this class
     */
    public DBConnection(Connection newConn)
        {
        conn = newConn;
        lastAccess = System.currentTimeMillis();
        }
    
    /**
     * Returns the database connection object so that statements can be sent
     * through it. Each getConnection() request from this class will reset the
     * access time to current date.
     * 
     * @return Connection database connection object
     */
    public Connection getConnection()
        {
        lastAccess = System.currentTimeMillis();
        return conn;
        }
    
    /**
     * Returns the time that the connection was last accessed.
     * 
     * @return long Time last accessed.
     */
    public long getAccessTime()
        {
        return lastAccess;
        }
    
    /**
     * Returns true if the connection can be used for statements, false if
     * connection cannot be used.
     * 
     * @return boolean is available
     */
    public boolean isAvailable()
        {
        return isAvailable;
        }
    
    /**
     * Sets this connection to available; this must be done manually after a
     * statement (or series of statements) is finished. Brokers handle this
     * automatically. Availability also must be set to false before statement is
     * sent to the connection.
     * 
     * @param newAvailable boolean available status
     * @return boolean change in availability (re-initialized isAvailable param)
     */
    public boolean setAvailable(boolean newAvailable)
        {
        isAvailable = newAvailable;
        return isAvailable;
        }
    
    }
