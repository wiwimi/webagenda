/**
 * application - ConnectionInterface.java
 */
package application;

import java.net.InetAddress;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public abstract class ConnectionInterface {

	private InetAddress ip = null;
	
	public InetAddress getIp()
	{
		return ip;
	}
	
	void setIp(InetAddress ip)
	{
		this.ip = ip;
	}
	
}
