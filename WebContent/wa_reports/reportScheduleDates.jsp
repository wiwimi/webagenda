<% 
	if(session.getAttribute("username") == null)
	{
		response.sendRedirect("../wa_login/login.jsp");
	}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.ScheduleBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.schedule.Shift" %>
<%@ page import="business.schedule.Schedule" %>
<%@ page import="business.Employee" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Report Schedules</title>

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
					<div class="widgetTitle" id="locationTitle">Report Schedule</div>
	   </div>
		<div id="printerIcon">
			<h3></h3>
		</div>
		<%
			Schedule sched = new Schedule();
		   	Employee user = (Employee) session.getAttribute("currentEmployee");
		   	sched.setCreatorID(user.getEmpID());
		    ScheduleBroker broker = ScheduleBroker.getBroker();
			Schedule[] reported = broker.get(sched, user);
			
			Schedule st = reported[reported.length-1];
			
		%>
		
			<div id="printerIcon">
				<a href="javascript:window.print()"> </a>
			</div>
				
			<div id="excelIcon" >
				<a href="schedulesxls.jsp"> </a>
			</div>
		
		<div class="widgetLowerRectangle" id="reportLowerRectangle">
		
		<div id="reportHeader">
							<div id="titleHeader">
								<h2 id="name">Schedules Report:  </h2>
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
														<th>Start Date : </th>
														<th> End Date:</th>
													</tr>
												</thead>
												<tfoot class="foot">
													<tr class="headerRow">
														<th>Start Date : </th>
														<th> End Date:</th>
													</tr>
												</tfoot>
												<tbody>
			<%
				for (int i=0; i<reported.length; i++)
				{
					st=reported[i];
		    %>									
							  		<tr>
										<td><%=st.getStartDate()%></td>
										<td><%= st.getEndDate()%>  </td>
					  				</tr>
		    <%
				}
		    %>
		    	  		</tbody>
					  </table>
					</div>
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