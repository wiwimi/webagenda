/**
 * testDB - TestTemplates.java
 */
package testDB;

import exception.DBDownException;
import exception.DBException;
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
			Position[] posarray = pbrok.get(new Position("Cook"));
			for(Position p : posarray)
				System.out.println(p);
			System.out.println(pbrok.delete(new Position("Fryer")));
			pbrok.create(new Position("Fryer"));
			
			posarray = pbrok.get(new Position("Fryer"));
			pbrok.update(new Position("Fryer","someone who fries food"));
			posarray = pbrok.get(new Position("Cook"));
			for(Position p : posarray)
				System.out.println(p);
			
			pbrok.get(new Position("Fryer"));
			
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
