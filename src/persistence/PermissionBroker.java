/**
 * persistence - PermissionBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import business.Employee;
import business.permissions.PermissionAccess;
import business.permissions.PermissionLevel;
import business.permissions.*;

import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import exception.PermissionBrokerViolationException;

/**
 * @author peon-dev
 * @version 0.01.00
 *
 * PermissionBroker in the persistence package differs from the one in the permissions package.
 * This Broker object accesses the PermissionAccess class's methods as they limit what can be 
 * retrieved on the PermissionBroker found in the same package. PermissionAccess denies users 
 * without proper permissions to use the Broker's methods. The Broker in the permissions package
 * does direct manipulation (does not include validation) so by hiding its methods this is
 * additional security.
 * 
 * 
 * FIXME: With all DBException throws, log file should be written to.
 */
public class PermissionBroker extends Broker<PermissionLevel>{
	
	/** Employee object that is utilizing the PermissionBroker. All validation from this class relies on
	 * that object. If it is null, exceptions will be thrown. */
	private Employee employee = null;
	
	private static volatile PermissionBroker pbrok = null; 
	
	/**
	 * Constructor that initializes an Employee object that will be used for every method call 
	 * and validated against.
	 * 
	 * @param emp Employee calling PermisionBroker in permissions package.
	 */
	public PermissionBroker(Employee emp) throws PermissionBrokerViolationException
	{
		if(this.employee == null) throw new PermissionBrokerViolationException("A null value was detected instead of an Employee.");
		this.employee = emp;
	}
	
	@Override
	public boolean create(PermissionLevel createObj) throws DBException {	
		try {
			return PermissionAccess.getAccess().createPermissionLevel(createObj, employee);
		} catch (InvalidPermissionException e) {
			
			throw new DBException("A Permission conflict occured. Please view the permission log file.");
		} catch (DBDownException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(PermissionLevel deleteObj) throws DBException {
		try {
			return PermissionAccess.getAccess().deletePermissionLevel(deleteObj, employee);
		} catch (InvalidPermissionException e) {	
			throw new DBException("A Permission conflict occured. Please view the permission log file.");
		} catch (DBDownException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public PermissionLevel[] get(PermissionLevel searchTemplate)
			throws DBException, DBDownException {
		return business.permissions.PermissionBroker.getBroker().get(searchTemplate);
	}
	
	public static PermissionBroker getBroker(Employee e) throws PermissionBrokerViolationException
	{
		if(pbrok == null) pbrok = new PermissionBroker(e);
		return pbrok;
	}

	/**
	 * UNUSED
	 */
	@Override
	@Deprecated
	public PermissionLevel[] parseResults(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(PermissionLevel oldObj, PermissionLevel updateObj) throws DBException {
		try {
			return PermissionAccess.getAccess().updatePermissionLevel(updateObj, employee);
		} catch (InvalidPermissionException e) {	
			throw new DBException("A Permission conflict occured. Please view the permission log file.");
		} catch (DBDownException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public PermissionLevel[] getAllBelow(Employee emp) throws DBException, DBDownException
	{
		String str = emp.getPLevel();
		if(Character.isLetter(str.charAt(str.length() - 1)))
		{
			return business.permissions.PermissionBroker.getBroker().getAllBelow(
					Integer.parseInt(str.substring(0,str.length() - 1)));
		}
		else {
			return business.permissions.PermissionBroker.getBroker().getAllBelow(
					Integer.parseInt(str));
		}
	}
	
	
}
