/**
 * messagelog - Logging.java
 */
package messagelog;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Date;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class Logging {

	public static final String LOG_FILE_HEADER_TXT				= ""; //TODO
	
	/** Flag for enabling logging. Definitely recommended; if no logging is
	 * performed, all logging messages will be ignored, no files created.
	 * LinkedList will remain null. */
	private static boolean enable_logging						= true;
	
	/** The base location where all log files are stored.<br> 
	 * TODO: Have installer determine where the log folder(s) are located. */
	public static final String LOG_LOCATION 					= "log/";
	/** Where the connection log file(s) are stored -- monitor where connections
	 * are originating from, what time they occur, and successes or failures. */
	public static final String[] LOGARRAY = new String[]{
		new String(LOG_LOCATION + "connection.log"),
		new String(LOG_LOCATION + "access.log"),
		new String(LOG_LOCATION + "init.log")
	};
	public static final String ERR_ENTRY						= "(EE)",
							   WARN_ENTRY						= "(WW)",
							   NORM_ENTRY						= "(--)";
	/** Location of Connection log in the logfiles linked list array */
	public static final int CONN_LOG							= 0,
					   ACCESS_LOG								= 1,
					   INIT_LOG									= 2;
							   
	/** Linked list that holds log file references */
	private static LinkedList<LogFile> logfiles					=  null;
	
	/**
	 * Initializes log files by creating the linked list which holds log files and
	 * adding individual pre-defined log file types to it, which can be accessed
	 * by *_LOG integer variables.
	 */
	public static void initializeLogs() throws exception.InitializedLogFileException 
	{
		if(!enable_logging) return;
		
		if(logfiles != null) {
			throw new exception.InitializedLogFileException
				("Cannot initialize an already-initialized log file array");
		}
			
		// Initialize the new Log files if exception does not interfere
		logfiles = new LinkedList<LogFile>();
		
		for(int i = 0; i < 1; i++)
		{
			// Initialize all the log files here
			try {
				logfiles.add(new LogFile(LOGARRAY[CONN_LOG])); // Connection log is firest
				logfiles.add(new LogFile(LOGARRAY[ACCESS_LOG]));
				logfiles.add(new LogFile(LOGARRAY[INIT_LOG]));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Writes to log file specified by parameters, will also flush after every write. 
	 * Since recording behavior is important especially leading up to any issues the program
	 * may encounter, data integrity is a high priority. Logs should not be used frequently
	 * for debugging messages (those should probably go to system.out)
	 * 
	 * @param LOGFILE Type of Logfile to write to, such as the connection log file
	 * @param TYPE Style of log entry, whether line represents an error, warning, or simple message
	 * @param message String message to write to log file.
	 */
	public static void writeToLog(int LOGFILE, String TYPE, String message)
	{
		if(!enable_logging) return;
		try {
			// the line.separator command is a system-independant newline character inserted at the end of a log file.
			logfiles.get(LOGFILE).logMessage(TYPE + " " + message + " [" + new Date() + "]" + System.getProperty("line.separator")); 
			logfiles.get(LOGFILE).flushLog();
		} catch (IOException e) {
			System.err.println("Attempted to write to log file, write failed. File may be closed or filesystem read-only.");
			System.err.println("Could not write the following message to log: " + message);
		}
	}
	
	/**
	 * Goes through the linked list using an iterator to close all log files.
	 */
	public static void closeAllLogs()
	{
		if(!enable_logging) return;
		Iterator<LogFile> it = logfiles.iterator();
		while(it.hasNext())
		{
			try {
				it.next().closeLogFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void flushAllLogs() throws IOException
	{
		Iterator<LogFile> it = logfiles.iterator();
		while(it.hasNext())
		{
			it.next().flushLog();
		}
		
	}
	
}
