/**
 * 
 */
package business;

/**
 *	Class that all broker-handled objects must extend.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public abstract class BusinessObject implements Cloneable
	{

	/* (non-Javadoc)
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
			//This should never happen.
			throw new InternalError(e.toString());
			}
		}
	
	}
