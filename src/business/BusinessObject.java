/**
 * 
 */
package business;

/**
 * Class that all broker-handled objects must extend. This prevents unwanted
 * objects from being sent to a Broker that cannot be dealt with. (Type Safety)
 * 
 * @author Daniel Wehr, Daniel Kettle
 * @version 0.1.0
 */
public abstract class BusinessObject implements Cloneable
    {
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    protected BusinessObject clone()
        {
        try
            {
            return (BusinessObject)super.clone();
            }
        catch (CloneNotSupportedException e)
            {
            // This should never happen.
            throw new InternalError(e.toString());
            }
        }
    
    }
