package uiConnection.users;

import exception.DBDownException;
import exception.DBException;
import persistence.NotificationBroker;
import business.Notification;
import business.Employee;

/**
 * Class that grabs Notification information from the Broker and parses it into
 * an appropriate format or grabs specific entries for displaying in interface.
 * 
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * Edited by: Mark
 */
public class UserNotifications {

	public static final Notification getMostRecentUnread(Employee caller) throws DBException, DBDownException
	{
		Notification[] notes = (NotificationBroker.getBroker().get(
				new Notification(-1,-1,caller.getEmpID(),false,null,null),caller));
		if(notes == null) return null;
		if(!notes[notes.length - 1].isViewed()) {
			return notes[notes.length - 1];
		}
		return null;
	}
	
	public static final Notification[] getAllUnread(Employee caller) throws DBException, DBDownException
	{
		Notification[] notes = (NotificationBroker.getBroker().get(
				new Notification(-1,-1,caller.getEmpID(),false,null,null),caller));
		if(notes == null) return null;
		Notification[] unread = new Notification[notes.length];
		int j = 0;
		for(int i = 0; i < notes.length; i++) 
		{
			if(!notes[i].isViewed()) {
				unread[j] = notes[i];
				j++;
			}
		}
		return unread;
	}
	
	
}
