/**
 * application - CachableResult.java
 */
package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import business.Cachable;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class CachableResult extends Observable {
	
	private byte priority					= 4;
	
	private Cachable search_terms			= null;
	
	private String sql_statement			= null;
	
	public CachableResult(Cachable c, String sql_command)
	{
		search_terms = c;
		sql_statement = sql_command;
	}
	
	public CachableResult(Cachable c,String sql_command , byte new_priority)
	{
		search_terms = c;
		this.priority = new_priority;
		sql_statement = sql_command;
	}
	
	public ResultSet getResults(Connection object) throws SQLException
	{
		if(sql_statement == null) return null;
		if(object == null) return null;
		Statement stat = (Statement) object.createStatement();
		
		return stat.executeQuery(sql_statement);
	}
	
	
}
