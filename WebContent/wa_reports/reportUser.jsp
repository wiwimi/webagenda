<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.Employee" %>
<%@ page import="java.util.*" %>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Report Location</title>

    <%
         Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
        if (user==null)
        {
        	response.sendRedirect("wa_login/login.jsp");
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
		        response.sendRedirect("wa_login/login.jsp");
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
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css" media="screen"/>
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
	  <ul id="crumbsLonger">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><a href="reports.jsp">Reports</a></li>
	    <li><a href="searchUser.jsp"> User Selection </a></li>
	    <li><a href="#">User's Report</a></li>
	   </ul>
    </div>
	<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">User Report <div id="helpIcon"></div></div>
			</div>
				<div id="printerIcon">
						<a href="javascript:window.print()"> </a>
				</div>
			<div class="widgetLowerRectangle" id="userLowerRectangle">
			
				<%
					Employee emp = new Employee();
				    String empId = request.getParameter("empId");
				    int empIdInt = Integer.parseInt(empId);
				    emp.setEmpID(empIdInt);
					EmployeeBroker broker = EmployeeBroker.getBroker();
					Employee[] reported = broker.get(emp, user);
					broker.initConnectionThread();
					
				%>
				<div id="reportHeader">
					<div id="userName">
						<h2 id="name">Employee ID: <%= reported[0].getEmpID()%> </h2>
						<div id="date"><%= new java.util.Date()%></div>
					</div>
				</div>
				
				<div id="report">
				
				<%
					String email ="NA", pos ="NA", birthDate="NA";
				if (reported[0].getEmail()!=null)
					email = reported[0].getEmail();
				if ( reported[0].getPrefPosition()!=null)
					pos =  reported[0].getPrefPosition();
				if(reported[0].getBirthDate()!=null)
					birthDate = reported[0].getBirthDate().toString();
				%>
				<hr></hr>
					<h3> Given Name: </h3>
					<div id="loc">
						<%= reported[0].getGivenName()%>
					</div>
					<h3> Family Name:</h3>
					<div id="loc">
						<%= reported[0].getFamilyName()%>
					</div>
					
					<h3> Employee ID:</h3>
					<div id="loc">
						<%= reported[0].getEmpID()%>
					</div>
					
					<h3> Email:</h3>
					<div id="loc">
						<%=email %>
					</div>
					
					<h3> Position:</h3>
					<div id="loc">
						<%=pos%>
					</div>
					
					<h3> Birth Date:</h3>
					<div id="loc">
						<%= birthDate%>
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