<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Skills</title>

<!--  Includes -->
<jsp:include page="../wa_includes/PageLayoutAdmin.jsp"/>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

</head>
<body>
		<div id="instructions">
			Fields marked with <em class="asterisk" > *</em> are required.
			<br></br>
		</div>
<br></br>
<br></br>
		<div id="skillWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="skillUpperRectangle">
				<div class="widgetTitle" id="skillWidgetTitle">Skills </div>
			</div>
			
		<div class="widgetLowerRectangle" id="skillLowerRectangle">
		

		<div id ="userForm">
			<form class="addSkillForm" method="post">
			<div id="skill">
			
			<div id="searchArea">
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='update_user.jsp';"> 
						<input type="submit" name="submit" class="button" value="Delete">
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
			</div>
				
				 <fieldset>
					<legend > Skill Details
					
							<p>	<label> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="skillName" size ="30"> </p>
							
							<p>	<label> Description: </label></p>
							<textarea  name="descreption" cols="23" rows="6" tabindex="101"></textarea>
					</legend>
				</fieldset>
			
				</div>
				
				<div id="searchArea">
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='update_user.jsp';">
						 <input type="submit" name="submit" class="button" value="Delete">
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
				</div>
				
				</form>
			</div>
			</div>

</div>
<div id="footer"></div>

</body>
</html>