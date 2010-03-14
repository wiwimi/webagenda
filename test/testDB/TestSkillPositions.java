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
		try {
			
			
			Boolean b = pbrok.create(newpos);
			
			assertEquals(new Boolean(true),b);
			
			
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
		
		Boolean b;
		try {
			b = pbrok.delete(newpos);
			assertEquals(new Boolean(true),b);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetPosition() {
		try {
			assertEquals(new Position("Cook",new Skill[]{new Skill("Cooking")}).toString(),pbrok.get(new Position("Cook"))[0].toString());
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testParseResultsResultSet() {
		// THIS WILL NOT BE IMPLEMENTED UNTIL FURTHER NOTICE
	}

	@Test
	public void testUpdatePosition() {
		Position p = new Position("Cook","A person who cooks things",new Skill[]{new Skill("Cooking")});
		
		try {
			pbrok.update(p);
			assertEquals(p.getDescription(),pbrok.get(p)[0].getDescription());
			p.setDescription(null);
			pbrok.update(p);
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
