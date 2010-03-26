<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>WebAgenda -Updating Location- </title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!-- Plug-ins -->
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/helpUpdateLocation.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Flash/flashmessenger.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Confirmation/confirm.css" />

</head>
<body>
	<div id="instructions">
		   Fields marked with <em class="asterisk" > *</em> are required.
	</div>
	<%
		if(request.getParameter("update") != null)
		{
			if(request.getParameter("update").equals("true"))
			{
				//out.println("Location was updated");
	%>
				<script type="text/javascript">
					$(function()
				    {
						$.flashMessenger("The location has been successfully updated", 
						{ 	
							modal:true, 
							autoClose: false 
						});	
					});
				</script>
	<% 			   
			}
			else if(request.getParameter("update").equals("false"))
			{
	%>
				<script type="text/javascript">
					$(function()
					{
						 $.flashMessenger("The attempt to update failed.",
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
				<div class="widgetTitle" id="locationsWidgetTitle">Locations <div id="helpIcon"></div> </div>
			</div>
		
		<div class="widgetLowerRectangle" id="locationsLowerRectangle">
			<div id ="creationForm">
				<form class="updateLocationForm" action="../UpdateLocation" id="form" name="form" method="POST">
					<div id="location">
					<%
						String locName = request.getParameter("locName");
						String locDesc= request.getParameter("locDesc");
						if(locDesc != null)
						{
							if(locDesc.length() > 1) {
								locDesc = locDesc.trim();
							}
						}
						Location oldLoc = new Location(locName);
						session.setAttribute("oldLoc",oldLoc);
					 %>
						<div id="formButtons">
							<input type="submit" name="submit"  class="button" value="Update" > 
							<input type="button" name="submit" class="button"  onClick="location.href='locSearchResults.jsp?locName=' + form.locName.value + '&locDesc=' + form.locDesc.value" value="Search" /> 
							<input type="reset" name="clear" class="button" value="Clear Screen"> 
						</div>
				     <fieldset>
					 <legend > Location Details </legend>
					 
						<p>	<label class="label"> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="locName" value ="<%=locName%>" class="required" size ="30"> </p>
						<p>	<label class="label"> Description: </label></p>
						<textarea  name="locDesc" cols="23" rows="6" tabindex="101"> <%=locDesc%> </textarea>
					</fieldset>
						<div id="formButtons">
					        <input type="submit" name="submit"  class="button" value="Update" > 
							<input type="button" name="submit" class="button"  onClick="location.href='locSearchResults.jsp?locName=' + form.locName.value + '&locDesc=' + form.locDesc.value" value="Search" /> 
							<input type="reset" name="clear" class="button" value="Clear Screen"> 
					     </div>
				     </div>
			    </form>
			</div>
		 </div>
	  </div>
	  <div id="footer"></div>
</body>
</html>
