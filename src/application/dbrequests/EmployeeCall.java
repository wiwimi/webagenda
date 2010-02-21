/**
 * application.dbrequests - EmployeeCall.java
 */
package application.dbrequests;

import business.Employee;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Employee calls to the database as Strings that can be manipulated.
 * 
 */
public class EmployeeCall extends DbCall {

	private static final String EMPLOYEE_TABLE = "EMPLOYEE";
	
	public String getAllEmployees()
	{
		return returnSelect(new String[]{"*"}, new String[]{EMPLOYEE_TABLE}, null); 
	}
	
	public String getEmployeeSearch(Employee e)
	{
		return null;
	}
	
}
