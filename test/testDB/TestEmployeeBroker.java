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

/**
 * @author Daniel Wehr, Daniel Kettle
 * @version 0.1.0
 *
 */
public class TestEmployeeBroker
	{
	private EmployeeBroker empBroker;
	private Date d;
	private Employee user;
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
		{
		empBroker = EmployeeBroker.getBroker();
		empBroker.initConnectionThread();
		user = new Employee(12314, "Chaney", "Henson",  d, "user1", "password",  "2a" );
		
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
		try {
			newEmp = new Employee(80000,"Bilbo","Baggins",null,"bilb01","password",1,'a');
			newEmp.setActive(true);
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(newEmp);
		//Add employee
		boolean successful;
		try
			{
			successful = empBroker.create(newEmp, user);
			System.out.println(successful);
			assertTrue(successful);
			System.out.println("Employee added: "+successful);
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
		catch (InvalidPermissionException  e)
			{
			e.printStackTrace();
			}
		catch (PermissionViolationException e) 
			{
				e.printStackTrace();
			}
		
		//Search for employee.
		try
			{
			Employee empSearch = new Employee();
			empSearch.setEmpID(80000);
			
			Employee[] results = empBroker.get(empSearch, user);
			if (results == null)
				fail("Employee search failed, employee not returned.");
			System.out.println("Employee retrieved: "+results[0]);
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
		catch (NullPointerException e)
			{
			e.printStackTrace();
			}
		catch (InvalidPermissionException e)
			{
			e.printStackTrace();
			}
		
		//Delete the test user.
		try
			{
			boolean deleted = empBroker.delete(newEmp, user);
			assertTrue(deleted);
			System.out.println("Employee deleted: "+ deleted);
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
			newEmp = new Employee(80002,"Bilbo","Baggins",null,"bilb01","password",
					"1a");
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		newEmp.setActive(true);
		newEmp.setEmail("email@YAHOO.CA");
		newEmp.setPrefLocation("Mohave Grill");
		newEmp.setPrefPosition("admin");
	
		//Add employee
		boolean successful;
		try
			{
			successful = empBroker.create(newEmp, user);
			assertTrue(successful);
			System.out.println("Employee added: "+successful);
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
		
		//Search for disabled employee.
		try
			{
			Employee empSearchDisabled = new Employee();
			empSearchDisabled.setEmpID(80002);
			Employee[] results = empBroker.get(empSearchDisabled, user);
			if (results == null)
				fail("Employee search failed, employee not returned.");
			System.out.println("Employee retrieved: "+results[0]);
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
		catch (NullPointerException e)
			{
			e.printStackTrace();
			}
		catch (InvalidPermissionException e)
		{
		e.printStackTrace();
		}
		
		//Delete the test user.
		try
			{
			boolean deleted = empBroker.delete(newEmp, user);
			assertTrue(deleted);
			System.out.println("Employee deleted: "+ deleted);
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
	 * Test method for {@link persistence.EmployeeBroker#delete(business.Employee)}.
	 */
	@Test
	public void testFullDeleteEmployee()
		{
		
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
		searchEmp1.setEmpID(38202);
		
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
		
		Employee newEmp = new Employee();
		
		newEmp.setEmpID(80000);
		newEmp.setGivenName("Bilbo");
		newEmp.setFamilyName("Baggins");
		newEmp.setUsername("bilb01");
		newEmp.setPassword("password");
		//newEmp.setPLevel("2a", user);
		newEmp.setActive(true);
		
		//Add employee
		try
			{
			boolean successful = empBroker.create(newEmp, user);
			assertTrue(successful);
			System.out.println("Employee added: "+successful);
			System.out.println(newEmp);
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
		
		//Modify new employee and send in as update.
		newEmp.setLastLogin(new Timestamp(System.currentTimeMillis()));
		newEmp.setBirthDate(new Date(System.currentTimeMillis() - (20l * 1000l * 60l * 60l * 24l * 365l)));
		newEmp.setEmail("fakeemail@fake.com");
		try
			{
			boolean successful = empBroker.update(null,newEmp,user);
			assertTrue(successful);
			System.out.println("Employee updated: "+successful);
			System.out.println(newEmp);
			}
		catch (DBException e1)
			{
			e1.getCause().printStackTrace();
			fail();
			}
		catch (DBDownException e)
			{
			e.printStackTrace();
			} catch (InvalidPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PermissionViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		//Search for employee employee.
		try
			{
			Employee[] results = empBroker.get(newEmp, user);
			System.out.println("Employee retrieved: "+results[0]);
			
			//Check accuracy of SQL Date.
			java.util.Date tempDate = new java.util.Date(results[0].getLastLogin().getTime());
			System.out.println("Login as date: "+tempDate.toString());
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
		
		//Delete the test user.
		try
			{
			boolean deleted = empBroker.delete(newEmp, user);
			assertTrue(deleted);
			System.out.println("Employee deleted: "+ deleted);
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
			fail();
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
			}
		catch (InvalidPermissionException e)
			{
			e.printStackTrace();
			}
		}
	
	}
