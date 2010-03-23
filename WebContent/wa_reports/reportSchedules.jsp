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
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="CSS/report.css" type="text/css"/>
<link rel="stylesheet" href="CSS/print.css" type="text/css" media="print"/>

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
					Location loc = new Location("Mohave Grill");
					Employee user = (Employee) session.getAttribute("currentEmployee");
					LocationBroker broker = LocationBroker.getBroker();
					Location[] reported = broker.get(loc, user);
					broker.initConnectionThread();
					
				%>
				
				<div id="reportHeader">
					<div id="locName">
						<h2 id="name"> <%= reported[0].getName()%> </h2>
						<h2 id="date"><%= new java.util.Date()%></h2>
					</div>
				</div>
				
				<div id="report">
				<hr></hr>
					<h3> Name: </h3>
					<div id="loc">
						<%= reported[0].getName()%>
					</div>
					<h3> Description:</h3>
					<div id="desc">
						<%= reported[0].getDesc()%>
					</div>
			   </div>
			   
			    <div id="instructions" class="center">
			   		End of Report
			   		<div class="page-break"></div>
			</div>  
		</div>  
		</div>      
                 
<div id="footer"></div>
</body>
</html>