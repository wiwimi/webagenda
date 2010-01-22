/**
 * persistence - Broker.java
 */
package persistence;

/**
 * All brokers should inherit this class
 * 
 * @author peon-dev
 * @version 0.01.00
 *
 */
public abstract class Broker {
	
	public abstract Object getBrokerObject(Object o);
	
	public abstract int flushCache();
	
	public abstract int clearCache();
	
	public abstract int cache(Cachable cache_obj);
	
	
}
