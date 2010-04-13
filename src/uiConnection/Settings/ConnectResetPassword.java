package uiConnection.Settings;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This method will check to see if the passwords match when a change password request has been submitted
 */
public class ConnectResetPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectResetPassword() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String oldPassword = (String) request.getAttribute("oldPass");
		String newPass1 = (String) request.getAttribute("newPass1");
		String newPass2 = (String) request.getAttribute("newPass2");
		
		if(!newPass1.equals(newPass2))
		{
			//the passwords do not match so redirect them back to the reset password screen with an error
			response.sendRedirect("wa_settings/Password.jsp?error=The passwords entered do not match");
		}
		else
		{
			//The passwords do match so proceed with action to reset password
		}
	}

}
