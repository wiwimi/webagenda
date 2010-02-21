/**
 * application - CachableResult.java
 */
package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;

import persistence.EmployeeBroker;

import messagelog.Logging;


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
	
	private ResultSet results				= null;
	
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
	
	public String getSql()
	{
		return this.sql_statement;
	}
	
	public void setResults(ResultSet rs, String sql)
	{
		if(sql != sql_statement)
		{
			Logging.writeToLog(Logging.ACCESS_LOG, Logging.ERR_ENTRY, "Sql command " + sql + " found to be inconsistent with statement that generated results: " + sql_statement);
			return;
		}
		results = rs;
		this.notifyObservers(results);
	}
	
	public ResultSet getResults()
	{
		return results;
	}
	
	public void printResults() throws SQLException
	{
		results.next();
		while(results.next()) {
			System.out.print(results.getInt(EmployeeBroker.empID) + " " );
			System.out.print(results.getInt(EmployeeBroker.supervisorID) + " ");
			System.out.print(results.getString(EmployeeBroker.givenName) + " ");
			System.out.print(results.getString(EmployeeBroker.familyName) + " ");
			System.out.print(results.getDate(EmployeeBroker.birthDate) + " ");
			System.out.print(results.getString(EmployeeBroker.email) + " ");
			System.out.print(results.getString(EmployeeBroker.username) + " ");
			System.out.print(results.getDate(EmployeeBroker.lastLogin) + " ");
			System.out.print(results.getString(EmployeeBroker.password) + " ");
			System.out.print(results.getString(EmployeeBroker.prefPosition) + " ");
			System.out.print(results.getString(EmployeeBroker.plevel) + " ");
			System.out.print(results.getBoolean(EmployeeBroker.active) + " ");
			System.out.println();
		}
	}
	
	
}
