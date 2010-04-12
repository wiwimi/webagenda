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
 * PermissionBroker in the persistence package (this) differs from the one in
 * the permissions package. This Broker object accesses the PermissionAccess
 * class's methods as they limit what can be retrieved on the PermissionBroker
 * found in the same package. PermissionAccess denies users without proper
 * permissions to use the Broker's methods. The Broker in the permissions
 * package does direct manipulation (does not include validation) so by hiding
 * its methods this is additional security.
 * 
 * @author Daniel Kettle
 * @version 0.01.25 FIXME: With all DBException throws, log file should be
 *          written to.
 */
public class PermissionBroker extends Broker<PermissionLevel>
    {
    
    /** Static broker object that is initialized with a getBroker() method call. */
    private static volatile PermissionBroker pbrok = null;
    
    /**
     * Constructor that initializes an Employee object that will be used for
     * every method call and validated against.
     * 
     * @param emp Employee calling PermisionBroker in permissions package.
     */
    public PermissionBroker() throws DBException
        {
        }
    
    @Override
    public boolean create(PermissionLevel createObj, Employee emp) throws DBException,
            DBDownException, InvalidPermissionException, PermissionViolationException
        {
        return PermissionAccess.getAccess().createPermissionLevel(createObj, emp);
        }
    
    @Override
    public boolean delete(PermissionLevel deleteObj, Employee caller) throws DBException,
            InvalidPermissionException, DBDownException, PermissionViolationException
        {
        return PermissionAccess.getAccess().deletePermissionLevel(deleteObj, caller);
        }
    
    @Override
    public PermissionLevel[] get(PermissionLevel searchTemplate, Employee caller)
            throws DBException, DBDownException
        {
        return business.permissions.PermissionBroker.getBroker().get(searchTemplate, caller);
        }
    
    /**
     * Returns a specific Permission Level by having user explicitly input the
     * level and version, instead of a search tempate using a PermissionLevel
     * object.
     * 
     * @param level int
     * @param version char
     * @param emp Employee caller
     * @return Permission[] level
     * @throws DBException
     * @throws DBDownException
     */
    public PermissionLevel[] get(int level, char version, Employee emp) throws DBException,
            DBDownException
        {
        return business.permissions.PermissionBroker.getBroker().get(level, version, emp);
        }
    
    /**
     * Returns the PermissionBroker object, initializing it if necessary, to
     * perform methods in this Broker.
     * 
     * @return PermissionBroker
     * @throws DBException
     */
    public static PermissionBroker getBroker() throws DBException
        {
        if (pbrok == null)
            pbrok = new PermissionBroker();
        return pbrok;
        }
    
    /**
     * UNUSED
     */
    @Override
    @Deprecated
    protected PermissionLevel[] parseResults(ResultSet rs) throws SQLException
        {
        // TODO Auto-generated method stub
        return null;
        }
    
    @Override
    public boolean update(PermissionLevel oldPL, PermissionLevel updateObj, Employee caller)
            throws DBException, InvalidPermissionException, DBDownException,
            PermissionViolationException
        {
        return PermissionAccess.getAccess().updatePermissionLevel(oldPL, updateObj, caller);
        
        }
    
    /**
     * Returns all the permission levels that are below a certain Employee's
     * built in permission level. This way, an employee does not have to worry
     * about level inputs and it applies to the caller automatically, or if a
     * higher-permissioned employee calls this using an employee under them, it
     * will return that data. A higher-level employee should not be returned to
     * an under-privileged user at any point, unless it's basic name/contact
     * data for supervisors of an Employee.
     * 
     * @param emp Employee to get all permission levels under them
     * @return PermissionLevel[]
     * @throws DBException
     * @throws DBDownException
     */
    public PermissionLevel[] getAllBelow(Employee emp) throws DBException, DBDownException
        {
        return business.permissions.PermissionBroker.getBroker().getAllBelow(
                    emp.getLevel());
        }
    
    }
