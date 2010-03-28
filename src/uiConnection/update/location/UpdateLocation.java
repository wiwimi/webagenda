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
import business.Employee;
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
				Employee user = null;
				boolean success=false;
				LocationBroker broker = null;
				Location oldLoc=null, newLoc=null;
				
				try {
					    
						broker = LocationBroker.getBroker();
						broker.initConnectionThread();
						user = (Employee)session.getAttribute("currentEmployee");
						newLoc = new Location(locName, locDesc);
						oldLoc = (Location) session.getAttribute("oldLoc");
						//FIXME pass in the logged in employee object (from session) instead of null.
						Location[] results = broker.get(oldLoc, user);
						oldLoc = new Location(results[0].getName(), results[0].getDesc());
						
						if(results!=null)
						//FIXME pass in the logged in employee object (from session) instead of null.
						success = broker.update(oldLoc, newLoc, user);
						
						
					if (success)
					{
						//Confirm that the location was updated
						response.sendRedirect("wa_location/updateLocation.jsp?update=true&locName=" + locName +"&locDesc=" + locDesc );
					}
				}
				catch (DBException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the location
					out.println("DBEXCE");
					response.sendRedirect("wa_location/updateLocation.jsp?update=false&locName=" + locName +"&locDesc=" + locDesc );
					
					
					
				} catch (DBDownException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					out.println("Down");
					// Failed to update the location
					response.sendRedirect("wa_location/updateLocation.jsp?update=false&locName=" + locName +"&locDesc=" + locDesc );
					
					
				}
				catch(Exception e)
				{
					e.printStackTrace();
					out.println("Exce " + "*" + oldLoc.getName() + "*" +  oldLoc.getDesc() + "*" +newLoc.getName() + "*" +newLoc.getDesc()
							+ "*"+ user.getUsername());
					
					// Failed to update the location
					response.sendRedirect("wa_location/updateLocation.jsp?update=false&locName=" + locName +"&locDesc=" + locDesc );
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
