<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="business.Employee" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/schedule.css" type="text/css" media="screen" />

<title>Web Agenda- Schedule</title>

    <%
		Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
	if (user.getLevel()==99)
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
	%>
</head>
<body>

<div id="scheduleWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule</div>
			</div>
			
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
				THE SCHEDULE HERE
				
				<br>
				<br>
				<br>
				<br>
				
				
			</div>
</div>
<div id="footer"></div>
</body>
</html>
