/**
 * testDB - TestConnectionLauncher.java
 */
package testDB;

import java.io.IOException;

import application.ConnectionLauncher;
import application.ConnectionManager;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class TestConnectionLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Tests new method of assigning database parameters.
		
		// Ensure that launcher runs without throwing exceptions
		try {
			ConnectionLauncher.main(null);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
