<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<title>Web Agenda- Positions</title>

<!--  Includes -->
<jsp:include page="../wa_includes/PageLayoutAdmin.jsp"/>

</head>
<body>
	<div id="instructions">
		Fields marked with <em class="asterisk" > *</em> are required.
		<br></br>
	</div>

		<div id="skillWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="skillUpperRectangle">
				<div class="widgetTitle" id="skillWidgetTitle">Positions </div>
			</div>
			
		<div class="widgetLowerRectangle" id="skillLowerRectangle">

		<div id ="userForm">
			<form class="addPositionForm" method="post">
			<div id="position">
			
			<div id="searchArea">
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='update_position.jsp';"> 
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
			</div>
				
				 <fieldset>
					<legend > Position Details
					
							<p>	<label> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="skillName" size ="30"> </p>
							
							
								<!--This should be populated from MaintainSkills use case -->
							<p>
									<label id="theSelect" class="theSelect"> Required Skills: </label>  
											<select name="skills" size=3 multiple>
													<option value="1">Accounting</option>
													<option value="2">Cooking</option>
													<option value="3">Event Planning</option>
											</select>
											<br></br>
											<br></br>
							</p>		
							
							<p>	<label> Description: </label></p>
							<textarea  name="descreption" cols="23" rows="6" tabindex="101"></textarea>
					
					
					</legend>
				</fieldset>
			
				       <div id="searchArea">
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="submit" name="delete" class="button" value="Delete"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='update_position.jsp';"> 
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
			          </div>
				</div>
				</form>
			</div>
			</div>

</div>
<div id="footer"></div>

</body>
</html>