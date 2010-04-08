/**
 * 
 */
package testDB;

import static org.junit.Assert.*;
import java.sql.Date;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import application.ScheduleGenerator;
import business.Employee;
import business.schedule.Location;
import business.schedule.Schedule;
import business.schedule.ScheduleTemplate;
import business.schedule.Shift;
import persistence.ScheduleBroker;
import persistence.ScheduleTemplateBroker;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestScheduleGenerator
	{
	private ScheduleTemplateBroker stb;
	private ScheduleBroker sb;
	private Employee user;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
		{
		stb = ScheduleTemplateBroker.getBroker();
		sb = ScheduleBroker.getBroker();
		user = new Employee(12314, "Chaney", "Henson","user1", "password", 99, 'a');
		}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
		{
		stb = null;
		sb = null;
		user = null;
		}
	
	/**
	 * Test method for {@link application.ScheduleGenerator#generateSchedule(business.schedule.ScheduleTemplate, java.sql.Date, java.sql.Date, business.schedule.Location, business.schedule.Shift[], business.Employee)}.
	 */
	@Test
	public void testGenerateSchedule()
		{
		try
			{
			//Attempt to create a schedule from the test template.
			ScheduleTemplate get = new ScheduleTemplate(1,-1,null);
			
		
			ScheduleTemplate fromDB = stb.get(get, user)[0];
			
			ArrayList<Shift> partialMatches = new ArrayList<Shift>();
			
			System.out.println("---------- Generating Schedule ----------");
			Schedule genSched = ScheduleGenerator.generateSchedule(fromDB, Date.valueOf("2010-04-18"), Date.valueOf("2010-04-24"), new Location("Mohave Grill"), partialMatches, user);
			assertNotNull(genSched);
			System.out.println("---------- Generation Successful ----------");
			
			System.out.println("---------- Printing Unadded ----------");
			System.out.println("Schedule: "+genSched);
			
			Shift[] shiftList = genSched.getShifts().toArray();
			
			for (Shift shift : shiftList)
				{
				System.out.println("\tShift - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime());
				
				if (shift.getEmployees().size() > 0)
					{
					for (Employee emp : shift.getEmployees().toArray())
						System.out.println("\t\tApplies to: "+emp);
					}
				}
			System.out.println("---------- Print Complete ----------");
			System.out.println("---------- Printing Recommended Employees ----------");
			for (Shift s : partialMatches)
				{
				System.out.println("For shift: "+s);
				for (Employee e : s.getEmployees().toArray())
					System.out.println("\t"+e);
				}
			System.out.println("---------- Print Complete ----------");
			
			System.out.println("---------- Adding to Database ----------");
			assertTrue(sb.create(genSched, user));
			System.out.println("---------- DB Add Successful ----------");
			
			System.out.println("---------- Printing Added Schedule ----------");
			shiftList = genSched.getShifts().toArray();
			
			for (Shift shift : shiftList)
				{
				System.out.println("\tShift - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime());
				
				if (shift.getEmployees().size() > 0)
					{
					for (Employee emp : shift.getEmployees().toArray())
						System.out.println("\t\tApplies to: "+emp);
					}
				}
			System.out.println("---------- Print Complete ----------");
			
			System.out.println("---------- Deleting From Database ----------");
			assertTrue(sb.delete(genSched, user));
			System.out.println("---------- Delete Successful ----------");
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
		}
	
	}
