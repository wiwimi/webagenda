<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="business.Employee" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--  Noorin Hasan -->

<%@ page import="persistence.ScheduleBroker" %>
<%@ page import="persistence.ScheduleTemplateBroker" %>
<%@ page import="business.schedule.ScheduleTemplate" %>
<%@ page import="business.schedule.Shift" %>
<%@ page import="business.schedule.Schedule" %>
<%@ page import="business.Employee" %>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<!--  Libraries -->
<script type ="text/javascript" src ="../lib/js/jquery-1.3.2.min.js"> </script>

<!-- Plug-ins -->
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/deleteUser.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<title>Web Agenda- Template Schedule</title>

<%
         Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
        if (user==null)
        {
        	response.sendRedirect("../wa_login/login.jsp");
        	return;
        }
        else
        {
		
	    	PermissionBroker pb = PermissionBroker.getBroker();
	    	PermissionLevel[] perms =  pb.get(user.getLevel(), user.getVersion(), user);
	    	Permissions perm = perms[0].getLevel_permissions();
	    	
	    
	        if (perm.isCanManageEmployees()==true)
			{
				%>
					<!-- Includes -->
					<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>
				<%
		    }
			else
			{
				response.sendRedirect("../wa_login/login.jsp");
				return;
			}
        }
	%>

</head>
<body>

	<%
		ScheduleTemplate search = new ScheduleTemplate();
		search.setCreatorID(user.getEmpID());
		ScheduleTemplateBroker stb = null;
		stb = ScheduleTemplateBroker.getBroker();
		ScheduleTemplate[] results = null;
		results = stb.get(search, user);
		ScheduleTemplate st = null;
		
		//System.out.println("Schedule Template: "+st.getName()+" - ID: "+st.getSchedTempID());
	
	
	%>
	
	<div id="crumb">
		  <ul id="crumbsLonger">
		    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
		    <li><a href="createScheduleFromTemplate.jsp">Create Schedule</a></li>
		    <li><b><a href="#">Activate Schedule</a></b></li>
		   </ul>
		</div>
		
<div id="scheduleWidget" class="fullWidget">
	<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
			<div class="widgetTitle" id="scheduleWidgetTitle">Schedule Generation</div>
	</div>
	<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
		<div id="creationForm">
	   		<form class="validatedForm" action="../ActivateSchedule" id="form" method="post">
			 	<div id="formButtons">
							<input type="submit" name="submit" class="button" value="Activate Schedule"> 
				</div>
			 	
			 	<fieldset> 
					<legend> Generated Schedule</legend>	
					
					
				<% 
				if(request.getParameter("message") != null)
				{
					if(request.getParameter("message").equals("true"))
					{
						// Display the proposed schedule that was created by the selected template
						Schedule genSched = (Schedule) session.getAttribute("genSched");
						session.setAttribute("genSched", genSched);
						Shift[] shiftList = genSched.getShifts().toArray();
						
						for (Shift shift : shiftList)
						{
							if(!genSched.equals(null) && genSched!=(null))
							{
								Employee[]  emps = null;
						
								if (shift.getEmployees().size() > 0)
								{
									//for (Employee emp : shift.getEmployees().toArray())
									emps = shift.getEmployees().toArray();
									
								
								}
								else
								{
									out.println("No employees available to fill the shifts");
								}
				%>
				
							<div id="report">
								    <div id="tableArea">
										<div class="userAdmin">
												<table class="sortable" id="userTable">
																<thead class="head">
																	<tr class="headerRow">
																		<th>Shift - Day: <%=shift.getDay() %></th>
																		<th colspan=4>Time</th>
																	</tr>
																</thead>
																<tfoot class="foot">
																	<tr class="headerRow">
																		<th>Shift - Day: <%=shift.getDay() %></th>
																		<th colspan=4>Time</th>
																	</tr>
																</tfoot>
																<tbody>
																	<tr id="colored">
																		<th>Employee</th>
																		<th><%= shift.getStartTime()%> - <%=shift.getEndTime()%></th>
																	</tr>	
													  		<%
																  	for (Employee emp : emps)
																	{
																		//System.out.println("\t\tApplies to: "+emp);
													  		%>
														  				<tr>
																			<td><%=emp.getGivenName() %> , <%=emp.getFamilyName() %> </td>
																			<td id="center"><%= emp.getPrefPosition()%> / <%= emp.getPrefLocation()%>  </td>
														  				</tr>
													  		<%
																  	}
													  		%>
																</tbody>
														</table>
										   </div>
								     </div>
							    </div>	
							   		
				<%
			    	    	}
					     }
				%>		 	
						<div id="formButtons">
							<input type="submit" name="submit" class="button" value="Activate Schedule"> 
						</div>	<hr/>	
				<%		
				     } 
				}
				%>	
		 </fieldset>
	  </form>
    </div>	
  </div>
</div>
<div id="footer"></div>
</body>
</html>