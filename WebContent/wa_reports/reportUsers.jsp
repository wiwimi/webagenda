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
Only employees who are active in the system are displayed in this report.
</div>
<body>
	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="usersUpperRectangle">
					<div class="widgetTitle" id="userTitle">Report Users</div>
				</div>
				<div id="printerIcon">
					<h3></h3>
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
						<h2 id="name">All Users </h2>
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
											<td>
												<%=reportedArray[index].getPrefPosition()%> 
											</td>
											<td>
												<%=reportedArray[index].getBirthDate()%> 
											</td>
											<td>
												<%=reportedArray[index].getEmail()%> 
											</td>
											<td>
												<%=reportedArray[index].getSupervisorID()%> 
											</td>
										</tr>
								<% 
									}
								%>
								</tbody>
						</table>
					</div>
			</div> <!-- End Div Report-->
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