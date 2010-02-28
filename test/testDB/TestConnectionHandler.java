package testDB;


import exception.InitializedLogFileException;

import application.DBConnection;
import persistence.EmployeeBroker;

public class TestConnectionHandler {

	public TestConnectionHandler() throws InterruptedException {
	EmployeeBroker.getBroker().initConnectionThread();
	DBConnection dbc0 = EmployeeBroker.getBroker().getConnection();
	
	DBConnection dbc1 = EmployeeBroker.getBroker().getConnection();
	DBConnection dbc2 = EmployeeBroker.getBroker().getConnection();
	DBConnection dbc3 = EmployeeBroker.getBroker().getConnection();
	DBConnection dbc4 = EmployeeBroker.getBroker().getConnection();
	
	Thread.sleep(10000);
	
	dbc3.setAvailable(true);
	dbc4.setAvailable(true);
	
	Thread.sleep(10000);
	
	dbc0.setAvailable(true);
	dbc1.setAvailable(true);
	dbc2.setAvailable(true);
	}

	/**
	 * @param args
	 * @throws InitializedLogFileException 
	 */
	public static void main(String[] args) throws InterruptedException {
	new TestConnectionHandler();
	}

}
