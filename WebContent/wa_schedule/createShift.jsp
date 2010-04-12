<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="business.Employee" %>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating User</title>

<%
         Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
        if (user==null)
        {
        	response.sendRedirect("../wa_login/login.jsp");
			return;
        }
        else
        {
		
	    	PermissionBroker pb = PermissionBroker.getBroker();
	    	PermissionLevel[] perms =  pb.get(user.getLevel(), user.getVersion(), user);
	    	Permissions perm = perms[0].getLevel_permissions();
	    	
	    
	        if (perm.isCanManageEmployees()==true)
			{
				%>
					<!-- Includes -->
					<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>
				<%
		    }
			else
			{
				response.sendRedirect("../wa_login/login.jsp");
		        return;
			}
        }
	%>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

</head>
<body>

<div id="scheduleWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule</div>
			</div>
			
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
				<table class="sortable" id="userTable">
					<thead class="head">
						<tr class="headerRow">
							<th>Employee</th>
							<th>Monday</th>
							<th>Tuesday</th>
							<th>Wednesday</th>
							<th>Thursday</th>
							<th>Friday</th>
							<th>Saturday</th>
							<th>Sunday</th>
						</tr>
					</thead>
					
					<tfoot class="foot">
						<tr class="headerRow">
							<th>Employee</th>
							<th>Monday</th>
							<th>Tuesday</th>
							<th>Wednesday</th>
							<th>Thursday</th>
							<th>Friday</th>
							<th>Saturday</th>
							<th>Sunday</th>
						</tr>
					</tfoot>
					<tbody>
					<%for(int index2=0; index2 <7; index2++)
					{%>
						<tr>
							<td>Bob</td>
							<%
							for(int index = 0; index < 7; index++)
							{
							%>
							<td>Start:<select name="startTime">
									<option value="12"> 12:00PM</option> 
									<option value="1230" >12:30PM</option>
									<option value="1" >1:00PM</option>
									<option value="130" >1:30PM</option>
									<option value="2" >2:00PM</option>
									<option value="230" >2:30PM</option>          
								</select> <br />
								End: <select name="endTime">
									<option value="enabled"> 12:00PM</option> 
									<option value="disabled" >12:30PM</option>  
								</select> 	
							</td>
							<%
							} 
							%>	
							</td>
						</tr>
						<%} %>		
					</tbody>
				</table>
			</div>
</div>
<div id="footer"></div>
</body>
</html>