<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.schedule.Position" %>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.permissions.PermissionBroker" %>
<%@ page import="business.permissions.PermissionLevel" %>
<%@ page import="business.permissions.PermissionAccess" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Agenda- Adding a User</title>


<%
	Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
	if (user.getLevel()==99)
	{
%>
	<!--  Includes -->
	<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>
<%
	}
	else
	{
%>
		<!--  Includes -->
	<jsp:include page="../wa_includes/pageLayoutUser.jsp"/>
<%
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
<script type="text/javascript" src="../lib/js/helpUser.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="../CSS/creationForm.css"/>
<link rel="stylesheet" type="text/css" href="CSS/table.css"/>
<link rel="stylesheet" type="text/css" href="../CSS/Popup/popup.css"/>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" href="../CSS/Validation/val.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Validation/screen.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

		
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
										
										    $.flashMessenger("The user has been successfully created without sending an e-mail.", 
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
										
										    $.flashMessenger("The user has been successfully created. An e-mail has been sent with the account details.", 
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
										$.flashMessenger("A problem had occured while creating the user. Make sure the ID is unique",
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
		    <li><a href="reports.jsp">Reports</a></li>
		    <li><b><a href="#">Search A User to Report</a></b></li>
		   </ul>
   	   </div>
        <div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">Users <div id="helpIcon"></div></div>
			</div>
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
		<div id ="creationForm">
			<form class="validatedForm" action="../AddUser" id="form" method="post">
				 <div id="instructions">
					Search a user to report.
				</div>
				 
				 <div id="personal">
					 	<div id="formButtons">
							<input type="button" name="submit" class="button" value="Search" onClick="location.href='../wa_user/userSearchResults.jsp?familyName='+ form.familyName.value + '&empId=' + form.empId.value + '&dob=' + form.dob.value + '&givenName=' + form.givenName.value + '&user=' + form.user.value + '&status=' + form.status.value + '&email=' + form.email.value">
							<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='newUser.jsp?empId=&familyName=&status=&username=&dob='">
						</div>
				<%
				 	String givenName = "", familyName ="", dob ="" , username ="", email="", password ="", empId="";
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
					if(request.getParameter("dob")!=null)
					{
						dob = request.getParameter("dob");
					}
					if(request.getParameter("empId")!=null)
					{
						empId = request.getParameter("empId");
					}
					if(request.getParameter("username")!=null)
					{
						username = request.getParameter("username");
					}
					if(request.getParameter("password")!=null)
					{
						password = request.getParameter("password");
					}
				
				%>
				<fieldset>
					<legend > Personal </legend>
						<p>	<label class ="label"> Given Name:  <em class="asterisk"> * </em> </label> <input type="text" name="givenName" class="required" size="30" maxLength="30" value="<%=givenName%>"/> </p>
						<p>	<label class ="label"> Family Name: <em class="asterisk"> * </em> </label> <input type="text" name="familyName" class="required" size="30" maxLength="30" value="<%=familyName%>"/> </p>
						<p>	<label class ="label"> Date of Birth: </label> <input type="text" name ="dob"  id="dob" size ="10" value="<%=dob%>"/></p>
						<p>	<label class ="label"> Username: <em class="asterisk"> * </em> </label> <input type="text" name ="user" id="user"  class="required"  size ="30" value="<%=username%>"/></p>
	                    <p>
					        <label for="cemail" class="label"> E-Mail:</label>
				            <input type="text" id="cemail" name="email" size="30" value="<%=email%>"/>
				        </p>
					</fieldset>
				</div>
				<div id="work">
				<fieldset>
					<legend> Working Preferences </legend>
							<div>
								<p>	<label id="theSelect" class="theSelect"> Status: <em class="asterisk"> * </em> </label> 
										<select name="status">
											<option value="enabled"> Enabled</option> 
											<option value="disabled" >Disabled</option>  
										</select> 
								</p>
							</div>				
							<div>
							    <p>
									<label id="theSelect" class="theSelect" for="empId">Employee Id: <em class="asterisk"> * </em> </label>
									<input type="text" size="30" name="empId" class="required" maxLength="30" value="<%=empId%>"/>
								</p>
							</div>
			   </fieldset>
			   </div>
		  </form>
		</div> <!-- End Creation Form -->
	</div>
</div>
<div id="footer"></div>
</body>
</html>
