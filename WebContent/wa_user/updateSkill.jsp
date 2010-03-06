<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="business.Skill" %>
<%@ page import="persistence.SkillBroker" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating Skill </title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/skill.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>


<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

</head>
<body>
<br></br>
		
		<div id="skillsWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="skillsWidgetUpperRectangle">
				<div class="widgetTitle" id="skillsTitle">Skills</div>
		</div>
			
		<div class="widgetLowerRectangle" id="skillsWidgetLowerRectangle">

			<div id="skillsIcon">
				<h3>Skills</h3>
			</div>
			
			<div id="searchArea">
				<input type="text" value="" size=30></input><button type="submit" value="Search">Search</button>
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
							SkillBroker broker = SkillBroker.getBroker();
							
							Skill skill = new Skill("");
							Skill[] skillArray = broker.get(skill);
							
							for(int index = 0; index <skillArray.length; index++)
							{
						%>
							<tr>
							   <td>
											
									<a href="addSkill.jsp?=<%=skillArray[index].getName()%>"> <b> <%=skillArray[index].getName()%> </b></div></a>
									<div class="row-actions"><span class='edit'>
									<a href="#"> Edit </a>   | </span>  <span class='delete'>
									<a class='submitdelete' href='#'>Delete</a></span></div>
								</td>
								<td>
									<a href="addSkill.jsp?=<%= skillArray[index].getName() %>"> <%=skillArray[index].getDesc()%> </a>
								</td>
							</tr>
						<% 
							}
						%>			
								
					</tbody>
				</table>
				
			</div>
			
			</div> <!-- End Table Area -->
			
		</div>
</div>
<div id="footer"></div>

</body>
</html>
