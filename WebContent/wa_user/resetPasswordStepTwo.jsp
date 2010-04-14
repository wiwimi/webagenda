<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.schedule.Position" %>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.permissions.PermissionBroker" %>
<%@ page import="business.permissions.PermissionLevel" %>
<%@ page import="business.permissions.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Agenda- Reset Password</title>

<%
         Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
        if (user==null)
        {
        	response.sendRedirect("../wa_login/login.jsp");
        	return;
        }
        else
        {
		
	    	PermissionBroker pb = PermissionBroker.getBroker();
	    	PermissionLevel[] perms =  pb.get(user.getLevel(), user.getVersion(), user);
	    	Permissions perm = perms[0].getLevel_permissions();
	    	
	    
	        if (perm.isCanManageEmployees()==true && user.getActive()==true)
			{
				%>
					<!-- Includes -->
					<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>
				<%
		    }
	        else if (user.getActive()==false)
	        {
	        	response.sendRedirect("../wa_login/login.jsp?message=locked");
        	    return;
	        }
			else
			{
				response.sendRedirect("../wa_login/login.jsp");
		        return;
			}
        }
	%>

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
<script type="text/javascript" src="../lib/js/helpResetPassStepTwo.js"></script>

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
										
										    $.flashMessenger("The password has been successfully updated without sending an e-mail.", 
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
										
										    $.flashMessenger("The password has been successfully updated. An e-mail has been sent with the new password.", 
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
										$.flashMessenger("A problem had occured while resetting the password. Make sure the details correspond to one user",
								        {
											   modal:true,
							    		       clsName:"err", 
								    		   autoClose:false
								    	 }); 
								   }); 
							</script>
				<%
						}
						else if(request.getParameter("message").equals("sentError"))
						{
			%>
							<script type="text/javascript">
								$(function()
								    {
										$.flashMessenger("Your SMTP ports are blocked,please uncheck the sending option and try again",
								        {
											   modal:true,
							    		       clsName:"err", 
								    		   autoClose:false
								    	 }); 
								   }); 
							</script>
				<%
						}
						else if (request.getParameter("message").equals("perm"))
						{
				%>
							<script type="text/javascript">
							$(function()
							    {
									$.flashMessenger("You do not have the right permission to perform the operation.",
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
		    <li><a href="resetPasswordStepOne.jsp">Reset Password (Step 1 of 2) </a></li>
		    <li><b><a href="#">Reset Password (Step 2 of 2) </a></b></li>
		   </ul>
		</div>
		<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="passwordsUpperRectangle">
					<div class="widgetTitle" id="passwordTitle">Password <div id="helpIcon"> </div></div>
				</div>
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
		
		<div id="instructions"> <br></br> 
			Fields marked with <em class="asterisk" > *</em> are required.
			By default randomly generated passwords are going to be sent to the employee's e-mail. If that field was missing it will be sent to your e-mail. 
			You can uncheck this option. 
		</div>
		<div id ="creationForm">
			<form class="validatedForm" action="../ResetPassword" id="form" method="post">
				 <div id="personal">
					 	<div id="formButtons">
		 	    			<input type="submit" name="submit" class="button" value="Reset Password"> 
							<input type="button" name="submit" class="button" value="Search" onClick="location.href='resetPasswordStepOne.jsp?familyName='+ form.familyName.value + '&givenName=' + form.givenName.value + '&user=' + form.user.value + '&email=' + form.email.value">
							<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='resetPasswordStepTwo.jsp?username=&familyName=&givenName=&email='">
						</div>
				<%
				 	String givenName = "", familyName ="",username ="", email="", password ="", empId="";
					
				
				    if(request.getParameter("empUsername")!=null)
				    {
				    	username = request.getParameter("empUsername");
				    	
				    	Employee emp = new Employee();
						EmployeeBroker broker = EmployeeBroker.getBroker();
						Employee[] empArray = null;
						emp.setEmail(email);
						
						empArray = broker.get(emp, user);
						
				    	givenName = empArray[0].getGivenName();
				    	familyName = empArray[0].getFamilyName();
				    	username = empArray[0].getUsername();
				    	email = empArray[0].getEmail();
				    }
				    else
				    {
						if(request.getParameter("givenName")!=null)
						{
							givenName = request.getParameter("givenName");
						}
						if(request.getParameter("familyName")!=null)
						{
							familyName = request.getParameter("familyName");
						}
						if(request.getParameter("email")!=null)
						{
						    email = request.getParameter("email");
						}
						if(request.getParameter("password")!=null)
						{
							password = request.getParameter("password");
						}
						
						if(request.getParameter("user")!=null)
						{
							username = request.getParameter("user");
						}
						if(request.getParameter("password")!=null)
						{
						    password = request.getParameter("password");
						}
				    }
				
				
				%>
				<fieldset>
					<legend > Personal </legend>
						<p>	<label class ="label"> Given Name:  <em class="asterisk"> * </em> </label> <input type="text" name="givenName" class="required" size="30" maxLength="30" value="<%=givenName%>"/> </p>
						<p>	<label class ="label"> Family Name: <em class="asterisk"> * </em> </label> <input type="text" name="familyName" class="required" size="30" maxLength="30" value="<%=familyName%>"/> </p>
						<p>	<label class ="label"> Username: <em class="asterisk"> * </em> </label> <input type="text" name ="user" id="user"  class="required"  size ="30" value="<%=username%>"/></p>
	                    <p>
					        <label for="cemail" class="label"> E-Mail:</label>
				            <input type="text" id="cemail" name="email" size="30" value="<%=email%>"/>
				        </p>
				        
				        <p>
				        	<label class="label" >Generate Password: <em class="asterisk"> * </em> </label>
				        	<input type="text" class="required" name="password" id ="pwd" size="20" value="<%=password%>"/>
				       </p>
				       <p>
				       		<label class="label"> Send New Password: </label> <input type="checkbox" checked name="sendingOption" value="send">
				       </p>
					</fieldset>
				</div>
			
				  <div id="formButtons">
			 	    <input type="submit" name="submit" class="button" value="Reset Password"> 
					<input type="button" name="submit" class="button" value="Search" onClick="location.href='resetPasswordStepOne.jsp?familyName='+ form.familyName.value + '&givenName=' + form.givenName.value + '&user=' + form.user.value + '&email=' + form.email.value">
					<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='resetPasswordStepTwo.jsp?username=&familyName=&givenName=&email='">
		          </div>
			</form>
		</div>
     </div>
</div>
		
					
	
<div id="footer"></div>
</body>
</html>
