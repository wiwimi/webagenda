/**
 * testDB - TestPermissions.java
 */
package testDB;

import java.sql.Date;

import exception.DBException;

import business.Employee;
import business.permissions.PermissionAccess;
import business.permissions.PermissionLevel;
import persistence.PermissionBroker;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class TestPermissions {
	
	public static void main(String[] args)
	{
		PermissionBroker pbrok = PermissionBroker.getBroker();
		try {
			// Produces an empty set
			pbrok.get(PermissionAccess.getAccess().getDefault());
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PermissionLevel[] pla = pbrok.get(PermissionAccess.getAccess().getLevel(1, 'a'));
			for(PermissionLevel pl : pla)
				System.out.println(pl);
		}
		catch(DBException e2)
		{
			e2.printStackTrace();
		}
		try {
			PermissionLevel[] pla = pbrok.get(PermissionAccess.getAccess().getLevel(2, 'a'));
			for(PermissionLevel pl : pla)
				System.out.println(pl);
		}
		catch(DBException e2)
		{
			e2.printStackTrace();
		}
		try {
			PermissionLevel[] pla = pbrok.get(PermissionAccess.getAccess().getLevel(-1,' '));
			for(PermissionLevel pl : pla)
				System.out.println(pl);
		}
		catch(DBException e2)
		{
			e2.printStackTrace();
		}
		
		System.out.println("CREATE");
		try {
			Employee e= new Employee(234,"Guy","Guy",new Date(0),"user234","password","2a");
			PermissionLevel pl = PermissionAccess.getAccess().getLevel(0, ' ');
			pl = PermissionAccess.getAccess().setPermissions(PermissionAccess.getAccess().getLevel0(), pl);
			boolean b = PermissionAccess.getAccess().createPermissionLevel(pl, e);
			System.out.println(b);
		}
		catch(Exception E)
		{
			E.printStackTrace();
		}
	}

}
