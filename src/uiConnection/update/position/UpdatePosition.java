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
import exception.DBNoChangeException;
import persistence.PositionBroker;
import business.Employee;
import business.Skill;
import business.schedule.Position;;

/**
 * This servlet is used to update a position that is already in the system using the 
 * position management screen
 * @author Noorin Hasan
 * edited by:mark
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
				Position[]  results = null;
			
				try {
						broker = PositionBroker.getBroker();
						broker.initConnectionThread();
						
						Employee user = (Employee)session.getAttribute("currentEmployee");
						newPos = new Position(posName, posDesc, skills);
						results = broker.get(oldPos, user);
						oldPos = (Position) session.getAttribute("oldPos");
						oldPos = new Position (results[0].getName() , results[0].getDescription(), results[0].getPos_skills());
						
						if(results!=null)
							success = broker.update(oldPos, newPos, user);
					if (success)
					{
						//Confirm that the position was updated
						response.sendRedirect("wa_user/updatePosition.jsp?update=true&posName=" + posName+ "&posDesc=" + posDesc);
					}
				}
				catch (DBNoChangeException dbncE)
				{
					response.sendRedirect("wa_user/updatePosition.jsp?update=noChange&posName=" + posName + "&posDesc=" + posDesc);

				}
				catch (DBException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the position
					response.sendRedirect("wa_user/updatePosition.jsp?update=false&posName=" + posName + "&posDesc=" + posDesc);
					
				} catch (DBDownException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the position
					//response.sendRedirect("wa_user/updatePosition.jsp?update=false&posName=" + posName + "&posDesc=" + posDesc);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
					
					// Failed to update the position
					response.sendRedirect("wa_user/updatePosition.jsp?update=false&posName=" + posName + "&posDesc=" + posDesc);
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
