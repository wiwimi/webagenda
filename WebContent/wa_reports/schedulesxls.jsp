<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<% response.setHeader("Content-Disposition", "attachment; filename=\"users-report.xls\""); %>                                                                                                           

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
	margin-right: 1.0em;
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
-->
</style>
<%
		Schedule sched = new Schedule();
		Employee user = (Employee) session.getAttribute("currentEmployee");
		sched.setCreatorID(user.getEmpID());
		ScheduleBroker broker = ScheduleBroker.getBroker();
		Schedule[] reported = broker.get(sched, user);
		
		Schedule st = reported[reported.length-1];
%>

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