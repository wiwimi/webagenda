package uiConnection.update.position;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.PositionBroker;
import business.schedule.Position;
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
		      
		        //Create or get the session object from the HTTPSession object
		        //HttpSession session = request.getSession();
		        PrintWriter out = response.getWriter();
		        String posName = request.getParameter("posName");
				String desc = request.getParameter("desc");
				String[] pos_skills = request.getParameterValues("skill");
				Skill[] skills = null;
				   if (pos_skills != null) 
				   {
					   skills= new Skill[pos_skills.length];
				      for (int i = 0; i < pos_skills.length; i++) 
				      {
				         out.println ("<b>"+pos_skills[i]+"<b>");
				         Skill tempSkill = new Skill(pos_skills[i]);
				         skills[i] = (tempSkill);
				         
				      }
				   } 
				       
				  
				boolean success;
				PositionBroker broker = null;
				
				try {
					    
						broker = PositionBroker.getBroker();
						broker.initConnectionThread();
						
						Position pos = new Position(posName, desc, skills);
						success = broker.create(pos);
						
					if (success)
					{
						//Confirm that the user was added
						response.sendRedirect("wa_user/newPosition.jsp?message=true");
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the position
					response.sendRedirect("wa_user/newPosition.jsp?message=false");
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the position
					response.sendRedirect("wa_user/newPosition.jsp?message=false");
				}
				catch(Exception e)
				{
					// Failed to add the position
					response.sendRedirect("wa_user/newPosition.jsp?message=false");
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
