<%@ page import="persistence.ScheduleBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.schedule.Shift" %>
<%@ page import="business.schedule.Schedule" %>
<%@ page import="business.Employee" %>
<%@ page import="java.util.*" %>

<%-- Author: Noorin --%>
<%-- Set the content type header with the JSP directive --%>
<%@ page contentType="application/vnd.ms-excel" %>
                                                                                                                   
<%-- Set the content disposition header --%>
<% response.setHeader("Content-Disposition", "attachment; filename=\"schedules-report.xls\""); %>                                                                                                           

<%-- Can't import CSS Files thus the style was explicitly identified below --%>
<style type="text/css">
<!--
#titleHeader h2
{
	font-size: 100%;
	font-family: Arial,  Verdana, Helvetica, sans-serif;
	display: block;
	display: inline-block;
	line-height: 1.8;
	vertical-align: top;
	width:25%;
}
#report h3
{
	font-size: 100%;
	font-family: Arial,  Verdana, Helvetica, sans-serif;
	display: block;
	display: inline-block;
	width:25%;
}
#name
{	
	float:left;
	margin-left: 4.0em;
	font-size: 100%;
	font-family: Arial,  Verdana, Helvetica, sans-serif;
}
#report
{
	margin-left: 4.0em;
	font-size: 100%;
	font-family: Arial,  Verdana, Helvetica, sans-serif;
}
#loc, #desc, #pos
{
	margin-left: 2.0em;
	font-size: 100%;
}
#date
{
	float:right;
	margin-right: 5.0em;
}
#reportHeader
{
	
	width: 100%;
	height: 70px;
	background-color:#F0F0F0;
}
.center
{
	margin:1% 0px; padding:0px;
	text-align:center;
}
#tableArea
{
	position:relative;
	left:2.5%;
	width:95%;
}

#userTable
{
	-webkit-border-bottom-left-radius: 4px 4px;
	-webkit-border-bottom-right-radius: 4px 4px;
	border-spacing: 0px 0px;
	-webkit-border-top-left-radius: 4px 4px;
	-webkit-border-top-right-radius: 4px 4px;
	border-style: solid;
	border-width: 1px;
	clear: both;
	margin: 0px;
	width: 100%;
}

tr
{
	border-style: solid;
	border-width: 1px;
	border-color: #F0F0F0;
	clear: both;
}
thead
{
	border-color: inherit;
	display: table-header-group;
	background-color:#F0F0F0 ;
	color:black;
}
tfoot
{
	border-color: inherit;
	display: table-footer-group;
	background-color:#F0F0F0 ;
	color:black;
}
tbody
{
	border-color: inherit;
	display: table-row-group;
	color:black;
}
#usertable tr, .usertable.th
{
	border-bottom-style: solid;
	border-bottom-width: 1px;
	font-size: 11px;
	vertical-align: text-top;
}
td
{
	float: right;
}

#userTable th
{
	line-height: 1.3em;
	padding: 7px 7px 8px;
	text-align: left;
}
.sched
{
	float:left;
	margin-left: 4.0em;
	font-size: 100%;
	font-family: Arial,  Verdana, Helvetica, sans-serif;
	
}
.bold
{
	font-weight:bold;
}
#left
{
	float:left;
	margin-right:0%;
}
#right
{
	float:right;
	margin-left:10px;
}
-->
</style>
<%
	Schedule sched = new Schedule();
	Employee user = (Employee) session.getAttribute("currentEmployee");
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

		     <div id="reportHeader">
				<div id="titleHeader">
					<h2 id="name">Schedule Report:  </h2>
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
													<table class="sortable" id="userTable">
														<thead class="head">
															<tr class="headerRow">
																<th>Shift - Day: <%=shift.getDay() %></th>
																<th colspan=4>Time</th>
															</tr>
														</thead>
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
											  		    <tfoot class="foot">
															<tr class="headerRow">
																<th>Shift - Day: <%=shift.getDay() %></th>
																<th colspan=4>Time</th>
															</tr>
														</tfoot>
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