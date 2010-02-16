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
public interface ConnectionInterface {

	public boolean isConnected(Connection get_conn);
	
	public Connection addConnection(Connection c, InetAddress ip);
	
	public Connection getConnection();
	
}
