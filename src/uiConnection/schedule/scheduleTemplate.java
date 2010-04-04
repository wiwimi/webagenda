package uiConnection.schedule;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utilities.DoubleLinkedList;

import business.schedule.ScheduleTemplate;
import business.schedule.ShiftPosition;
import business.schedule.ShiftTemplate;

/**
 * @author Mark Hazlett
 * Servlet implementation class scheduleTemplate
 */
@WebServlet(name="scheduleTemplate", urlPatterns={"/wa_schedule/scheduleTemplate"})
public class scheduleTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public scheduleTemplate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			HttpSession session = request.getSession();
			
			ScheduleTemplate template = (ScheduleTemplate)session.getAttribute("schedule");
			
			if(request.getParameter("action").equals("change"))
			{
				/*
				 * Changes a current shift template in the schedule template list
				 * This needs to report back to the createShiftTemplate screen to fill in all the fields necessary in order to change it
				 * The screen should report back with a changeSuccess action in order to completely modify successfully
				 */
			}
			else if(request.getParameter("action").equals("delete"))
			{
				//removes the shift template from the list
				template.getShiftTemplates().remove(Integer.parseInt(request.getParameter("number")));
			}
			else
			{
				/*
				 * There was no parameters sent meaning the user wants to add a new shift template to the schedule template
				 * 
				 * */
				
				String startTime = request.getParameter("startTime");
				String endTime = request.getParameter("endTime");
				String dayOfWeek = request.getParameter("dayOfWeek");
				
				int counter = Integer.parseInt(request.getParameter("counter"));
				
				DoubleLinkedList<ShiftPosition> listOfPositions = new DoubleLinkedList<ShiftPosition>();
				
				for(int index = 0; index < counter; index++)
				{
					
					ShiftPosition position = new ShiftPosition();
					position.setPosName(request.getParameter("positionType" + index));
					position.setPosCount(Integer.parseInt(request.getParameter("positionNumber" + index)));
					
					listOfPositions.add(position);
				}
				
				DateFormat sdf = new SimpleDateFormat("hh:mm");
	
				try
				{
					ShiftTemplate shiftTemplate = new ShiftTemplate();
					
					Time startTimeParsed = (Time) sdf.parse(startTime);	
					Time endTimeParsed = (Time)sdf.parse(endTime);
					
					shiftTemplate.setStartTime(startTimeParsed);
					shiftTemplate.setEndTime(endTimeParsed);
					shiftTemplate.setDay(Integer.parseInt(dayOfWeek));
					
					shiftTemplate.setShiftPositions(listOfPositions);
					
					template.getShiftTemplates().add(shiftTemplate);
					
				}
				catch(Exception e)
				{
					//catch the date parse exception
				}
			}
			
		}
		finally
		{
			response.sendRedirect("createShiftTemplate.jsp");
		}
	}

}
