package update;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		        
		        String locName = request.getParameter("locName");
				String desc = request.getParameter("desc");	
				LocationBroker broker = LocationBroker.getBroker();
				Location loc = new Location(locName, desc);
				System.out.println("dnt");
				boolean success;
				try {
					success = broker.create(loc);
					if (success==true)
					{
						System.out.println(success);
					}
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
	}

}
