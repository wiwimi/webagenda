/**
 * testDB - TestNotification.java
 */
package testDB;

import java.sql.Timestamp;

import exception.DBDownException;
import exception.DBException;

import business.Notification;
import persistence.NotificationBroker;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class TestNotification {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		NotificationBroker nbrok = NotificationBroker.getBroker();
		try {
			Notification[] nots = nbrok.get(null); // get all
			for(int i = 0; i < nots.length; i++)
			{
				System.out.println(nots[i]);
			}
			
			nbrok.delete(new Notification(3,5,5,false,"Hello World","test"));
			boolean b = nbrok.create(new Notification(3, 5, 5,false, "Hello World", "test"));
			
			System.out.println(b);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}