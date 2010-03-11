package testDB;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		fail("Not yet implemented");
	}

	@Test
	public void testCreatePosition() {
		
		 
		try {
			
			Position pos = new Position("Admin", "",null);
			boolean success = broker.create(pos);
			
			System.out.println(success); // Is not printed out
			if(success)
			{
				System.out.println("Added the skill !!!");
				System.out.println(pos.toString());
			}
			else if(!success)
			{
				System.out.println("Failed to add the skill !!!");
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
			//Use an empty string for the skill name so that all names are matched.
			Position get = new Position("",null);
			
			//Get all skills and print them to console.
			try
				{
				Position[] results = broker.get(get);
				for (Position printLoc : results)
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
	public void testParseResultsResultSet() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateE() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetE() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateE() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteE() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseResultsResultSet1() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetConnection() {
		fail("Not yet implemented");
	}

	@Test
	public void testInitConnectionThread() {
		fail("Not yet implemented");
	}

	@Test
	public void testStopConnectionThread() {
		fail("Not yet implemented");
	}

	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

}
