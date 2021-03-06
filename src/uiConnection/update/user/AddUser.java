package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistence.EmployeeBroker;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;
import exception.PermissionViolationException;
import business.Employee;

/**
 * This servlet will add a user from the requested add user screen. 
 * @author Noorin Hasan
 * Edited By: mark
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
		        java.sql.Date  sqlBirthDate=null;
		        empBroker = EmployeeBroker.getBroker();
				empBroker.initConnectionThread();
		        
		       //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		   
		        Employee user = (Employee)session.getAttribute("currentEmployee");
		        
		        PrintWriter out = response.getWriter();
		        
		        String familyName = request.getParameter("familyName");
		        String givenName = request.getParameter("givenName");
		        String password = request.getParameter("password");
		       
				String pos = request.getParameter("pos");
				String email=request.getParameter("email");
				String username = request.getParameter("user");
				String dob = request.getParameter("dob");
				
				String permission = request.getParameter("perm");
				String permLevel = "", version="";
				int permLevelInt =0;
				char v = ' ';
				if(permission !=null)
				{
					// Split the permission to get the level and the version
					StringTokenizer st = new StringTokenizer(permission , "-");
					while (st.hasMoreTokens ()) 
					{
						permLevel = st.nextToken();
						version = st.nextToken();
					}
					v = version.charAt(0);
					permLevelInt = Integer.parseInt(permLevel);
				}
				
				
				String loc= request.getParameter("loc");
				String empId = request.getParameter("empId");
				String supId = request.getParameter("supId");
				
				// If the email is empty, send the user details to the admin who is creating the account 
				if(email==null || email.equals(""))
				{
					email = user.getEmail();
				}
				String[] emailSendToList = { email };
				Employee emp = null;
				
				
				// To determine whether an email should be sent or not
				
				String sendingOption = request.getParameter("sendingOption");
				
				try 
				{
					if(empId!=null && !empId.equals(""))
					{
						int empIdInt = Integer.parseInt(empId);
						
						if(permission!=null && !permission.equals(""))
						   emp = new Employee(empIdInt,givenName,familyName,username, password, permLevelInt,v);
						
						else
							emp = new Employee(empIdInt,givenName,familyName,username, password, 0, 'a');
					}
					else
					{
						
						if(permission!=null && !permission.equals(""))
						{
							
							   emp = new Employee(-1,givenName,familyName,username, password, permLevelInt,v);
						}
						else
						{
							   emp = new Employee(-1,givenName,familyName,username, password, 0, 'a');
						}
					}
					
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
						Gmail smtpMailSender = null;
						
						try 
						{
							// Send an email with the account's details and confirm the user with the results
							if(sendingOption!=null) 
							{
								smtpMailSender = new Gmail();
								smtpMailSender.postMail(emailSendToList, "Deerfoot Account Details", password);
								System.out.println("Sucessfully Sent mail to All Users test");
								
								//Confirm that the user was added
								response.sendRedirect("wa_user/newUser.jsp?message=sent&familyName=" + familyName +"&givenName=" + givenName
										+ "&username=" + username +  "&email=" + email + "&password=" + password
										+ "&dob=" + dob + "&empId=" + empId + "&status=" + emp.getActive() + "&sendingOption=" + sendingOption);
							}
							//Confirm that the user has been added but an email has not been sent.
							else
							{
								//Confirm that the user was added
								response.sendRedirect("wa_user/newUser.jsp?message=true&familyName=" + familyName +"&givenName=" + givenName
										+ "&username=" + username +  "&email=" + email
										+ "&dob=" + dob + "&empId=" + empId + "&status=" + emp.getActive() + "v=" +v + "permLevel" + permLevel);
							}	
						} 
						catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
							// If the ports are blocked, inform the user and return all their details back
							response.sendRedirect("wa_user/resetPasswordStepTwo.jsp?message=sentError&familyName=" + familyName +"&givenName=" + givenName
										+ "&username=" + username +  "&email=" + email
										+ "&dob=" + dob + "&empId=" + empId + "&status=" + emp.getActive() + "v=" +v + "permLevel" + permLevel);
							
						}
					}
				}
				catch (DBException e) 
				{
					e.printStackTrace();
					
					
					//Even if the user is not created, return the values to the form
					response.sendRedirect("wa_user/newUser.jsp?message=false&familyName=" + familyName +"&givenName=" + givenName
							+ "&username=" + username +  "&email=" + email
							+ "&dob=" + dob + "&empId=" + empId + "&status=" + emp.getActive() + "v=" +v + "permLevel" + permLevel + "user=" + user.getEmpID());
				}	
				
				catch (DBDownException e) 
				{
					e.printStackTrace();
					
					//Even if the user is not created, return the values to the form
					response.sendRedirect("wa_user/newUser.jsp?message=false&familyName=" + familyName +"&givenName=" + givenName
							+ "&username=" + username +  "&email=" + email
							+ "&dob=" + dob + "&empId=" + empId + "&status=" + emp.getActive() + "v=" +v + "permLevel" + permLevel);
				}	
				catch (InvalidPermissionException  e)
				{
					e.printStackTrace();
					
					
					//Even if the user is not created, return the values to the form
					response.sendRedirect("wa_user/newUser.jsp?message=perm&familyName=" + familyName +"&givenName=" + givenName
							+ "&username=" + username +  "&email=" + email + "&password=" + password
							+ "&dob=" + dob + "&empId=" + empId + "&status=" + emp.getActive() + "&sendingOption=" + sendingOption);
				}
				
				catch (PermissionViolationException e) 
				{
					e.printStackTrace();
					
					//Even if the user is not created, return the values to the form
					response.sendRedirect("wa_user/newUser.jsp?message=perm&familyName=" + familyName +"&givenName=" + givenName
							+ "&username=" + username +  "&email=" + email + "&password=" + password
							+ "&dob=" + dob + "&empId=" + empId + "&status=" + emp.getActive() + "&sendingOption=" + sendingOption);
				
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
