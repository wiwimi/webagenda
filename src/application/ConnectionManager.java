/**
 * 
 */
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import exception.DBDownException;

/**
 * @author Daniel Wehr, Daniel Kettle
 * @version 0.1.0
 */
public class ConnectionManager
    {
    /**
     * Database user that accesses WebAgenda data. Defaults to WABroker.
     */
    private static String  db_user         = "WABroker";
    /**
     * Password that is used for database user.
     */
    private static String  db_pass         = "password";
    /**
     * Database table that holds WebAgenda data. Methods will fail if this is
     * inaccurate.
     */
    private static String  db_name         = "WebAgenda";
    /**
     * Driver for accessing tables and data. Defaults to MySQL driver included.
     */
    private static String  db_driver       = "com.mysql.jdbc.Driver";
    /**
     * Driver instance, holds an instance of the driver so memory isn't wasted
     * re-creating the instance whenever a connection is required.
     */
    private static Object  db_drv_instance = null;
    /**
     * Driver information string that determines port and host and table name
     * used in the connection
     */
    private static String  db_url          = "jdbc:mysql://localhost:3306/" + db_name;
    /**
     * Flag for whether or not the currently held connection settings are from
     * the properties file.
     */
    private static boolean hasSettings     = false;
    
    /**
     * Returns an initialized connection instance wrapped in WebAgenda's
     * DBConnection object for use with Broker objects for connecting to
     * database and performing statements. Instances are controlled by Brokers
     * so memory usage doesn't spiral out of control when the database is it
     * with many connection requests.
     * 
     * @return DBConnection object -- holds a connection with availability
     *         settings.
     * @throws DBDownException If the database information is incorrect or
     *             connection cannot be made (Database daemon / process /
     *             service is not running
     */
    public static DBConnection getConnection() throws DBDownException
        {
        Connection conn = null;
        try
            {
            if (db_drv_instance == null)
                db_drv_instance = Class.forName(db_driver).newInstance();
            
            if (!hasSettings)
                getFileSettings();
            
            conn = DriverManager
                    .getConnection(db_url + "?user=" + db_user + "&password=" + db_pass);
            }
        catch (CommunicationsException e)
            {
            throw new DBDownException("Database Down", e);
            }
        catch (InstantiationException e)
            {
            e.printStackTrace();
            }
        catch (IllegalAccessException e)
            {
            e.printStackTrace();
            }
        catch (ClassNotFoundException e)
            {
            e.printStackTrace();
            }
        catch (SQLException e)
            {
            e.printStackTrace();
            }
        
        return new DBConnection(conn);
        }
    
    /**
     * @return the db_name
     */
    public static String getDb_name()
        {
        return db_name;
        }

    /**
     * Attempts to get connection configuration information from an external
     * properties file.
     */
    private static void getFileSettings()
        {
        File file = new File("Properties.txt");
        
        if (!hasSettings && file.exists())
            {
            try
                {
                BufferedReader bw = new BufferedReader(new FileReader(file));
                db_user = bw.readLine();
                db_pass = bw.readLine();
                db_name = bw.readLine();
                db_driver = bw.readLine();
                db_url = bw.readLine() + db_name;
                bw.close();
                
                // System.out.println("Copied settings from file.");
                hasSettings = true;
                }
            catch (FileNotFoundException e)
                {
                e.printStackTrace();
                }
            catch (IOException e)
                {
                e.printStackTrace();
                }
            }
        }
    
    /**
     * Simple system.out test depicting whether Properties file meets the
     * default values set. A false printout will determine that the value has
     * been changed (is custom) so functionality may vary if a value is
     * incorrect.
     */
    protected static void printTest()
        {
        System.out.println("User: " + (db_user.equals("WABroker")));
        System.out.println("Pass: " + (db_pass.equals("password")));
        System.out.println("Name: " + (db_name.equals("WebAgenda")));
        System.out.println("Driver: " + (db_driver.equals("com.mysql.jdbc.Driver")));
        System.out
                .println("URL: " + (db_url.equals("jdbc:mysql://localhost:3306/" + db_name)));
        }
    }
