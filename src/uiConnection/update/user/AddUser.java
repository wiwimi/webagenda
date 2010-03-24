package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;

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
import business.schedule.Position;

/**
 * Servlet implementation class addUser
 */
@WebServlet(name="AddUser", urlPatterns={"/AddUser"})
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		        EmployeeBroker empBroker= null;
		        boolean success = false;
		        empBroker = EmployeeBroker.getBroker();
				empBroker.initConnectionThread();
		        
		       //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		   
		        Employee user = (Employee)session.getAttribute("currentEmployee");
		        
		        PrintWriter out = response.getWriter();
		        
		        String familyName = request.getParameter("familyName");
		        String givenName = request.getParameter("givenName");
		        String password = request.getParameter("password");
				//String[] pos_skills = request.getParameterValues("skill");
				//String status =  request.getParameter("status");
				//String pos = request.getParameter("pos");
				//String email=request.getParameter("email");
				//String username = request.getParameter("user");
				//String permLevel = request.getParameter("permLevel");
				//String location= request.getParameter("location");
				String empId = request.getParameter("empId");
				//String supId = request.getParameter("supId");
				
				int empIdInt = Integer.parseInt(empId);
				//int supIdInt = Integer.parseInt(supId);
				
				
				
				try 
				{
					out.println(password);
					Employee emp = new Employee(empIdInt,givenName,familyName,"bilb01",password,
					"1a");
					
					success = empBroker.create(emp, user);
					
					if (success)
					{
						//Confirm that the user was added
						response.sendRedirect("wa_user/newUser.jsp?message=true");
					}
				}
				catch (DBException e) 
				{
					e.printStackTrace();
					out.println("DB Exception");
					out.println(givenName);
					out.println(familyName);
					out.println(empIdInt);
					response.sendRedirect("wa_user/newUser.jsp?message=false");
				}
				catch (DBDownException e) 
				{
					e.printStackTrace();
					out.println("DB Down Exception");
					response.sendRedirect("wa_user/newUser.jsp?message=false");
				}
				catch (InvalidPermissionException  e)
				{
					e.printStackTrace();
					out.println("Invalid Permisi");
					response.sendRedirect("wa_user/newUser.jsp?message=false");
				}
				catch (PermissionViolationException e) 
				{
					e.printStackTrace();
					out.println("Perm Viol");
					response.sendRedirect("wa_user/newUser.jsp?message=false");
				}
				finally
				{
					empBroker.stopConnectionThread();
				}
			
			 }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
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
