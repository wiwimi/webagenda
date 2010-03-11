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
		try {
			Position[] pos = pbrok.getBroker().get(new Position("Waiter",null));
			for(Position p : pos)
				System.out.println(p);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
