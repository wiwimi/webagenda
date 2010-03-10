/**
 * testDB - TestAllBrokers.java
 */
package testDB;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.DBDownException;
import exception.DBException;

import business.*;
import business.permissions.*;
import business.permissions.PermissionBroker;
import business.schedule.Location;
import business.schedule.Rule;
import business.schedule.Schedule;

import persistence.*;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class TestAllBrokers {

	private Broker[] brokers = new Broker[] {
			EmployeeBroker.getBroker(),
			LocationBroker.getBroker(),
			NotificationBroker.getBroker(),
			PermissionBroker.getBroker(),
			RuleBroker.getBroker(),
			ScheduleBroker.getBroker(),
			SkillBroker.getBroker()
	};
	private Date now = new Date(999999L);
	private int current_broker = -1;
	private BusinessObject[] bo = new BusinessObject[]{
		new Employee(999,"Test","Test",now,"testuser","test","1a"),
		new Location("test", "test location description"),
		new Notification(999, 1, 1, false, "test", "test"),
		null,
		new Rule(999,999,"test","test value"),
		new Schedule(),
		new Skill("test","test description")
	};
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		current_broker ++;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	/**
	 * These tests assume that the get() method returns proper object arrays.
	 * Test method for {@link persistence.Broker#create(business.BusinessObject)}.
	 */
	@Test
	public void testCreate() {
		try {
			
			for(BusinessObject o : bo) {
				brokers[current_broker].create(bo[current_broker]);
				assertEquals(brokers[current_broker].get(bo[current_broker]), bo[current_broker]);
				current_broker++;
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Test method for {@link persistence.Broker#get(business.BusinessObject)}.
	 */
	@Test
	public void testGet() {
		try {
			BusinessObject[] objects = brokers[current_broker].get(null);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link persistence.Broker#update(business.BusinessObject)}.
	 */
	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link persistence.Broker#delete(business.BusinessObject)}.
	 */
	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link persistence.Broker#parseResults(java.sql.ResultSet)}.
	 */
	@Test
	public void testParseResults() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link persistence.Broker#getConnection()}.
	 */
	@Test
	public void testGetConnection() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link persistence.Broker#initConnectionThread()}.
	 */
	@Test
	public void testInitConnectionThread() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link persistence.Broker#stopConnectionThread()}.
	 */
	@Test
	public void testStopConnectionThread() {
		fail("Not yet implemented");
	}

}
