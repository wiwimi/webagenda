/**
 * 
 */
package testDB;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import exception.DBException;
import application.ConnectionManager;
import business.Employee;
import persistence.EmployeeBroker;

/**
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestEmpCreateDelete
	{
	
	public TestEmpCreateDelete()
		{
		EmployeeBroker broker = EmployeeBroker.getBroker();
		
		Employee newEmp = new Employee();
		
		newEmp.setEmployee_id(80000);
		newEmp.setGivenName("Bilbo");
		newEmp.setFamilyName("Baggins");
		newEmp.setUsername("bilb01");
		newEmp.setPassword("password");
		newEmp.setPermission_level("2a");
		newEmp.setActive(true);
		
		//Add employee
		boolean successful;
		try
			{
			successful = broker.create(newEmp);
			System.out.println("Employee added: "+successful);
			}
		catch (DBException e)
			{
			e.printStackTrace();
			}
		
		//Create employee to use for ID search and deletion.
		Employee empSearchDelete = new Employee();
		empSearchDelete.setEmployee_id(80000);
		
		//Try to delete employee.
		try
			{
			boolean deleted = broker.delete(empSearchDelete);
			System.out.println("Employee deleted: "+ deleted);
			}
		catch (DBException e)
			{
			e.printStackTrace();
			}
		
		//Search for disabled employee.
		try
			{
			empSearchDelete.setActive(false);
			Employee[] results = broker.get(empSearchDelete);
			System.out.println("Employee retrieved: "+results[0]);
			}
		catch (SQLException e)
			{
			e.printStackTrace();
			}
		
		//Delete the test user.
		Connection conn = ConnectionManager.getConnection().getConnection();
		String delete = "DELETE FROM `WebAgenda`.`Employee` WHERE empID = 80000;";
		try
			{
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(delete);
			}
		catch (SQLException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		//Stop the employee broker so this test can close.
		broker.stopConnectionThread();
		}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		new TestEmpCreateDelete();
		}
	
	}
