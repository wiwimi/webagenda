<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="business.schedule.Position" %>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.Employee" %>
<%@ page import = "exception.DBDownException" %>
<%@ page import = "exception.DBException" %>

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
<script type="text/javascript" src="../lib/js/sorttable.js"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>
<script type="text/javascript" src="../lib/js/deletePosition.js"></script>
<script type="text/javascript" src="../lib/js/helpPosSearchResults.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="CSS/table.css" ></link>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css"  media="screen"/>
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css"  media="screen"/>
<link rel="stylesheet" type="text/css"  href="CSS/icons.css"></link>
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
		<div id="skillsWidget" class="fullWidget"> <div id="backIcon" > <a onClick="history.go(-1);return true;"> Back </a> </div>
			<div class="widgetUpperRectangle" id="positionssWidgetUpperRectangle">
				<div class="widgetTitle" id="positionsTitle">Positions <div id="helpIcon"></div> </div>
		</div>
			
		<div class="widgetLowerRectangle" id="positionsWidgetLowerRectangle">

			<div id="positionsIcon">
				<h3>Positions</h3>
			</div>
			
			<div id="searchArea">
			<form id="form">
				<input type="text" size=30 name="randomSearch">
				<input type="button" name="submit"  class="button" value="Search" onClick="location.href='posSearchResults.jsp?randomSearch=' + form.randomSearch.value"> 
			</form>
			</div>
			
							<% 
							 try
							 {
								Employee user = (Employee) session.getAttribute("currentEmployee");
								PositionBroker broker = PositionBroker.getBroker();
								Position pos= null;
								Position[] posArray=null;
								
								if(request.getParameter("posName")!=null || request.getParameter("posDesc")!=null )
								{
									pos = new Position();
									if(!request.getParameter("posName").equals(""))
									{
										pos.setName(request.getParameter("posName"));
									}
									if (!request.getParameter("posDesc").equals(""))
									{
										pos.setDescription(request.getParameter("posDesc"));
									}
									
									else if (request.getParameter("posName").equals("") && (request.getParameter("posDesc").equals("")))
									{
										pos = new Position("");
										
									}
									posArray = broker.get(pos, user);
							 	 }
								
								else if(request.getParameter("randomSearch")!=null)
								{
									
									if((!request.getParameter("randomSearch").equals("")))
									{
										// If it was a name
										Position byName = new Position(request.getParameter("randomSearch"));
										Position[] posArrayByName =  broker.get(byName, user);
										
										// If it was a desc
										Position byDesc = new Position();
										byDesc.setDescription(request.getParameter("randomSearch"));
										Position[] posArrayByDesc =  broker.get(byDesc, user);
										
										//If both are not empty Concat them
										if(posArrayByDesc!=null && posArrayByName!=null)
										{
											for (int i=0; i<posArrayByName.length; i++)
											{
												for (int x=0; x<posArrayByDesc.length; x++)
												{
													// If it is not refferring to the same object
													if(posArrayByDesc[x].getName()==posArrayByName[i].getName())
													{
														int strlength = posArrayByDesc.length + posArrayByName.length;
														
														posArray = new Position[strlength];
														System.arraycopy(posArrayByDesc, 0, posArray, 0, posArrayByDesc.length);
													    System.arraycopy(posArrayByName, 0, posArray, posArrayByDesc.length, posArrayByName.length);
														
													}
													else if (posArrayByDesc[x].getName()!=posArrayByName[i].getName())
													{
														posArray = posArrayByName;
													}
												}
											}
											
										}
										
										
										if(posArrayByDesc!=null)
										{
											posArray = posArrayByDesc;
										}
										else if(posArrayByName!=null)
										{
											posArray = posArrayByName;
										}
									}
										
									else if((request.getParameter("randomSearch").equals("")))
									{
										pos = new Position("");
										posArray = broker.get(pos, user);
									}
									
						    }
									
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
													<a href="updatePosition.jsp?posName=<%=posArray[index].getName()%>&posDesc=<%=posArray[index].getDescription()%>" > Edit </a> </span>  <span class='delete'>
													<a href="javascript:;" onClick="removePosition('<%=posArray[index].getName()%>');">
														| Delete</a></span>
													<span class='report'> <a href="../wa_reports/reportPosition.jsp?posName=<%=posArray[index].getName()%>"> | Report
													</a> </span>
													
											    </div>
											</td>
											<td>
											     <%
											     	if(posArray[index].getDescription()!=null && !posArray[index].getDescription().equals(""))
											     	{
											     %>
											     		<a href="updatePosition.jsp?=<%= posArray[index].getName() %>"> <%=posArray[index].getDescription()%> </a>
											     <%	}
											     	else
											     	{
											     %>
											     		<a href="updatePosition.jsp?=<%= posArray[index].getName() %>"> None </a>
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
		</div> 
	</div>
</div>
<div id="footer"></div>
</body>


</html>


