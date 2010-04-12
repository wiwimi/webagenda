<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="application.Backup" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Security Settings</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>


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
<script type="text/javascript" src="../lib/js/helpBackup.js"></script>

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
						$.flashMessenger("A backup has been sucessfully created", 
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

						$.flashMessenger("An error occurred while creating the backup. Contact your admin",

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
		    <li><b><a href="#">Backup</a></b></li>
		   </ul>
	</div>

	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="backupUpperRectangle">
					<div class="widgetTitle" id="passwordTitle">Full Backup <div id="helpIcon"></div>  </div>
				</div>
				
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
				<div id ="creationForm">
					<form class="validatedForm" action="../ExportBackup" id="form" method="post">
							<div id="formButtons">
							 	    <input type="submit" name="submit" class="button" value="Backup" onClick ="save()"> 
							</div>
							<fieldset>
								<legend > Export Backup </legend>
									<div id="instructions"> 
									   By clicking on the Backup button , the system performs a full 
									    backup to ensure recover in the unfortunate case of any failure.
									    Once you hit the button a .sql file is automatically exported. The file's name
									    corresponds to the exact time it was made. 
									 </div>
							 </fieldset>
					 </form>
				</div>                  
	         </div>                     
	    </div>                             
<div id="footer"></div>
</body>
</html>