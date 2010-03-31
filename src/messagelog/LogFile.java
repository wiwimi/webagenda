/**
 * messagelog - LogFile.java
 */
package messagelog;

import java.io.*;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class LogFile {

	/** File that is written to  */
	private File f_log								= null;
	/** FileWriter that writes to File object */
	private FileWriter fw_log						= null;
	/** BufferedWriter that buffers writes to FileWriter */
	private BufferedWriter bw_log					= null;
	
	/**
	 * Constructor that initializes a file to a String parameter
	 * @param file_location String
	 * @throws IOException If file not found, no write permissions, etc
	 */
	LogFile(String file_location) throws IOException
	{
		f_log = new File(file_location);
		if(!initFile()) {
			throw new IOException("Chances are you cannot write to file location " + file_location);
		}
		
		fw_log = new FileWriter(f_log);
		bw_log = new BufferedWriter(fw_log);
	}
	
	/**
	 * Basic method to determine if the log file exists. If file does not exist and method cannot write to desired location,
	 * an error is thrown.
	 * @return
	 */
	public boolean initFile()
	{
		if(!f_log.exists()) {
			try {
				f_log.createNewFile();
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Logs a message to this log file
	 * @param log_message String
	 * @throws IOException
	 */
	public void logToFile(String log_message) throws IOException
	{
		bw_log.write(log_message);
	}
	
	/**
	 * Closes this log file 
	 * @throws IOException
	 */
	public void closeLogFile() throws IOException
	{
		bw_log.close();
	}
	
	/**
	 * A duplicate method of logToFile I guess
	 * @param str String
	 * @throws IOException
	 */
	public void logMessage(String str) throws IOException
	{
		bw_log.write(str);
	}
	
	/**
	 * Flushes the log file data
	 * @throws IOException
	 */
	public void flushLog() throws IOException
	{
		bw_log.flush();
	}
	
	
}
