package uiConnection.update.position;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import persistence.PositionBroker;
import business.Employee;
import business.schedule.Location;
import business.schedule.Position;
import exception.DBDownException;
import exception.DBException;

/**
 * Servlet implementation class DeletePosition
 * @author Noorin Hasan
 */
public class DeletePosition extends HttpServlet {
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
		        String posName = request.getParameter("posName");
		        boolean success;
		        Position delPos = null;
				
				try {
					    PositionBroker broker = PositionBroker.getBroker();
						broker.initConnectionThread();
						
						Employee user = (Employee)session.getAttribute("currentEmployee");
						
						delPos = new Position(posName);
						Position[] results = broker.get(delPos, user);
						success = broker.delete(results[0], user);
						
					if (success)
					{
						//Confirm that the user was deleted
						response.sendRedirect("wa_user/posSearchResults.jsp?delete=true&posName=&posDesc=");
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to delete the location
					response.sendRedirect("wa_user/posSearchResults.jsp?delete=false&posName=&posDesc=");
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the location
					response.sendRedirect("wa_user/posSearchResults.jsp?delete=false&posName=&posDesc=");
				}
				catch(Exception e)
				{
					// Failed to add the location
					response.sendRedirect("wa_user/posSearchResults.jsp?delete=false&posName=&posDesc=");
				}
				finally
				{
					out.close();
				}
		   }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePosition() {
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
