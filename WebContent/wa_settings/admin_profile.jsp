<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayout.jsp"/>

<!-- Libraries -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<style type="text/css">@import "../lib/js/jquery.datepick.css";</style> 
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/userAddStyle.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<title>Adding User</title>
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

		<div id ="updateAdmin">
			<form class="updateAdminForm" method="post">
			 <div id="personal">
			
				 <fieldset>
					<legend > Personal 
					
							<p>	<label> Given Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="givenName" size ="30"> </p>
							<p>	<label> Family Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="familyName" size ="30"> </p>
							<p>	<label> Date of Birth: <em class="asterisk"> * </em> </label> <input type="text" name ="dob" id="dob" size ="10"></p>
							<p>	<label> Personal E-mail: </label> <input type="text"  name ="email" size ="30"> </p>
					
					</legend>
				</fieldset>
			</div>
			
			<fieldset>
					<legend > Account Settings
			
							<p> <label> User Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="userName" size ="30"> </p>
							<p> <label> Password: <em class="asterisk"> * </em> </label> <input type="password"  name ="userName" size ="30"> </p>
							<p> <label> Password: <em class="asterisk"> * </em> </label> <input type="password"  name ="userName" size ="30"> </p>
			
			</legend>
				</fieldset>
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
				</form>
			</div>
			</div>
		</div>
<script type="text/javascript">
        $(function()
		{
                
			
			$("#dob").datepick({showOn: 'button', buttonImageOnly: true, buttonImage:'../lib/images/icons/Color/calendar.gif'});
			
			$(".addUserForm fieldset").mouseover(function()
			{
				$(this).addClass("over");
			});
			
        });
		
</script>

<div id="footer"></div>

</body>
</html>