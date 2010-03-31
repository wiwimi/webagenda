package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ConnectionLauncher {

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
