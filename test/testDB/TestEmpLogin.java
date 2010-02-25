/**
 * 
 */
package testDB;

import java.io.IOException;
import java.sql.SQLException;

import messagelog.Logging;
import exception.InitializedLogFileException;
import exception.InvalidLoginException;
import business.Employee;
import persistence.EmployeeBroker;

/**
 * Example for how to validate users when they attempt to log into the system.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class TestEmpLogin
	{
	
	public TestEmpLogin()
		{
		
		try {
			Logging.initializeLogs();
		} catch (InitializedLogFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		 * Create sample username/password strings. These would normally be
		 * entered by the user in the web interface. "user1" exists in the test
		 * data and should return an employee object. "fakeUser" does not exist
		 * and should throw an exception.
		 */
		String user1 = "user3", user2 = "fakeUser";
		String password1 = "password", password2 = "pass";
		
		// Get the employee broker.
		EmployeeBroker empBroker = EmployeeBroker.getBroker();
		
		// Try to use login credentials to get an employee object.
		System.out.println("Attempting login with 'user3'/'password'");
		try
			{
			Employee loggedIn = empBroker.tryLogin(user1, password1);
			
			/*
			 * If the above line passes with no errors, you could then add the
			 * employee object to the database. For now, we'll just print that the
			 * login was successful, and the contents of the employee object that
			 * was returned.
			 */

			System.out.println("Login Successful:");
			System.out.println(loggedIn);
			}
		catch (InvalidLoginException e)
			{
			System.out.println(e.getMessage());
			}
		catch (SQLException e)
			{
			e.printStackTrace();
			}
		
		System.out.println("\nAttempting login with 'fakeUser'/'pass'");
		try
			{
			Employee loggedIn = empBroker.tryLogin(user2, password2);
			
			/*
			 * The above line should fail since the username/pass are not in the
			 * DB, and an exception will be thrown instead.
			 */
			}
		catch (InvalidLoginException e)
			{
			/*
			 * For now we'll just catch the exception and print its message.
			 * Instead, you might do something in the UI when this exception is
			 * caught to tell them that their login failed or was incorrect.
			 */
			System.out.println(e.getMessage());
			}
		catch (SQLException e)
			{
			e.printStackTrace();
			}
		
		}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		new TestEmpLogin();
		
		}
	
	}
