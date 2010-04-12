<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="java.util.*" %>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Adding Location</title>

     <%
         Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
        if (user==null)
        {
        	response.sendRedirect("wa_login/login.jsp");
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
			%>
					<!--  Includes -->
				<jsp:include page="../wa_includes/pageLayoutUser.jsp"/>
			<%
				}
        }
	%>

<!-- Libraries -->
<script type="text/javascript" src ="../lib/js/jquery-1.3.2.min.js"> </script>

<!-- Plug-ins -->
<script type="text/javascript" src="../lib/js/jquery.validate.js" ></script>
<script type="text/javascript" src ="../lib/js/jquery.flashmessenger.js"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/cmxforms.js"></script>
<script type="text/javascript" src= "../lib/js/val.js"> </script> 
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/deleteLocation.js"></script>
<script type="text/javascript" src="../lib/js/helpLocation.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="../CSS/creationForm.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="CSS/table.css" type="text/css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Validation/val.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../CSS/Validation/screen.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../CSS/Popup/popup.css" media="screen"></link>
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen"/>
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />

</head>
<body>
	<% 
		if(request.getParameter("message") != null)
		{
			if(request.getParameter("message").equals("true"))
			{
	%>
				<script type="text/javascript">
					$(function()
					{
						$.flashMessenger("The location has been successfully created", 
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

						$.flashMessenger("An error occurred while creatin the location. Make sure the name is unique.",

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
	
	<div id="crumb">
		  <ul id="crumbsLonger">
		    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
		    <li><b><a href="#">New Location</a></b></li>
		   </ul>
	</div>
	
	<div id="locationWidget" class="fullWidget">
		<div class="widgetUpperRectangle" id="locationsUpperRectangle">
			<div class="widgetTitle" id="locationsWidgetTitle">Locations <div id="helpIcon"></div> </div>
		</div>
		<div class="widgetLowerRectangle" id="locationsLowerRectangle">
		
		<div id="instructions">
	        Fields marked with <em class="asterisk" > *</em> are required.
	    </div>
	
		
		<%
			String locDesc="", locName="";
			
			if (request.getParameter("locDesc")!=null)
				locDesc = request.getParameter("locDesc");
			
			if (request.getParameter("locName")!=null)
				locName = request.getParameter("locName");
		
		%>
		<div id ="creationForm">
			<form class="addLocationForm" action="../AddLocation" id="form" name="form" method="POST">
				<div id="location">
					<div id="formButtons">
						<input type="submit" name="submit"  class="button" value="Add" > 
						<input type="button" name="submit" class="button"  onClick="location.href='locSearchResults.jsp?locName=' + form.locName.value + '&locDesc=' + form.locDesc.value" value="Search" > 
						<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='newLocation.jsp?locName=&locDesc='"> 
				    </div>
				    <fieldset>
						<legend > Location Details </legend>
							<p>	<label class="label"> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="locName" class="required" size ="30" value="<%=locName%>"/> </p>
							<p>	<label class="label"> Description: </label></p>
							<textarea  name="locDesc" cols="23" rows="6" tabindex="101"> <%=locDesc%> </textarea>
				    </fieldset>
				    <div id="formButtons">
						<input type="submit" name="submit"  class="button" value="Add" > 
						<input type="button" name="submit" class="button"  onClick="location.href='locSearchResults.jsp?locName=' + form.locName.value + '&locDesc=' + form.locDesc.value" value="Search" > 
						<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='newLocation.jsp?locName=&locDesc='"> 
				   </div>
			   </div>
		   </form>
	    </div>
	    </div>
    </div>
    
<div id="footer"></div>

</body>
</html>
