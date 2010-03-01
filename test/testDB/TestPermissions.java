/**
 * testDB - TestPermissions.java
 */
package testDB;

import java.sql.SQLException;

import business.permissions.PermissionAccess;
import business.permissions.PermissionLevel;
import persistence.EmployeeBroker;
import persistence.PermissionBroker;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class TestPermissions {
	
	public static void main(String[] args)
	{
		EmployeeBroker ebrok = EmployeeBroker.getBroker();
		PermissionBroker pbrok = PermissionBroker.getBroker();
		try {
			pbrok.get(PermissionAccess.getAccess().getDefault());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
