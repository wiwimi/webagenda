package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.mail.MessagingException;
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
 * Resetting the password of a user by Admin
 * If the sending option was selected, an email is sent to the user
 * If the user's email is empty, an email is sent to the Admin who is resetting the password
 * @author Noorin Hasan
 */
@WebServlet(name="ResetPassword", urlPatterns={"/ResetPassword"})
public class ResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		        //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		        
		        EmployeeBroker broker;
		        boolean success = false;
		       
		        broker = EmployeeBroker.getBroker();
				broker.initConnectionThread();
			    String familyName = request.getParameter("familyName");
			    String givenName = request.getParameter("givenName");
				String username = request.getParameter("user");
				String newPassword = request.getParameter("password");
				String email = request.getParameter("email");
				
				Employee user = (Employee)session.getAttribute("currentEmployee");
				
				// If an email has not been set, send the password details to the admin resetting the password
				if(email==null || email.equals(""))
				{
					email = user.getEmail();
				}
				String[] emailSendToList = { email };
		       
				// To determine whether an email should be sent or not
				String sendingOption  = request.getParameter("sendingOption");
		        
		       
			        try 
					{
						success = broker.resetPassword(username, newPassword, user);
						if (success)
						{
							Gmail smtpMailSender = null;
							
							try 
							{
								// Send an email with the account's details and confirm the user with the results
								if(sendingOption!=null) 
								{
									smtpMailSender = new Gmail();
									smtpMailSender.postMail(emailSendToList, "New Password for " + username, newPassword);
									
									//Confirm that the password was updated and return the details back to the screen
									
									response.sendRedirect("wa_user/resetPasswordStepTwo.jsp?message=sent&familyName=" + familyName +
											"&givenName=" + givenName + "&user=" + username + "&password=" +newPassword  + "&email=" + email);
								}
								//Confirm that the user has been added but an email has not been sent.
								else
								{
									//Confirm that the password was updated and return the details back to the screen
									
									response.sendRedirect("wa_user/resetPasswordStepTwo.jsp?message=true&familyName=" + familyName +
											"&givenName=" + givenName + "&user=" + username + "&password=" +newPassword + "&email=" + email);
								}
							} 
							catch (MessagingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								response.sendRedirect("wa_user/resetPasswordStepTwo.jsp?message=sentError&familyName=" + familyName +
											"&givenName=" + givenName + "&user=" + username + "&password=" +newPassword + "&email=" + email);
                            }
						}
					}
					catch (DBException e) 
					{
						e.printStackTrace();
						response.sendRedirect("wa_user/resetPasswordStepTwo.jsp?message=false&familyName=" + familyName +
											"&givenName=" + givenName + "&user=" + username + "&password=" +newPassword + "&email=" + email);
					}
			
					catch (DBDownException e) 
					{
						e.printStackTrace();
						
						response.sendRedirect("wa_user/resetPassword.jsp?message=false&familyName=" + familyName +
											"&givenName=" + givenName + "&user=" + username + "&password=" +newPassword + "&email=" + email);
					 }
					
					finally
					{
						broker.stopConnectionThread();
					}
		        }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPassword() {
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
