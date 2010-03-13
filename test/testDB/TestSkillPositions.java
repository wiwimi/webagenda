package testDB;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import exception.DBDownException;
import exception.DBException;

import business.Skill;
import business.schedule.Position;

import persistence.PositionBroker;
import persistence.SkillBroker;

public class TestSkillPositions {

	private PositionBroker pbrok = null;
	private Skill[] skills = new Skill[]{new Skill("The Force"),
			new Skill("Dark Side"),
			new Skill("Evil"),
			new Skill("Awesome Costume")
	};
	private Position newpos = new Position("Deathstar",skills);
	
	@Before
	public void setUp() throws Exception {
		pbrok = PositionBroker.getBroker();
	}

	@After
	public void tearDown() throws Exception {
		pbrok = null;
	}

	@Test
	public void testGetBroker() {
		
	}

	@Test
	public void testCreatePosition() {
		
//		try {
//			pbrok.delete(new Position("Waiter",null));
//		} catch (DBException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (DBDownException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
		
//		try {
//			Position p = new Position("Waiter",null);
//			pbrok.create(p);
//			Position[] pos;
//			pos = PositionBroker.getBroker().get(p);
//			assertEquals(pos[0],p);
//		} catch (DBException e) {
//			// TODO Auto-generated catch block
//			System.out.println("DB Exception: " + e.getLocalizedMessage());
//		} catch (DBDownException e) {
//			// TODO Auto-generated catch block
//			System.out.println("DBDown Exception");
//		}
		
		
		try {
			
			
			Position testP = new Position("Nobody",null);
			boolean b = pbrok.create(testP);
			System.out.println(b);
			assertEquals(PositionBroker.getBroker().get(testP)[0].equals(testP));
			b = pbrok.delete(testP);
			System.out.println(b);
			
			System.out.println("Starting Creation Test With Skill[]");
			pbrok.create(newpos);
			pbrok.delete(newpos);
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void assertEquals(boolean equals) {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void testDeletePosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPosition() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseResultsResultSet() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePosition() {
		fail("Not yet implemented");
	}

}
