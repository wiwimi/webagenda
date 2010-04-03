package uiConnection.schedule;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
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
		finally
		{
			response.sendRedirect("createShiftTemplate.jsp");
		}
	}

}
