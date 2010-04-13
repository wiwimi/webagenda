package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import persistence.EmployeeBroker;
import business.Employee;
import business.Skill;
import exception.DBDownException;
import exception.DBException;

/**
 * This servlet will Delete a user
 * @author Noorin Hasan
 * Edited By: Mark
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
		        Employee delUser = null;
		        
		        //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		        
		        String empId = request.getParameter("empId");
		        boolean success=false;
		        int empIdInt=-1;
		        Employee[]  results = null;
		        Employee user= null;
				
				try {
					    EmployeeBroker broker = EmployeeBroker.getBroker();
						broker.initConnectionThread();
						
						empIdInt = Integer.parseInt(empId);
						
						user = (Employee)session.getAttribute("currentEmployee");
						
						
						delUser = new Employee();
						delUser.setEmpID(empIdInt);
						
						results = broker.get(delUser, user);
						
						if(results!=null)
						success = broker.delete(results[0], user);
						
						
						
					if (success)
					{
						//Confirm that the user was deleted
						response.sendRedirect("wa_user/userSearchResults.jsp?message=true&empId=" + empIdInt);
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to delete the user
					response.sendRedirect("wa_user/userSearchResults.jsp?message=false&empId=" + empIdInt);
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to delete the user
					response.sendRedirect("wa_user/userSearchResults.jsp?message=false&empId=" + empIdInt);
				}
				catch(Exception e)
				{
					out.println(results[0].getEmpID());
					out.println(user.getEmpID());
					// Failed to add the location
					//response.sendRedirect("wa_user/userSearchResults.jsp?message=false&empId=" + empIdInt);
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
