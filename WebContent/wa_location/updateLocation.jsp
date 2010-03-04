<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating User</title>

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
		
		<div id="locationsWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="locationssWidgetUpperRectangle">
				<div class="widgetTitle" id="locationsTitle">Locations</div>
		</div>
			
		<div class="widgetLowerRectangle" id="locationsWidgetLowerRectangle">

			<div id="locationsIcon">
				<h3>Locations</h3>
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
							LocationBroker broker = LocationBroker.getBroker();
						
							Location loc = new Location("");
							
							Location[] locArray = broker.get(loc);
							
							for (Location printLoc : locArray)
							{
							System.out.println(printLoc);
							}
							
							
							for(int index = 0; index < locArray.length; index++)
							{
						%>
								<tr>
								<td>
											
									<a href="addLocation.jsp"><div id="locationImage"> <b> <%=locArray[index].getName()%> </b></div></a>
									<div class="row-actions"><span class='edit'>
									<a href="#"> Edit </a>   | </span>  <span class='delete'>
									<a class='submitdelete' href='#'>Delete</a></span></div>
								</td>
								<td>
								
								
									<%=locArray[index].getDesc()%>
								
								
								</td>
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