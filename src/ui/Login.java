package ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import exception.InvalidLoginException;
import business.Employee;
import persistence.EmployeeBroker;

/**
 * Servlet implementation class Login
 */
@WebServlet(name="Login", urlPatterns={"/login"})
public class Login extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
        response.setContentType("text/html;charset=UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        HttpSession loginSession = request.getSession();

        PrintWriter out = response.getWriter();
        try 
        {
        	EmployeeBroker empBroker = EmployeeBroker.getBroker();
        	Employee loggedIn = empBroker.tryLogin(username, password);
        	
        	System.out.println("Login Worked!");
        	
            //Login is successful
            loginSession.setAttribute("username", username);
            	
            response.sendRedirect("wa_dashboard/dashboard.jsp");
        } 
        catch (InvalidLoginException e)
		{
        	//login was unsuccessful
        	System.out.println("Login Didn't work!");
        	response.sendRedirect(("wa_login/login.jsp?LoginAttempt=1"));
			e.printStackTrace();
		} 
        catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        finally
        {
            out.close();
        }
	}

}
