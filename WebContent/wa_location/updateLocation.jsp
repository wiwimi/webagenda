<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>

<!--Author: Noorin-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Agenda- Updating Location</title>
<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

<!-- Plug-ins -->
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Confirmation/confirm.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Flash/flashmessenger.css" />


<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

</head>
<body>
<br></br>

		
 			  <% 
					if(request.getParameter("message") != null)
					{
						if(request.getParameter("message").equals("true"))
						{
			  %>
				
							<script type="text/javascript">
		
								$(function()
								    {
										
										    $.flashMessenger("The location has been successfully deleted", 
											{ 	
												modal:true, 
												autoClose: false 
											});	
										
										
								    });
								</script>
			
			    <% 			   
						}
						else if(request.getParameter("message").equals("false"))
						{
				%>
							
							<script type="text/javascript">
								$(function()
								    {
										
								       $.flashMessenger("An error occured while deleting the location. Please contact your admin",
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
			 <div id="locationWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="locationsUpperRectangle">
				<div class="widgetTitle" id="locationsWidgetTitle">Locations</div>
			</div>
			<div class="widgetLowerRectangle" id="locationsLowerRectangle">
				<div id="locationsIcon">
						<h3>Locations</h3>
				</div>
				<div id="searchArea">
						<input type="text" size=30/>
						<input type="submit" name="search"  class="button" value="Search" > 
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
									Location loc= null;
									if(request.getParameter("locName").equals(null))
									{
										
										loc = new Location("");
									}
									else
									{
										loc =new Location(request.getParameter("locName"));
									}
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
												<a href="javascript:;" onClick="removeLocation('<%=locArray[index].getName()%>');">
													Delete
												</a></span></div>
											</td>
											<td>
												<a href="addLocation.jsp?=<%=locArray[index].getName()%>"> <%=locArray[index].getDesc()%> </a>
											</td>
									<% 
										}
									
									%>			
								</tbody>
							</table>
						</div>
					</div> <!-- End tableArea -->
				</div> <!-- End widgetLowerRectangle -->
			</div> <!-- End locationsWidget -->
			
			
<div id="footer"></div>

	<script type="text/javascript">
				function removeLocation(id)
				{
					var txt = 'Are you sure you want to remove this Location?<input type="hidden" id="locName" name="locName" value="'+ id +'" />';
					
					$.prompt(txt,{buttons:{Delete:true, Cancel:false},
						callback: function(v,m,f){
							
							if(v) 
							{
								window.location= '../DeleteLocation?locName='+id;								
							}
							else
							{}
						}
					});
				 }
	</script>
</body>
</html>


