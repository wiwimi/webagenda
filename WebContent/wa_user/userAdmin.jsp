<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
				<div class="widgetTitle" id="usersTitle">Users</div>
			</div>
			
			<div class="widgetLowerRectangle" id="quickLinksLowerRectangle">

		<div id ="addUser">
			<form class="addUserForm" method="post">
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
			
			<div id="work">
		    <p>
			<fieldset>
				<legend> Working Preferences 
					<p>	<label> Status: <em class="asterisk"> * </em> </label> 
							        <select name="status" >
											<option value="enabled" > Enabled</option> 
											<option value="disabled" >Disabled</option>  
									</select> 
					</p>
									
							<!--This should be populated from MaintainJobType use case -->
					<p>		
							<label id="theSelect" class="theSelect"> Preferred Positions: </label>  
									<select name="positions" size=3 multiple>
											<option value="1">Bartender</option>
											<option value="2">Waitress</option>
											<option value="3">Other</option>
									</select>
									
					</p>
							
							<!--This should be populated from MaintainLocation use case -->
					<p>		
							<label id="theSelect" class="theSelect"> Preferred Locations: </label>  
									<select name="locaions" size=3 multiple>
											<option value="1">Hotel A</option>
											<option value="2">Hotel B</option>
											<option value="3">Basement</option>
									</select>
									
					</p>	
								<!--This should be populated from MaintainSkills use case -->
					<p>
							<label id="theSelect" class="theSelect"> Preferred Skills: </label>  
									<select name="skills" size=3 multiple>
											<option value="1">Accounting</option>
											<option value="2">Cooking</option>
											<option value="3">Event Planning</option>
									</select>
									<br></br>
									<br></br>
					</p>		
								<!--This should be populated from MaintainSkills use case -->
					
					<p>
							<label id="theSelect" class="theSelect"> Permission level: </label>  
									<select name="plevel" size=3>
											<option value="1">Level 1</option>
											<option value="2">Level 1a</option>
											<option value="3">Level 2b</option>
									</select>
				</legend>
				</fieldset>	
				</p>
				</div>
				
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
</div>
</body>
</html>