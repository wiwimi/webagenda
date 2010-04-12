package schedule;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Date;
import java.util.ArrayList;

import exception.DBDownException;
import exception.DBException;
import exception.InvalidPermissionException;

import application.ScheduleGenerator;
import business.Employee;
import business.schedule.Location;
import business.schedule.Schedule;
import business.schedule.ScheduleTemplate;
import business.schedule.Shift;
import persistence.EmployeeBroker;
import persistence.ScheduleBroker;
import persistence.ScheduleTemplateBroker;

/**
 * Servlet implementation class GenerateSchedule
 * @author Noorin Hasan
 */
@WebServlet(name="ActivateSchedule", urlPatterns={"/ActivateSchedule"})
public class ActivateSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		        PrintWriter out= response.getWriter();
		        
		    	//Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		        Employee user = (Employee)session.getAttribute("currentEmployee");
		        ScheduleBroker sb = ScheduleBroker.getBroker();
		        Schedule genSched = (Schedule)session.getAttribute("genSched");
				try
				{
					//Save the generated schedule to the database.
					sb.create(genSched, user);
					
					
					//Setting the proposed schedule in the session
					
					if(genSched!=null)
					{
						session.setAttribute("genSched", genSched);
						response.sendRedirect("wa_schedule/createScheduleFromTemplate.jsp?message=activated");
					}
					else
					{
						response.sendRedirect("wa_schedule/displayScheduleFromTemplate.jsp?message=false");
					}
				}
					
				catch (DBException e)
				{
					e.printStackTrace();
				}
				catch (DBDownException e)
				{
					e.printStackTrace();
				} catch (InvalidPermissionException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					response.sendRedirect("wa_schedule/displayScheduleFromTemplate.jsp?message=perm");
				}
			}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivateSchedule() {
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
