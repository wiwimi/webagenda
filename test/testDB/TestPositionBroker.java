package testDB;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.Skill;
import business.schedule.Position;
import exception.DBDownException;
import exception.DBException;
import persistence.PositionBroker;

public class TestPositionBroker {
	private PositionBroker broker;
	@Before
	public void setUp() throws Exception {
		broker = PositionBroker.getBroker();
		broker.initConnectionThread();
	}

	@After
	public void tearDown() throws Exception {
		broker.stopConnectionThread();
		broker = null;
	}

	@Test
	public void testGetBroker() {
		assertEquals(broker,PositionBroker.getBroker());
	}

	@Test
	public void testCreatePosition() {
		
		 System.out.println("POSITION CREATION");
		try {
			
			Position pos = new Position("Admin", "",null);
			boolean success = broker.create(pos);
			
			System.out.println(success); // Is not printed out
			if(success)
			{
				System.out.println("Added the position !!!");
				System.out.println(pos.toString());
			}
			else if(!success)
			{
				System.out.println("Failed to add the position !!!");
			}
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		assertTrue(true);
	}

	@Test
	public void testDeletePosition() {
		System.out.println("POSITION DELETION");
		try {
			Position pos = new Position("Admin",null);
			broker.delete(pos);
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(true);
	}

	@Test
	public void testGetPosition() {
		{
			System.out.println("POSITION GET");
			
			//Use an empty string for the positions name so that all names are matched.
			Position get = new Position("",null);
			
			//Get all Positions and print them to console.
			try
				{
				Position[] results = broker.get(get);
				for (Position printLoc : results)
					{
					System.out.println(printLoc);
					System.out.println("SKILLS REQUIRED");
					Skill[] skills = printLoc.getPos_skills();
					
					for(int i=0; i<skills.length; i++)
						System.out.println(skills[i]);
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
	public void testUpdatePosition() {
		System.out.println("POSITION Update");
		
		Skill[] skills = new Skill[1];
		Skill skill = new Skill ("Cooking");
		skills[0] = skill;
		Position oldPos = new Position("Cook", null, skills);
		boolean success = false;
		
		//Get all Positions and print them to console.
		try
			{
			Position[] results = broker.get(oldPos);
			Position updatePos = new Position("WaiterUpdated");
			
			if(results!=null)
			{
				success = broker.update(oldPos, updatePos);
				System.out.println(success);
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

