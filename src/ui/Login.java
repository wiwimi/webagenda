package ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import exception.DBDownException;
import exception.DBException;
import exception.InvalidLoginException;
import exception.PermissionViolationException;

import business.Employee;
import persistence.EmployeeBroker;

/**
 * @author mark
 * Servlet implementation class Login
 */
@WebServlet(name="Login", urlPatterns={"/login"})
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private static final String TIMER_KEY = "session.timer";

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() 
    {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    } 

	/**
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        response.setContentType("text/html;charset=UTF-8");
        
        //Get the username and password from the submitted login form and store them as string's
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        //Create or get the session object from the HTTPSession object
        HttpSession loginSession = request.getSession();
        
        PrintWriter out = response.getWriter();
        try 
        {
        	EmployeeBroker empBroker = EmployeeBroker.getBroker();
        	Employee loggedIn = empBroker.tryLogin(username, password);
        	
        	//Set a session to be equal to the employee object returned from the broker
        	//This is so that we can just pass it around the interface for information
        	loginSession.setAttribute("currentEmployee", loggedIn);
        	
        	System.out.println("Login Worked!");
        	
            //Login is successful
        	//create a session variable for just the currently logged in user's username
            loginSession.setAttribute("username", username);
            
            
            //Noorin - extend the session timeout 
            HttpSession session = request.getSession(); 
       
            
            //redirect the user to the dashbaord
            response.sendRedirect("wa_dashboard/dashboard.jsp");
        } 
        catch (InvalidLoginException e)
		{
        	//login was unsuccessful
        	System.out.println("Login Didn't work!");
        	//Because the login was unsucsseful redirect the user back to the login.jsp with an error message
        	response.sendRedirect(("wa_login/login.jsp?LoginAttempt=1"));
			e.printStackTrace();
		} 
        catch (DBException e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
        catch (DBDownException e)
		{
		// TODO Auto-generated catch block
		e.printStackTrace();
		} catch (PermissionViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        finally
	     {
	         out.close();
	     }
	}

}
