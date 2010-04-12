<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.ScheduleBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.schedule.Shift" %>
<%@ page import="business.schedule.Schedule" %>
<%@ page import="business.Employee" %>
<%@ page import="java.util.*" %>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<!--Author: Noorin-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Agenda- Updating Location</title>

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

<!-- Plug-ins -->
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/sorttable.js" ></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/deleteLocation.js"></script>
<script type="text/javascript" src="../lib/js/helpSelectSchedule.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="CSS/tableRows.css" ></link>
<link rel="stylesheet" type="text/css" href="CSS/icons.css" ></link>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css"  media="screen" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />

</head>
<body>
	  <div id="crumb">
	  <ul id="crumbsLonger">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><a href="reports.jsp">Reports</a></li>
	    <li><b><a href="#">Report Available Schedules</a></b></li>
	   </ul>
     </div>
	
		
	   <div id="scheduleWidget" class="fullWidget"> 
			<div class="widgetUpperRectangle" id="locationsUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle"> Report Schedules <div id="helpIcon"> </div></div>
			</div>
			<div class="widgetLowerRectangle" id="scheduleLowerRectangle">
				<div id="locationsIcon">
						<h3>Schedules </h3>
				</div>
				
				<div id="instructions">
					Select a schedule to report. Click on column headers to sort data through.
				</div>

				<% 
					        Schedule sched = new Schedule();
						   	sched.setCreatorID(user.getEmpID());
						    ScheduleBroker broker = ScheduleBroker.getBroker();
							Schedule[] reported = broker.get(sched, user);
							
							Schedule st = reported[reported.length-1];
							
							if (reported==null)
							{
						%>
						         <div id="instructions">
						      		There are no schedules in the system. <br>
						      	</div>
						<%
							}
							else
							{
								
								    
						%>    
						 
						 <div id="tableArea">
									<div class="userAdmin">
										<table class="sortable" id="userTable">
											<thead class="head">
												<tr class="headerRow">
													<th>Start Date</th>
													<th>End Date </th>
												</tr>
											</thead>
											<tfoot class="foot">
												<tr class="headerRow">
													<th>Start Date</th>
													<th>End Date </th>
												</tr>
											</tfoot>
											<tbody>
						
						<%
									for(int i = 0; i < reported.length; i++)
									{
										st=reported[i];
										
						%>	
									  <tr>
										<td>
											<a href="reportSchedules.jsp?startDate=<%=st.getStartDate()%>&endDate=<%=st.getEndDate() %>"><%=st.getStartDate()%></a>
											<div class="row-actions">
												 <span class='report'> <a href="reportSchedules.jsp?startDate=<%=st.getStartDate()%>&endDate=<%=st.getEndDate()%>"> | Report
											      </a> </span>
										    </div>
										 </td>
										<td>
											<a href="reportSchedules.jsp?startDate=<%=st.getStartDate()%>&endDate=<%=st.getEndDate() %>"><%=st.getEndDate()%></a>
											<div class="row-actions">
												 <span class='report'> <a href="reportSchedules.jsp?startDate=<%=st.getStartDate()%>&endDate=<%=st.getEndDate()%>"> | Report
											      </a> </span>
										    </div>
										 </td>
									</tr>
						<% 
									} 
							  }
					  
						 
					    %>
								</tbody>
							</table>
						</div>
					</div> <!-- End tableArea -->
				</div> <!-- End widgetLowerRectangle -->
			</div> <!-- End locationsWidget -->
<div id="footer"></div>
</body>
</html>


