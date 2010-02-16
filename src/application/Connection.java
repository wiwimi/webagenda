/**
 * application - Connection.java
 */
package application;

import java.net.InetAddress;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Connection object; represents a connection to a database.
 * This is a parent class of all connections so that connections can be differentiated (in case we want
 * to have connections unrelated to WebAgenda)
 */
public class Connection {

	private InetAddress ip 				= null;

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	
	
	
}
