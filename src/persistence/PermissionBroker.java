/**
 * persistence - PermissionBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import business.Employee;
import business.permissions.*;

import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;

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
	
	
	private static volatile PermissionBroker pbrok = null; 
	
	/**
	 * Constructor that initializes an Employee object that will be used for every method call 
	 * and validated against.
	 * 
	 * @param emp Employee calling PermisionBroker in permissions package.
	 */
	public PermissionBroker() throws DBException
	{
	}
	
	@Override
	public boolean create(PermissionLevel createObj, Employee emp) throws DBException, DBDownException, InvalidPermissionException, PermissionViolationException {
		return PermissionAccess.getAccess().createPermissionLevel(createObj, emp);
	}

	@Override
	public boolean delete(PermissionLevel deleteObj, Employee caller) throws DBException, InvalidPermissionException, DBDownException, PermissionViolationException {
		return PermissionAccess.getAccess().deletePermissionLevel(deleteObj, caller);
	}

	@Override
	public PermissionLevel[] get(PermissionLevel searchTemplate, Employee caller)
			throws DBException, DBDownException {
		return business.permissions.PermissionBroker.getBroker().get(searchTemplate,caller);
	}
	
	public PermissionLevel[] get(int level, char version, Employee emp) throws DBException, DBDownException
	{
		return business.permissions.PermissionBroker.getBroker().get(level, version, emp);
	}
	
	public static PermissionBroker getBroker() throws DBException
	{
		if(pbrok == null) pbrok = new PermissionBroker();
		return pbrok;
	}

	/**
	 * UNUSED
	 */
	@Override
	@Deprecated
	protected PermissionLevel[] parseResults(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(PermissionLevel oldPL, PermissionLevel updateObj, Employee caller) throws DBException, InvalidPermissionException, DBDownException, PermissionViolationException {
		return PermissionAccess.getAccess().updatePermissionLevel(oldPL, updateObj, caller);

	}
	
	public PermissionLevel[] getAllBelow(Employee emp) throws DBException, DBDownException
	{
		return business.permissions.PermissionBroker.getBroker().getAllBelow(
					emp.getLevel());
	}
	

	
}
