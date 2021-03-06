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
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- All Schedules</title>

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
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="CSS/report.css" media="screen">
<link rel="stylesheet" type="text/css" href="CSS/print.css"  media="print"/>
<link rel="stylesheet" type="text/css" href="CSS/table.css"  media="screen"/>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Popup/popup.css"/>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
<link rel="stylesheet" type="text/css" href="CSS/icons.css" ></link>
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />


</head>
<body>

<div id="crumb">
	  <ul id="crumbs">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><a href="reports.jsp">Reports</a></li>
	    <li><b><a href="#">Report Schedules</a></b></li>
	   </ul>
     </div>
     
	<div id="scheduleWidget" class="fullWidget"> 
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Report Schedules <div id="helpIcon"> </div></div>
			</div>
		<div id="printerIcon">
			<h3></h3>
		</div>
		<%
			Schedule sched = new Schedule();
		   	sched.setCreatorID(user.getEmpID());
		    ScheduleBroker broker = ScheduleBroker.getBroker();
			
			String schedID = request.getParameter("schedID");
			
			if(schedID!=null)
			{
				sched.setSchedID(Integer.parseInt(schedID));
			}
			
			Schedule[] reported = broker.get(sched, user);
			Schedule st = reported[reported.length-1];
			Shift[] shiftList = st.getShifts().toArray();
		%>
		
			<div id="printerIcon">
				<a href="javascript:window.print()"> </a>
			</div>
		<% 	
			if(schedID!=null)
			{
		%>	
				<div id="excelIcon" >
					<a href="schedulesxls.jsp?schedID=<%=schedID%>"> </a>
				</div>
		<%
			}
			else
			{
		%>
				<div id="excelIcon" >
					<a href="schedulesxls.jsp"> </a>
				</div>
		<%	
			}
		%>
		
		<div class="widgetLowerRectangle" id="reportLowerRectangle">
		
	  <div id="reportHeader">
				<div id="titleHeader">
					<h2 id="name">Schedules Report  </h2>
					<div id="date"><%= new java.util.Date()%></div>
				</div>
		</div>
		
		<%
				for (int i=0; i<reported.length; i++)
				{
					
		%>
				<div class="sched">
						<div id="left"><div class="bold"> Start Date:</div><%=st.getStartDate() %> </div>
						<div id="right"><div class="bold">End Date:</div><%= st.getEndDate()%></div> 
				    </div> 
				   <br><br>
					<div>		
					
					  <%
							for (Shift shift : shiftList)
							{
								//System.out.println("\tShift - Day: "+shift.getDay()+" - Time: "+shift.getStartTime() + " to " + shift.getEndTime());
								
								Employee[] emps = shift.getEmployees().toArray();
						%>
						<div id="report">
						     <hr/>
							<div id="tableArea">
												<div class="userAdmin">
													<table class="sortable" id="userTable" border="1">														<thead class="head">
															<tr class="headerRow">
																<th>Shift - Day: <%=shift.getDay() %></th>
																<th colspan=4>Time</th>
															</tr>
														</thead>
														<tfoot class="foot">
															<tr class="headerRow">
																<th>Shift - Day: <%=shift.getDay() %></th>
																<th colspan=4>Time</th>
															</tr>
														</tfoot>
														<tbody>
															<tr id="colored">
																<th>Employee</th>
																<th><%= shift.getStartTime()%> - <%=shift.getEndTime()%></th>
															</tr>	
											  		<%
														  	for (Employee emp : emps)
															{
																//System.out.println("\t\tApplies to: "+emp);
											  		%>
												  				<tr>
																	<td><%=emp.getGivenName() %> , <%=emp.getFamilyName() %> </td>
																	<td id="center"><%= emp.getPrefPosition()%> / <%= emp.getPrefLocation()%>  </td>
												  				</tr>
											  		<%
														  	}
											  		%>
											  		    
														</tbody>
												</table>
											</div>
									</div>
						       </div>  
				    <%
								}
							  }
				    %>
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