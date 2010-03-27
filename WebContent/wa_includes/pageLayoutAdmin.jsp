<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="business.Employee" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!--Author:Noorin-->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<!--  CSS files -->
<link rel="stylesheet" href="CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/style.css" type="text/css" media="screen" />

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<title>Insert title here</title>
</head>
<body>

<div id="container">
<div id="header">
		<div id="headerTitle">Deerfoot Inn and Casino 	</div>
		
	<div id="userArea">
		<h6>Welcome: <%out.println(session.getAttribute("username")); %> | <a href="#">Settings</a> | <a href="../logout">Logout</a></h6>
	</div>
</div>
</div>

<div id="middle"> 

<div id="sidebar">
	<!-- Gray colour for rectangles -->
		
		<div id= "dashboardUpperRectangle" class="upperRectangleCurrent">
			<div class="sidebarTitle" id="dashboardTitle">Dashboard</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id = "dashboardLowerRectangle" class = "lowerRectangle">
			<b> <a href ="../wa_dashboard/dashboard.jsp" > Dashboard  </a> </b> <br>
			<b> <a href ="../wa_reports/reports.jsp" > Reports </a> </b> <br>
		</div>
		
		<br>

		<!-- Gray colour for rectangles -->
		
		<div id = "scheduleUpperRectangle" class="upperRectangle">
			<div class="sidebarTitle" id="scheduleTitle">Schedule</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "scheduleLowerRectangle" class = "lowerRectangle">
			<b> <a href ="../wa_schedule/schedule.jsp" > View Schedule  </a> </b> <br>
			<b> <a href ="../wa_schedule/requestChange.jsp" > Request Shift Change </a> </b> <br>
			<b> <a href ="../wa_schedule/shiftExchangePool.jsp" > Availability </a> </b> <br>
			<b> <a href ="../wa_schedule/" > Create schedule </a> </b> <br>
		</div>
		
		<br>
		
		<!-- Gray colour for rectangles -->
		
		<div id ="usersUpperRectangle" class="upperRectangle">
			<div class="sidebarTitle" id="usersTitle">Users</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "usersLowerRectangle" class = "lowerRectangle">
			
			<b> <a href ="../wa_user/newUser.jsp" > Users </a> </b> <br>
			
		</div>
		<br>
		
		<div id= "locationsUpperRectangle" class="upperRectangle">
		
			<div class="sidebarTitle" id="locationsTitle"> Maintenance </div>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "locationsLowerRectangle" class = "lowerRectangle">
			
			<b> <a href ="../wa_location/newLocation.jsp"> Locations </a> </b> <br>
			<b> <a href ="../wa_user/newSkill.jsp" > Skills </a> </b> <br>
			<b> <a href ="../wa_user/newPosition.jsp" > Positions </a> </b> <br>
		
		</div>
		
		<br>
		<!-- Gray colour for rectangles -->
		
		<div id="mailUpperRectangle" class="upperRectangle">
			<div class="sidebarTitle" id="mailTitle"> Mail</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id="mailLowerRectangle" class = "lowerRectangle">
			
			<b> <a href ="../wa_email/inbox.jsp" > Inbox </a> </b> <br>
			<b> <a href ="../wa_email/compose.jsp"> Compose </a> </b> <br>
			<b> <a href ="../wa_email/drafts.jsp"> Drafts </a> </b> <br>
			<b> <a href =" " > Notifications </a> </b> <br>
		</div>
		
		<br>
		<div id= "helpUpperRectangle" class="upperRectangle">
			<div class="sidebarTitle" id="helpTitle">Help</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "helpLowerRectangle" class = "lowerRectangle">
			<b> <a href =" " > Search Help </a> </b> <br>
			<b> <a href ="../wa_help/contactAdmin.jsp">  Contact Admin </a> </b> <br>
			<b> <a href =" " > Online Help </a> </b> <br>
		</div>
		
		<br>
		<div id= "settingsUpperRectangle" class="upperRectangle">
			<div class="sidebarTitle" id="settingsTitle">Settings</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "settingsLowerRectangle" class = "lowerRectangle">
			<b> <a href ="../wa_settings/securitySettings.jsp"> Security </a> </b> <br>
			<b> <a href ="../wa_settings/password.jsp"> Change Password </a> </b> <br>
			<b> <a href ="../wa_settings/adminProfile.jsp"> Admin's Profile </a> </b> <br>
			<b> <a href ="../wa_settings/corporateSettings.jsp" > Corporate's Profile </a> </b> <br>
		</div>
	</div>
	
	<!-- End sidebar div -->
	<!-- End of Header file -->
</body>
</html>
