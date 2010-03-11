package testDB;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import persistence.PositionBroker;

public class TestSkillPositions {

	private PositionBroker pbrok = null;
	
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
		fail("Not yet implemented");
	}

	@Test
	public void testCreatePosition() {
		fail("Not yet implemented");
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
