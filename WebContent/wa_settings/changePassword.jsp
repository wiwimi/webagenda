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



<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>

</head>
	<% 
		if(request.getParameter("message") != null)
		{
			if(request.getParameter("message").equals("true"))
			{
				
 	%>
	              <script type="text/javascript">

					$(function()
					    {
							
							    $.flashMessenger("The password has been successfully changed.", 
								{ 	
									modal:true, 
									autoClose: false 
								});	
						});
					</script>
	<% 			   
			}
			else if(request.getParameter("message").equals("sent"))
			{
	%>
	              <script type="text/javascript">

					$(function()
					    {
							
							    $.flashMessenger("An error occurred. Make sure your old password is valid", 
								{ 	
									modal:true, 
									autoClose: false 
								});	
						});
					</script>
	<% 			   
			}
		}
	%>
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
					<form class="validatedForm" action="../ChangePassword" id="form" method="post">
						<div id="security">
					 		<div id="formButtons">
							 	    <input type="submit" name="submit" class="button" value="Update"> 
									<input type="reset" name="clear" class="button" value="Clear Screen">
							</div>
							<fieldset>
								<legend > Security Settings </legend>
									<p> <label class ="label"> Old Password: <em class="asterisk"> * </em> </label> <input type="password" name="oldPassword" class="required" size ="7"> </p>
									<p> <label class ="label"> New Password: <em class="asterisk"> * </em> </label> <input type="password"  name="password"   size ="7"> </p>
									<p> <label class ="label"> Confirm New Password: <em class="asterisk"> * </em> </label> <input type="password" name="confirm_password"  size ="7"> </p>
						 
						   </fieldset>
					   </div>
				 </form>
				</div>                  <!-- End updateProfileForm -->
	         </div>                       <!-- End widgetLowerRectangle -->
	    </div>                              <!-- End usersWidget -->
<div id="footer"></div>


</body>
</html>