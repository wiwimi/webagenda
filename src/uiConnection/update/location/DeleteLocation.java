package uiConnection.update.location;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistence.LocationBroker;
import business.Employee;
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
		        PrintWriter out = response.getWriter();
		        
		        //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		        
		        String locName  = request.getParameter("locName");
		        String locDesc  = request.getParameter("locDesc");
		        Location delLoc =null;
		        
		        boolean success=false;
				
				try {
					    LocationBroker broker = LocationBroker.getBroker();
						broker.initConnectionThread();
						
						Employee user = (Employee)session.getAttribute("currentEmployee");
						
						delLoc = new Location(locName, locDesc);
						Location[] results = broker.get(delLoc, user);
						success = broker.delete(results[0], user);
						
					if (success)
					{
						//Confirm that the location was deleted
						response.sendRedirect("wa_location/searchResults.jsp?delete=true&locName= ");
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
					
					// Failed to delete the location
					response.sendRedirect("wa_location/searchResults.jsp?delete=false&locName= ");
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
					
					// Failed to delete the location
					response.sendRedirect("wa_location/searchResults.jsp?delete=false&locName= ");
				}
				catch(Exception e)
				{
					// Failed to add the location
					response.sendRedirect("wa_location/searchResults.jsp?delete=false&locName= ");
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
		
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
