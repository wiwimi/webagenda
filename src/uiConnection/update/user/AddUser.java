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
		        EmployeeBroker empBroker;
		        boolean success = false;
		        java.sql.Date  sqlBirthDate;
		        empBroker = EmployeeBroker.getBroker();
				empBroker.initConnectionThread();
		        
		       //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		   
		        Employee user = (Employee)session.getAttribute("currentEmployee");
		        
		        PrintWriter out = response.getWriter();
		        
		        String familyName = request.getParameter("familyName");
		        String givenName = request.getParameter("givenName");
		        String password = request.getParameter("password");
		       
				String status =  request.getParameter("status");
				String pos = request.getParameter("pos");
				String email=request.getParameter("email");
				String username = request.getParameter("user");
				String dob = request.getParameter("dob");
				
				//String permLevel = request.getParameter("permLevel");
				String loc= request.getParameter("loc");
				String empId = request.getParameter("empId");
				String supId = request.getParameter("supId");
				
				int empIdInt = Integer.parseInt(empId);
				
				try 
				{
					
					Employee emp = new Employee(empIdInt,givenName,familyName,username, password, "1a");
					if (status.equalsIgnoreCase("enabled"))
						emp.setActive(true);
					
					else 
						emp.setActive(false);
					// Convert the date of birth from string to util.Date
					// Then convert the util.Date to sql.Date
					
					if (!dob.equals(null) && !dob.equals(""))
					{
						String revDob = dob.substring(6, dob.length()) + '-' + dob.substring(0, 2)
						+'-' + dob.substring(3, 5);		// Reversing the date so that it matches the argument required for sql.Date
						sqlBirthDate = java.sql.Date.valueOf(revDob);
						emp.setBirthDate(sqlBirthDate);
				    }
					if(!email.equals(null) && !email.equals(""))
					{
						emp.setEmail(email);
					}
					
					if(supId!=null)
					{
						int supIdInt = Integer.parseInt(supId);
						emp.setSupervisorID(supIdInt);
						
					}
					if(pos!=null)
					{
						emp.setPrefPosition(pos);
					}
					if(loc!=null)
					{
						emp.setPrefLocation(loc);
					}
					
					success = empBroker.create(emp, user);
					
					if (success)
					{
						//Confirm that the user was added
						response.sendRedirect("wa_user/newUser.jsp?message=true&familyName=" + familyName +"&givenName=" + givenName
								+ "&username=" + username +  "&email=" + email + "&password=" + password
								+ "&dob=" + dob + "&empId=" + empId);
					}
				}
				catch (DBException e) 
				{
					e.printStackTrace();
					out.println("DB Exception");
					out.println(givenName + " ");
					out.println(familyName + " ");
					out.println(empIdInt + " ");
					out.println(password + " ");
					out.println(username + "xDOB ");
					//out.println(sqlBirthDate + " ");
					
					response.sendRedirect("wa_user/newUser.jsp?message=false");
				}
				catch (DBDownException e) 
				{
					e.printStackTrace();
					out.println("DB Down Exception");
					out.println(givenName);
					out.println(familyName);
					out.println(empIdInt);
					response.sendRedirect("wa_user/newUser.jsp?message=false");
				}
				catch (InvalidPermissionException  e)
				{
					e.printStackTrace();
					out.println("Invalid Permisi");
					response.sendRedirect("wa_user/newUser.jsp?message=perm");
				}
				catch (PermissionViolationException e) 
				{
					e.printStackTrace();
					out.println("Perm Viol");
					response.sendRedirect("wa_user/newUser.jsp?message=perm");
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
