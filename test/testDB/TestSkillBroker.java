package testDB;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.Skill;
import exception.DBDownException;
import exception.DBException;
import persistence.SkillBroker;

public class TestSkillBroker {
	private SkillBroker broker;
	@Before
	public void setUp() throws Exception {
		broker = SkillBroker.getBroker();
		broker.initConnectionThread();
	}

	@After
	public void tearDown() throws Exception {
		broker.stopConnectionThread();
		broker = null;
	}

	@Test
	public void testCreateDeleteSkill() {
		
		 
		try {
			
			Skill skill = new Skill("Dancing", null);
			boolean success = broker.create(skill);
			
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
			broker.delete(skill);
		
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
				Skill[] results = broker.get(get);
				for (Skill printLoc : results)
					{
					System.out.println(printLoc);
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
	public void testUpdateSkill() {
		fail("Not yet implemented");
	}

}
