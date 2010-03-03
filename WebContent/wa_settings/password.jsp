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
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>

<!-- CSS -->
<link rel="stylesheet" href="CSS/userAddStyle.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/settings.css" type="text/css"></link>


<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />


	
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
					<form class="validatedForm" id="form" method="post" enctype="multipart/form-data">
					
					<fieldset>
								<legend > Account Settings </legend>
								<div>
									<label for="old_pwd" class = "label"> Old Password: <em class="asterisk"> * </em> </label> 
									<input type="password"  name ="old_pwd" id="old_pwd" size ="6" maxlength="8"/> 
								</div>
								
								<div>	 	
									
									<label for="password" class = "label" > New Password: <em class="asterisk"> * </em> </label> 
									<input type="password"  name ="password" id="password"/> 
								</div>	
								<div>
									<label for="confirm_password" class = "label" >Confirm password  <em class="asterisk"> * </em>  </label>
									<input id="confirm_password" name="confirm_password" type="password" />
								</div>	 
						</fieldset>
						<br> 
						<input type="submit" name="submit" class="button" value="Save"> 
						<input type="reset" name="clear" class="button" value="Clear Screen"> 
						<br></br>
					</form>
				</div>                  <!-- End updateProfileForm -->
	       </div>                       <!-- End widgetLowerRectangle -->
	</div>                              <!-- End usersWidget -->
<div id="footer"></div>


</body>
</html>