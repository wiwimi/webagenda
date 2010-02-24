<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- User's Profile</title>

<!--  Includes -->
<jsp:include page="../wa_includes/PageLayoutUser.jsp"/>

<!-- Libraries -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<style type="text/css">@import "../lib/js/jquery.datepick.css";</style> 
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/userAddStyle.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

</head>
<body>
Fields marked with <em class="asterisk" > *</em> are required.
<br></br>
Randomly generated passwords are going to be sent to the employee's e-mail. If that field was missing it will be sent to your e-mail.
<br></br>
<br></br>

		<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersTitle">Admin's Profile</div>
			</div>
			
		<div class="widgetLowerRectangle" id="quickLinksLowerRectangle">

		<div id ="updateProfileForm">
			<form class="updateProfileForm" method="post">

			<fieldset>
					<legend > Account Settings
			
							<p> <label> User Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="userName" size ="30"> </p>
							
							<p> <label> Old Password: <em class="asterisk"> * </em> </label> <input type="password"  name ="userName" size ="30"> </p>
							
							
							<p> <label> New Password: <em class="asterisk"> * </em> </label> <input type="password"  name ="userName" size ="30"> </p>
							<p> <label> New Password: <em class="asterisk"> * </em> </label> <input type="password"  name ="userName" size ="30"> </p>
			
			</legend>
				</fieldset>
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
				</form>
			</div>
			</div>
		</div>


<div id="footer"></div>

</body>
</html>