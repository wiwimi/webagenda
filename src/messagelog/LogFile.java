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

	private File f_log								= null;
	private FileWriter fw_log						= null;
	private BufferedWriter bw_log					= null;
	
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
	 * Basic method to determine if the log file 
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
	
	public void logToFile(String log_message) throws IOException
	{
		bw_log.write(log_message);
	}
	
	public void closeLogFile() throws IOException
	{
		bw_log.close();
	}
	
	public void logMessage(String str) throws IOException
	{
		bw_log.write(str);
	}
	
	public void flushLog() throws IOException
	{
		bw_log.flush();
	}
	
	
}
