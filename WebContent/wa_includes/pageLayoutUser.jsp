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
<!-- Edited by: Mark -->

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
		<h6>Welcome: <%out.println(session.getAttribute("username")); %> | <a href="../wa_settings/adminProfile.jsp">Settings</a> | <a href="../logout">Logout</a></h6>
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
		</div>
		
		<br>

		<!-- Gray colour for rectangles -->
		
		<div id = "scheduleUpperRectangle" class="upperRectangle">
			<div class="sidebarTitle" id="scheduleTitle">Schedule</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "scheduleLowerRectangle" class = "lowerRectangle">
			<b> <a href ="../wa_schedule/displayUserSchedule.jsp" > View Schedule  </a> </b> <br>
		</div>
		
		
		
		<br>
		<div id= "helpUpperRectangle" class="upperRectangle">
			<div class="sidebarTitle" id="helpTitle">Help</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "helpLowerRectangle" class = "lowerRectangle">
			<b> <a href ="../wa_help/demo.jsp"> Demo </a> </b> <br>
			<b> <a href ="../wa_help/contactAdmin.jsp">  Contact Admin </a> </b> <br>
			<b> <a href ="http://code.google.com/p/webagenda/w/list" > Online Help </a> </b> <br>
		</div>
		
		<br>
		<div id= "settingsUpperRectangle" class="upperRectangle">
			<div class="sidebarTitle" id="settingsTitle">Settings</div>
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "settingsLowerRectangle" class = "lowerRectangle">
			<b> <a href ="../wa_settings/changePassword.jsp"> Change Password </a> </b> <br>
		</div>
	</div>
	
	<!-- End sidebar div -->
	<!-- End of Header file -->
</body>
</html>
