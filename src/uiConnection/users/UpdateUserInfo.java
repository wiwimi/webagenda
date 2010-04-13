package uiConnection.users;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This servlet will update the user information from the requested form in the update user screen of Web Agenda.
 * @author Mark Hazlett
 */
public class UpdateUserInfo extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserInfo() {
        super();
        // TODO Auto-generated constructor stub
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
        
        //Create or get the session object from the HTTPSession object
        HttpSession loginSession = request.getSession();
        
        String givenName = (String)request.getAttribute("givenName");
        String familyName = (String)request.getAttribute("familyName");
        String dob = (String)request.getAttribute("dob");
        String email = (String)request.getAttribute("confirmEmail");
        String status = (String)request.getAttribute("status");
        String empId = (String)request.getAttribute("empId");
        String prefPosition = (String)request.getAttribute("prefPositionBox");
        
        try
        {
        	
        }
        finally
        {
        	response.sendRedirect("updateUser.jsp?search=");
        }
	}

}
