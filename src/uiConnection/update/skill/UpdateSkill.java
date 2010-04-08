package uiConnection.update.skill;

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
import persistence.SkillBroker;
import business.Employee;
import business.Skill;

/**
 * Servlet implementation class UpdateLocation
 * @author Noorin Hasan
 */
@WebServlet(name="UpdateSkill", urlPatterns={"/UpdateSkill"})
public class UpdateSkill extends HttpServlet {
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
		        String skillName = request.getParameter("skillName");
				String skillDesc = request.getParameter("skillDesc");	
				
				boolean success=false;
				SkillBroker broker = null;
			    Skill oldSkill=null, newSkill=null;
				
				try {
					    broker = SkillBroker.getBroker();
						broker.initConnectionThread();
						
						Employee user = (Employee)session.getAttribute("currentEmployee");
						
						newSkill = new Skill(skillName, skillDesc);
						oldSkill = (Skill) session.getAttribute("oldSkill");
						Skill[] results = broker.get(oldSkill, user);
						
						oldSkill = new Skill(results[0].getName(), results[0].getDesc());
					
						if(results!=null)
						success = broker.update(oldSkill, newSkill, user);
						
					if (success)
					{
						//Confirm that the location was updated
						response.sendRedirect("wa_user/updateSkill.jsp?update=true&skillName=" + skillName+ "&skillDesc=" + skillDesc);
					}
				}
				catch (DBException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the location
					response.sendRedirect("wa_user/updateSkill.jsp?update=false&skillName=" + skillName + "&skillDesc=" + skillDesc);
					
				} catch (DBDownException e) {
					
					// TODO Auto-generated catch block
					e.printStackTrace();
					
					// Failed to update the location
					response.sendRedirect("wa_user/updateSkill.jsp?update=false&skillName=" + skillName + "&skillDesc=" + skillDesc);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					
					// Failed to update the location
					response.sendRedirect("wa_user/updateSkill.jsp?update=false&skillName=" + skillName + "&skillDesc=" + skillDesc);
				}
				finally
				{
					out.close();
					broker.stopConnectionThread();
				}
		   }
    public UpdateSkill() {
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
