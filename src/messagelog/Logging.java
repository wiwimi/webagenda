/**
 * messagelog - Logging.java
 */
package messagelog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class Logging {

	public static final String LOG_FILE_HEADER_TXT				= ""; //TODO
	
	/** The base location where all log files are stored.<br> 
	 * TODO: Have installer determine where the log folder(s) are located. */
	public static final String LOG_LOCATION 					= "log/";
	/** Where the connection log file(s) are stored -- monitor where connections
	 * are originating from, what time they occur, and successes or failures. */
	public static final String CONNECTION_LOG					= LOG_LOCATION + "connection.log";
	public static final String ERR_ENTRY						= "(EE)",
							   WARN_ENTRY						= "(WW)",
							   NORM_ENTRY						= "(--)";
	
	public static final int CONN_LOG							= 0;
							   
	
	private static LinkedList<LogFile> logfiles					=  null;
	
	
	// FIXME: This code is messy and not properly structured
	
	public static void initializeLogs() throws exception.InitializedLogFileException 
	{
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
				logfiles.add(new LogFile(CONNECTION_LOG)); // Connection log is firest
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void writeToConnectionLog(String TYPE, String message)
	{
		try {
			logfiles.get(CONN_LOG).logMessage(TYPE + " " + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void writeToConnectionLog(String message)
	{
		writeToConnectionLog(Logging.NORM_ENTRY, message);
	}
	
	public static void closeAllLogs()
	{
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
	
}
