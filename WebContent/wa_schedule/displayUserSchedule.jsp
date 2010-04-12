<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="business.Employee" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--  Daniel Kettle (implemented calendars onto start and end fields with Noorin's help) -->
<!--  Libraries -->
<script type ="text/javascript" src ="../lib/js/jquery-1.3.2.min.js"> </script>

<!-- Plug-ins -->
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>


<%@ page import="persistence.ScheduleBroker" %>
<%@ page import="persistence.*" %>
<%@ page import="business.*" %>
<%@ page import="business.schedule.*" %>
<%@page import="uiConnection.users.UserNotifications"%>
<%@page import="persistence.EmployeeBroker"%>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/deleteUser.js"></script>
<script type="text/javascript" src="../lib/js/helpDisplayUserSchedule.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>

<title>Web Agenda- Schedule</title>


<script type="text/javascript">
var i = 1;

function addElement()
{
	if(i == 1)
	{
	// FIXME - Need to output the number of required shifts to be set if using a template
		//this is where we need to perform the add shift secion, once the shift area has been created, we will add it to the shift template
		  var ni = document.getElementById('myDiv');
		  var numi = document.getElementById('theValue');
		  var num = (document.getElementById('theValue').value -1)+ 2;
		  numi.value = num;
		  var newdiv = document.createElement('div');
		  var divIdName = 'my'+num+'Div';
		  newdiv.setAttribute('id',num);

		  var days=new Array();

		  days[0] = 'sunday';
		  days[1] = 'monday';
		  days[2] = 'tuesday';
		  days[3] = 'wednesday';
		  days[4] = 'thursday';
		  days[5] = 'friday';
		  days[6] = 'saturday';
		  
		  var weekOptions = '';
		  for(var index = 0; index < 7; index++)
		  {
			  weekOptions = weekOptions + '<option value='+index+'>'+days[index]+'</option>';
		  }
		  
		  newdiv.innerHTML = 'Start Time: <input type="text" size=10 name=startTime'+num+'/><br> End Time:<input type="text" size=10 name=endTime'+num+'/>'
		  +'<br> Employee: <select type="option" name="employeeId"><option value="emp1">Emp 1</option></select><br>Shift Date:<select name="weekSelect">'+weekOptions+'</select><br><button onClick="submit('+num+')">Confirm</button><hr>';
		  ni.appendChild(newdiv);
		  i++;
	}
}

function submit(divNum) 
{
	  var d = document.getElementById('myDiv');
	  var olddiv = document.getElementById(divNum);
	  d.removeChild(olddiv);
	  --i;
}
</script>

<%
	Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
	if (user.getLevel()==99)
	{
%>
	<!-- Includes -->
	<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>
<%
	}
	else
	{
%>
		<!--  Includes -->
	<jsp:include page="../wa_includes/pageLayoutUser.jsp"/>
<%
	}
%>


</head>
<body>
   
   <div id="crumb">
	  <ul id="crumbsLonger">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><b><a href="#">View Schedule</a></b></li>
	   </ul>
   </div>

<div id="scheduleWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule <div id="helpIcon"> </div></div>
			</div>
			
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
			<div id="shiftsTable">
				<table>
					<thead>
						<tr>
							<td>Date</td>
							<td>Start Time</td>
							<td>End Time</td>
						</tr>
					</thead>
					
					<tfoot>
						<tr>
							<td>Date</td>
							<td>Start Time</td>
							<td>End Time</td>
						</tr>					
					</tfoot>
					<tbody>
<% 
	ScheduleBroker broker = ScheduleBroker.getBroker();
	
	Schedule[] empSched = broker.getEmpSchedules((Employee)session.getAttribute("currentEmployee"));

	if(empSched == null)
	{
		out.println("<td colSpan=\"3\">There are no schedules to display</td>");
	}
	else
	{
		for(int index = 0; index < empSched[0].getShifts().size(); index++)
		{
			out.println("<tr>");
				out.println("<td>"+empSched[0].getShifts().get(index).getDay()+"</td>");
				out.println("<td>"+empSched[0].getShifts().get(index).getStartTime()+"</td>");
				out.println("<td>"+empSched[0].getShifts().get(index).getEndTime()+"</td>");
			out.println("</tr>");
		}
	}
%>
					</tbody>
				</table>
				</div>
				
			</div>
</div>
<div id="footer"></div>
</body>
</html>