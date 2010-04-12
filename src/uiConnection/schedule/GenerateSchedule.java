package uiConnection.schedule;
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
@WebServlet(name="GenerateSchedule", urlPatterns={"/GenerateSchedule"})
public class GenerateSchedule extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
     * @see HttpServlet#HttpServlet()
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException 
		    {
		        response.setContentType("text/html;charset=UTF-8");
		        ;
		        EmployeeBroker empBroker;
		        java.sql.Date  sqlStartDate;
		        empBroker = EmployeeBroker.getBroker();
				empBroker.initConnectionThread();
				
				ScheduleTemplateBroker stb = ScheduleTemplateBroker.getBroker();
				ScheduleBroker sb = ScheduleBroker.getBroker();
				
				//Create or get the session object from the HTTPSession object
		        HttpSession session = request.getSession();
		        
		        // Values captured from the form
		        String startDate = request.getParameter("schedStart");
		        
		        String loc = request.getParameter("loc");
		        String template = request.getParameter("template");
		   
		        Employee user = (Employee)session.getAttribute("currentEmployee");
		        
		        // Converting the string dates captured from the form to appropriate sql dates
		        
		        String revStartDate = startDate.substring(6, startDate.length()) + '-' + startDate.substring(0, 2)
				+'-' + startDate.substring(3, 5);		// Reversing the date so that it matches the argument required for sql.Date
				sqlStartDate = java.sql.Date.valueOf(revStartDate);
				
				// Adding a week to the start date
			   
				// Creating the location to be used in the schedule
				
				Location location = new Location (loc);
				
				try
				{
					//Attempt to create a schedule from the test template.
					ScheduleTemplate get = new ScheduleTemplate(1,-1,template);
				
					ScheduleTemplate fromDB = stb.get(get, user)[0];
					PrintWriter out = response.getWriter();
					
					ArrayList<Shift> partialMatches = new ArrayList<Shift>();
					
					//---------- Generating Schedule ----------
					Schedule genSched = ScheduleGenerator.generateSchedule(fromDB, sqlStartDate, location, partialMatches, user);
					
					//---------- Send the generated proposal back to the user by displaying it in jsp ----------");
					
					ArrayList<Shift> shiftList = genSched.getShifts().toArrayList();
					
					//Setting the proposed schedule in the session
					
					if(genSched!=null && shiftList.size() !=0)
					{
						out.println(shiftList.size());
						//session.setAttribute("genSched", genSched);
						//response.sendRedirect("wa_schedule/displayScheduleFromTemplate.jsp?message=true");
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
				}
			}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenerateSchedule() {
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
