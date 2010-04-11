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

<!-- The Jquery Library was taken off from this screen due to conflict errors -->

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />

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
									<p> <label class ="label"> Confirm New Password: <em class="asterisk"> * </em> </label> <input type="password" name="confirm_password"  size ="7"></p>
						            <%
						            
						             	String message = request.getParameter("message");
						            	if (message!=null) 
						            	{
						            		if (message.equals("false"))
						            		{
						            %>
						            			<div id="error"> An error occurred. Make sure the old password is correct. </div>
						            <%
						            		}
						            		else if (message.equals("mismatch"))
						            		{
						            %>	
						            				<div id="error"> Make sure the passwords match. </div>
						            <%			
						            		}
						            		else
						            		{
						            %>
						            			<div id="success"> The password was successfully created. </div>
						            <%
						            		}
						            	}
						            %>
						   </fieldset>
					   </div>
				 </form>
				</div>                  <!-- End updateProfileForm -->
	         </div>                       <!-- End widgetLowerRectangle -->
	    </div>                              <!-- End usersWidget -->
<div id="footer"></div>


</body>
</html>