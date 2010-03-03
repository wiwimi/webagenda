<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">




<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/location.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<title>Adding Location</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

</head>
<body>
	<div id="instructions">
	        Fields marked with <em class="asterisk" > *</em> are required.
	</div>
<br></br>
		<div id="locationWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="locationsUpperRectangle">
				<div class="widgetTitle" id="locationsWidgetTitle">Locations</div>
			</div>
			
		<div class="widgetLowerRectangle" id="locationsLowerRectangle">

		<div id ="addLocation">
			<form class="addLocationForm" method="post">
			<div id="location">
				 <fieldset>
					<legend > Location Details
					
							<p>	<label> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="locationName" size ="30"> </p>
							
							<p>	<label> Description: </label></p>
							<textarea  name="descreption" cols="23" rows="6" tabindex="101"></textarea>
					</legend>
				</fieldset>
					   <div id="searchArea">
					        <input type="submit" name="submit" class="button" value="Save"> 
							<input type="button" name="submit" class="button"  onClick="location.href='update_location.jsp'" value="Search" > 
					        <input type="submit" name="submit" class="button" value="Delete">
							<input type="submit" name="clear" class="button" value="Clear Screen"> 
			            </div>
				</div>
				</form>
			</div>
			</div>

</div>
<div id="footer"></div>

</body>
</html>
