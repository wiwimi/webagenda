<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Skills</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />

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
			<form class="addSkillForm" id="form" method="post">
			<div id="skill">
			
			<div id="searchArea">
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='updateSkill.jsp';"> 
						<input type="reset" name="clear" class="button" value="Clear Screen"> 
						<br></br>
			</div>
				
				 <fieldset>
					<legend > Skill Details </legend>
					
							<p>	<label class="label"> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="skillName" class="required" size ="30"> </p>
							
							<p>	<label class="label"> Description: </label></p>
							<textarea  name="descreption" cols="23" rows="6" tabindex="101"></textarea>
					
				</fieldset>
			
				</div>
				
				<div id="searchArea">
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='updatSkill.jsp';">
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
