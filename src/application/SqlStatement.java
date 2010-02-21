/**
 * application - SqlStatement.java
 */
package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.Observer;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Class that represents an Sql Statement with optional WebAgenda values such as priority and
 * which broker sent the statement.
 */
public class SqlStatement extends Observable {

	private byte priority 						= 4;
	private String sql_state					= null;
	private Object broker_obj_ref				= null;
	private ResultSet result					= null;
	/** Position in Queue of sql statements */
	private int position						= -1;
	
	/**
	 * Constructor that uses a priority of 4 (average urgency) to create an Sql Statement
	 * 
	 * @param broker_ref Object reference to the Broker that requested this statement
	 * @param state Statement to send to Database
	 */
	public SqlStatement(Object broker_ref, String state)
	{
		broker_obj_ref = broker_ref;
		sql_state = state;
	}
	
	/**
	 * Constructor that uses a custom priority, from 0 to 7 to create an Sql Statement
	 * 
	 * @param state Statment to send to database
	 * @param p Byte priority
	 */
	public SqlStatement(Object broker_ref, String state, byte p)
	{
		broker_obj_ref = broker_ref;
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
	 * @return String statement to request from database
	 */
	public String getStatement()
	{
		return this.sql_state;
	}
	
	/**
	 * By supplying a proper connection, this will issue a command to the database through the connection and will
	 * set the class variable ResultSet to the results which will be returned. It can also be retrieved through
	 * the getResult() method and set using setResult(ResultSet rs, String statement).<br>
	 * The setter requires the statement (as a string) because the cache looks at the statement given when
	 * retrieving previous results.
	 * 
	 * @param con Connection to database
	 * @param str String statement to database
	 * @return ResultSet results from statement
	 * @throws SQLException error is returned from statement
	 */
	ResultSet issueStatement(Connection con, String str) throws SQLException
	{
		Statement st = (Statement) con.createStatement();
		setResult(st.executeQuery(str),str);
		return getResult();
	}

	public ResultSet getResult() {
		return result;
	}

	protected void setResult(ResultSet result,String statement) {
		this.sql_state = statement;
		this.result = result;
		this.setChanged();
		this.notifyObservers(this);
	}
	
}
