package uiConnection.update.location;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exception.DBDownException;
import exception.DBException;
import persistence.LocationBroker;
import business.schedule.Location;

/**
 * Servlet implementation class addLocation
 */
public class AddLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
				String desc = request.getParameter("desc");	
				
				
				boolean success;
				
				try {
						LocationBroker broker = LocationBroker.getBroker();
						Location loc = new Location(locName, desc);
						System.out.println("dnt");
						success = broker.create(loc);
						if (success==true)
						{
							System.out.println(success);
						}
						
						 //redirect the user to the location page
			            response.sendRedirect("wa_location/newLocation.jsp");
			            
			            
				} catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		
		response.sendRedirect("wa_location/newLocation.jsp");
	}

}
