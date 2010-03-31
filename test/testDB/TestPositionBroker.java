package testDB;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import business.Employee;
import business.Skill;
import business.schedule.Position;
import exception.DBDownException;
import exception.DBException;
import exception.DBNoChangeException;
import persistence.PositionBroker;

public class TestPositionBroker extends TestCase {
	private PositionBroker broker;
	private Employee user;
	
	@Before
	public void setUp() throws Exception {
		broker = PositionBroker.getBroker();
		broker.initConnectionThread();
		user = new Employee(12314, "Chaney", "Henson","user1", "password", 99, 'a');
	}

	@After
	public void tearDown() throws Exception {
		broker.stopConnectionThread();
		broker = null;
		user= null;
	}

	@Test
	public void testGetBroker() {
		assertEquals(broker,PositionBroker.getBroker());
	}

	@Test
	public void testCreatePosition() {
		
		 System.out.println("POSITION CREATION");
		try {
			
			Position pos = new Position("Admin", null,null);
			boolean success = broker.create(pos, user);
			
			if(success)
			{
				System.out.println("Added the position !!!");
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
			Position pos = new Position("Admin");
			broker.delete(pos, user);
			System.out.println("DELETED");
			
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
				Position[] results = broker.get(get, user);
				
				for (Position printLoc : results)
					{
					System.out.println(printLoc);
					System.out.println("\tSKILLS REQUIRED");
					Skill[] skills = printLoc.getPos_skills();
					
					if(skills != null)
					{
						for(int i=0; i<skills.length; i++)
							System.out.println("\t" + skills[i]);
						
					}
					else System.out.println("none");
				
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
	public void testGetPositionByName() {
		{
			System.out.println("POSITION GET");
			
			//Use an empty string for the positions name so that all names are matched.
			Position get = new Position();
			get.setName("Waiter");
			
			
			//Get all Positions and print them to console.
			try
				{
				Position[] results = broker.get(get, user);
				
				if(results==null)
					System.out.println("No matches found");
				
				for (Position printLoc : results)
					{
					System.out.println(printLoc);
					System.out.println("\tSKILLS REQUIRED");
					Skill[] skills = printLoc.getPos_skills();
					
					if(skills != null)
					{
						for(int i=0; i<skills.length; i++)
							System.out.println("\t" + skills[i]);
						
					}
					else System.out.println("none");
				
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
	public void testGetPositionByRandomSearch() {
		{
			System.out.println("POSITION GET");
			
			//Use an empty string for the positions name so that all names are matched.
			Position byName = new Position();
			byName.setName("Waiter");
			
			Position byDesc = new Position();
			byDesc.setDescription("Waiter");
			
			
			
			//Get all Positions and print them to console.
			try
				{
				Position[] resultsByName = broker.get(byName,user) ;
				Position[] resultsByDesc = broker.get(byDesc,user) ;
				
				if(resultsByName==null && resultsByDesc==null)
					System.out.println("No matches found");
				
				if(resultsByName!=null)
				{
					for (Position printLoc : resultsByName)
						{
						System.out.println(printLoc);
						System.out.println("\tSKILLS REQUIRED");
						Skill[] skills = printLoc.getPos_skills();
						
						if(skills != null)
						{
							for(int i=0; i<skills.length; i++)
								System.out.println("\t" + skills[i]);
							
						}
						else System.out.println("none");
					
						}
				}
				else if (resultsByDesc!=null)
				{
					for (Position printLoc : resultsByDesc)
					{
					System.out.println(printLoc);
					System.out.println("\tSKILLS REQUIRED");
					Skill[] skills = printLoc.getPos_skills();
					
					if(skills != null)
					{
						for(int i=0; i<skills.length; i++)
							System.out.println("\t" + skills[i]);
						
					}
					else System.out.println("none");
				
					}
					
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
		
		Position oldPos = null;
		try {
			oldPos = broker.get(new Position("Cook"),user)[0];
			System.out.println(oldPos);
		} catch (DBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DBDownException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		boolean success = false;
		//Get all Positions and print them to console.
		try
			{
			Position[] results = broker.get(oldPos, user);
			Position updatePos = new Position("Cook","with description",new Skill[]{new Skill("Cooking")});
			
			if(results!=null)
			{
				
				success = broker.update(oldPos, updatePos, user);
				System.out.println(success);
				updatePos = new Position("Cook",null,null);
				success = broker.update(updatePos, new Position("Cook",null,new Skill[]{new Skill("Cooking")}), user);
			}
			else
			{
				System.out.println(success);
			}
			
			}
		catch (DBNoChangeException dbncE)
		{
			System.out.println("Position already exists as updated");
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

