<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="business.Skill" %>
<%@ page import="persistence.SkillBroker" %>
<%@ page import="business.Employee" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating Skill </title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!-- Plug-ins -->
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Confirmation/confirm.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Flash/flashmessenger.css" />

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->

<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/deleteSkill.js"></script>

</head>
<body>
<br></br>
			 <% 
					if(request.getParameter("delete") != null)
					{
						if(request.getParameter("delete").equals("true"))
						{
			 %>
							<script type="text/javascript">
											$(function()
										    {
												
												    $.flashMessenger("The skill has been successfully deleted", 
													{ 	
														modal:true, 
														autoClose: false 
													});	
											});
							</script>
			   <% 			   
						 }
						else if(request.getParameter("delete").equals("false"))
						{
				%>
							<script type="text/javascript">
								$(function()
								    {
										$.flashMessenger("An error occured while deleting the Skill. Make sure the skill is not attached to a position",
								        {
											   modal:true,
							    		       clsName:"err", 
								    		   autoClose:false
								    	 }); 
								   }); 
							</script>
				<%
						}
					}
				%>

	<div id="skillsWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="skillsWidgetUpperRectangle">
				<div class="widgetTitle" id="skillsTitle">Skills</div>
		</div>
		<div class="widgetLowerRectangle" id="skillsWidgetLowerRectangle">
		<div id="skillsIcon">
				<h3>Skills</h3>
			</div>
			<div id="searchArea">
			<form id="form">
				<input type="text" size=30/ name="skillName">
				<input type="submit" name="search"  class="button" value="Search" onClick="location.href='newSkill.jsp?skillName=' + form.skillName.value"> 
			</form>
			</div>
			<div id="tableArea">
							<div class="userAdmin">
				<table class="sortable" id="userTable">
					<thead class="head">
						<tr class="headerRow">
							<th>Name</th>
							<th>Description</th>
						</tr>
					</thead>
					<tfoot class="foot">
						<tr class="headerRow">
							<th>Name</th>
							<th>Description</th>
						</tr>
					</tfoot>
					<tbody>
						<% 
							Employee user = (Employee) session.getAttribute("currentEmployee");
							SkillBroker broker = SkillBroker.getBroker();
							Skill skill = new Skill("");
							Skill[] skillArray = broker.get(skill, user);
							
							for(int index = 0; index<skillArray.length; index++)
							{
						%>
							<tr>
							   <td>
									<a href="update.jsp?=<%=skillArray[index].getName()%>"> <b> <%=skillArray[index].getName()%> </b></div></a>
									<div class="row-actions"><span class='edit'>
									<a href="updateSkill.jsp?skillName=<%=skillArray[index].getName()%>&skillDesc=<%= skillArray[index].getDesc()%> "> Edit </a>   | </span>   <span class='delete'>
									<a href="javascript:;" onClick="removeSkill('<%=skillArray[index].getName()%>', '<%=skillArray[index].getDesc()%>' );">
										Delete</a></span></div>
								</td>
								<td>
								     <%
								     	if(skillArray[index].getDesc()!=null && !skillArray[index].getDesc().equals(""))
								     	{
								     %>
								     		<a href="newSkill.jsp?=<%= skillArray[index].getName() %>"> <%=skillArray[index].getDesc()%> </a>
								     <%	}
								     	else
								     	{
								     %>
								     		<a href="newSkill.jsp?=<%= skillArray[index].getName() %>"> None </a>
								     <%
								     	} 
							 }
								     %>
						  </td>	
					</tbody>
				</table>
			</div>
		</div> <!-- End Table Area -->
	</div>
</div>
<div id="footer"></div>
</body>
</html>

		
												
												
