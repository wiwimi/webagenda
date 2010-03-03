<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Adding a User</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />


</head>
<body>
	<div id="instructions">
		Fields marked with <em class="asterisk" > *</em> are required.
		Randomly generated passwords are going to be sent to the employee's e-mail. If that field was missing it will be sent to your e-mail.
		<br></br>
	</div>

<br></br>
<br></br>

		<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">Users</div>
			</div>
			
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
		
		<div id ="userForm">
			<form class="validatedForm" id="form" method="post">
			 <div id="personal">
				 	<div id="searchArea">
				 	
				            <input type="submit" name="submit" class="button" value="Save"> 
							<input type="button" name="submit" class="button" value="Search" onClick="location.href='updateUser.jsp';">
							<input type="submit" name="submit" class="button" value="Delete"> 
							<input type="reset" name="clear" class="button" value="Clear Screen">
							
					</div>
			
				 <fieldset>
					<legend > Personal </legend>
					
							<p>	<label class ="label"> Given Name:  <em class="asterisk"> * </em> </label> <input type="text"  name ="givenName" class ="required" size ="30"> </p>
							<p>	<label class ="label"> Family Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="familyName"  class ="required"  size ="30"> </p>
							<p>	<label class ="label"> Date of Birth: <em class="asterisk"> * </em> </label> <input type="text" name ="dob" id="dob" class ="required"  size ="10"></p>

				            <p>
				          		<label for="cemail" class="label"> E-Mail<em class="asterisk"> * </em></label>
			               		<input id="cemail" name="email" class="required email" />
			                </p>
					
				</fieldset>
			</div>
			
			<div id="work">
			<fieldset>
				<legend> Working Preferences </legend>
					<p>	<label id="theSelect" class="theSelect"> Status: <em class="asterisk"> * </em> </label> 
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
								<!--This should be populated from MaintainPermission use case -->
					
							<label id="theSelect" class="theSelect"> Permission level: </label>  
									<select name="plevel" size=3>
											<option value="1">Level 1</option>
											<option value="2">Level 1a</option>
											<option value="3">Level 2b</option>
									</select>
				</fieldset>
				</div>
				<div id="searchArea">
						 <input type="submit" name="submit" class="button" value="Save"> 
							<input type="button" name="submit" class="button" value="Search" onClick="location.href='updateUser.jsp';">
							<input type="submit" name="submit" class="button" value="Delete"> 
							<input type="reset" name="clear" class="button" value="Clear Screen"> 
				<br></br>
				</div>
				</form>
			</div>
			</div>
		</div>

<div id="footer"></div>

</body>
</html>
