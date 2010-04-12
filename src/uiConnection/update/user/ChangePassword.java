package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistence.EmployeeBroker;
import persistence.PositionBroker;
import persistence.SkillBroker;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;
import business.Employee;
import business.Skill;
import business.schedule.Location;
import business.schedule.Position;

/**
 * Servlet implementation class updateUser
 * @author Noorin Hasan
 */
@WebServlet(name="ChangePassword", urlPatterns={"/ChangePassword"})
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		        EmployeeBroker broker;
		        boolean success = false;
		       
		        broker = EmployeeBroker.getBroker();
				broker.initConnectionThread();
			
				Employee newEmp=null;
		       //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		   
		        Employee user = (Employee)session.getAttribute("currentEmployee");
		        
		        PrintWriter out = response.getWriter();
		        
		        String oldPassword = request.getParameter("oldPassword");
		        String newPassword = request.getParameter("password");
		        String confirmPassword = request.getParameter("confirm");
		        
		        if (!newPassword.equals(confirmPassword))
		        {
		        	response.sendRedirect("wa_settings/changePassword.jsp?message=mismatch&password=" + newPassword+ "&confirmPassword=" + confirmPassword);
		        }
		        else 
		        {
		        
			        try 
					{
						newEmp = new Employee();
						
						success = broker.changePassword(oldPassword, newPassword, user);
						if (success)
						{
							//Confirm that the user was updated
							response.sendRedirect("wa_settings/changePassword.jsp?message=true");
						}
					}
					catch (DBException e) 
					{
						e.printStackTrace();
	
						response.sendRedirect("wa_settings/changePassword.jsp?message=false");
					}
					catch (DBDownException e) 
					{
						e.printStackTrace();
						
						response.sendRedirect("wa_settings/changePassword.jsp?message=false");
						
				  }
					
					finally
					{
						broker.stopConnectionThread();
					}
		        }
			
			 }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		processRequest(request, response);
	}

}
