package uiConnection.update.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name="AddUser", urlPatterns={"/AddUser"})
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
		        
		        Employee emp = new Employee();
		        
		        
		        PrintWriter out = response.getWriter();
		        String familyName = request.getParameter("familyName");
		        
				String givenName = request.getParameter("givenName");
				
				String[] pos_skills = request.getParameterValues("skill");
				String status =  request.getParameter("status");
				String pos = request.getParameter("pos");
				String email=request.getParameter("email");
				String username = request.getParameter("user");
				//String permLevel = request.getParameter("permLevel");
				String location= request.getParameter("location");
				
				String empId = request.getParameter("empId");
				String supId = request.getParameter("supId");
				
				//int empIdInt = Integer.parseInt(empId);
				//int supIdInt = Integer.parseInt(supId);
				
				response.sendRedirect("wa_user/newUser.jsp?message=" + pos);
				
				
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
