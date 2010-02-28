/**
 * 
 */
package testDB;

import business.Employee;
import persistence.EmployeeBroker;

/**
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestEmpCreate
	{
	
	public TestEmpCreate()
		{
		EmployeeBroker broker = EmployeeBroker.getBroker();
		
		Employee newEmp = new Employee();
		
		newEmp.setEmployee_id(200);
		newEmp.setGivenName("Bilbo");
		newEmp.setFamilyName("Baggins");
		newEmp.setUsername("bilb01");
		newEmp.setPassword("password");
		
		
		
		}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		new TestEmpCreate();
		}
	
	}
