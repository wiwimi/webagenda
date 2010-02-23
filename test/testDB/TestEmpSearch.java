/**
 * 
 */
package testDB;

import java.sql.SQLException;
import persistence.EmployeeBroker;
import business.Employee;

/**
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestEmpSearch
	{
	public TestEmpSearch()
		{
		//Create broker.
		EmployeeBroker empBroker = EmployeeBroker.getBroker();
		
		//Create employees to search by an employee ID, and all active employees.
		Employee searchEmp1 = new Employee();
		searchEmp1.setEmployee_id(5);
		
		Employee searchEmp2 = new Employee();
		searchEmp2.setActive(true);
		
		Employee searchEmp3 = new Employee();
		searchEmp3.setSupervisor(3);
		
		//Run searches
		Employee[] byID = null, byActive = null, bySupervisor = null;
		try
			{
			byID = empBroker.get(searchEmp1);
			byActive = empBroker.get(searchEmp2);
			bySupervisor = empBroker.get(searchEmp3);
			}
		catch (SQLException e)
			{
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		
		// PRINT RESULTS! :D
		// This is just using the basic toString that dumps all data.
		System.out.println("---------- Search Results by ID 5 ----------");
		for (Employee emp : byID)
			{
			System.out.println(emp);
			}
		
		System.out.println("\n---------- Search Results by Active State True ----------");
		for (Employee emp : byActive)
			{
			System.out.println(emp);
			}
		
		System.out.println("\n---------- Search Results by Supervisor 3 ----------");
		for (Employee emp : bySupervisor)
			{
			System.out.println(emp);
			}
		}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
		{
		new TestEmpSearch();
		}
	
	}
