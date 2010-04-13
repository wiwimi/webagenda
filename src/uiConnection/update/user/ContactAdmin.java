package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;

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
 * Servlet implementation for contact Admin
 * Allows users to send e-mails to any of the employees who have e-mails saved in the database
 * SMTP ports have to be enabled for this to work
 * @author Noorin Hasan
 */
@WebServlet(name="ContactAdmin", urlPatterns={"/ContactAdmin"})
public class ContactAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		        EmployeeBroker empBroker;
		        empBroker = EmployeeBroker.getBroker();
				empBroker.initConnectionThread();
		        
		       //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		        PrintWriter out = response.getWriter();
		        
		        Employee sender = (Employee)session.getAttribute("currentEmployee");
		        String receipt = request.getParameter("receipt");
		        String message = request.getParameter("message");
		        
		        message = "From " + sender + " " + message; // Adding the sender's name to the message.
		        String subject = request.getParameter("subject");
		        String[] emailSendToList = { receipt };
				
		        Gmail smtpMailSender = null;
						
					try 
					{
						// Send an email to the Admin
					    smtpMailSender = new Gmail();
						smtpMailSender.postMail(emailSendToList, subject, message);
						
						//Confirm that the e-mail was sent
						response.sendRedirect("wa_help/contactAdmin.jsp?message=true");
					} 
					catch (MessagingException e) {
						// TODO Auto-generated catch block
						
						//e.printStackTrace();
						response.sendRedirect("wa_help/contactAdmin.jsp?message=false");
					}
				empBroker.stopConnectionThread();
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactAdmin() {
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
