/**
 * persistence - PositionBroker.java
 */
package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import exception.DBDownException;
import exception.DBException;
import business.Position;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 */
public class PositionBroker extends Broker<Position> {

	@Override
	public boolean create(Position createObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Position deleteObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position[] get(Position searchTemplate) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position[] parseResults(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Position updateObj) throws DBException,
			DBDownException {
		// TODO Auto-generated method stub
		return false;
	}

}
