<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<!--  CSS files -->
<link rel="stylesheet" href="CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/style.css" type="text/css" media="screen" />

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

<!-- Javascript Files -->
<script type="text/javascript" src="js/dashboard.js"></script>


<title>Insert title here</title>
</head>
<body>

<div id="container">
<div id="header">
	<div id="headerTitle">Deerfoot Inn and Casino	
	</div>
<div id="userArea">
	<h6>Welcome: <%session.getAttribute("username"); %> | <a href="#">Settings</a> | Logout</h6>
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
			<b> <a href ="" > Reports </a> </b> <br>
		</div>
		
		<br>

		<!-- Gray colour for rectangles -->
		
		<div id = "scheduleUpperRectangle" class="upperRectangle">
		
			<div class="sidebarTitle" id="scheduleTitle">Schedule</div>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "scheduleLowerRectangle" class = "lowerRectangle">
			
			<b> <a href ="../wa_schedule/schedule.jsp" > View Schedule  </a> </b> <br>
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
			
			<b> <a href ="../wa_user/add_user.jsp" > Add </a> </b> <br>
			<b> <a href ="../wa_user/delete_user.jsp" > Delete </a> </b> <br>
			<b> <a href ="../wa_user/update_user.jsp" > Update </a> </b> <br>
			<b> <a href ="../wa_user/add_skill.jsp" > Skills </a> </b> <br>
			<b> <a href ="../wa_user/add_position.jsp" > Positions </a> </b> <br>
		</div>
		<br>
		
		<div id= "locationsUpperRectangle" class="upperRectangle">
		
			<div class="sidebarTitle" id="locationsTitle"> Locations </div>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "locationsLowerRectangle" class = "lowerRectangle">
			
			<b> <a href ="../wa_location/add_location.jsp"> Add </a> </b> <br>
			<b> <a href ="../wa_location/update_location.jsp"> Update </a> </b> <br>
		
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
		
		<!--Noorin-->
		<div id= "settingsUpperRectangle" class="upperRectangle">
		
			<div class="sidebarTitle" id="settingsTitle">Settings</div>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "settingsLowerRectangle" class = "lowerRectangle">
			
			<b> <a href ="../wa_settings/security_settings.jsp"> Security </a> </b> <br>
			<b> <a href ="../wa_settings/admin_profile.jsp"> Admin's Profile </a> </b> <br>
			<b> <a href ="../wa_settings/corporate_settings.jsp" > Corporate's Profile </a> </b> <br>
		</div>
		
		
	</div>
	<!-- End sidebar div -->
	<!-- End of Header file -->
	
	

</body>
</html>
</body>
</html>