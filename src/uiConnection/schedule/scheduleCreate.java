package uiConnection.schedule;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import business.schedule.ScheduleTemplate;
import business.schedule.ShiftTemplate;

/**
 * This servlet is used to create a schedule from the create schedule screen
 * @author mark
 */
public class scheduleCreate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public scheduleCreate() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		ScheduleTemplate sched = (ScheduleTemplate)session.getAttribute("schedule");
		
		if(request.getParameter("action").equals("addShift"))
		{
			ShiftTemplate shiftTemp = new ShiftTemplate();
			
			sched.getShiftTemplates().add(shiftTemp);
		}
	}

}
