<%@ page import="persistence.EmployeeBroker" %>
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
td
{
	float: left;
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

#userTable th
{
	line-height: 1.3em;
	padding: 7px 7px 8px;
	text-align: left;
}
-->
</style>
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
						<h1 id="name">Deerfoot Users </h1>
						<h2 id="date"><%= new java.util.Date()%></h2>
					</div>
</div>
				
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
											<%
													if(reportedArray[index].getPrefPosition()!=null)
													{
											%>
														<%=reportedArray[index].getPrefPosition()%> 
											<%
													}
													else
													{
											%>
														NA
											<%
													}
											%>
												</td>
												<td>
											<%
													if(reportedArray[index].getBirthDate()!=null)
													{
											%>
														<%=reportedArray[index].getBirthDate()%> 
											<%
													}
													else
													{
											%>
														NA
											<%
													}
											%>
												</td>
												<td>
											<%
													if(reportedArray[index].getEmail()!=null)
													{
											%>
														<%=reportedArray[index].getEmail()%> 
											<%
													}
													else
													{
											%>
														NA
											<%
													}
											%>
												</td>
												<td>
											<%
													if(reportedArray[index].getSupervisorID()!=-1)
													{
											%>
														<%=reportedArray[index].getSupervisorID()%> 
											<%
													}
													else
													{
											%>
														NA
											<%
													}
											%>
												</td>
										 </tr>
										<% 
											}
									     %>
									</tbody>
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
						</table>
						<div id="instructions" class="center">
							   		<b>End of Report </b>
							   		<div class="page-break"></div>
						</div>  