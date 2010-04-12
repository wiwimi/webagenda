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
<%@page import="persistence.EmployeeBroker"%>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!--  CSS files -->
<link rel="stylesheet" href="CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery-ui-1.7.2.custom.min.js" type="text/javascript"></script>
<script src="../lib/js/js/jquery.easing.1.3.js" type="text/javascript" language="JavaScript"></script>
<script src="../lib/js/js/jquery.jBreadCrumb.1.1.js" type="text/javascript" language="JavaScript"> </script>


<!-- Javascript Files -->

<title>Web Agenda - Dashboard</title>

   <%
         Employee e = (Employee) request.getSession().getAttribute("currentEmployee");
        if (e==null)
        {
        	response.sendRedirect("wa_login/login.jsp");
        }
        else
        {
		
	    	PermissionBroker pb = PermissionBroker.getBroker();
	    	PermissionLevel[] perms =  pb.get(e.getLevel(), e.getVersion(), e);
	    	Permissions perm = perms[0].getLevel_permissions();
	    	
	    
	        if (perm.isCanManageEmployees()==true)
			{
			%>
				<!-- Includes -->
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
							<td>Date</td>
							<td>Start Time</td>
							<td>End Time</td>
						</tr>
					</thead>
					
					<tfoot>
						<tr>
							<td>Date</td>
							<td>Start Time</td>
							<td>End Time</td>
						</tr>					
					</tfoot>
					<tbody>
<% 
	ScheduleBroker broker = ScheduleBroker.getBroker();
	
	Schedule[] empSched = broker.getEmpSchedules((Employee)session.getAttribute("currentEmployee"));

	if(empSched == null)
	{
		out.println("<td colSpan=\"3\">There are no schedules to display</td>");
	}
	else
	{
		for(int index = 0; index < empSched[0].getShifts().size(); index++)
		{
			out.println("<tr>");
				out.println("<td>"+empSched[0].getShifts().get(index).getDay()+"</td>");
				out.println("<td>"+empSched[0].getShifts().get(index).getStartTime()+"</td>");
				out.println("<td>"+empSched[0].getShifts().get(index).getEndTime()+"</td>");
			out.println("</tr>");
		}
	}
%>
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
