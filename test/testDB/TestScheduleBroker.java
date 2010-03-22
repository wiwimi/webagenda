/**
 * 
 */
package testDB;

import static org.junit.Assert.*;
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
	 * Test method for {@link persistence.ScheduleBroker#create(business.schedule.Schedule, business.Employee)}.
	 */
	@Test
	public void testCreateScheduleEmployee()
		{
		fail("Not yet implemented"); // TODO
		}
	
	/**
	 * Test method for {@link persistence.ScheduleBroker#delete(business.schedule.Schedule, business.Employee)}.
	 */
	@Test
	public void testDeleteScheduleEmployee()
		{
		fail("Not yet implemented"); // TODO
		}
	
	/**
	 * Test method for {@link persistence.ScheduleBroker#get(business.schedule.Schedule, business.Employee)}.
	 */
	@Test
	public void testGetScheduleEmployee()
		{
		//Grab the test schedule template and print its contents.
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
			System.out.println("Schedule Template ID: "+st.getSchedID());
			
			Shift[] shiftList = st.getShifts().toArray();
			
			for (Shift shift : shiftList)
				{
				System.out.println("\tShift Template - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime());
				
				Employee[] emps = shift.getEmployees().toArray();
				
				for (Employee emp : emps)
					{
					System.out.println("\t\tApplies to: "+emp);
					}
				}
			}
		
		assertTrue(true);
		}
	
	/**
	 * Test method for {@link persistence.ScheduleBroker#update(business.schedule.Schedule, business.schedule.Schedule, business.Employee)}.
	 */
	@Test
	public void testUpdateScheduleScheduleEmployee()
		{
		fail("Not yet implemented"); // TODO
		}
	
	}
