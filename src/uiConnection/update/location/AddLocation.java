package uiConnection.update.location;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.DBDownException;
import exception.DBException;
import persistence.LocationBroker;
import business.Employee;
import business.schedule.Location;

/**
 * This servlet is used to add a location to the database using the location management screen
 * @author Noorin Hasan
 * edited by:Mark
 */
@WebServlet(name="AddLocation", urlPatterns={"/AddLocation"})
public class AddLocation extends HttpServlet {
	private static final long serialVersionUID = 2L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		      
		        //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		        PrintWriter out = response.getWriter();
		        String locName = request.getParameter("locName");
				String locDesc = request.getParameter("locDesc");	
				boolean success;
				LocationBroker broker = null;
				
				try {
					    
						broker = LocationBroker.getBroker();
						broker.initConnectionThread();
						
						Location loc = new Location(locName, locDesc);
						Employee user = (Employee)session.getAttribute("currentEmployee");
						
						success = broker.create(loc, user);
						
					if (success)
					{
						//Confirm that the location was added
						response.sendRedirect("wa_location/newLocation.jsp?message=true&locName=" + locName+ "&locDesc=" + locDesc);
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the location
					response.sendRedirect("wa_location/newLocation.jsp?message=false&locName=" + locName+ "&locDesc=" + locDesc);
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the location
					response.sendRedirect("wa_location/newLocation.jsp?message=false&locName=locName=" + locName+ "&locDesc=" + locDesc);
				}
				catch(Exception e)
				{
					// Failed to add the location
					response.sendRedirect("wa_location/newLocation.jsp?message=false&locName=locName=" + locName+ "&locDesc=" + locDesc);
				}
				finally
				{
					out.close();
					broker.stopConnectionThread();
				}
		   }
    public AddLocation() {
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
