<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/schedule.css" type="text/css" media="screen" />

<title>Web Agenda- Schedule</title>

<!-- Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>


</head>
<body>

<div id="scheduleWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule</div>
			</div>
			
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
				<a href="createShiftTemplate.jsp">Create Template</a> | <a href="addSchedule.jsp">Create Schedule</a><br /><br />
				
				Days 
				<select>
					<option>1</option>
					<option>2</option>
					<option>3</option>
					<option>4</option>
					<option>5</option>
					<option>6</option>
					<option>7</option>
				</select><br /><br />
				
				From 
				<select>
					<option>Mon</option>
					<option>Tue</option>
					<option>Wed</option>
					<option>Thur</option>
					<option>Fri</option>
					<option>Sat</option>
					<option>Sun</option>
				</select> To 
				
				<select>
					<option>Mon</option>
					<option>Tue</option>
					<option>Wed</option>
					<option>Thur</option>
					<option>Fri</option>
					<option>Sat</option>
					<option selected="selected">Sun</option>
				</select>	
				
			</div>
</div>
<div id="footer"></div>
</body>
</html>