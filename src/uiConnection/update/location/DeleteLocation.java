package uiConnection.update.location;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import persistence.LocationBroker;
import business.schedule.Location;
import exception.DBDownException;
import exception.DBException;

/**
 * Servlet implementation class DeleteLocation
 */
@WebServlet(name="DeleteLocation", urlPatterns={"/DeleteLocation"})
public class DeleteLocation extends HttpServlet {
	private static final long serialVersionUID = 3L;

	/**
     * @see HttpServlet#HttpServlet()
     */
      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
    	  
		        response.setContentType("text/html;charset=UTF-8");
				response.sendRedirect("wa_location/updateLocation.jsp?message=false");
		      //Create or get the session object from the HTTPSession object
		       // HttpSession session = request.getSession();
		       
		        PrintWriter out = response.getWriter();
		        String locName = request.getParameter("locName");
		        out.println("Hellow");
		        out.println(locName);
		        
		        boolean success;
				
				try {
					    LocationBroker broker = LocationBroker.getBroker();
						broker.initConnectionThread();
						
						Location loc = new Location(locName);
						success = broker.delete(loc);
						
					if (success)
					{
						//Confirm that the user was deleted
						response.sendRedirect("wa_location/updateLocation.jsp?message=true");
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to delete the location
					response.sendRedirect("wa_location/updateLocation.jsp?message=false");
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the location
					response.sendRedirect("wa_location/updateLocation.jsp?message=false");
				}
				catch(Exception e)
				{
					// Failed to add the location
					response.sendRedirect("wa_location/updateLocation.jsp?message=false");
				}
				finally
				{
					out.close();
				}
		   }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteLocation() {
    	
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
		

		
		
		//response.sendRedirect("wa_location/newLocation.jsp");
		
	}

}
