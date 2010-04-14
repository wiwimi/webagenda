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
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/helpGeneratedReport.js"></script>

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
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />

</head>
<body>
	 <div id="crumb">
	  <ul id="crumbsLonger">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><a href="reports.jsp">Reports</a></li>
	    <li><a href="searchLocation.jsp">Search a Loc to Report</a></li>
	    <li><a href="../wa_location/locSearchResults.jsp?locName=&locDesc=">Results</a></li>
	    <li><b><a href="#">Report Location</a></b></li>
	    
	   </ul>
     </div>

     
		<div id="locationWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="locationsUpperRectangle">
				<div class="widgetTitle" id="locationsWidgetTitle">Locations <div id="helpIcon"></div> </div>
			</div>
		       <div id="printerIcon">
					<a href="javascript:window.print()"> </a>
				</div>
				
				<%
					String locName = request.getParameter("locName");
					Location loc = new Location(locName);
					LocationBroker broker = LocationBroker.getBroker();
					broker.initConnectionThread();
					Location[] reported = broker.get(loc, user);
				%>
				
				<div id="excelIcon" >
					<a href="locationsxls.jsp?locName=<%=locName%>"> </a>
				</div>
				
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
				
				<div id="reportHeader">
					<div id="titleHeader">
						<h2 id="name"> Location Report: <%= reported[0].getName()%> </h2>
						<div id="date"><%= new java.util.Date()%></div>
					</div>
				</div>
				<%
					String descr ="NA";
					if (reported[0].getDesc()!=null)
						descr = reported[0].getDesc();
				%>
				<div id="report">
					<hr class="d" />
					<h3> Name: </h3>
					<div id="loc">
						<%= reported[0].getName()%>
					</div>
					<h3> Description:</h3>
					<div id="desc">
						<%= descr%>
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