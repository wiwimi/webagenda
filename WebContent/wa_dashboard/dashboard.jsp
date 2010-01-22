<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="CSS/style.css" type="text/css" media="screen" />
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	
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
		
		<div id= "dashboardUpperRectangle" class="upperRectangle">
		
			<b>Dashboard</b>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id = "dashboardLowerRectangle" class = "lowerRectangle">
			
			<b> <a href ="" > Dashboard  </a> </b> <br>
			<b> <a href ="" > Statistics </a> </b> <br>
		</div>
		
		<br>

		<!-- Gray colour for rectangles -->
		
		<div id = "scheduleUpperRectangle" class="upperRectangle">
		
			<b>Schedule</b>
		
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
		
			<b>Users</b>
		
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
		
			<b>Mail</b>
		
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
		
			<b>Help</b>
		
		</div>
		
		<!-- White Background for boxes-->
		
		<div id= "helpLowerRectangle" class = "lowerRectangle">
			
			<b> <a href =" " > Search Help </a> </b> <br>
			<b> <a href =" " >  Contact Admin </a> </b> <br>
			<b> <a href =" " > Online Help </a> </b> <br>
		</div>
	
	</div>
</div>

<div id="footer"></div>
</div>
</body>
</html>