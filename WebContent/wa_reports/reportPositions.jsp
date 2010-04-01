<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="business.schedule.Position" %>
<%@ page import="business.Skill" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Report Positions</title>

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
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>

</head>

<div id="instructions">
Only the first 110 characters are displayed for the description column. For further information about a particular location,
please generate a report for that particular location.
</div>
<body>

	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="locationsUpperRectangle">
					<div class="widgetTitle" id="locationTitle">Report Locations</div>
				</div>
				<div id="printerIcon">
					<a href="#"> </a>
				</div>
				
				<div id="excelIcon" >
					<a href="positionsxls.jsp"> </a>
				</div>
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
				<%
					Position pos = new Position("");
					Employee user = (Employee) session.getAttribute("currentEmployee");
					PositionBroker broker = PositionBroker.getBroker();
					broker.initConnectionThread();
					Position[] reportedArray = broker.get(pos, user);
				%>
				
				<div id="reportHeader">
					<div id="titleHeader">
						<h2 id="name">All Positions </h2>
						<h2 id="date"><%= new java.util.Date()%></h2>
					</div>
				</div>
				
				<div id="report">
					<hr/>
					<div id="tableArea">
					<div class="userAdmin">
						<table class="sortable" id="userTable">
							<thead class="head">
								<tr class="headerRow">
									<th>Name</th>
									<th> Description</th>
								</tr>
							</thead>
							<tfoot class="foot">
								<tr class="headerRow">
									<th>Name</th>
									<th> Description</th>
								</tr>
							</tfoot>
							<tbody>
								<% 
									for(int index = 0; index < reportedArray.length; index++)
									{
										
								%>
										<tr>
											<td>
												<%=reportedArray[index].getName()%> 
											</td>
										     <td>
								<%
										if (reportedArray[index].getDescription()!=null)
										{
											if (reportedArray[index].getDescription().length()>2)
											{
								%>	
												<%=reportedArray[index].getDescription().substring(0, 1)%>
								<%
											}
										
											else
											{
								%>
												<%=reportedArray[index].getDescription()%>
								<%
											}
										}
										else
										{
								%>	
											NA
								<%	
										}
								%>	
											</td>
										</tr>
								<% 
									}
								%>
								</tbody>
						</table>
					</div>
			</div> <!-- End Div Report -->	
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