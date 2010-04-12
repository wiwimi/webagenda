/**
 * 
 */
package testDB;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.ScheduleBroker;
import business.Employee;
import business.schedule.Schedule;
import business.schedule.Shift;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestScheduleBroker extends TestCase
	{
	private ScheduleBroker sb = null;
	private Employee user;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
		{
		sb = ScheduleBroker.getBroker();
		user = new Employee(12314, "Chaney", "Henson","user1", "password", 99, 'a');
		}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
		{
		sb = null;
		user = null;
		}
	
	/**
	 * Test method for {@link persistence.ScheduleBroker#create(business.schedule.Schedule, business.Employee)}
	 * and {@link persistence.ScheduleBroker#delete(business.schedule.Schedule, business.Employee)}.
	 */
	@Test
	public void testCreateUpdateDeleteSchedule()
		{
		int sID = -1;
		System.out.println("---------- START TEST CREATE ----------");
		//Create a test schedule to be created and deleted.
		Employee emp1 = new Employee();
		emp1.setEmpID(38202);
		Employee emp2 = new Employee();
		emp2.setEmpID(38382);
		
		Shift shift1 = new Shift();
		shift1.setDay(2);
		shift1.setStartTime(Time.valueOf("08:00:00"));
		shift1.setEndTime(Time.valueOf("17:00:00"));
		shift1.getEmployees().add(emp2);
		shift1.getEmployees().add(emp1);
		
		Shift shift2 = shift1.clone();
		shift2.setDay(3);
		
		Schedule sched = new Schedule();
		sched.setCreatorID(12314);
		sched.setStartDate(Date.valueOf("2010-04-25"));
		sched.getShifts().add(shift2);
		sched.getShifts().add(shift1);
		
		try
			{
			//Attempt to add schedule to database.
			assertTrue(sb.create(sched, user));
			
			//Create should have filled in ID values on schedule template.
			if (sched.getSchedID() != -1)
				{
				System.out.println("Test schedule created with ID: "+sched.getSchedID());
				sID = sched.getSchedID();
				}
			else
				{
				fail("Schedule ID not given to original object on create.");
				}
			
			//Repeat testGet to show schedule template was added.
			testGetSchedule();
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
		System.out.println("---------- END TEST CREATE ----------");
		System.out.println("---------- START TEST UPDATE ----------");
		
		Schedule get = new Schedule();
		get.setSchedID(sID);
		
		try
			{
			//Get the original schedule template from the DB to be updated.
			Schedule old = sb.get(get, user)[0];
			
			//Clone the schedule template to use for update, and use the original as old.
			Schedule update = old.clone();
			
			//Set shifts to start at 7am instead of 8am.
			for (Shift shift : update.getShifts().toArrayList())
				{
				shift.setStartTime(Time.valueOf("07:00:00"));
				}
			
			//Attempt to update schedule template in database.
			assertTrue(sb.update(old, update, user));
			
			//Repeat testGet to show schedule template was updated.
			testGetSchedule();
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
		
		System.out.println("---------- END TEST UPDATE ----------");
		System.out.println("---------- START TEST DELETE ----------");
		
		try
			{
			//Get the test schedule from the DB to delete.
			Schedule delete = sb.get(get, user)[0];
			
			//Attempt to delete schedule template from database.
			assertTrue(sb.delete(delete, user));
			
			//Repeat testGet to show schedule template was deleted.
			testGetSchedule();
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
		
		System.out.println("---------- END TEST DELETE ----------");
		}
	
	/**
	 * Test method for {@link persistence.ScheduleBroker#get(business.schedule.Schedule, business.Employee)}.
	 */
	@Test
	public void testGetSchedule()
		{
		System.out.println("----- START TEST GET -----");
		//Grab the test schedules and print contents.
		Schedule search = new Schedule();
		search.setCreatorID(12314);
		
		Schedule[] results = null;
		try
			{
			results = sb.get(search, user);
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
		
		Schedule st = results[results.length-1];
		
		System.out.println("Schedule: "+st);
		
		ArrayList<Shift> shiftList = st.getShifts().toArrayList();
		
		for (Shift shift : shiftList)
			{
			System.out.println("\tShift - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime());
			
			ArrayList<Employee> emps = shift.getEmployees().toArrayList();
			
			for (Employee emp : emps)
				{
				System.out.println("\t\tApplies to: "+emp);
				}
			}
		
		System.out.println("----- END TEST GET -----");
		}
	
	/**
	 * Test method for {@link persistence.ScheduleBroker#getEmpSchedules(Employee)}.
	 */
	@Test
	public void testGetEmpSchedules()
		{
		System.out.println("----- START TEST GET EMP SCHEDULES -----");
		//Grab the test schedules and print contents.
		Employee emp = new Employee();
		emp.setEmpID(38202);
		
		
		Schedule[] results = null;
		try
			{
			results = sb.getEmpSchedules(emp);
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
		
		Schedule st = results[results.length-1];
		
		System.out.println("Schedule: "+st);
		
		ArrayList<Shift> shiftList = st.getShifts().toArrayList();
		
		for (Shift shift : shiftList)
			{
			System.out.println("\tShift - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime());
			
			ArrayList<Employee> emps = shift.getEmployees().toArrayList();
			
			for (Employee e : emps)
				{
				System.out.println("\t\tApplies to: "+e);
				}
			}
		
		System.out.println("----- END TEST GET EMP SCHEDULES -----");
		}

	}
