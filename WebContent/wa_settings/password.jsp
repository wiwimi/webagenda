<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- User's Profile</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>


<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>


<!--  CSS files -->
<link rel="stylesheet" href="CSS/userAddStyle.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/settings.css" type="text/css"></link>

<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

</head>
<body>
	<div id="instructions">
		Fields marked with <em class="asterisk" > * </em> are required.
		You must retype your password. This helps protect your information.
		<br></br>
		<br></br>
	</div>
	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="passwordsUpperRectangle">
					<div class="widgetTitle" id="passwordTitle">Password</div>
				</div>
				
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
	
				<div id ="updateProfileForm">
					<form class="validatedForm" method="post">
		
						<fieldset>
								<legend > Account Settings </legend>
									<label for="old_pwd"> Old Password: <em class="asterisk"> * </em> </label> 
									<input type="password"  name ="old_pwd" id="old_pwd" size ="6" maxlength="8"/> 
										 	
									<div class="validated" id="password_li">
										 <label for="password"> New Password: <em class="asterisk"> * </em> </label> 
										 <input class="validated" type="password"  name ="password" id="password" size ="6" maxlength="8"/> 
									  	 <span id="pwdInfo"> 6 - 8 alphanumeric characters</span> 
									</div>
									 <div id="confirmpass_li">
									     <label for="confirmpass"> New Password: <em class="asterisk"> * </em> </label> 
									      <div id="confirmpass_img"></div>
									     <input name="confirmpass" id="confirmpass" type="password" size ="6" maxlength="8" />
									 	 <div id="confirmpass_msg"></div>
									 </div>
									 
									 
						</fieldset>
						
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
					</form>
				</div>                  <!-- End updateProfileForm -->
	       </div>                       <!-- End widgetLowerRectangle -->
	</div>                              <!-- End usersWidget -->
<div id="footer"></div>


</body>
</html>