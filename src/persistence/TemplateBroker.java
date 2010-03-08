/**
 * persistence - TemplateBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import exception.DBDownException;
import exception.DBException;
import business.*;
import business.schedule.*;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class TemplateBroker extends Broker<BusinessObject> {

	@Override
	public boolean create(BusinessObject createObj) throws DBException,
			DBDownException {
		
		if(createObj == null) throw new NullPointerException();
		
		
		if(createObj instanceof WorkingSchedule)
		{
			
		}
		else if(createObj instanceof WorkingShift)
		{
			
		}
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(BusinessObject deleteObj) throws DBException,
			DBDownException {
		
		if(deleteObj instanceof WorkingSchedule)
		{
			
		}
		else if(deleteObj instanceof WorkingShift)
		{
			
		}
		return false;
	}

	@Override
	public BusinessObject[] get(BusinessObject searchTemplate)
			throws DBException, DBDownException {
		
		if(searchTemplate instanceof WorkingSchedule)
		{
			
		}
		else if(searchTemplate instanceof WorkingShift)
		{
			
		}
		return null;
	}

	@Override
	public BusinessObject[] parseResults(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(BusinessObject updateObj) throws DBException,
			DBDownException {
		if(updateObj instanceof WorkingSchedule)
		{
			
		}
		else if(updateObj instanceof WorkingShift)
		{
			
		}
		return false;
	}


}
