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
@WebServlet(name="UpdateAdminProfile", urlPatterns={"/UpdateAdminProfile"})
public class UpdateAdminProfile extends HttpServlet {
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
			        java.sql.Date  sqlBirthDate;
			        broker = EmployeeBroker.getBroker();
					broker.initConnectionThread();
					Employee result =null;
					Employee newEmp=null;
			       //Create or get the session object from the HTTPSession object
			        HttpSession session = request.getSession();
			   
			        Employee user = (Employee)session.getAttribute("currentEmployee");
			        PrintWriter out = response.getWriter();
			        String familyName = request.getParameter("familyName");
			        String givenName = request.getParameter("givenName");
			        String email=request.getParameter("email");
					String username = request.getParameter("user");
					String dob = request.getParameter("dob");
					Employee oldEmp =null;
				
				
				try 
				{
					newEmp = new Employee();
					oldEmp = (Employee)session.getAttribute("oldEmp");
					newEmp.setEmpID(user.getEmpID());
					
					result = broker.get(oldEmp, user)[0];
					newEmp = oldEmp.clone();
					
					
					newEmp.setEmpID(user.getEmpID());
					newEmp.setPassword(user.getPassword());
					newEmp.setPrefLocation(user.getPrefLocation());
					newEmp.setPrefPosition(user.getPrefPosition());
					newEmp.setSupervisorID(user.getSupervisorID());
					newEmp.setFamilyName(familyName);
					newEmp.setGivenName(givenName);
					newEmp.setUsername(username);
					
					// Convert the date of birth from string to util.Date
					// Then convert the util.Date to sql.Date
					
					if (!dob.equals(null) && !dob.equals(""))
					{
						String revDob = dob.substring(6, dob.length()) + '-' + dob.substring(0, 2)
						+'-' + dob.substring(3, 5);		// Reversing the date so that it matches the argument required for sql.Date
						sqlBirthDate = java.sql.Date.valueOf(revDob);
						newEmp.setBirthDate(sqlBirthDate);
				    }
					if(!email.equals(null) && !email.equals(""))
					{
						newEmp.setEmail(email);
					}
					success = broker.update(result, newEmp, user);
					if (success)
					{
						//Confirm that the user was updated
						response.sendRedirect("wa_settings/adminProfile.jsp?message=true&familyName=" + familyName +"&givenName=" + givenName
								+ "&username=" + username +  "&email=" + email 
								+ "&dob=" + dob + "&userID=" + user.getEmpID());
					}
				}
				catch (DBException e) 
				{
					e.printStackTrace();
					
					//DEBUGGING
					
					//out.println(newEmp.getUsername());
					//out.println(newEmp.getEmpID());
					//out.println(newEmp.getFamilyName());
					
					//out.println(oldEmp.getUsername());
					//out.println(oldEmp.getEmpID());
					//out.println(oldEmp.getFamilyName());
					//Even if the user is not updated, return the values to the form
					response.sendRedirect("wa_settings/adminProfile.jsp?message=false&familyName=" + familyName +"&givenName=" + givenName
							+ "&username=" + username +  "&email=" + email 
							+ "&dob=" + dob + "&userID=" + user.getEmpID());
				}
				catch (DBDownException e) 
				{
					e.printStackTrace();
					//out.println("DB Down Exception");
				
					
					//Even if the user is not created, return the values to the form
					response.sendRedirect("wa_settings/adminProfile.jsp?message=false&familyName=" + familyName +"&givenName=" + givenName
							+ "&username=" + username +  "&email=" + email 
							+ "&dob=" + dob + "&userID=" + user.getEmpID());
				}
				catch (InvalidPermissionException  e)
				{
					e.printStackTrace();
					
					//DEBUGGING
					//out.println("Invalid Permission");
					
					//Even if the user is not created, return the values to the form
					response.sendRedirect("wa_settings/adminProfile.jsp?message=perm&familyName=" + familyName +"&givenName=" + givenName
							+ "&username=" + username +  "&email=" + email 
							+ "&dob=" + dob + "&userID=" + user.getEmpID());
				}
				catch (PermissionViolationException e) 
				{
					e.printStackTrace();
					//out.println("Perm Violation");
					//Even if the user is not created, return the values to the form
					response.sendRedirect("wa_settings/adminProfile.jsp?message=perm&familyName=" + familyName +"&givenName=" + givenName
							+ "&username=" + username +  "&email=" + email 
							+ "&dob=" + dob + "&userID=" + user.getEmpID());
				}
				finally
				{
					broker.stopConnectionThread();
				}
			
			 }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateAdminProfile() {
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
