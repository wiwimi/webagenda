/**
 * 
 */
package testDB;

import static org.junit.Assert.*;
import java.sql.Date;
import java.sql.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import persistence.ScheduleBroker;
import business.Employee;
import business.schedule.Schedule;
import business.schedule.Shift;
import exception.DBDownException;
import exception.DBException;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestScheduleBroker
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
		user = new Employee(12314, "Chaney", "Henson","user1", "password",  "2a" );
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
	public void testCreateDeleteSchedule()
		{
		System.out.println("---------- START TEST CREATE DELETE ----------");
		//Create a test schedule to be created and deleted.
		Employee emp1 = new Employee();
		emp1.setEmpID(38382);
		Employee emp2 = new Employee();
		emp2.setEmpID(38202);
		
		Shift shift = new Shift();
		shift.setDay(2);
		shift.setStartTime(new Time(8l * 1000 * 60 * 60));
		shift.setEndTime(new Time (17l * 1000 * 60 * 60));
		shift.getEmployees().add(emp1);
		shift.getEmployees().add(emp2);
		
		Schedule sched = new Schedule();
		sched.setCreatorID(12314);
		sched.setStartDate(Date.valueOf("2010-04-21"));
		sched.setEndDate(Date.valueOf("2010-04-27"));
		sched.getShifts().add(shift);
		
		try
			{
			//Attempt to add schedule to database.
			assertTrue(sb.create(sched, user));
			
			//Repeat testGet to show schedule was added.
			testGetSchedule();
			
			//Delete the added schedule.
			assertTrue(sb.delete(sched, user));
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
		System.out.println("---------- END TEST CREATE DELETE ----------");
		}
	
	/**
	 * Test method for {@link persistence.ScheduleBroker#get(business.schedule.Schedule, business.Employee)}.
	 */
	@Test
	public void testGetSchedule()
		{
		System.out.println("---------- START TEST GET ----------");
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
		
		for (Schedule st : results)
			{
			System.out.println("Schedule ID: "+st.getSchedID());
			
			Shift[] shiftList = st.getShifts().toArray();
			
			for (Shift shift : shiftList)
				{
				System.out.println("\tShift - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime());
				
				Employee[] emps = shift.getEmployees().toArray();
				
				for (Employee emp : emps)
					{
					System.out.println("\t\tApplies to: "+emp);
					}
				}
			}
		
		assertTrue(true);
		System.out.println("---------- END TEST GET ----------");
		}
	
	/**
	 * Test method for {@link persistence.ScheduleBroker#update(business.schedule.Schedule, business.schedule.Schedule, business.Employee)}.
	 */
	@Test
	public void testUpdateSchedule()
		{
		fail("Not yet implemented"); // TODO
		}
	
	}
