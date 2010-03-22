package uiConnection.update.skill;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import persistence.SkillBroker;
import business.Employee;
import business.Skill;
import business.schedule.Location;
import exception.DBDownException;
import exception.DBException;

/**
 * Servlet implementation class DeleteSkill
 */
@WebServlet(name="DeleteSkill", urlPatterns={"/DeleteSkill"})
public class DeleteSkill extends HttpServlet {
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
		        String skillName = request.getParameter("skillName");
		        String skillDesc = request.getParameter("skillDesc");
		        
		        Skill delSkill =null;
		        
		        boolean success=false;
				
				try {
					    SkillBroker broker = SkillBroker.getBroker();
						broker.initConnectionThread();
						
						Employee user = (Employee)session.getAttribute("currentEmployee");
						
						delSkill = new Skill(skillName, skillDesc);
						Skill[] results = broker.get(delSkill, user);
						
						if(results!=null)
						success = broker.delete(results[0], user);
						
					if (success)
					{
						//Confirm that the user was deleted
						response.sendRedirect("wa_user/skillSearchResults.jsp?delete=true&skillName=&skillDesc=");
					}
				}
				catch (DBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to delete the location
					response.sendRedirect("wa_user/skillSearchResults.jsp?delete=false&skillName=&skillDesc=");
					
				} catch (DBDownException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// Failed to add the location
					response.sendRedirect("wa_user/skillSearchResults.jsp?delete=false&skillName=&skillDesc=");
				}
				catch(Exception e)
				{
					// Failed to add the location
					response.sendRedirect("wa_user/skillSearchResults.jsp?message=false&skillName=&skillDesc=");
				}
				finally
				{
					out.close();
				}
		   }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteSkill() {
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
