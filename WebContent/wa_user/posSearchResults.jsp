<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="business.schedule.Position" %>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.Employee" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating Position </title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!-- Plug-ins -->
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>	

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>
<script type="text/javascript" src="../lib/js/deletePosition.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Confirmation/confirm.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Flash/flashmessenger.css" />
</head>
<body>
			<% 
					if(request.getParameter("delete") != null)
					{
						if(request.getParameter("delete").equals("true"))
						{
			 %>
							<script type="text/javascript">
											$(function()
										    {
												
												    $.flashMessenger("The position has been successfully deleted", 
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
										$.flashMessenger("An error occured while deleting the Position. Please contact your admin",
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
			<div class="widgetUpperRectangle" id="positionssWidgetUpperRectangle">
				<div class="widgetTitle" id="positionsTitle">Positions</div>
		</div>
			
		<div class="widgetLowerRectangle" id="positionsWidgetLowerRectangle">

			<div id="skillsIcon">
				<h3>Positions</h3>
			</div>
			
			<div id="searchArea">
			<form id="form">
				<input type="text" size=30/ name="posName">
				<input type="submit" name="search"  class="button" value="Search" onClick="location.href='newPosition.jsp?posName=' + form.posName.value"> 
			</form>
			</div>
			
							<% 
								Employee user = (Employee)session.getAttribute("currentEmployee");	
							
								PositionBroker broker = PositionBroker.getBroker();
								Position pos = new Position("",null);
								Position[] posArray = broker.get(pos, user);
								
								if(posArray==null)
								{
							%>
									<div id="instructions">
										Your search didn't match any positions.<br>
							      		For better results try more general fields and make sure all words are spelled correctly.
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
									for(int index = 0; index <posArray.length; index++)
									{
							%>
										<tr>
										   <td>
												<a href="newPostion.jsp?=<%=posArray[index].getName()%>"> <b> <%=posArray[index].getName()%> </b></a>
												<div class="row-actions"><span class='edit'>
												<a href="updatePosition.jsp?posName=<%=posArray[index].getName()%>&posDesc=<%=posArray[index].getDescription()%>" > Edit </a>   | </span>  <span class='delete'>
												<a href="javascript:;" onClick="removePosition('<%=posArray[index].getName()%>');">
													Delete</a></span></div>
											</td>
											<td>
											     <%
											     	if(posArray[index].getDescription()!=null && posArray[index].getDescription().equals(""))
											     	{
											     %>
											     		<a href="newPosition.jsp?=<%= posArray[index].getName() %>"> <%=posArray[index].getDescription()%> </a>
											     <%	}
											     	else
											     	{
											     %>
											     		<a href="newPosition.jsp?=<%= posArray[index].getName() %>"> None </a>
											     <%
											     	} 
											     %>
									
													
											</td>
										</tr>
						<% 
									}
							}
						%>			
					</tbody>
				</table>
			</div>
		</div> 
	</div>
</div>
<div id="footer"></div>
</body>


</html>


