<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="business.Position" %>
<%@ page import="persistence.PositionBroker" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating Position </title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
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
			<div class="widgetUpperRectangle" id="positionssWidgetUpperRectangle">
				<div class="widgetTitle" id="positionsTitle">Positions</div>
		</div>
			
		<div class="widgetLowerRectangle" id="positionsWidgetLowerRectangle">

			<div id="skillsIcon">
				<h3>Positions</h3>
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
							PositionBroker broker = PositionBroker.getBroker();
							
							Position pos = new Position("");
							Position[] posArray=null;
							//broker.create(pos);
							
							for(int index = 0; index <posArray.length; index++)
							{
						%>
							<tr>
							   <td>
											
									<a href="newPosition.jsp?=<%=posArray[index].getName()%>"> <b> <%=posArray[index].getName()%> </b></div></a>
									<div class="row-actions"><span class='edit'>
									<a href="#"> Edit </a>   | </span>  <span class='delete'>
									<a class='submitdelete' href='#'>Delete</a></span></div>
								</td>
								<td>
									<a href="newPosition.jsp?=<%= posArray[index].getName() %>"> <%=posArray[index].getDesc()%> </a>
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
