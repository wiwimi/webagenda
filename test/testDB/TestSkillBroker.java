package testDB;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.Employee;
import business.Skill;
import business.schedule.Location;
import exception.DBDownException;
import exception.DBException;
import persistence.SkillBroker;

public class TestSkillBroker {
	private SkillBroker broker;
	private Employee user;
	@Before
	public void setUp() throws Exception {
		broker = SkillBroker.getBroker();
		broker.initConnectionThread();
		user = new Employee(12314, "Chaney", "Henson","user1", "password",  "2a" );
	}

	@After
	public void tearDown() throws Exception {
		broker.stopConnectionThread();
		broker = null;
		user=null;
	}

	@Test
	public void testCreateDeleteSkill() {
		
		 
		try {
			
			Skill skill = new Skill("Dancing", null);
			boolean success = broker.create(skill, user);
			
			System.out.println(success); // Is not printed out
			if(success)
			{
				System.out.println("Added the skill !!!");
				System.out.println(skill.toString());
			}
			else if(!success)
			{
				System.out.println("Failed to add the skill !!!");
			}
			
		} catch (DBException e) {
			e.printStackTrace();
			fail();
		} catch (DBDownException e) {
			e.printStackTrace();
			fail();
		}
		
		try {
			Skill skill = new Skill("Dancing");
			broker.delete(skill, user);
		
		} catch (DBException e) {
			e.printStackTrace();
			fail();
		} catch (DBDownException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	@Test
	public void testGetSkill() {
		{
			//Use an empty string for the skill name so that all names are matched.
			Skill get = new Skill("");
			
			//Get all skills and print them to console.
			try
				{
				Skill[] results = broker.get(get, user);
				for (Skill printLoc : results)
					{
					System.out.println(printLoc + " " + printLoc.getDesc());
					}
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
			
			assertTrue(true);
			}
	}

	@Test
	public void testUpdateSkill() 
	{
		Skill oldSkill = new Skill("Cooking", null);
		Skill newSkill = new Skill("Cooking", "Cook");
		   
		try {
			boolean success = broker.update(oldSkill,newSkill, user);
			if(success)
			{
				System.out.println(newSkill.toString());
			}
			
		} catch (DBException e) {
			e.printStackTrace();
			fail();
		} catch (DBDownException e) {
			e.printStackTrace();
			fail();
		}
		
		//Reverse the changes of the update.
		try {
			boolean success = broker.update(newSkill,oldSkill, user);
			if(success)
			{
				System.out.println(oldSkill.toString());
			}
		
		} catch (DBException e) {
			e.printStackTrace();
			fail();
		} catch (DBDownException e) {
			e.printStackTrace();
			fail();
		}
		
		
		
		assertTrue(true);
	}
}
