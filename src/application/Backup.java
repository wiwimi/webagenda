/**
 * 
 */
package application;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class Backup
	{
	
	public static String backupDB(File backupDir, File sqlDir)
		{
		if (backupDir == null)
			throw new NullPointerException("Target backup directory can not be null.");
		if (sqlDir == null)
			throw new NullPointerException("MySQL bin directory can not be null.");
		
		String filePath = null;
		
		//Get necessary parameters.
		SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date now = new Date(System.currentTimeMillis());
		String formatDate = format.format(now);
		String DBName = ConnectionManager.getDb_name();
		
		//Ensure backup folder exists.
		if (!backupDir.exists())
			backupDir.mkdir();
		
		filePath = backupDir+File.separator+formatDate+".sql";
		String backupFilePath = backupDir+File.separator+"backupErrors.sql";
		String command = "mysqldump --databases "+DBName+" -u WABroker -ppassword --single-transaction --skip-extended-insert --complete-insert --log-error="+backupFilePath+" --result-file="+filePath;
		
		Runtime rt = Runtime.getRuntime();
		try
			{
			Process pr = rt.exec(command, null, sqlDir);
			
			int exit = pr.waitFor();
			
			if (exit == 0)
				System.out.println("Backup complete. New File: "+filePath);
			else
				System.out.println("Backup failed, code: "+exit);
			}
		catch (IOException e)
			{
			e.printStackTrace();
			}
		catch (InterruptedException e)
			{
			e.printStackTrace();
			}
		
		return filePath;
		}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		Backup.backupDB(new File("C:/WebAgendaBackup/"), new File("D:/Program Files/MySQL/MySQL Server 5.1/bin/"));
		}
	
	}
