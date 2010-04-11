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
<title>Web Agenda- Contact Admin</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

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
<script type="text/javascript" src="../lib/js/helpContactAdmin.js"></script>

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
<link rel="stylesheet" type="text/css" href="CSS/help.css" media="screen" />
		
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
										
										    $.flashMessenger("The message has been successfully sent.", 
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
										
										    $.flashMessenger("An error occurred while sending the message. Make sure the SMTP ports are not blocked", 
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
 <div id="usersWidget" class="fullWidget">
		<div class="widgetUpperRectangle" id="usersUpperRectangle">
			<div class="widgetTitle" id="usersWidgetTitle">Contact <div id="helpIcon"></div></div>
		</div>
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
		<div id ="creationForm">
			<form class="validatedForm" action="../ContactAdmin" id="form" method="post">
				 <div id="personal">
					 	<div id="formButtons">
					 	    <input type="submit" name="submit" class="button" value="Send"> 
							<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='newUser.jsp?empId=&familyName=&status=&username=&dob='">
						</div>
						<fieldset>
							<legend> Contact Admin </legend>
										
									<!--This should be populated from the database -->
									<div>
									    <p>
											<label id="theSelect" class="theSelect" for="supervisorId">Receipt: <em class="asterisk"> * </em> </label> 
											<input type="text" size="30" disabled="disabled" value="Edit supervisor Id" maxLength="30" class="required"/>
											<input id="empIdButton" id="supIdButton" type="button" name="submit" class="button" value="Edit"/>
										</p>
										
										 <p>
											<label id="label" class="label">Subject: <em class="asterisk"> * </em> </label> 
											<input type="text" size="30"  maxLength="30" name="subject" class="required"/>
										</p>
									</div>
									<div id="contactAdmin">
										<p>	
											<label class="label"> Your Enquiry  <em class="asterisk"> * </em></label> 
											<textarea  name="message" cols="60" rows="15" tabindex="101"></textarea>
										</p>
									</div>
						</fieldset>
			     </div>
				  
				  <div id="formButtons">
			 	    <input type="submit" name="submit" class="button" value="Send"> 
					<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='newUser.jsp?empId=&familyName=&status=&username=&dob='">
				</div>
					
					<!-- The Pop up Screens -->
					
					
			<div id="idPopup">
			<a id="idPopupClose">x</a>
					<h1>Employees</h1>
						<div id="instructions">
						Closing the screen saves the selected item.
					</div>
				<div id="tableArea" >
						<div class="userAdmin">
						<table class="sortable" id="userTable" >
							 <thead class="head">
								 <tr class="headerRow">
									<th>Email</th>
									<th>Family Name</th>
									<th>Given Name</th>
									<th></th>
								 </tr>
							  </thead>
							<tfoot class="foot">
								<tr class="headerRow">
									<th> Email </th>
									<th>Family Name</th>
									<th>Given Name</th>
									<th></th>
								</tr>
							</tfoot>
							<tbody>
								<% 
								    Employee user = (Employee)session.getAttribute("currentEmployee"); 
									EmployeeBroker empBroker = EmployeeBroker.getBroker();
								    Employee emp = new Employee();
									emp.setActive(true);
									Employee[] empArray = empBroker.get(emp, user);
									int count = empBroker.getEmpCount();
									for (int index =0; index<count; index++)
								{
								%>
								         <%
								         	if (empArray[index].getEmail()!=null)
								         	{
								         %>
											<tr>
												<td><div id="profileImage"> <b> <%= empArray[index].getEmail() %> </b></div></td>
												<td><%= empArray[index].getFamilyName() %></td>
												<td><%= empArray[index].getGivenName() %></td>
												<td> <input type="radio" name="receipt" value="<%= empArray[index].getEmail() %>"> </td>
											</tr>
											
										<%
								         	}
										%>
								<% 
									}
								%>			
							 </tbody>
						</table>
					</div>
				</div> <!-- End Table Area -->
			</div> <!-- End empPopup div -->		
			<div id="backgroundPopup"></div>
						</form>
					</div> <!-- End Creation Form -->
			</div>
</div>
<div id="footer"></div>
</body>
</html>
