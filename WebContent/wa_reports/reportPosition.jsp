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
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Report Position</title>

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
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
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

</head>
<body>
     <div id="crumb">
	  <ul id="crumbsLonger">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><a href="reports.jsp">Reports</a></li>
	    <li><a href="searchPosition.jsp">Search a Position to Report</a></li>
	    <li><a href="../wa_user/posSearchResults.jsp?posName=&posDesc=">Results</a></li>
	    <li><b><a href="#">Report Position</a></b></li>
	    
	   </ul>
     </div>
		<div id="positionWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="positionUpperRectangle">
				<div class="widgetTitle" id="positionsWidgetTitle">Report Position  <div id="helpIcon"></div></div>
			</div>
			
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
				<div id="printerIcon">
					<a href="javascript:window.print()"> </a>
				</div>
				
				<div id="excelIcon" >
					<a href="positionsxls.jsp?posName=<%=posName%>"> </a>
				</div>
				
			<div class="widgetLowerRectangle" id="positionLowerRectangle">
			   <div id="reportHeader">
					<div id="titleHeader">
						<h2 id="name"> Position Report: <%= reported[0].getName()%> </h2>
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