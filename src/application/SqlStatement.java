/**
 * application - SqlStatement.java
 */
package application;

import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Class that represents an Sql Statement with optional WebAgenda values such as priority and
 * which broker sent the statement.
 */
public class SqlStatement {

	private byte priority 						= 4;
	private Statement sql_state					= null;
	private Object broker_obj_ref				= null;
	/** ResultSet is only non-null IF multiple threading is enabled. If Brokers use the Singular thread for
	 * retrieving db data, the resultset is determined in ConnectionManager. Otherwise, this class will
	 * save the result set. */
	private ResultSet result					= null;
	
	/**
	 * Constructor that uses a priority of 4 (average urgency) to create an Sql Statement
	 * 
	 * @param broker_ref Object reference to the Broker that requested this statement
	 * @param state Statement to send to Database
	 */
	public SqlStatement(Object broker_ref, Statement state)
	{
		sql_state = state;
	}
	
	/**
	 * Constructor that uses a custom priority, from 0 to 7 to create an Sql Statement
	 * 
	 * @param state Statment to send to database
	 * @param p Byte priority
	 */
	public SqlStatement(Object broker_ref, Statement state, byte p)
	{
		sql_state = state;
		priority = p;
	}
	
	/**
	 * Setter to set priority, can only be accessed in the package.
	 * 
	 * @param b byte priority, 0 is highest.
	 */
	void setPriority(byte b)
	{
		priority = b;
	}
	
	/**
	 * Getter to retrieve priority from SqlStatement
	 * 
	 * @return byte priority
	 */
	public byte getPriority()
	{
		return priority;
	}

	/**
	 * Returns the Broker instance so that the application can return the ResultSet back. Yes, this is an area may cause
	 * a bottle neck, but SqlStatement is only required when all brokers use one Singular Thread to contact the database.
	 * Having independant Broker db connections would negate the need for this class and hence the resulting bottleneck.
	 * 
	 * @return Object instanceof Broker object
	 */
	Object getBrokerReference()
	{
		return broker_obj_ref;
	}
	
	/**
	 * Returns the statement that was sent to the database.
	 * 
	 * @return
	 */
	Statement getStatement()
	{
		return this.sql_state;
	}
	
}
