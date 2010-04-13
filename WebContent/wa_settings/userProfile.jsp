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

<!-- Author: Noorin Hasan-->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Agenda- Updating a User</title>

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


<!-- Libraries -->
<script type ="text/javascript" src ="../lib/js/jquery-1.3.2.min.js"> </script>

<!-- Plug-ins -->
<script type="text/javascript" src="../lib/js/jquery.validate.js"></script>
<script type="text/javascript" src ="../lib/js/jquery.flashmessenger.js"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/cmxforms.js"></script>
<script type="text/javascript" src= "../lib/js/val.js"> </script>
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/deleteUser.js"></script>
<script type="text/javascript" src="../lib/js/passwordGenerator.js"></script>
<script type="text/javascript" src="../lib/js/generatePwd.js"></script>
<script type="text/javascript" src="../lib/js/helpUpdateProfile.js"></script>

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
										
										    $.flashMessenger("Your profile successfully updated", 
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
										$.flashMessenger("A problem had occured while updating your profile, contact your admin to make sure you have the right set of permissions",
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
		     <li><b><a href="#">Update Profile</a></b></li>
		   </ul>
	    </div>
		

        <div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">User's Profile <div id="helpIcon"></div></div>
			</div>
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
		
		<div id="instructions">
			For further updates contact your Admin.
		</div>
		
		<div id ="creationForm">
			<form class="validatedForm" action="../UpdateUserProfile" id="form" method="post">
				 <div id="personal">
					 	<div id="formButtons">
					 	    <input type="submit" name="submit" class="button" value="Update"> 
							<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='adminProfile.jsp?message=clear'">
						</div>
				<%
				 	String email="";
					
				    EmployeeBroker empBroker = EmployeeBroker.getBroker();
					user = (Employee)session.getAttribute("currentEmployee");
					
					int empIdInt = user.getEmpID();
					
					String empId = Integer.toString(empIdInt); // Convert to string to pass properly to the servlet.
					
					Employee searchEmp = new Employee();
					
					searchEmp.setEmpID(empIdInt);
					 
					Employee[] results = empBroker.get(searchEmp, user);
					
				
					if (results[0].getEmail()!=null)
						email = results[0].getEmail();
					
					Employee oldEmp = new Employee();
					oldEmp.setEmpID(empIdInt);
					
					session.setAttribute("oldEmp", oldEmp);
					
					 if (request.getParameter("message")!=null)
					 {
						 if (request.getParameter("message").equals("clear"))
						 {
							email="";
						 }
					 }
						

				%>
				<fieldset>
					<legend > Personal </legend>
						 <p>
					         <label for="cemail" class="label"> Personal E-Mail:</label>
				             <input type="text" id="cemail" name="email" size="30" value="<%=email%>"/>
				        </p>
				</fieldset>
				
				</div>
				</form>
			</div>
		</div>
	</div>
<div id="footer"></div>
</body>
</html>
