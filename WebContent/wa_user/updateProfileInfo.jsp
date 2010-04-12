<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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

<title>Web Agenda- Adding a User</title>

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
				response.sendRedirect("../wa_login/login.jsp");
		        return;
			}
        }
	%>

<!-- Libraries -->
<script type="text/javascript" src ="../lib/js/jquery-1.3.2.min.js"></script>

<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>


<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/validation.css" type="text/css"></link>
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 

</head>
<body>
	<div id="instructions">
		Fields marked with <em class="asterisk" > *</em> are required.
		Randomly generated passwords are going to be sent to the employee's e-mail. If that field was missing it will be sent to your e-mail.
		<br></br>
	</div>

<br></br>
<br></br>

		<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">Users</div>
			</div>
			
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
		
		<% 
			EmployeeBroker broker = EmployeeBroker.getBroker();
		
			int count = broker.getEmpCount();
			
			Employee emp = new Employee();
			
			emp.setEmpID(Integer.parseInt(request.getParameter("id")));
			
			
			Employee[] empArray = broker.get(emp, user);
		%>

		<div id ="userForm">
			<form class="addUserForm" method="post">
			 <div id="personal">
				 	<div id="searchArea">
				 	
				            <input type="submit" name="submit" class="button" value="Save" onClick="location.href='../updateUserInfo';"> 
				            <input type="submit" name="cancel" class="button" value="Cancel" onClick="location.href='updateUser.jsp';">
							<input type="submit" name="submit" class="button" value="Delete" onClick="location.href='#';"> 
							
					</div>
			
				 <fieldset>
					<legend > Personal </legend>
					
							<p>	<label id="theSelect" class="theSelect" > First Name: <em class="asterisk"> * </em> </label> <input type="text"  value="<%= empArray[0].getGivenName()%>" name ="givenName" size ="30"> </p>
							<p>	<label id="theSelect" class="theSelect" > Last Name: <em class="asterisk"> * </em> </label> <input type="text"  value="<%= empArray[0].getFamilyName()%>" name ="familyName" size ="30"> </p>
							<p>	<label id="theSelect" class="theSelect" > Date of Birth: <em class="asterisk"> * </em> </label> <input type="text" name ="dob" value="<%= empArray[0].getBirthDate()%>" id="dob" size ="10"></p>

					       <div id="email">
								<label id="theSelect" class="theSelect"  for="email"> E-mail: <em class="asterisk"> * </em> </label> 
							    <input name="confirmEmail" value="<%= empArray[0].getEmail()%>" id="confirmpass" type="text" size ="30" maxlength="30" />
						   </div>

				</fieldset>
			</div>
			
			<div id="work">
			<fieldset>
				<legend> Working Preferences </legend>
					<p>	<label> Status: <em class="asterisk"> * </em> </label> 
							        <select name="status" >
											<option value="enabled" > Enabled</option> 
											<option value="disabled" >Disabled</option>  
									</select> 
					</p>
					
					<p>
						<label id="theSelect" class="theSelect" for="empId">Employee Id:</label>
						<input type="text" name="empId" size="30" maxLength="30" disabled="disabled" value="<%= empArray[0].getEmpID() %>"/>
						<button type="submit" value="edit">edit</button>
					</p>
									
							<!--This should be populated from MaintainJobType use case -->
					<p>		
							<label id="theSelect" class="theSelect"> Preferred Positions: </label>  
							<input id="prefPositionBox" type="text" size="30" maxlength="30" disabled="disabled" value="<%= empArray[0].getPrefPosition()%>" name="prefPositionBox" />
							<button type="submit" value="edit">edit</button>
									
					</p>
							
							<!--This should be populated from MaintainLocation use case -->
					<p>		
							<label id="theSelect" class="theSelect"> Preferred Locations: </label>  
							<input id="prefLocationBox" type="text" size="30" value="" disabled="disabled" maxlength="30" value="<%= empArray[0].getPrefLocation()%>" name="prefLocationBox" />
							<button type="submit" value="edit">edit</button>
					</p>	
								<!--This should be populated from MaintainSkills use case -->
					<p>
							<label id="theSelect" class="theSelect"> Preferred Skills: </label>  
									<input id="prefSkillsBox" type="text" size="30" maxlength="30" disabled="disabled" value="skill goes here" name="prefSkillsBox" />
									<button type="submit" value="edit">edit</button>
									<br></br>
									<br></br>
					</p>		
								<!--This should be populated from MaintainPermission use case -->
					
					<p>
							<label id="theSelect" class="theSelect"> Permission level: </label>  
									<input id="permissionBox" type="text" size="30" maxlength="30" value="<%= empArray[0].getLevel()%>" name="permissionBox" />
				</fieldset>
				</div>
				<div id="searchArea">
						<input type="submit" name="submit" class="button" value="Save" onclick="../updateUserInfo"> 
						<input type="submit" name="cancel" class="button" value="Cancel" onClick="updateUser.jsp"> 
						<input type="submit" name="submit" class="button" value="Delete">
						<br></br>
				</div>
				</form>
			</div>
			</div>
		</div>

<div id="footer"></div>

</body>
</html>