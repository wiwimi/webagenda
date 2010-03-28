/**
 * 
 */
package testDB;

import static org.junit.Assert.*;

import java.sql.Time;
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

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestScheduleTemplateBroker
	{
	private ScheduleTemplateBroker stb = null;
	private Employee user;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
		{
		stb = ScheduleTemplateBroker.getBroker();
		user = new Employee(12314, "Chaney", "Henson","user1", "password",  "2a" );
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
	 * Test method for {@link persistence.ScheduleTemplateBroker#create(ScheduleTemplate, Employee)},
	 * {@link persistence.ScheduleTemplateBroker#update(ScheduleTemplate, ScheduleTemplate, Employee)},
	 * and {@link persistence.ScheduleTemplateBroker#delete(ScheduleTemplate, Employee)}.
	 */
	@Test
	public void testCreateUpdateDeleteScheduleTemplate()
		{
		int stID = -1;
		System.out.println("---------- START TEST CREATE ----------");
		
		ShiftPosition shiftPos = new ShiftPosition();
		shiftPos.setPosName("Cook");
		shiftPos.setPosCount(2);
		
		ShiftTemplate shiftTemp1 = new ShiftTemplate();
		shiftTemp1.setDay(2);
		shiftTemp1.setStartTime(Time.valueOf("08:00:00"));
		shiftTemp1.setEndTime(Time.valueOf("17:00:00"));
		shiftTemp1.getShiftPositions().add(shiftPos);
		
		ShiftTemplate shiftTemp2 = shiftTemp1.clone();
		shiftTemp2.setDay(3);
		
		ScheduleTemplate st = new ScheduleTemplate();
		st.setCreatorID(12314);
		st.setName("Created by TestScheduleTemplateBroker");
		st.getShiftTemplates().add(shiftTemp2);
		st.getShiftTemplates().add(shiftTemp1);

		try
			{
			//Attempt to add schedule template to database.
			assertTrue(stb.create(st, user));
			
			//Create should have filled in ID values on schedule template.
			if (st.getSchedTempID() != -1)
				{
				System.out.println("Test schedule template created with ID: "+st.getSchedTempID());
				stID = st.getSchedTempID();
				}
			else
				{
				fail("Schedule template ID not given to original object on create.");
				}
			
			//Repeat testGet to show schedule template was added.
			testGetScheduleTemplate();
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
		
		System.out.println("---------- END TEST CREATE ----------");
		System.out.println("---------- START TEST UPDATE ----------");
		
		ScheduleTemplate get = new ScheduleTemplate();
		get.setSchedTempID(stID);
		
		try
			{
			//Get the original schedule template from the DB to be updated.
			ScheduleTemplate old = stb.get(get, user)[0];
			
			//Clone the schedule template to use for update, and use the original as old.
			ScheduleTemplate update = old.clone();
			
			//Set shifts to start at 7am instead of 8am.
			for (ShiftTemplate shiftTemp : update.getShiftTemplates().toArray())
				{
				shiftTemp.setStartTime(Time.valueOf("07:00:00"));
				}
			
			//Attempt to update schedule template in database.
			assertTrue(stb.update(old, update, user));
			
			//Repeat testGet to show schedule template was updated.
			testGetScheduleTemplate();
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
		
		System.out.println("---------- END TEST UPDATE ----------");
		System.out.println("---------- START TEST DELETE ----------");
		
		try
			{
			//Get the test schedule from the DB to delete.
			ScheduleTemplate delete = stb.get(get, user)[0];
			
			//Attempt to delete schedule template from database.
			assertTrue(stb.delete(delete, user));
			
			//Repeat testGet to show schedule template was deleted.
			testGetScheduleTemplate();
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
	 * Test method for {@link persistence.ScheduleTemplateBroker#get(ScheduleTemplate, Employee)}.
	 */
	@Test
	public void testGetScheduleTemplate()
		{
		System.out.println("----- START TEST GET -----");
		//Grab the last test schedule template and print contents.
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
		
		ScheduleTemplate st = results[results.length-1];
		
		System.out.println("Schedule Template: "+st.getName()+" - ID: "+st.getSchedTempID());
		
		ShiftTemplate[] shiftList = st.getShiftTemplates().toArray();
		
		for (ShiftTemplate shift : shiftList)
			{
			System.out.println("\tShift Template - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime() + " - "+shift);
			
			ShiftPosition[] positions = shift.getShiftPositions().toArray();
			
			for (ShiftPosition pos : positions)
				{
				System.out.println("\t\tRequires "+pos.getPosCount()+" "+pos.getPosName()+" - "+pos);
				}
			}
		
		System.out.println("----- END TEST GET -----");
		}

	
	}
