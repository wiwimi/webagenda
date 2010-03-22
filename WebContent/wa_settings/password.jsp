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

<title>Web Agenda- Change Password</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>
	
</head>
<body>
	<div id="instructions">
		Fields marked with <em class="asterisk" > * </em> are required.
		You must retype your password. This helps protect your information. <br>
		Please use 6-8 alphanumeric characters.
	</div>
	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="passwordsUpperRectangle">
					<div class="widgetTitle" id="passwordTitle">Password</div>
				</div>
				
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
				<div id ="creationForm">
					<form class="validatedForm" action="" id="form" method="post">
						<div id="security">
					 		<div id="formButtons">
							 	    <input type="submit" name="submit" class="button" value="Update"> 
									<input type="reset" name="clear" class="button" value="Clear Screen">
							</div>
							<fieldset>
								<legend > Account Settings </legend>
									<p>
										<label for="old_pwd" class = "label"> Old Password: <em class="asterisk"> * </em> </label> 
										<input type="password"  name ="old_pwd" id="old_pwd" size ="7" maxlength="8"/> 
									</p>
									<p>	 	
										<label for="password" class = "label" > New Password: <em class="asterisk"> * </em> </label> 
										<input type="password"  name ="password" id="password" size ="7" maxlength="8"//> 
									</p>	
									<p>
										<label for="confirm_password" class = "label" >Confirm password  <em class="asterisk"> * </em>  </label>
										<input id="confirm_password" name="confirm_password" type="password" size ="7" maxlength="8"/>
									</p>	 
						   </fieldset>
					   </div>
				 </form>
				</div>                  <!-- End updateProfileForm -->
	         </div>                       <!-- End widgetLowerRectangle -->
	    </div>                              <!-- End usersWidget -->
<div id="footer"></div>


</body>
</html>