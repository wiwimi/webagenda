<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/helpBackup.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="../CSS/creationForm.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="CSS/table.css" type="text/css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Popup/popup.css" media="screen"></link>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen"/>

</head>
<body>

	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="backupUpperRectangle">
					<div class="widgetTitle" id="passwordTitle">Full Backup <div id="helpIcon"></div>  </div>
				</div>
				
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
				<div id ="creationForm">
					<form class="validatedForm" action="" id="form" method="post">
						<div id="security">
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
									    This file could be imported later for recovery.
									 </div> 
									 
									 
						   </fieldset>
						   
						 	<fieldset>
								<legend > Import Backup </legend>
									<div id="instructions"> 
									    You can import files that you have previously exported by simply uploading the file below.
									    
									 </div> 
								   <br>  <br>
		 						   <input type="file" name="submit"  > 
									
									 
									 
						   </fieldset>
					   </div>
				 </form>
				</div>                  
	         </div>                     
	    </div>                             
<div id="footer"></div>
<script type="text/javascript">
function save(){
	// empty for now, this will trigger Dan's code to save the file
}
</script>

</body>
</html>