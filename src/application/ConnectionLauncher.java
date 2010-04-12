package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Daniel Kettle
 * @version 0.01.10
 * @license GPL 2
 */
public class ConnectionLauncher {

	/**
	 * Method that sets the database information when run;
	 * Uses Properties file in following order to determine
	 * what values are set:<br>
	 * Username<br>
	 * Password<br>
	 * Database Instance Name<br>
	 * Driver String (Name)<br>
	 * Driver URL (hostname, port; database instance name is appended in 
	 * this method) 
	 * 
	 * TODO: implement main's args[] array to also set 
	 * database information (order is important, unless
	 * flags are used)
	 * 
	 * Prints out a basic test to System.out.
	 * 
	 * @param args String[] array of values.
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{

		
		File file = new File("Properties");
		
		BufferedReader bw = new BufferedReader(new FileReader(file));
			ConnectionManager.setDb_user(bw.readLine());
			ConnectionManager.setDb_pass(bw.readLine());
			String tablename = bw.readLine();
			ConnectionManager.setDb_name(tablename);
			ConnectionManager.setDb_driver(bw.readLine());
			ConnectionManager.setDb_url(bw.readLine() + tablename);
			
			ConnectionManager.printTest();
		
	}
	
}
