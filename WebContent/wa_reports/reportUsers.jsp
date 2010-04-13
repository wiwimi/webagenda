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
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Report Employees</title>

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

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>


<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/helpGeneratedReport.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="CSS/report.css" media="screen">
<link rel="stylesheet" type="text/css" href="CSS/print.css"  media="print"/>
<link rel="stylesheet" type="text/css" href="CSS/table.css"  media="screen"/>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Popup/popup.css"/>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />

</head>
<body>

	 <div id="crumb">
	  <ul id="crumbs">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><a href="reports.jsp">Reports</a></li>
	    <li><b><a href="#">Report Enabled Users</a></b></li>
	   </ul>
     </div>
	<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">Users Report <div id="helpIcon"></div></div>
			</div>
				<div id="printerIcon">
						<a href="javascript:window.print()"> </a>
				</div>
				
				<div id="excelIcon" >
					<a href="usersxls.jsp?active=true"> </a>
				</div>
				
			<div class="widgetLowerRectangle" id="userLowerRectangle">
				<%
					Employee emp = new Employee();
					emp.setActive(true);
					EmployeeBroker broker = EmployeeBroker.getBroker();
					broker.initConnectionThread();
					Employee[] reportedArray = broker.get(emp, user);
				%>
				
				<div id="reportHeader">
					<div id="titleHeader">
						<h2 id="name">All Enabled Users </h2>
						<div id="date"><%= new java.util.Date()%></div>
					</div>
				</div>
				
				<div id="report">
					<hr/>
					<div id="tableArea">
					<div class="userAdmin">
						<table class="sortable" id="userTable" border="1">
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
		   		<div class="page-break"></div>
		</div>  
	</div>  
</div>      
<div id="footer"></div>
</body>
</html>