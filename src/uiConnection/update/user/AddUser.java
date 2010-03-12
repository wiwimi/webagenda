package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.EmployeeBroker;
import persistence.PositionBroker;
import exception.DBDownException;
import exception.DBException;

import business.Employee;
import business.Skill;
import business.schedule.Position;

/**
 * Servlet implementation class addUser
 */
public class AddUser extends HttpServlet {
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
		        String familyName = request.getParameter("familyName");
				String givenName = request.getParameter("givenName");
				String[] pos_skills = request.getParameterValues("skill");
				String status =  request.getParameter("status");
				String[] pos = request.getParameterValues("pos");
				String email=request.getParameter("email");
				String username = request.getParameter("username");
				String[] permLevel = request.getParameterValues("permLevel");
				
				Employee emp = new Employee();
				if (status.equals("enabled"))
					emp.setActive(true);
				else
					emp.setActive(false);
				
				emp.setFamilyName(familyName);
				emp.setGivenName(givenName);
				emp.setUsername(username);
				emp.setEmail(email);
				emp.setPrefLocation(preferredLocation);
				
				
				
				if (pos_skills != null) 
				{
					  Skill[] skills= new Skill[pos_skills.length];
				      for (int i = 0; i < pos_skills.length; i++) 
				      {
				         Skill tempSkill = new Skill(pos_skills[i]);
				         skills[i] = (tempSkill);
				      }
				   } 
				boolean success;
				EmployeeBroker broker = null;
				
				try {
					    
						broker = EmployeeBroker.getBroker();
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
    public AddUser() {
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
