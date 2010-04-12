<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="business.Employee" %>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>
<% 
if(session.getAttribute("username") != null)
{
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<!-- Aurthor: Noorin -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/report.css" type="text/css"/>
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Popup/popup.css"/>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>


<!-- Javascript Files -->

<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/helpReports.js"></script>

<title>Web Agenda - Reports</title>

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
</head>
<body>

  <div id="crumb">
	  <ul id="crumbs">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><b><a href="reports.jsp">Reports</a></b></li>
	   </ul>
   </div>

	<!-- Start middle Content div -->
	<div id="middleContent">
		
		<div id="reportsWidget" class="fullWidgetDashboard">
			<div class="widgetUpperRectangle" id="ReportsUpperRectangle">
				<div class="widgetTitle" id="quickLinksTitle">Reports <div id="helpIcon"></div> </div>
			</div>
			<div class="widgetLowerRectangle" id="reportsLowerRectangle">
				
				     <div id="instructions">
						Click on an item to generate a report. You can either report a list of items or choose a particular item to report.
					</div>
					<div id="usersIcon"><h3>Users</h3></div>
						<ul >
							<li> <a href="reportUsers.jsp"> All Active Users </a></li>
							<li> <a href="reportDisabledUsers.jsp"> All Disabled Users </a></li>
							<li>  <a href="searchUser.jsp">  A User </a></li>
						</ul>
					<div id="locationsIcon"><h3> Locations</h3> </div>
						<ul id="list">
							<li>  <a href="reportLocations.jsp">  All Locations </a> </li>
							<li>  <a href="searchLocation.jsp">  A Location </a></li>
						</ul>
					<div id="schedulesIcon"> <h3> Schedules</h3> </div> 
						<ul id="list">
							<li> <a href="reportSchedules.jsp"> All Schedules </a></li>
							<li> <a href="reportScheduleDates.jsp"> A Schedule </a> </li>
						</ul>
					<div id="positionsIcon"> <h3> Positions </h3> </div>
						<ul id="list">
							<li> <a href="reportPositions.jsp"> All Positions </a> </li>
							<li> <a href="searchPosition.jsp"> A Position </a> </li>
						</ul>
					 </div>
		</div>
		
	</div>	<!-- End middle content div -->
<div id="footer"></div>

</body>
</html>

<%
}
else
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>
