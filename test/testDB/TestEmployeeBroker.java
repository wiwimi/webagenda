/**
 * 
 */
package testDB;

import static org.junit.Assert.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidLoginException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;
import persistence.EmployeeBroker;
import business.Employee;
import business.permissions.PermissionBroker;
import business.permissions.PermissionLevel;

/**
 * @author Daniel Wehr, Daniel Kettle
 * @version 0.1.0
 *
 */
public class TestEmployeeBroker
	{
	private EmployeeBroker empBroker;
	private Employee user;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
		{
		empBroker = EmployeeBroker.getBroker();
		empBroker.initConnectionThread();
		user = new Employee(12314, "Chaney", "Henson","user1", "password",  "2a" );
		
		}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
		{
		empBroker.stopConnectionThread();
		empBroker = null;
		user = null;
		}
	
	/**
	 * Test method for {@link persistence.EmployeeBroker#create(business.Employee)},
	 * {@link persistence.EmployeeBroker#disable(business.Employee)} and
	 * {@link persistence.EmployeeBroker#delete(business.Employee)}.
	 */
	@Test
	public void testCreateDeleteEmployee()
		{
		System.out.println("******************** CREATE/DELETE TEST ********************");
		
		Employee newEmp = null;
		
		try
			{
			newEmp = new Employee(80000,"Bilbo","Baggins","bilb01","password",1,'a');
			}
		catch (DBException e1)
			{
			e1.printStackTrace();
			fail("Employee constructor failed.");
			}
		
      newEmp.setBirthDate(Date.valueOf("2018-03-02"));
            
		try
			{
			//Add employee
			assertTrue(empBroker.create(newEmp, user));
			
			//Search for employee.
			Employee empSearch = new Employee();
			empSearch.setEmpID(80000);
			Employee[] results = empBroker.get(empSearch, user);
			if (results == null)
				fail("Employee search failed, employee not returned.");
			
			//Delete the test user.
			assertTrue(empBroker.delete(results[0], user));
			}
		catch (DBException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (DBDownException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (InvalidPermissionException  e)
			{
			e.printStackTrace();
			fail();
			}
		catch (PermissionViolationException e) 
			{
			e.printStackTrace();
			fail();
			}
		}
	

	/**
	 * Test method for {@link persistence.EmployeeBroker#create(business.Employee)},
	 * {@link persistence.EmployeeBroker#disable(business.Employee)} and
	 * {@link persistence.EmployeeBroker#delete(business.Employee)}.
	 */
	@Test
	public void testCreateDeleteEmployee2()
		{
		System.out.println("******************** CREATE/DELETE TEST ********************");
		
		Employee newEmp = null;
		try {
			newEmp = new Employee(80002,"Bilbo","Baggins","bilb03","password",
					"1a");
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		newEmp.setActive(false);
		newEmp.setEmail("email@YAHOO.CA");
		newEmp.setPrefLocation("Mohave Grill");
	
		boolean successful;
		try
			{
			//Add employee
			successful = empBroker.create(newEmp, user);
			assertTrue(successful);
			System.out.println("Employee added: "+successful);
			
			//Get employee object from DB.
			Employee search = new Employee();
			search.setEmpID(80002);
			Employee old = empBroker.get(search, user)[0];
			
			//Set employee inactive.
			Employee update = old.clone();
			update.setActive(false);
			assertTrue(empBroker.update(old, update, user));
			
			//Search for disabled employee.
			Employee empSearchDisabled = new Employee();
			empSearchDisabled.setActive(false);
			Employee[] results = empBroker.get(empSearchDisabled, user);
			if (results == null)
				fail("Employee search failed, employee not returned.");
			System.out.println("Employee retrieved: "+results[0]);

			//Delete the test employee.
			assertTrue(empBroker.delete(update, user));
			}
		catch (DBException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (DBDownException e)
			{
			e.printStackTrace();
			}
		catch (InvalidPermissionException e)
			{
			e.printStackTrace();
			}
		catch (PermissionViolationException e) 
			{
				e.printStackTrace();
			}
		
	}
	
	/**
	 * Test method for {@link persistence.EmployeeBroker#get(business.Employee)}.
	 */
	@Test
	public void testGetEmployee()
		{
		System.out.println("******************** GET TEST ********************");
		//Create employees to search by an employee ID, and all active employees.
		Employee searchEmp1 = new Employee();
		searchEmp1.setEmpID(39203);
		
		Employee searchEmp2 = new Employee();
		searchEmp2.setActive(true);
		
		Employee searchEmp3 = new Employee();
		searchEmp3.setSupervisorID(28472);
		
		//Run searches
		Employee[] byID = null, byActive = null, bySupervisor = null;
		try
			{
			byID = empBroker.get(searchEmp1, user);
			byActive = empBroker.get(searchEmp2, user);
			bySupervisor = empBroker.get(searchEmp3, user);
			
			//Testing getting a particular field 
			System.out.println("LOCATION" + bySupervisor[0].getPrefLocation());
			}
		catch (DBException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (DBDownException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (InvalidPermissionException e)
			{
			e.printStackTrace();
			fail();
			} catch (PermissionViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull("No ID results.",byID);
		assertNotNull("No active results.",byActive);
		assertNotNull("No supervisor results.",bySupervisor);
		
		// PRINT RESULTS! :D
		// This is just using the basic toString that dumps all data.
		System.out.println("---------- Search Results by ID 5 ----------");
		for (Employee emp : byID)
			{
			System.out.println(emp);
			}
		
		System.out.println("\n---------- Search Results by Active State True ----------");
		for (Employee emp : byActive)
			{
			System.out.println(emp);
			}
		
		System.out.println("\n---------- Search Results by Supervisor 3 ----------");
		for (Employee emp : bySupervisor)
			{
			System.out.println(emp);
			}
		
		}
	
	/**
	 * Test method for {@link persistence.EmployeeBroker#update(business.Employee)}.
	 */
	@Test
	public void testUpdateEmployee()
		{
		System.out.println("******************** UPDATE TEST ********************");
		
		Employee newEmp = null;
		try {
			newEmp = new Employee(80000,"Bilbo","Baggins","bilb01","password",1,'a');
		} catch (DBException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if(newEmp == null) fail("Employee not initialized for this test");
		
		try
			{
			//Add employee
			assertTrue(empBroker.create(newEmp, user));
			System.out.println("Employee added: "+newEmp);
			System.out.println(newEmp);
			
			//Save new employee to old var.
			Employee oldEmp = newEmp.clone();
			
			//Modify new employee and send in as update.
			newEmp.setLastLogin(new Timestamp(System.currentTimeMillis()));
			newEmp.setBirthDate(new Date(System.currentTimeMillis() - (20l * 1000l * 60l * 60l * 24l * 365l)));
			newEmp.setEmail("fakeemail@fake.com");
			
			assertTrue(empBroker.update(oldEmp,newEmp,user));
			System.out.println("Employee updated: "+newEmp);
			System.out.println(newEmp);
			
			//Search for employee.
			Employee[] results = empBroker.get(newEmp, user);
			System.out.println("Employee retrieved: "+results[0]);
			
			//Check accuracy of SQL Date.
			java.util.Date tempDate = new java.util.Date(results[0].getLastLogin().getTime());
			System.out.println("Login as date: "+tempDate.toString());
			
			//Delete the test user.
			assertTrue(empBroker.delete(results[0], user));
			System.out.println("Employee deleted: "+newEmp);
			}
		catch (DBException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (DBDownException e)
			{
			e.printStackTrace();
			}
		catch (InvalidPermissionException e)
			{
			e.printStackTrace();
			} catch (PermissionViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		}
	
	/**
	 * Test method for {@link persistence.EmployeeBroker#update(business.Employee)}.
	 */
	@Test
	public void testUpdateEmployee2()
		{
		System.out.println("******************** UPDATE TEST ********************");
		
		try {
			Employee searchEmp = new Employee();
			searchEmp.setEmpID(28472);
			
			Employee oldEmp = empBroker.get(searchEmp, user)[0];
			
			Employee newEmp = oldEmp.clone();
			newEmp.setGivenName("Hosam");
			newEmp.setUsername("Hosam01");

			assertTrue(empBroker.update(oldEmp, newEmp, user));
			System.out.println("Employee updated: "+newEmp);
			
			assertTrue(empBroker.update(newEmp, oldEmp, user));
			System.out.println("Employee change reversed: "+oldEmp);
			}
		catch (DBException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (DBDownException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (InvalidPermissionException e)
			{
			e.printStackTrace();
			fail();
			} 
		catch (PermissionViolationException e) 
			{
			e.printStackTrace();
			fail();
			}
		}
	
	/**
	 * Test method for {@link persistence.EmployeeBroker#update(business.Employee)}.
	 */
	@Test
	public void testPermEmployee()
		{
		System.out.println("******************** Permission Level ********************");
		
		PermissionBroker permBroker = PermissionBroker.getBroker();
		int level = user.getLevel();
		System.out.println(level);
		PermissionLevel[] permArray;
		try {
			permArray = permBroker.getAllBelow(level);
			
			for(int index = 0; index <permArray.length; index++)
			{
				System.out.println(permArray[index].getLevel());
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		}
	
	/**
	 * Test method for {@link persistence.EmployeeBroker#tryLogin(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testTryLogin()
		{
		System.out.println("******************** LOGIN TEST ********************");
		
		/*
		 * Create sample username/password strings. These would normally be
		 * entered by the user in the web interface. "user1" exists in the test
		 * data and should return an employee object. "fakeUser" does not exist
		 * and should throw an exception.
		 */
		String user1 = "user3", user2 = "fakeUser";
		String password1 = "password", password2 = "pass";
		
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
			e.printStackTrace();
			fail(e.getMessage());
			}
		catch (DBException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (DBDownException e)
			{
			e.printStackTrace();
			} catch (PermissionViolationException e) {
			// TODO Auto-generated catch block
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
			fail();
			loggedIn.getActive();
			loggedIn = null;
			}
		catch (InvalidLoginException e)
			{
			/*
			 * For now we'll just catch the exception and print its message.
			 * Instead, you might do something in the UI when this exception is
			 * caught to tell them that their login failed or was incorrect.
			 */
			System.out.println(e.getMessage());
			assertTrue(true);
			}
		catch (DBException e)
			{
			e.printStackTrace();
			fail();
			}
		catch (DBDownException e)
			{
			e.printStackTrace();
			} catch (PermissionViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	}
