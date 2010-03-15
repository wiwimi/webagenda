/**
 * 
 */
package testDB;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import exception.DBDownException;
import exception.DBException;
import business.schedule.Location;
import persistence.LocationBroker;

/**
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestLocationBroker
	{
	private LocationBroker broker;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
		{
		broker = LocationBroker.getBroker();
		broker.initConnectionThread();
		}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
		{
		broker.stopConnectionThread();
		broker = null;
		}
	
	/**
	 * Test method for {@link persistence.LocationBroker#create(business.schedule.Location)}.
	 */
	@Test
	public void testCreateDeleteLocation()
		{
		Location loc = new Location("Casino", "Test Location");
		    
		try {
			boolean success = broker.create(loc);
			if(success)
			{
				System.out.println(loc.toString());
			}
			
		} catch (DBException e) {
			e.printStackTrace();
			fail();
		} catch (DBDownException e) {
			e.printStackTrace();
			fail();
		}
		
		//Attempt to delete the test location.
		try {
		broker.delete(loc);
		
		} catch (DBException e) {
			e.printStackTrace();
			fail();
		} catch (DBDownException e) {
			e.printStackTrace();
			fail();
		}
		
		assertTrue(true);
		}
	
	/**
	 * Test method for {@link persistence.LocationBroker#get(business.schedule.Location)}.
	 */
	@Test
	public void testGetLocation()
		{
		//Use an empty string for the location name so that all names are matched.
		Location get = new Location("");
		
		//Get all locations and print them to console.
		try
			{
			Location[] results = broker.get(get);
			for (Location printLoc : results)
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
	
	@Test
	public void testGetLocation2()
		{
		//Search for full name.
		Location get = new Location("Mohave Grill");
		
		//Get all locations and print them to console.
		try
			{
			Location[] results = broker.get(get);
			for (Location printLoc : results)
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
	@Test
	public void testGetLocation3()
		{
		//Search for beginning part of location name.
		Location get = new Location("Mohave");
		
		//Get all locations and print them to console.
		try
			{
			Location[] results = broker.get(get);
			for (Location printLoc : results)
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
	
	@Test
	public void testGetLocation4()
		{
		//Search for end part of location name.
		Location get = new Location("Grill");
		
		//Get all locations and print them to console.
		try
			{
			Location[] results = broker.get(get);
		
			if(results==null || results.length==0)
				System.out.println("There are no results");
		
			
			for (Location printLoc : results)
				{
				System.out.println("Description: "+printLoc.getDesc());
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
	
	/**
	 * Test method for {@link persistence.LocationBroker#update(business.schedule.Location)}.
	 */
	@Test
	public void testUpdateLocation()
		{
		Location oldLoc = new Location("Mohave Grill", null);
		Location newLoc = new Location("Mohave Grill", "Restaurant");
		   
		try {
			boolean success = broker.update(oldLoc,newLoc);
			if(success)
			{
				System.out.println(newLoc.toString());
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
			boolean success = broker.update(newLoc,oldLoc);
			if(success)
			{
				System.out.println(oldLoc.toString());
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

