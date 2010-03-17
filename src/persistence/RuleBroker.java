/**
 * persistence - RuleBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.DBConnection;
import business.schedule.Rule;

import exception.DBDownException;
import exception.DBException;
import business.Employee;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class RuleBroker extends Broker<Rule> {

	private static volatile RuleBroker rbrok = null;
	
	private RuleBroker()
	{
		
	}
	
	public static RuleBroker getBroker()
	{
		if(rbrok == null) rbrok = new RuleBroker();
		return rbrok;
	}

	@Override
	public boolean create(Rule createObj,Employee caller) throws DBException, DBDownException {
		if (createObj == null)
			throw new NullPointerException("Can not create null notification.");
		
		/*
		 * Create insert string.
		 */
		String insert = String.format(
				"INSERT INTO `WebAgenda`.`RULE` " +
				"(`ruleID`, `shiftTempID`,`ruleType`,`ruleValue`)" +
				" VALUES (%s,%s,%s,%s);",
				createObj.getRuleID(), createObj.getShiftTempID(),
				(createObj.getRuleType() == null ? "NULL" : "'" + createObj.getRuleType() + "'"),
				(createObj.getRuleValue() == null ? "NULL" : "'" + createObj.getRuleValue() + "'"));
		/*
		 * Send insert to database. SQL errors such as primary key already in use
		 * will be caught, and turned into our own DBAddException, so this method
		 * will only have one type of exception that needs to be caught. If the
		 * insert is successful, return true.
		 */
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(insert);
			conn.setAvailable(true);
			
			if (result != 1)
				throw new DBException(
						"Failed to create rule, result count incorrect: " +
								result);
			}
		catch (SQLException e)
			{
			// TODO Need additional SQL exception processing here.
			throw new DBException("Failed to create rule.", e);
			}
		
		return true;
	}

	@Override
	public boolean delete(Rule deleteObj,Employee caller) throws DBException, DBDownException {
		if (deleteObj == null)
			throw new NullPointerException("Can not delete null notification.");
		
		String delete = String.format(
				"DELETE FROM `WebAgenda`.`RULE` WHERE ruleID = '%s';",
				deleteObj.getRuleID());
		
		boolean success;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int result = stmt.executeUpdate(delete);
			
			if (result != 1)
				throw new DBException("Failed to delete rule, result count incorrect: " +	result);
			else
				success = true;
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to delete rule.",e);
			}
		
		return success;
	}

	@Override
	public Rule[] get(Rule searchTemplate,Employee caller) throws DBException, DBDownException {
		String select;
		
		if (searchTemplate == null)
			{
			select = "SELECT * FROM `WebAgenda`.`RULE`;";
			}
		else
			{
			select = String.format(
					"SELECT * FROM `WebAgenda`.`RULES` WHERE ruleID LIKE '%s%%'",
					searchTemplate.getRuleID());
			}
		
		// Get DB connection, send query, and reopen connection for other users.
		// Parse returned ResultSet into array of locations.
		Rule[] foundRules = null;
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			ResultSet searchResults = stmt.executeQuery(select);
			conn.setAvailable(true);
			
			foundRules = parseResults(searchResults);
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to complete location search.",e);
			}
		
		// Return locations that matched search.
		return foundRules;
	}

	@Override
	public Rule[] parseResults(ResultSet rs) throws SQLException {
		// List will be returned as null if no results are found.
		Rule[] ruleList = null;
		
		if (rs.last())
			{
			// Results exist, get total number of rows to create array of same
			// size.
			int resultCount = rs.getRow();
			ruleList = new Rule[resultCount];
			
			// Return ResultSet to beginning to start retrieving notifications.
			rs.beforeFirst();
			for (int i = 0; i < resultCount && rs.next(); i++)
				{
				Rule rule = new Rule(rs.getInt("ruleID"),rs.getInt("shiftTempID"),
						rs.getString("ruleType"),rs.getString("ruleValue"));
				ruleList[i] = rule;
				}
			}
		
		return ruleList;
		
	}

	@Override
	public boolean update(Rule updateObj,Employee caller) throws DBException, DBDownException {
		if (updateObj == null)
			throw new NullPointerException("Can not update null rule.");
		
		// Create sql update statement from notification object.
		String update = String.format(
				"UPDATE `WebAgenda`.`RULE` SET `ruleID` = %s, `shiftTempID` = %s," +
				"`ruleType` = '%s', `ruleValue` = '%s'"+
				"WHERE `ruleID` = %s;",
				updateObj.getRuleID(), updateObj.getShiftTempID(), updateObj.getRuleType(),
				updateObj.getRuleValue());
		
		// Get DB connection, send update, and reopen connection for other users.
		try
			{
			DBConnection conn = this.getConnection();
			Statement stmt = conn.getConnection().createStatement();
			int updateRowCount = stmt.executeUpdate(update);
			conn.setAvailable(true);
			
			// Ensure
			if (updateRowCount != 1)
				throw new DBException(
						"Failed to update rule: rowcount incorrect.");
			}
		catch (SQLException e)
			{
			throw new DBException("Failed to update rule.", e);
			}
		
		return true;
	}
	
	
	
}
