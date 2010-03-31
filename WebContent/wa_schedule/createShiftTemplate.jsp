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

<script type="text/javascript">
function addPosition()
{
	  var numi = document.getElementById('positionValue');
	  var num = (document.getElementById('positionValue').value -1)+ 2;
	  var ni = document.getElementById('position');
	  numi.value = num;
	  var newdiv = document.createElement('div');
	  var divIdName = 'position'+num;
	  newdiv.setAttribute('id',num);

	  newdiv.innerHTML = "<label for=\"Position1\">Position "+(num + 1)+":</label><input type=\"text\" size=\"15\" name=\"name\" /><label for=\"name\">Name:</label><input type=\"text\" size=\"15\" name=\"name\" />";
	  ni.appendChild(newdiv);

	
}

function addShift()
{
	  var ni = document.getElementById('shift');
	  var numi = document.getElementById('theValue');
	  var num = (document.getElementById('theValue').value -1)+ 2;
	  numi.value = num;
	  var newdiv = document.createElement('div');
	  var divIdName = 'shift'+num;
	  newdiv.setAttribute('id',num);

	  newdiv.innerHTML = "<div id=\"shift"+num+"\">" + 					
		"<h3>Shift "+(num + 1)+"</h3>" + 
		"<label for=\"startTimeShift\">Start Time:</label><input type=\"text\"  size=\"30\" name=\"startTimeShift\"/>" + 
		"<label for=\"endTimeShift\">End Time:</label><input type=\"text\"  size=\"30\" name=\"endTimeShift\"/>" + 
		"<label for=\"day\">Day of the Week: </label><input type=\"text\" size=\"10\" name=\"day\" /><br />" + 
		"<button onclick=\"addPosition()\" value=\"addPosition\">Add Position</button><br />" + 
		"<label for=\"Position1\">Position 1:</label><input type=\"text\" size=\"15\" name=\"name\" /><label for=\"name\">Name:</label><input type=\"text\" size=\"15\" name=\"name\" />" + 
		"<div id=\"position"+(num+1)+"\"></div>" +
	"</div>";
	  ni.appendChild(newdiv);
}
</script>

</head>
<body>

<div id="scheduleWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule</div>
			</div>
			
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
				<a href="createShiftTemplate.jsp">Create Template</a> | <a href="addSchedule.jsp">Create Schedule</a><br /><br />
				
					<label for="templateName">Template Name:</label><input type="text" size="30" name="templateName"/><br />
					<button onclick="addShift()">Add Shift</button>
								
					<div id="shift">				
						<h3>Shift 1</h3>
						<label for="startTimeShift">Start Time:</label><input type="text"  size="30" name="startTimeShift"/>
						<label for="endTimeShift">End Time:</label><input type="text"  size="30" name="endTimeShift"/>
						<label for="day">Day of the Week: </label><input type="text" size="10" name="day" /><br />
						
						<button onclick="addPosition()" value="addPosition">Add Position</button><br />
						<label for="Position1">Position 1:</label><input type="text" size="15" name="name" /><label for="name">Name:</label><input type="text" size="15" name="name" />
						<input type="hidden" value="0" id="positionValue" />
						<div id="position"></div>
						
					<input type="hidden" value="0" id="theValue" />
					</div>	
			</div>
</div>
<div id="footer"></div>
</body>
</html>