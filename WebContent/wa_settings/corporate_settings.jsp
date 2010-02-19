<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayout.jsp"/>


<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/settings.css" type="text/css" media="screen" />


<title>Insert title here</title>
</head>
<body>

<div id="settingsWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="settingsUpperRectangle">
				<div class="widgetTitle" id="settingsTitle">Corporate's Profile</div>
			</div>
			
		<div class="widgetLowerRectangle" id="settingsLowerRectangle">


<div id="corporateProfile">
<form class="updateAdminForm" method="post">
	<fieldset>
      <legend>Your Company</legend>
		<br>
					<p><label> Name:  </label> <input type="text"  name ="name"> </p>
	</fieldset>
	
	<fieldset>
		<legend>Schedule</legend>
									<p><label> Number of working days:</label> <select name="workdingDays">
										<option value="one">1</option>
										<option value="two">2</option>
										<option value="three">3</option>
										<option value="one">4</option>
										<option value="two">5</option>
										<option value="three">6</option>
										<option value="three">7</option>
										</select></p>
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