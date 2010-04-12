<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--  Author: Noorin Hasan 
		Edited by: Mark Hazlett
-->

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
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/deleteUser.js"></script>
<script type="text/javascript" src="../lib/js/helpSchedule.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
<link rel="stylesheet" href="../wa_dashboard/CSS/effects.css" type="text/css" media="screen" />
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
					if(request.getParameter("message") != null)
					{
						if(request.getParameter("message").equals("perm"))
						{
				%>
							<script type="text/javascript">
								$(function()
								    {
										
								       $.flashMessenger("You don't have the right permission to create the schedule. Please make sure you are logged in",
								        {
											   modal:true,
							    		       clsName:"err", 
								    		   autoClose:false
								    	 }); 
								   }); 
							</script>
			   <%
						}	else if(request.getParameter("message").equals("sundayError"))
						{
			    %>
										<script type="text/javascript">
											$(function()
											    {
													
											       $.flashMessenger("Make sure the day you selected is a Sunday",
											        {
														   modal:true,
										    		       clsName:"err", 
											    		   autoClose:false
											    	 }); 
											   }); 
										</script>
					<%
							}
						else if(request.getParameter("message").equals("empty"))
						 {
					%>
										<script type="text/javascript">
											$(function()
											    {
													
											       $.flashMessenger("There were no available employees matching the template for the requested week.",
											        {
														   modal:true,
										    		       clsName:"err", 
											    		   autoClose:false
											    	 }); 
											   }); 
										</script>
					<%
							}
						else if(request.getParameter("message").equals("activated"))
						{
				%>
							<script type="text/javascript">
								$(function()
								    {
										
								       $.flashMessenger("The schedule has been successfully activated.",
								        {
											   modal:true,
								    		   autoClose:false
								    	 }); 
								   }); 
							</script>
				<%
						}
					}
				%>

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
    <li><b><a href="#">Create Schedule from Template</a></b></li>
   </ul>
</div>
<div id="scheduleWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule Generation <div id="helpIcon"></div> </div>
			</div>
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
				<div id="creationForm">
				   <form class="validatedForm" action="../GenerateSchedule" id="form" method="post">
					 	<fieldset> 
							<legend> Template Selection</legend>
							
							
								<p>	<label class ="label"> Template: </label> 
									<select class="required">
									<%
										for (int i=0; i<results.length; i++)
										{
											st= results[i];
									%>
										  <option> <%=st.getName() %></option>
									<%		
										}
									%>
									</select>
								</p>
								
						</fieldset>
						<fieldset>
							<legend> Schedule Details</legend>
								<p>	<label class ="label"> Start Date: </label> <input type="text" name ="schedStart" id="schedStart" class="required" size ="10" value=""/></p>
								
								<p>	<label class ="label"> Location: </label> 
									<select name = "loc" class="required">
									<%
										LocationBroker locBroker = LocationBroker.getBroker();
										Location loc= null;
										loc = new Location("");
										Location[] locArray = locBroker.get(loc, user);
									
										for(int index = 0; index < locArray.length; index++)
										{
									%>
										  <option> <%=locArray[index].getName()%></option>
									<%		
										}
									%>
									</select>
								</p>
								
								<div id="formButtons">
									<input type="submit" name="submit" class="button" value="Create Schedule"> 
								</div>
						</fieldset>	
					</form>	
				</div>
		</div>	
	</div>
<div id="footer"></div>
</body>
</html>