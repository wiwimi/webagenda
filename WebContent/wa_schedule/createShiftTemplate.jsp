<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.ScheduleBroker" %>
<%@ page import="persistence.ScheduleTemplateBroker" %>
<%@ page import="business.schedule.*" %>   
 
<!-- Author: Mark Hazlett -->
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

<%@ page import="persistence.ScheduleBroker" %>
<%@ page import="persistence.ScheduleTemplateBroker" %>
<%@ page import="business.schedule.ScheduleTemplate" %>
<%@ page import="business.schedule.Shift" %>
<%@ page import="business.schedule.Schedule" %>
<%@ page import="business.Employee" %>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="persistence.PositionBroker" %>


<script type="text/javascript">
function addPosition()
{
	  var ni = document.getElementById('addPositionDiv');
	  var numi = document.getElementById('counter');
	  var num = (document.getElementById('counter').value -1)+ 2;
	  numi.value = num;
	  var newdiv = document.createElement('div');
	  var divIdName = 'position'+(num +1);
	  newdiv.setAttribute('id',divIdName);
	  newdiv.innerHTML = '<label for="positionType'+(num + 1)+'">Position Type</label><input type="text" size=30 name="positionType'+(num + 1)+'" />' +
			'<label for="positionNumber'+(num + 1)+'">Number of Positions</label><input type="text" size=10 name="positionNumber'+(num + 1)+'" />';
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

<% 
ScheduleTemplate newSched;

	if(session.getAttribute("schedule") ==null)
	{
		newSched = new ScheduleTemplate();
		session.setAttribute("schedule",newSched);
	}
	else
	{
		newSched = (ScheduleTemplate)session.getAttribute("schedule");
	}
%>
					<label for="templateName">Template Name:</label><input type="text" size="30" name="templateName" value="<%
						if(newSched.getName() == null)
						{
							
						}
						else
						{
							out.println(newSched.getName());
						} %>"/><br />
<h3>Current Shifts</h3>
<%
if(newSched.getShiftTemplates().size() == 0)
{
	%>
	<table>
		<thead>
			<tr>
				<td>Day</td>
				<td>Start Time</td>
				<td>End Time</td>
				<td>Positions</td>
				<td>Number</td>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td>Day</td>
				<td>Start Time</td>
				<td>End Time</td>
				<td>Positions</td>
				<td>Number</td>
			</tr>
		</tfoot>
		<tbody>
			<tr>
				<td colspan="5">There are no shifts added</td>
			</tr>
		</tbody>
	</table>
	<%	
}
else
{
	%>
	<table>
		<thead>
			<tr>
				<td>Day</td>
				<td>Start Time</td>
				<td>End Time</td>
				<td>Positions</td>
				<td>Number</td>
				<td>Change</td>
				<td>Delete</td>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<td>Day</td>
				<td>Start Time</td>
				<td>End Time</td>
				<td>Positions</td>
				<td>Number</td>
				<td>Change</td>
				<td>Delete</td>
			</tr>
		</tfoot>
		<tbody>
		<%
		for(int index = 0; index < newSched.getShiftTemplates().size(); index++)
		{
			out.println("<tr>");
			out.println("<td>"+newSched.getShiftTemplates().get(index).getDay()+"</td>");
			out.println("<td>"+newSched.getShiftTemplates().get(index).getStartTime()+"</td>");
			out.println("<td>"+newSched.getShiftTemplates().get(index).getEndTime()+"</td>");
			out.println("<td>"+newSched.getShiftTemplates().get(index).getShiftPositions()+"</td>");
			out.println("<td><a href=\"scheduleTemplate?action=change&number="+index+"\">Change</a></td>");
			out.println("<td><a href=\"scheduleTemplate?action=delete&number="+index+"\">Delete</a></td>");
			out.println("</tr>");
		}
			
		%>
		</tbody>
	</table>
	<%
}
%>
<h3>Add a Shift Template</h3>			
<form action="scheduleTemplate" method="POST">
	<label for="startTime">Start Time: </label><input type="text" size=30 name="startTime" placeholder="00:00"/>
	<label for="endTime">End Time: </label><input type="text" size=30 name="endTime" placeholder="00:00"/><br />
	<label for="dayOfWeek">Day Of Week: </label><input type="text" size=10 name=dayOfWeek /><br /><br />
	
	Positions <button type="button" onClick="addPosition()">Add Position</button><br />
	
<% 
	PositionBroker broker = PositionBroker.getBroker();
	
	Position[] positions = broker.get(new Position(), (Employee)session.getAttribute("currentEmployee"));
	
	out.println("<label for=\"positionType1\">Position Type</label>");
	out.println("<select name=\"positionType1\">");
	
	for(int index = 0; index < positions.length; index++)
	{
		out.println("<option value="+index+">"+positions[index].getName()+"</option>");		
	}
	
	out.println("</select>");
%>
	<label for="positionNumber1">Number of Positions</label><input type="text" size=10 name="positionNumber1" />
	
	<input type="hidden" value="0" id="counter" name="counter"/>
	<div id="addPositionDiv"></div>
	<br />
	<button type="submit" value="submit">Submit Shift Template</button>
</form>
					
		</div>
</div>
<div id="footer"></div>
</body>
</html>