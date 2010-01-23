<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

<!-- Javascript Files -->
<script type="text/javascript" src="js/dashboard.js"></script>

<title>Web Agenda - Dashboard</title>
</head>
<body>
<div id="container">
<div id="header">
<div id="headerTitle">Deerfoot Inn and Casino</div>
<div id="userArea">
	<h6>Welcome: <%session.getAttribute("username"); %> | <a href="#">Settings</a> | Logout</h6>
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
			
			<b> <a href ="" > Dashboard  </a> </b> <br>
			<b> <a href ="" > Statistics </a> </b> <br>
		</div>
		
		<br>

		<!-- Gray colour for rectangles -->
		
		<div id = "scheduleUpperRectangle" class="upperRectangle">
		
			<div class="sidebarTitle" id="scheduleTitle">Schedule</div>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "scheduleLowerRectangle" class = "lowerRectangle">
			
			<b> <a href ="" > View Schedule  </a> </b> <br>
			<b> <a href ="" > Request Change </a> </b> <br>
			<b> <a href ="" > Availability </a> </b> <br>
		</div>
		
		<br>
		
		<!-- Gray colour for rectangles -->
		
		<div id ="usersUpperRectangle" class="upperRectangle">
		
			<div class="sidebarTitle" id="usersTitle">Users</div>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "usersLowerRectangle" class = "lowerRectangle">
			
			<b> <a href =" " > Add </a> </b> <br>
			<b> <a href =" " > Delete </a> </b> <br>
			<b> <a href =" " > Update </a> </b> <br>
		</div>
		
		<br>
		<!-- Gray colour for rectangles -->
		
		<div id="mailUpperRectangle" class="upperRectangle">
		
			<div class="sidebarTitle" id="mailTitle">Mail</div>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id="mailLowerRectangle" class = "lowerRectangle">
			
			<b> <a href =" " > Inbox </a> </b> <br>
			<b> <a href =" " > Compose </a> </b> <br>
			<b> <a href =" " > Drafts </a> </b> <br>
			<b> <a href =" " > Notifications </a> </b> <br>
		</div>
		
		<br>
		
		<div id= "helpUpperRectangle" class="upperRectangle">
		
			<div class="sidebarTitle" id="helpTitle">Help</div>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "helpLowerRectangle" class = "lowerRectangle">
			
			<b> <a href =" " > Search Help </a> </b> <br>
			<b> <a href =" " >  Contact Admin </a> </b> <br>
			<b> <a href =" " > Online Help </a> </b> <br>
		</div>
	
	</div>
	<!-- End sidebar div -->
	
	<!-- Start middle Content div -->
	<div id="middleContent">
		<div id="quickLinksWidget" class="fullWidget">
			<div class="widgetUpperRectangle">
				<div class="widgetTitle" id="quickLinksTitle">Quick Links</div>
			</div>
			
			<div class="widgetLowerRectangle">
				some widget data here
			</div>
		</div>
		
		<div id="scheduleWidget" class="fullWidget">
			<div class="widgetUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule</div>
			</div>
			
			<div class="widgetLowerRectangle">
				some widget data here
			</div>
		</div>
		<div id="firstColumn" class="column">
			<div id="notificationsWidget" class="halfWidget">
				<div class="widgetUpperRectangle">
					<div class="widgetTitle" id="notificationsWidgetTitle">Notifications</div>
				</div>
			
				<div class="widgetLowerRectangle">
					some widget data here
				</div>
			</div>
		</div>
		
		<div id="secondColumn" class="column">
			<div id="mailWidget" class="halfWidget">
				<div class="widgetUpperRectangle">
					<div class="widgetTitle" id="mailWidgetTitle">Mail(1)</div>
				</div>
			
				<div class="widgetLowerRectangle">
					some widget data here
				</div>
			</div>
		</div>
		
	</div>
	<!-- End middle content div -->
</div>

<div id="footer"></div>
</div>
</body>
</html>