<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="business.schedule.Location" %>
<%@ page import = "exception.DBDownException" %>
<%@ page import = "exception.DBException" %>

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
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/deleteLocation.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Confirmation/confirm.css" />
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Flash/flashmessenger.css" />

</head>
<body>
<br></br>  
		<% 
			if(request.getParameter("delete") != null)
			{
				if(request.getParameter("delete").equals("true"))
				{
					//out.println("Location was deleted");
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
				else if(request.getParameter("delete").equals("false"))
				{
		%>
							
				<script type="text/javascript">
					$(function()
					    {
							
					       $.flashMessenger("The location has not been deleted.",
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
				<form id="form">
						<input type="text" size=30/ name="locName">
						<input type="submit" name="search"  class="button" value="Search" onClick="location.href='newLocation.jsp?locName=' + form.locName.value"> 
				</form>
				</div>
				
						<% 
						  try{
							Employee user = (Employee) session.getAttribute("currentEmployee");
							LocationBroker broker = LocationBroker.getBroker();
							Location loc= null;
							String locName ="";
							
							
							if(request.getParameter("locName").equals("") && request.getParameter("locDesc").equals(""))
							{
								loc = new Location("");
							}
							else
							{
								loc =new Location("");
								if(!request.getParameter("locName").equals(""))
									loc.setName(request.getParameter("locName"));
								if(!request.getParameter("locDesc").equals(""))
									loc.setDesc(request.getParameter("locName"));
							}
							Location[] locArray = broker.get(loc, user);
							
							if (locArray==null)
							{
						%>
						      	<div id="instructions">
						      		Your search didn't match any locations. <br>
						      		For better results try more general fields and make sure all words are spelled correctly.
							    </div>
						<%
							}
							else
							{
								    locArray = broker.get(loc, user);
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
									for(int index = 0; index < locArray.length; index++)
									{
										
						%>	
									<tr>
										<td>
											<a href="updateLocation.jsp?location=<%=locArray[index].getName()%>"><div id="locationImage"> <b> <%=locArray[index].getName()%> </b></div></a>
											<div class="row-actions"><span class='edit'>
											<a href="updateLocation.jsp?locName=<%=locArray[index].getName()%>&locDesc=<%=locArray[index].getDesc()%> "> Edit </a>   | </span>  <span class='delete'>
											<a href="javascript:;" onClick="removeLocation('<%=locArray[index].getName()%>', '<%=locArray[index].getDesc()%>');">
												Delete
											</a></span></div>
										</td>
										<td>
											<a href="updateLocation.jsp?=<%=locArray[index].getName()%>"> <%=locArray[index].getDesc()%> </a>
										</td>
						<% 
									} 
							  }
						  }
							catch (DBException e)
							{
								e.printStackTrace();
								
							}
							catch (DBDownException e)
							{
								e.printStackTrace();
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


