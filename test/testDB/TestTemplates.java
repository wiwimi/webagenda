/**
 * testDB - TestTemplates.java
 */
package testDB;

import exception.DBDownException;
import exception.DBException;
import business.Employee;
import business.schedule.Position;
import persistence.PositionBroker;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class TestTemplates {

	public static void main(String[] args)
	{
		
		PositionBroker pbrok = PositionBroker.getBroker();
		try {
			Employee user = new Employee(12314, "Chaney", "Henson","user1", "password", 99, 'a');
			Position[] posarray = pbrok.get(new Position("Cook",null), user);
			for(Position p : posarray)
				System.out.println(p);
			System.out.println(pbrok.delete(new Position("Fryer",null), user));
			pbrok.create(new Position("Fryer",null), user);
			
			posarray = pbrok.get(new Position("Fryer",null), user);
			pbrok.update(null,new Position("Fryer","someone who fries food",null), user);
			posarray = pbrok.get(new Position("Cook",null), user);
			for(Position p : posarray)
				System.out.println(p);
			
			pbrok.get(new Position("Fryer",null), user);
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
