<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="java.util.*" %>
<%@ page import="business.schedule.Location" %>
<%@ page import = "exception.DBDownException" %>
<%@ page import = "exception.DBException" %>

<!--Author: Noorin-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Agenda- Updating Location</title>

    <%
		Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
	if (user.getLevel()==99)
	{
	%>
		<!-- Includes -->
		<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>
	<%
		}
		else
		{
	%>
			<!--  Includes -->
		<jsp:include page="../wa_includes/pageLayoutUser.jsp"/>
	<%
		}
	%>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

<!-- Plug-ins -->
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/sorttable.js" ></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/deleteLocation.js"></script>
<script type="text/javascript" src="../lib/js/helpUserSearchResults.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="CSS/table.css" ></link>
<link rel="stylesheet" type="text/css" href="CSS/icons.css" ></link>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css"  media="screen" />

</head>
<body>
<div id="instructions">
			Click on column headers to sort data through.
</div>
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
		
	   <div id="locationWidget" class="fullWidget"> <div id="backIcon" > <a onClick="history.go(-1);return true;"> Back </a> </div>
			<div class="widgetUpperRectangle" id="locationsUpperRectangle">
				<div class="widgetTitle" id="locationsWidgetTitle">Locations <div id="helpIcon"> </div></div>
			</div>
			<div class="widgetLowerRectangle" id="locationsLowerRectangle">
				<div id="locationsIcon">
						<h3>Locations </h3>
				</div>
				<div id="searchArea">
				<form id="form">
						<input type="text" size=30 name="randomSearch" value=""/>
						<input type="button" name="submit"  class="button" value="Search" onClick="location.href='locSearchResults.jsp?randomSearch=' + form.randomSearch.value"> 
				</form>
				</div>
				
						<% 
						  try{
							LocationBroker broker = LocationBroker.getBroker();
							broker.initConnectionThread();
							Location loc= null;
							Location[] locArray=null;
							
							if(request.getParameter("locName")!=null || request.getParameter("locDesc")!=null )
							{
								loc = new Location();
								if(!request.getParameter("locName").equals(""))
								{
									loc.setName(request.getParameter("locName"));
								}
								if (!request.getParameter("locDesc").equals(""))
								{
									loc.setDesc(request.getParameter("locDesc"));
								}
								
								else if (request.getParameter("locName").equals("") && (request.getParameter("locDesc").equals("")))
								{
									loc = new Location("");
									
								}
								locArray = broker.get(loc, user);
						 	 }
							
							else if(request.getParameter("randomSearch")!=null)
							{
								
								if((!request.getParameter("randomSearch").equals("")))
								{
									// If it was a name
									Location byName = new Location(request.getParameter("randomSearch"));
									Location[] locArrayByName =  broker.get(byName, user);
									
									// If it was a desc
									Location byDesc = new Location();
									byDesc.setDesc(request.getParameter("randomSearch"));
									Location[] locArrayByDesc =  broker.get(byDesc, user);
									
									//If both are not empty Concat them
									if(locArrayByDesc!=null && locArrayByName!=null)
									{
										int totallength = locArrayByDesc.length + locArrayByName.length;
										
										//TODO The logic is still broken, if an object has the same name as description
										// it appears twice ...
										
										// Concat the 2 arrays
										for (int i=0; i<locArrayByName.length; i++)
										{
											for (int x=0; x <locArrayByDesc.length; x++)
											{
												// If they are NOT referring to the same object
												if((!locArrayByName[i].getName().equals(locArrayByDesc[x].getName())))
												{
													//out.println(locArrayByName[i].getName() + " " );
													locArray = new Location[totallength];
													System.arraycopy(locArrayByDesc, 0, locArray, 0, locArrayByDesc.length);
												    System.arraycopy(locArrayByName, 0, locArray, locArrayByDesc.length, locArrayByName.length);
												}
												
												else
												{
													//out.println(locArrayByName[i].getName() + " " );
													locArray = locArrayByName;
												}
												
											} 
										}
									}
									else if(locArrayByDesc!=null && locArrayByName ==null)
									{
										locArray = locArrayByDesc;
									}	else if(locArrayByName!=null && locArrayByDesc == null)
									{
										locArray = locArrayByName;
									}
								
								}
									
								else if((request.getParameter("randomSearch").equals("")))
								{
									loc = new Location("");
									locArray = broker.get(loc, user);
								}
								
							}
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
								
								    
						%>    
						 
						 <div id="tableArea">
									<div class="userAdmin">
										<table class="sortable" id="userTable">
											<thead class="head">
												<tr class="headerRow">
													<th>Name</th>
													<th>Description </th>
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
											<a href="updateLocation.jsp?locName=<%=locArray[index].getName()%>&locDesc=<%=locArray[index].getDesc() %>"><div id="locationImage"> <b> <%=locArray[index].getName()%> </b></div></a>
											<div class="row-actions">
												<span class='edit'> <a href="updateLocation.jsp?locName=<%=locArray[index].getName()%>&locDesc=<%=locArray[index].getDesc()%> "> Edit </a> </span>  
												<span class='delete'> <a href="javascript:;" onClick="removeLocation('<%=locArray[index].getName()%>&locDesc=<%=locArray[index].getDesc()%>');">| Delete
											     </a></span> 
											     <span class='report'> <a href="../wa_reports/reportLocation.jsp?locName=<%=locArray[index].getName()%>&locDesc=<%=locArray[index].getDesc()%> "> | Report
											      </a> </span>
										    </div>
										 </td>
											<td>
											     <%
											     	if(locArray[index].getDesc()!=null && !locArray[index].getDesc().equals(""))
											     	{
											     %>
											     		<a href="updateLocation.jsp?=<%= locArray[index].getName() %>"> <%=locArray[index].getDesc().trim()%> </a>
											     <%	}
											     	else
											     	{
											     %>
											     		<a href="updateLocation.jsp?=<%= locArray[index].getName() %>"> None </a>
											     <%
											     	} 
											     %>
											</td>
									</tr>
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


