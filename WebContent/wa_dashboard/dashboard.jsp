<%@page contentType="text/html" pageEncoding="UTF-8"
		import="persistence.NotificationBroker, business.Notification,
				business.Employee, exception.*" %>

<% 
if(session.getAttribute("username") != null)
{
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="persistence.ScheduleBroker" %>
<%@ page import="persistence.*" %>
<%@ page import="business.*" %>
<%@ page import="business.schedule.*" %>
<%@page import="uiConnection.users.UserNotifications"%>
<%@page import="persistence.EmployeeBroker"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!--  CSS files -->
<link rel="stylesheet" href="CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/style.css" type="text/css" media="screen" />

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery-ui-1.7.2.custom.min.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<title>Web Agenda - Dashboard</title>

<%
	Employee e = (Employee) request.getSession().getAttribute("currentEmployee");
	if (e.getLevel()==99)
	{
%>
	<!--  Includes -->
	<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>
<%
	}
	else
	{
%>
		<!--  Includes -->
	<jsp:include page="../wa_includes/pageLayoutUser.jsp"/>
<%
	}
%>

</head>
<body>
<% 
	
	Notification n = null;
	try {
		System.out.println(e);
		n = UserNotifications.getMostRecentUnread(e);
	}
	catch(DBException dbE) {
		out.println(dbE.getMessage());
	}
%>
	<% if(n != null) 
	{
		Employee sender = new Employee();
			sender.setEmpID(n.getSenderID());
		sender = EmployeeBroker.getBroker().get(sender,e)[0];
		if(sender != null) out.println(
					"<div id=\"notification\">"
				+ 		"<div id=\"notificationText\">"
				+ 			n.getMessage() + " - from " + sender.getUsername()  
				+ 		"</div>"
				+		"<div id=\"notificationCloseButton\">Close</div>"
				+ 	"</div>");
	}
	%>
	
	<!-- Start middle Content div -->
	<div id="middleContent">
		
		<div id="scheduleWidget" class="fullWidgetDashboard">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule</div>
			</div>
			
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
			<div id="shiftsTable">
				<table>
					<thead>
						<tr>
							<td>Sunday</td>
							<td>Monday</td>
							<td>Tuesday</td>
							<td>Wednesday</td>
							<td>Thursday</td>
							<td>Friday</td>
							<td>Saturday</td>
						</tr>
					</thead>
					
					<tfoot>
						<tr>
							<td>Sunday</td>
							<td>Monday</td>
							<td>Tuesday</td>
							<td>Wednesday</td>
							<td>Thursday</td>
							<td>Friday</td>
							<td>Saturday</td>
						</tr>					
					</tfoot>
					<tbody>
					<tr>
<% 
	ScheduleBroker broker = ScheduleBroker.getBroker();
	Schedule[] schedule = broker.get(new Schedule(), (Employee)session.getAttribute("currentEmployee"));
	
	if(schedule.length == 0)
	{
		out.println("<td colSpan=\"7\">There are no shifts in the system<td>");
	}
	else
	{
	
		for(int index = 0; index < schedule.length; index++)
		{
			if(schedule[index].getShifts().get(index).getEmployees().get(index).equals((Employee)session.getAttribute("currentEmployee")))
			{
				for(int i = 0; i < schedule[index].getShifts().size(); i++)
				{
					out.println("<td>"+schedule[index].getShifts().get(i)+"</td>");
				}
			}
		}
	}
%>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
		</div>
		
			<div id="notificationsWidget" class="fullWidgetDashboard">
				<div class="widgetUpperRectangle" id="notificationsUpperRectangle">
					<div class="widgetTitle" id="notificationsWidgetTitle">Notifications</div>
				</div>
			
				<div class="widgetLowerRectangle" id="notificationsLowerRectangle">
					<% 
						
					if(e != null) {
					
						Notification[] notes = UserNotifications.getAllUnread(e);
						if(notes == null) {
							out.println("No New Notifications");
						}
						else {
							for(int i = 0; i < notes.length; i++)
							{
								out.println(notes[i].getMessage());
							}
						}
					}
					else {
						out.println("No New Notifications");
					}
					%>
					
				</div>
			</div>
		</div><!-- End middle content div -->
<div id="footer"></div>

</body>
</html>

<%
}
else
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>
