/**
 * 
 */
package testDB;

import static org.junit.Assert.*;

import java.sql.Time;
import javax.persistence.Persistence;
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
	 * Test method for {@link persistence.ScheduleTemplateBroker#create(ScheduleTemplate, Employee)}.
	 */
	@Test
	public void testCreateDeleteScheduleTemplate()
		{
		System.out.println("---------- START TEST CREATE ----------");
		//Create a test schedule template to be created and deleted.
		ShiftPosition shiftPos1 = new ShiftPosition();
		shiftPos1.setPosName("Cook");
		shiftPos1.setPosCount(2);
		ShiftPosition shiftPos2 = new ShiftPosition();
		shiftPos2.setPosName("Cook");
		shiftPos2.setPosCount(2);
		
		ShiftTemplate shiftTemp1 = new ShiftTemplate();
		shiftTemp1.setDay(2);
		shiftTemp1.setStartTime(Time.valueOf("08:00:00"));
		shiftTemp1.setEndTime(Time.valueOf("17:00:00"));
		shiftTemp1.getShiftPositions().add(shiftPos1);
		
		ShiftTemplate shiftTemp2 = new ShiftTemplate();
		shiftTemp2.setDay(3);
		shiftTemp2.setStartTime(Time.valueOf("08:00:00"));
		shiftTemp2.setEndTime(Time.valueOf("17:00:00"));
		shiftTemp2.getShiftPositions().add(shiftPos2);
		
		ScheduleTemplate schedTemp = new ScheduleTemplate();
		schedTemp.setCreatorID(12314);
		schedTemp.setName("Created by TestScheduleTemplateBroker");
		schedTemp.getShiftTemplates().add(shiftTemp2);
		schedTemp.getShiftTemplates().add(shiftTemp1);
		
		try
			{
			//Attempt to add schedule template to database.
			assertTrue(stb.create(schedTemp, user));
			
			//Repeat testGet to show schedule template was added.
			testGetScheduleTemplate();
			
			//Delete the added schedule template..
			assertTrue(stb.delete(schedTemp, user));
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
	 * Test method for {@link persistence.ScheduleTemplateBroker#update(ScheduleTemplate, ScheduleTemplate, Employee)}.
	 * This will fail if the create was not successful.
	 */
	public void testUpdateScheduleTemplate()
		{
		System.out.println("---------- START TEST UPDATE ----------");
		System.out.println("---------- END TEST UPDATE ----------");
		}
	
	/**
	 * Test method for {@link persistence.ScheduleTemplateBroker#delete(ScheduleTemplate, Employee)}.
	 * This will fail if the create was not successful.
	 */
	public void testDeleteScheduleTemplate()
		{
		System.out.println("---------- START TEST UPDATE ----------");
		System.out.println("---------- END TEST UPDATE ----------");
		}
	
	/**
	 * Test method for {@link persistence.ScheduleTemplateBroker#get(ScheduleTemplate, Employee)}.
	 */
	@Test
	public void testGetScheduleTemplate()
		{
		System.out.println("---------- START TEST GET ----------");
		//Grab the test schedule templates and print contents.
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
				System.out.println("\tShift Template - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime() + " - "+shift);
				
				ShiftPosition[] positions = shift.getShiftPositions().toArray();
				
				for (ShiftPosition pos : positions)
					{
					System.out.println("\t\tRequires "+pos.getPosCount()+" "+pos.getPosName()+" - "+pos);
					}
				}
			}
		System.out.println("---------- END TEST GET ----------");
		}

	
	}
