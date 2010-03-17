package uiConnection.update.location;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

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
		        PrintWriter out = response.getWriter();
		        String locName  = request.getParameter("locName");
		        String locDesc  = request.getParameter("locDesc");
		        Location delLoc =null;
		        
		        boolean success=false;
				
				try {
					    LocationBroker broker = LocationBroker.getBroker();
						broker.initConnectionThread();
						
						delLoc = new Location(locName, locDesc);
						Location[] results = broker.get(delLoc);
						success = broker.delete(results[0]);
						
					if (success)
					{
						//Confirm that the user was deleted
						response.sendRedirect("wa_location/searchResults.jsp?delete=true");
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
					
					// Failed to delete the location
					response.sendRedirect("wa_location/searchResults.jsp?delete=false");
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
					// Failed to delete the location
					response.sendRedirect("wa_location/searchResults.jsp?delete=false");
				}
				catch(Exception e)
				{
					// Failed to add the location
					response.sendRedirect("wa_location/searchResults.jsp?delete=false");
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
