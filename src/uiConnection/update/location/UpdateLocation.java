package uiConnection.update.location;

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
import persistence.LocationBroker;
import business.schedule.Location;

/**
 * Servlet implementation class UpdateLocation
 */
@WebServlet(name="UpdateLocation", urlPatterns={"/UpdateLocation"})
public class UpdateLocation extends HttpServlet {
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
				
				boolean success=false;
				LocationBroker broker = null;
				Location oldLoc=null, newLoc=null;
				
				try {
					    
						broker = LocationBroker.getBroker();
						broker.initConnectionThread();
						
						newLoc = new Location(locName, locDesc);
						oldLoc = (Location) session.getAttribute("oldLoc");
						Location[] results = broker.get(oldLoc);
						oldLoc = new Location(results[0].getName(), results[0].getDesc());
						
						if(results!=null)
						success = broker.update(oldLoc, newLoc);
						
					if (success)
					{
						//Confirm that the location was updated
						response.sendRedirect("wa_location/updateLocation.jsp?update=true");
					}
				}
				catch (DBException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the location
					response.sendRedirect("wa_location/updateLocation.jsp?update=false");
					
					
					
				} catch (DBDownException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the location
					response.sendRedirect("wa_location/updateLocation.jsp?update=false");
					
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
					// Failed to update the location
					response.sendRedirect("wa_location/updateLocation.jsp?update=false");
				}
				finally
				{
					out.close();
					broker.stopConnectionThread();
				}
		   }
    public UpdateLocation() {
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
