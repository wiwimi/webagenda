<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Report Employees</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script src="../lib/js/zebraTable.js" type="text/javascript" ></script>
<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="../CSS/creationForm.css"></link>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="CSS/report.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="CSS/print.css" media="print"/>
<link rel="stylesheet" type="text/css" href="CSS/table.css" media="screen"></link>

</head>
<body>
	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="usersUpperRectangle">
					<div class="widgetTitle" id="userTitle">Report Disabled Users</div>
				</div>
				<div id="printerIcon">
						<a href="javascript:window.print()"> </a>
				</div>
				
				<div id="excelIcon" >
					<a href="usersxls.jsp"> </a>
				</div>
				
			<div class="widgetLowerRectangle" id="userLowerRectangle">
				<%
					Employee emp = new Employee();
					emp.setActive(true);
					Employee user = (Employee) session.getAttribute("currentEmployee");
					EmployeeBroker broker = EmployeeBroker.getBroker();
					broker.initConnectionThread();
					Employee[] reportedArray = broker.get(emp, user);
				%>
				
				<div id="reportHeader">
					<div id="titleHeader">
						<h2 id="name">All Disabled Users </h2>
						<div id="date"><%= new java.util.Date()%></div>
					</div>
				</div>
				
				<div id="report">
					<hr/>
					<div id="tableArea">
					<div class="userAdmin">
						<table class="sortable" id="userTable">
							<thead class="head">
								<tr class="headerRow">
									<th>Given Name</th>
									<th>Family Name </th>
									<th>Employee ID</th>
									<th>Position </th>
									<th>DOB </th>
									<th>Email</th>
									<th>Supervisor ID</th>
								</tr>
							</thead>
							<tfoot class="foot">
								<tr class="headerRow">
									<th>Given Name</th>
									<th>Family Name </th>
									<th>Employee ID</th>
									<th>Position </th>
									<th>DOB </th>
									<th>Email</th>
									<th>Supervisor ID</th>
								</tr>
							</tfoot>
							<tbody>
								<% 
									for(int index = 0; index < reportedArray.length; index++)
									{
								%>
										<tr>
											<td>
												<%=reportedArray[index].getGivenName()%> 
											</td>
											<td>
												<%=reportedArray[index].getFamilyName()%> 
											</td>
											<td>
												<%=reportedArray[index].getEmpID()%> 
											</td>
										 <%
										 	if (reportedArray[index].getPrefPosition()!=null)
										 	{
										 %>
												<td>
													<%=reportedArray[index].getPrefPosition()%> 
												</td>
										<%
										 	}
										 	else
										 	{
										%>
												<td> NA </td>
										<%
											}
										 	if (reportedArray[index].getBirthDate()!=null)
										 	{
										%>
												<td>
													<%=reportedArray[index].getBirthDate()%> 
												</td>
										<%
										 	}
										 	else
										 	{
										 %>
										 		<td> NA </td>
										 <%
										 	}
										 	if (reportedArray[index].getEmail()!=null)
										 	{
										 %>
												<td>
													<%=reportedArray[index].getEmail()%> 
												</td>
										<%
										 	}
										 	else
										 	{
										%>
												<td> NA </td>
										<%  }
										 	if(reportedArray[index].getSupervisorID()!=-1)
										 	{
										%>
												<td>
													<%=reportedArray[index].getSupervisorID()%> 
												</td>
										<% 
										 	}
										 	else
										 	{
										 %>
										 	<td> NA </td>
										<%  
											}
										%>
											</tr>
										<% 
										
										   }
									    %>
								</tbody>
						</table>
					</div>
			</div> <!-- End Div Report-->
		</div>
		<div id="endInstructions" class="center">
		   		End of Report
		</div>  
		<!-- Used to display page counters at the bottom of the page while printing -->
		<div id="bottom"> <h2></h2></div>
		<div class="page-break"></div>
	</div>  
</div>      
<div id="footer"></div>
</body>
</html>