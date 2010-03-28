package uiConnection.update.position;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistence.PositionBroker;
import business.schedule.Position;
import business.Employee;
import business.Skill;
import exception.DBDownException;
import exception.DBException;

/**
 * Servlet implementation class AddPosition
 */
public class AddPosition extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
      @SuppressWarnings("null")
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		      
		        PrintWriter out = response.getWriter();
		        
		        //Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		        
		        String posName = request.getParameter("posName");
				String posDesc = request.getParameter("posDesc");
				String[] posSkills = request.getParameterValues("skillCheck");
				
				Skill[] skills = null;
				   if (posSkills != null) 
				   {
					   skills= new Skill[posSkills.length];
				      for (int i = 0; i < posSkills.length; i++) 
				      {
				         Skill tempSkill = new Skill(posSkills[i]);
				         skills[i] = (tempSkill);
				       }
				   } 
				boolean success=false;
				PositionBroker broker = null;
				
				try {
					    
						broker = PositionBroker.getBroker();
						broker.initConnectionThread();
						Employee user = (Employee)session.getAttribute("currentEmployee");
						Position pos = new Position(posName, posDesc, skills);
						
						success = broker.create(pos, user);
						
					if (success)
					{
						//Confirm that the user was added
						response.sendRedirect("wa_user/newPosition.jsp?message=true&posName=" + posName + "&posDesc" + posDesc);
						
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the position
					response.sendRedirect("wa_user/newPosition.jsp?message=false&posName=" + posName + "&posDesc" + posDesc);
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the position
					response.sendRedirect("wa_user/newPosition.jsp?message=false&posName=" + posName + "&posDesc" + posDesc);
				}
				catch(Exception e)
				{
					// Failed to add the position
					response.sendRedirect("wa_user/newPosition.jsp?message=false&posName=" + posName + "&posDesc" + posDesc);
				}
				finally
				{
					out.close();
					broker.stopConnectionThread();
				}
		   }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPosition() {
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
