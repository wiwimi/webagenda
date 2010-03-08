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
			
			pbrok.create(new Position("Fryer"));
			
			posarray = pbrok.get(new Position("Fryer"));
			for(Position p : posarray)
				System.out.println(p);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
