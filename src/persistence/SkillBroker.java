/**
 * persistence - SkillBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import business.BusinessObject;
import exception.DBException;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class SkillBroker extends Broker {

	private static SkillBroker sbrok = null;
	
	private SkillBroker()
	{
		
	}
	
	public static SkillBroker getBroker()
	{
		if(sbrok == null) sbrok = new SkillBroker();
		return sbrok;
	}

	@Override
	public boolean create(BusinessObject createObj) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(BusinessObject deleteObj) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BusinessObject[] get(BusinessObject searchTemplate)
			throws DBException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BusinessObject[] parseResults(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(BusinessObject updateObj) throws DBException {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
