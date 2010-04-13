/**
 * 
 */
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Provides basic functionality for running hot backups on the system.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class Backup
    {
    /**
     * Creates a new backup of the WebAgenda database and all information it
     * contains, using the given installation directory for the existing MySQL
     * server, and the target save directory.
     * 
     * @return a string containing the absolute path to the newly created sql
     *         file
     */
    public static String backupDB()
        {
        File backupLocFile = new File("C:/WebAgendaConfig/backupLocations.txt");
        
        if (!backupLocFile.exists())
            throw new NullPointerException("Backup locations file has been removed: "+backupLocFile.getAbsolutePath());
        
        File backupDir = null;
        File sqlDir = null;
        try
            {
            BufferedReader in = new BufferedReader(new FileReader(backupLocFile));
            String backupDirString = in.readLine();
            String sqlDirString = in.readLine();
            
            backupDir = new File(backupDirString.substring(backupDirString.indexOf(' ') + 1,
                    backupDirString.length()));
            sqlDir = new File(sqlDirString.substring(sqlDirString.indexOf(' ') + 1, sqlDirString
                    .length()));
            in.close();
            }
        catch (FileNotFoundException e1)
            {
            e1.printStackTrace();
            }
        catch (IOException e1)
            {
            e1.printStackTrace();
            }
        
        // Get necessary parameters.
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date now = new Date(System.currentTimeMillis());
        String formatDate = format.format(now);
        String DBName = ConnectionManager.getDb_name();
        
        // Ensure backup folder exists.
        if (!backupDir.exists())
            backupDir.mkdir();
        
        String filePath = backupDir + File.separator + formatDate + ".sql";
        String errorLogPath = backupDir + File.separator + "backupErrors.txt";
        String command = "mysqldump --databases " +
                DBName +
                " -u WABroker -ppassword --single-transaction --skip-extended-insert --complete-insert --log-error=" +
                errorLogPath + " --result-file=" + filePath;
        
        Runtime rt = Runtime.getRuntime();
        try
            {
            // Execute backup command.
            Process pr = rt.exec(command, null, sqlDir);
            
            // Wait for backup to finish.
            int exit = pr.waitFor();
            
            /*
             * Debug printouts. if (exit == 0)
             * System.out.println("Backup complete. New File: "+filePath); else
             * System.out.println("Backup failed, code: "+exit);
             */
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
    
    }
