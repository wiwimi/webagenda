package uiConnection.backup;

import java.io.File;
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
import application.Backup;


/**
 * This servlet is used to perform a system backup to a specified location on the server
 * @author Noorin Hasan
 */
@WebServlet(name="ExportBackup", urlPatterns={"/ExportBackup"})
public class ExportBackup extends HttpServlet {
	private static final long serialVersionUID = 4L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		        
		        //Backup DB.
		        try
		        {
		        	Backup.backupDB();
		        	
		        	//Confirm that the backup was done
					response.sendRedirect("wa_settings/backup.jsp?message=true");
		        }
		        catch(Exception e)
		        {
		        	//Confirm that the backup was done
					response.sendRedirect("wa_settings/backup.jsp?message=false");
		        	
		        }
		        
				
				
		   
		    }
    public ExportBackup() {
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
