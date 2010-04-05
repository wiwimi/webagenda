<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.Employee" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Report Location</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="../CSS/creationForm.css"></link>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="CSS/report.css" media="screen">
<link rel="stylesheet" type="text/css" href="CSS/print.css"  media="print"/>
<link rel="stylesheet" type="text/css" href="CSS/table.css"  media="screen"/>

</head>
<body>
	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="locationsUpperRectangle">
					<div class="widgetTitle" id="locationTitle">Report Location</div>
				</div>
				<div id="printerIcon">
					<h3></h3>
				</div>
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
				<%
					String locName = request.getParameter("locName");
					Location loc = new Location(locName);
					Employee user = (Employee) session.getAttribute("currentEmployee");
					LocationBroker broker = LocationBroker.getBroker();
					broker.initConnectionThread();
					Location[] reported = broker.get(loc, user);
				%>
				
				<div id="reportHeader">
					<div id="titleHeader">
						<h2 id="name"> <%= reported[0].getName()%> </h2>
						<div id="date"><%= new java.util.Date()%></div>
					</div>
				</div>
				
				<div id="report">
					<hr class="d" />
					<h3> Name: </h3>
					<div id="loc">
						<%= reported[0].getName()%>
					</div>
					<h3> Description:</h3>
					<div id="desc">
						<%= reported[0].getDesc()%>
					</div>
			    </div>
			   
			    <div id="endInstructions" class="center">
			   		End of Report
			   		<div class="page-break"></div>
			</div>  
		</div>  
		</div>      
                 
<div id="footer"></div>
</body>
</html>