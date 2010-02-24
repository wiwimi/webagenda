<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% 
if(session.getAttribute("username") != null)
{
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
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

<!--  Includes -->
<jsp:include page="../wa_includes/PageLayoutAdmin.jsp"/>
</head>
<body>

	<div id="notification">
		<div id="notificationText">This is a test notification</div>
		<div id="notificationCloseButton">Close</div>
	</div>
	
	<!-- Start middle Content div -->
	<div id="middleContent">
	
		<div id="quickLinksWidget" class="fullWidgetDashboard">
			<div class="widgetUpperRectangle" id="quickLinksUpperRectangle">
				<div class="widgetTitle" id="quickLinksTitle">Quick Links</div>
			</div>
			
			
			<div class="widgetLowerRectangle" id="quickLinksLowerRectangle">
			<div id="contentHolder">
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
				<a href="#"><div class="linkBox">test</div></a>
			</div>
		</div>
		</div>
		
		<div id="scheduleWidget" class="fullWidgetDashboard">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule</div>
			</div>
			
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
				some widget data here
			</div>
		</div>
		
		<div id="firstColumn" class="column">
			<div id="notificationsWidget" class="halfWidgetDashboard">
				<div class="widgetUpperRectangle" id="notificationsUpperRectangle">
					<div class="widgetTitle" id="notificationsWidgetTitle">Notifications</div>
				</div>
			
				<div class="widgetLowerRectangle" id="notificationsLowerRectangle">
					some widget data here
				</div>
			</div>
		</div>
		
		<div id="secondColumn" class="column">
			<div id="mailWidget" class="halfWidgetDashboard">
				<div class="widgetUpperRectangle" id="mailWidgetUpperRectangle">
					<div class="widgetTitle" id="mailWidgetTitle">Mail(1)</div>
				</div>
			
				<div class="widgetLowerRectangle" id="mailWidgetLowerRectangle">
					some widget data here
				</div>
			</div>
		</div>
		
	</div>
	</div>
	<!-- End middle content div -->


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