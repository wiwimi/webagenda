package testDB;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllBrokerTests
	{
	
	public static Test suite()
		{
		TestSuite suite = new TestSuite(
				TestEmployeeBroker.class,
				TestLocationBroker.class,
				TestPositionBroker.class,
				TestScheduleBroker.class,
				TestScheduleTemplateBroker.class,
				TestSkillBroker.class,
				TestSkillPositions.class);
		//$JUnit-BEGIN$
		
		//$JUnit-END$
		return suite;
		}
	
	}
