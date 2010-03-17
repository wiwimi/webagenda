/**
 * 
 */
package testDB;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exception.DBDownException;
import exception.DBException;
import business.Employee;
import business.schedule.ScheduleTemplate;
import business.schedule.ShiftPosition;
import business.schedule.ShiftTemplate;
import persistence.ScheduleTemplateBroker;
import utilities.DoubleLinkedList;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestScheduleTemplateBroker
	{
	ScheduleTemplateBroker stb = null;
	private Date d;
	private Employee user;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
		{
		stb = ScheduleTemplateBroker.getBroker();
		user = new Employee(12314, "Chaney", "Henson",  d, "user1", "password",  "2a" );
		}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
		{
		stb = null;
		user = null;
		}
	
	/**
	 * Test method for {@link persistence.ScheduleTemplateBroker#get(business.schedule.ScheduleTemplate)}.
	 */
	@Test
	public void testGetScheduleTemplate()
		{
		//Grab the test schedule template and print its contents.
		ScheduleTemplate search = new ScheduleTemplate();
		search.setCreatorID(12314);
		
		ScheduleTemplate[] results = null;
		try
			{
			results = stb.get(search, user);
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
		
		for (ScheduleTemplate st : results)
			{
			System.out.println("Schedule Template: "+st.getName());
			
			ShiftTemplate[] shiftList = st.getShiftTemplates().toArray();
			
			for (ShiftTemplate shift : shiftList)
				{
				System.out.println("\tShift Template - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime());
				
				ShiftPosition[] positions = shift.getShiftPositions().toArray();
				
				for (ShiftPosition pos : positions)
					{
					System.out.println("\t\tRequires "+pos.getPosCount()+" "+pos.getPosName());
					}
				}
			}
		
		assertTrue(true);
		}

	
	}
