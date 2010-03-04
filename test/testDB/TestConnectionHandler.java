package testDB;


import exception.DBDownException;
import exception.InitializedLogFileException;

import application.DBConnection;
import persistence.EmployeeBroker;

public class TestConnectionHandler {

	public TestConnectionHandler() throws InterruptedException {
	EmployeeBroker.getBroker().initConnectionThread();
	DBConnection dbc0 = null, dbc1 = null, dbc2 = null, dbc3 = null, dbc4 = null;
	try
		{
		dbc0 = EmployeeBroker.getBroker().getConnection();
		
		dbc1 = EmployeeBroker.getBroker().getConnection();
		dbc2 = EmployeeBroker.getBroker().getConnection();
		dbc3 = EmployeeBroker.getBroker().getConnection();
		dbc4 = EmployeeBroker.getBroker().getConnection();
		}
	catch (DBDownException e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	
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
