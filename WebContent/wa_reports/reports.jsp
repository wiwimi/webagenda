<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="business.Employee" %>
<% 
if(session.getAttribute("username") != null)
{
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<!-- Aurthor: Noorin -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/report.css" type="text/css"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery-ui-1.7.2.custom.min.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<title>Web Agenda - Reports</title>

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

	<div id="instructions">
		Click on an item to generate a report. You can either report a list of items or choose a particular item to report.
	</div>
	
	<!-- Start middle Content div -->
	<div id="middleContent">
		<div id="quickLinksWidget" class="fullWidgetDashboard">
			<div class="widgetUpperRectangle" id="quickLinksUpperRectangle">
				<div class="widgetTitle" id="quickLinksTitle">Reports</div>
			</div>
			
			<div class="widgetLowerRectangle" id="quickLinksLowerRectangle">
				
					<div id="usersIcon"><h3>Users</h3></div>
						<ul>
							<li> <a href="reportUsers.jsp"> All Active Users </a></li>
							<li> <a href="reportDisabledUsers.jsp"> All Disabled Users </a></li>
							<li>  <a href="searchUser.jsp">  A User </a></li>
						</ul>
					<div id="locationsIcon"><h3> Locations</h3> </div>
						<ul>
							<li>  <a href="reportLocations.jsp">  All Locations </a> </li>
							<li>  <a href="searchLocation.jsp">  A Location </a></li>
						</ul>
					<div id="schedulesIcon"> <h3> Schedules</h3> </div> 
						<ul>
							<li> <a href="reportSchedules.jsp"> All Schedules </a></li>
							<li> <a href="reportScheduleDates.jsp"> A Schedule </a> </li>
						</ul>
					<div id="positionsIcon"> <h3> Positions </h3> </div>
						<ul>
							<li> <a href="reportPositions.jsp"> All Positions </a> </li>
							<li> <a href="newPosition.jsp"> A Position </a> </li>
						</ul>
					 </div>
		</div>
		
	</div>	<!-- End middle content div -->
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
