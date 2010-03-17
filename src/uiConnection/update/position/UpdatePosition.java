package uiConnection.update.position;

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
import persistence.PositionBroker;
import business.Skill;
import business.schedule.Position;;

/**
 * Servlet implementation class UpdateLocation
 */
@WebServlet(name="UpdatePosition", urlPatterns={"/UpdatePosition"})
public class UpdatePosition extends HttpServlet {
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
		        String posName = request.getParameter("posName");
				String posDesc = request.getParameter("posDesc");	
				
				boolean success=false;
				PositionBroker broker = null;
				Position oldPos=null, newPos=null;
				//String[] pos_skills = request.getParameterValues("skillSetForm.skill");
				Skill[] skills = null;
				
			
				try {
						broker = PositionBroker.getBroker();
						broker.initConnectionThread();
						
						newPos = new Position(posName, posDesc, skills);
						oldPos = (Position) session.getAttribute("oldPos");
						
						Position[] results = broker.get(oldPos);
						
						success = broker.update(results[0], newPos);
						
						
					if (success)
					{
						//Confirm that the location was updated
						response.sendRedirect("wa_user/updatePosition.jsp?update=true");
					}
				}
				catch (DBException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the location
					//response.sendRedirect("wa_user/updatePosition.jsp?update=false");
					
				} catch (DBDownException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the location
					//response.sendRedirect("wa_user/updatePosition.jsp?update=false");
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
					// Failed to update the location
					//response.sendRedirect("wa_user/updatePosition.jsp?update=false");
				}
				finally
				{
					out.close();
					broker.stopConnectionThread();
				}
		   }
    public UpdatePosition() {
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
