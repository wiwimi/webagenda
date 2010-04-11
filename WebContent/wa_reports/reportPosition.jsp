<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.schedule.Position" %>
<%@ page import="business.Skill" %>
<%@ page import="business.Employee" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Report Position</title>

<%
	Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
	if (user.getLevel()==99)
	{
%>
	<!--  Includes -->
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
					<div class="widgetTitle" id="positionTitle">Report Position</div>
				</div>
				<div id="printerIcon">
					<a href="javascript:window.print()"> </a>
				</div>
			<div class="widgetLowerRectangle" id="positionLowerRectangle">
			
				<%
					String posName = request.getParameter("posName");
					Position pos = new Position(posName);
					PositionBroker broker = PositionBroker.getBroker();
					Position[] reported = broker.get(pos, user);
					broker.initConnectionThread();
					String descr = "NA";
					
					if(pos.getDescription()!=null)
					descr = pos.getDescription();
					
				%>
				
				<div id="reportHeader">
					<div id="titleHeader">
						<h2 id="name"> <%= reported[0].getName()%> </h2>
						<div id="date"><%= new java.util.Date()%></div>
					</div>
				</div>
				
				<div id="report">
				<hr></hr>
					<h3> Name: </h3>
					<div id="pos">
						<%= reported[0].getName()%>
					</div>
			   <h3> Description:</h3>
					<div id="pos">
						<%= descr%>
					</div>
					
					<h3> Skills:</h3>
					
					<div id="skills">
						<%
							Skill[] skills = reported[0].getPos_skills();
							if (skills!=null)
							{
								for (int i=0; i<skills.length; i++)
								{
							%>
									<li>
										<ul>
											<%=skills[i].getName()%>
										</ul>
									</li>
							<%
								}
							}
							else if(skills==null)
							{
							%>
								No skills assigned.
							<%
							}
							%>
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