package testDB;

import java.awt.HeadlessException;
import java.io.IOException;
import java.sql.SQLException;

import exception.InitializedLogFileException;

import messagelog.Logging;

import oldClasses.application.ConnectionManager;
import application.DBConnection;
import persistence.EmployeeBroker;

public class TestConnectionHandler {

	/**
	 * @param args
	 * @throws InitializedLogFileException 
	 */
	public static void main(String[] args) throws InitializedLogFileException {
		
		try {
			Logging.initializeLogs();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EmployeeBroker.getBroker().initConnectionThread();
		DBConnection dbc1 = EmployeeBroker.getBroker().getConnection();
		dbc1.setAvailable(false);
		
		DBConnection dbc2 = EmployeeBroker.getBroker().getConnection();
		//dbc2.setAvailable(false);
		DBConnection dbc3 = EmployeeBroker.getBroker().getConnection();
		//dbc3.setAvailable(false);
		DBConnection dbc4 = EmployeeBroker.getBroker().getConnection();
		//dbc4.setAvailable(false);
		DBConnection dbc5 = EmployeeBroker.getBroker().getConnection();
		//dbc5.setAvailable(false);
		
		dbc1.setAvailable(true);
		
		

	}

}
