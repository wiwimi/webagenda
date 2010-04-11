<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.schedule.Position" %>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.permissions.PermissionBroker" %>
<%@ page import="business.permissions.PermissionLevel" %>
<%@ page import="business.permissions.PermissionAccess" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Agenda- Demo</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script type ="text/javascript" src ="../lib/js/jquery-1.3.2.min.js"> </script>

<!-- Plug-ins -->
<script type="text/javascript" src="../lib/js/jquery.validate.js"></script>
<script type="text/javascript" src ="../lib/js/jquery.flashmessenger.js"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/cmxforms.js"></script>
<script type="text/javascript" src= "../lib/js/val.js"> </script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/passwordGenerator.js"></script>
<script type="text/javascript" src="../lib/js/generatePwd.js"></script>
<script type="text/javascript" src="../lib/js/helpDemo.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="../CSS/creationForm.css"/>
<link rel="stylesheet" type="text/css" href="CSS/table.css"/>
<link rel="stylesheet" type="text/css" href="../CSS/Popup/popup.css"/>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" href="../CSS/Validation/val.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Validation/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
<link rel="stylesheet" type="text/css" href="CSS/help.css" media="screen" />
		
</head>
<body>
 <div id="usersWidget" class="fullWidget">
		<div class="widgetUpperRectangle" id="usersUpperRectangle">
			<div class="widgetTitle" id="helpWidgetTitle">Demo <div id="helpIcon"></div></div>
		</div>
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
		<div id ="creationForm">
			<form class="validatedForm" id="form" > 
			<fieldset>
				<legend> Demo </legend>
				 	<object width="425" height="344">
							<param name="movie" value="http://www.youtube.com/v/GwQMnpUsj8I&hl=en&fs=1">
							</param><param name="allowFullScreen" value="true">
							</param><param name="allowscriptaccess" value="always">
							</param><embed src=http://www.youtube.com/v/GwQMnpUsj8I&hl=en&fs=1 
							type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="425" height="344">
						</embed>
					</object>
			</fieldset>
		  </form>
	</div> <!-- End Creation Form -->
			</div>
</div>
<div id="footer"></div>
</body>
</html>
