<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="CSS/style.css" type="text/css"
	media="screen" />

<title>Web Agenda - Dashboard</title>
</head>
<body>
<div id="container">
<div id="header">
<div id="headerTitle">Deerfoot Inn and Casino</div>
<div id="loginArea">
<div id="rememberMe"><input type="checkbox" name="rememberMe"
	value="rememberMe" />Remember me?</div>
<form action="login" method="POST"><label for="username">Username:</label><input
	name="username" type="text" size="20" /> <label for="password">Password:</label><input
	name="password" type="password" size="20" />
<button type="submit" value="submit">Login</button>
</form>
</div>
</div>

<div id="middle">

<div id="sidebar">
	<!-- Gray colour for rectangles -->
		
		<div id= "dashboardUpperRectangle" class="upperDashboard">
		
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
<script type = "text/javascript">

		$(function()
		{
			    $('#helpUpperRectangle').click(function()
				{
					if($('#helpLowerRectangle').is(":hidden"))
					{
				
						$('#helpLowerRectangle').slideDown('fast');
					}
					else
					{
						$('#helpLowerRectangle').slideUp('fast');
					
					}
				
				});
				
				$('#usersUpperRectangle').click(function()
				{
					if($('#usersLowerRectangle').is(":hidden"))
					{
				
						$('#usersLowerRectangle').slideDown('fast');
					}
					else
					{
						$('#usersLowerRectangle').slideUp('fast');
					}
				
				});
				$('#mailUpperRectangle').click(function()
				{
					if($('#mailLowerRectangle').is(":hidden"))
					{
				
						$('#mailLowerRectangle').slideDown('fast');
					}
					else
					{
					
						$('#mailLowerRectangle').slideUp('fast');
					
					}
				
				});
				$('#scheduleUpperRectangle').click(function()
				{
					if($('#scheduleLowerRectangle').is(":hidden"))
					{
				
						$('#scheduleLowerRectangle').slideDown('fast');
					}
					else
					{
						$('#scheduleLowerRectangle').slideUp('fast');
					
					}
				
				});
				$('#dashboardUpperRectangle').click(function()
				{
					if($('#dashboardLowerRectangle').is(":hidden"))
					{
				
						$('#dashboardLowerRectangle').slideDown('fast');
					}
					else
					{
						$('#dashboardLowerRectangle').slideUp('fast');
					
					}
				
				});
		});

		</script>
	
	</div>
</div>

<div id="footer"></div>
</div>
</body>
</html>