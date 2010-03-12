package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import persistence.EmployeeBroker;
import business.Employee;
import exception.DBDownException;
import exception.DBException;

/**
 * Servlet implementation class DeleteUser
 */
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
    	  
		        response.setContentType("text/html;charset=UTF-8");
		        PrintWriter out = response.getWriter();
		        String empId = request.getParameter("empId");
		        boolean success;
				
				try {
					    EmployeeBroker broker = EmployeeBroker.getBroker();
						broker.initConnectionThread();
						
						Employee emp = new Employee();
						int empIdInt = Integer.parseInt(empId);
						
						emp.setEmpID(empIdInt);
						success = broker.delete(emp);
						
					if (success)
					{
						//Confirm that the user was deleted
						response.sendRedirect("wa_user/updateUser.jsp?message=true");
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to delete the user
					response.sendRedirect("wa_user/updateUser.jsp?message=false");
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the location
					response.sendRedirect("wa_user/updateUser.jsp?message=false");
				}
				catch(Exception e)
				{
					// Failed to add the location
					response.sendRedirect("wa_user/updateUser.jsp?message=false");
				}
				finally
				{
					out.close();
				}
		   }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
