<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/schedule.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../lib/js/weekCalender/jquery.weekcalendar.css"/>

<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery-ui-1.7.2.custom.min.js"></script>
<script src="../lib/js/weekCalender/jquery.weekcalendar.js"></script>
<script src="js/schedule.js"></script>

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
				<div id='calendar'></div>
			</div>
</div>
<div id="footer"></div>
</body>
</html>