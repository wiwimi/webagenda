<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="business.Employee" %>
<%@ page import="business.permissions.PermissionLevel" %>
<%@ page import="business.permissions.*" %>
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

<!-- Plug-ins -->
<script type="text/javascript" src="../lib/js/jquery.validate.js"></script>
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/cmxforms.js"></script>
<script type="text/javascript" src= "../lib/js/val.js"> </script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/helpPassword.js"></script>

<!-- CSS Files -->
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css"  media="screen" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<title>Web Agenda- Change Password</title>


<%
         Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
        if (user==null)
        {
        	response.sendRedirect("wa_login/login.jsp");
        	return;
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
					<!-- Includes -->
					<jsp:include page="../wa_includes/pageLayoutUser.jsp"/>
				<%
			}
        }
	%>
<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
</head>
<body>

	<div id="crumb">
		  <ul id="crumbsLonger">
		    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
		    <li><b><a href="#">Change Password</a></b></li>
		   </ul>
	</div>

    <div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="passwordsUpperRectangle">
					<div class="widgetTitle" id="passwordTitle">Password <div id="helpIcon"> </div></div>
				</div>
				
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
			
			<div id="instructions">
				Fields marked with <em class="asterisk" > * </em> are required.
				You must retype your password. This helps protect your information. <br>
				Please use 6-8 alphanumeric characters.
			</div>
	
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
									<p> <label class ="label"> Confirm New Password: <em class="asterisk"> * </em> </label> <input type="password" name="confirm"  size ="7"></p>
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
						            			<div id="success"> The password was successfully changed. </div>
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