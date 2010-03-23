/**
 * testDB - TestNotification.java
 */
package testDB;

import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;

import business.Employee;
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
	 * 
	 * I can confirm that creating, deleting, and updating work for this test.
	 * 
	 */
	public static void main(String[] args) {
		
		NotificationBroker nbrok = NotificationBroker.getBroker();
		
		
		
		try {
			
			Employee user = new Employee(12314, "Chaney", "Henson", "user1", "password",  "2a" );
			nbrok.create(new Notification(1,28472,12314,false,"Can you read this?","Public"), user);
			nbrok.create(new Notification(2,12314,28472,false,"I sure can","Public"), user);
			
			Notification[] nots = nbrok.get(null, user); // get all
			
			for(int i = 0; i < nots.length; i++)
			{
				System.out.println(nots[i]);
			}
			
			nots = nbrok.get(new Notification(2),user);
			for(Notification n : nots)
				System.out.println(n);
			
			Notification n2 = new Notification(1,28472,12314,true,"Can you read?","Public");
			
			nbrok.update(nots[0],n2,user);
			
			nots = nbrok.get(new Notification(2),user);
			System.out.println(nots[0] == n2); // This should be false
			
			
			nbrok.delete(new Notification(1), user);
			nbrok.delete(new Notification(2), user);
			boolean b = nbrok.create(new Notification(3, 12314, 39280,true, "Hello World", "test"), user);
			
			System.out.println(b);
			
			nbrok.delete(new Notification(3),user);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBDownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPermissionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
