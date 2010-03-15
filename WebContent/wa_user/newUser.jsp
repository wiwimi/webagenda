<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="persistence.SkillBroker" %>
<%@ page import="business.Skill" %>
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

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!-- Plug-ins -->
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/deleteUser.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />

</head>
<body>
			<% 
					if(request.getParameter("message") != null)
					{
						if(request.getParameter("message").equals("true"))
						{
							//out.println("Position was added");
			 %>
				              <script type="text/javascript">
		
								$(function()
								    {
										
										    $.flashMessenger("The user has been successfully created", 
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
										$.flashMessenger("A problem had occured while creating the user.",
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
		<div id="instructions">
			Fields marked with <em class="asterisk" > *</em> are required.
			Randomly generated passwords are going to be sent to the employee's e-mail. If that field was missing it will be sent to your e-mail.
		</div>

        <div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">Users</div>
			</div>
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
		<div id ="creationForm">
			<form class="validatedForm" action="../AddUser" id="form" method="post">
				 <div id="personal">
					 	<div id="formButtons">
					 	    <input type="submit" name="submit" class="button" value="Add"> 
							<input type="button" name="submit" class="button" value="Search" onClick="location.href='updateUser.jsp?empId='+ form.empId.value">
							<input type="reset" name="clear" class="button" value="Clear Screen">
				</div>
				<fieldset>
					<legend > Personal </legend>
						<p>	<label class ="label"> Given Name:  <em class="asterisk"> * </em> </label> <input type="text"  name ="givenName" class ="required" size ="30"> </p>
						<p>	<label class ="label"> Family Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="familyName"  class ="required"  size ="30"> </p>
						<p>	<label class ="label"> Date of Birth: </label> <input type="text" name ="dob" id="dob" size ="10"></p>
						<p>	<label class ="label"> Username: </label> <input type="text" name ="user" id="user"   size ="30"></p>
	                    <p>
					         <label for="cemail" class="label"> E-Mail</label>
				             <input type="text" id="cemail" name="email" size="30"/>
				        </p>
					</fieldset>
				</div>
				<div id="work">
				<fieldset>
					<legend> Working Preferences </legend>
						<p>	<label id="theSelect" class="theSelect"> Status: <em class="asterisk"> * </em> </label> 
								<select name="status">
									<option value="enabled"> Enabled</option> 
									<option value="disabled" >Disabled</option>  
								</select> 
						</p>
										
						<!--This should be populated from the database -->
					    <p>
							<label id="theSelect" class="theSelect" for="empId">Employee Id: <em class="asterisk"> * </em> </label>
							<input type="text" name="empId" class="required" size="30" maxLength="30" value=""/>
						</p>
						
						<!--This should be populated from the database -->
					    <p>
							<label id="theSelect" class="theSelect" for="supervisorId">Supervisor Id:</label>
							<input type="text" size="30" disabled="disabled" value="Edit Supervisor Id" maxLength="30" value=""/>
							<input id="empIdButton" id="supIdButton" type="button" name="submit" class="button" value="Edit"/>
						</p>
					
						
						<!--This should be populated from MaintainJobType use case -->
					    <div id="posButton">
						<p>
							<label id="theSelect" class="theSelect"> Preferred Position: </label>  
							<input id="prefPositionBox" type="text" size="30" maxlength="30" disabled="disabled" value="Edit positions" name="prefPositionBox" />
							<input type="button" name="submit" class="button" value="Edit"/>
						</p>
						</div>
						
						<!--This should be populated from MaintainLocation use case -->
						<div id="locationsButton">
					    <p>		
							<label id="theSelect" class="theSelect"> Preferred Location: </label>  
							<input id="prefLocationBox" type="text" size="30" maxlength="30" disabled="disabled" value="Edit locations" name="prefLocationBox" />
							<input type="button" name="submit" class="button" value="Edit"/>
					    </p>
					    </div>
						
						<!--This should be populated from MaintainSkills use case -->
					 	<div id="skillsButton">
						<p>
							<label id="theSelect" class="theSelect"> Preferred Skills: </label>	
							<input id="prefSkillsBox" type="text" size="30" maxlength="30" disabled="disabled" value="Edit skills" name="prefSkillsBox" />
							<input type="button" name="submit" class="button" value="Edit"/>
						</p>
						</div>
		
						<!--This should be populated from MaintainPermission use case -->
						<p>
							<label id="theSelect" class="theSelect"> Permission level: </label>  
							<input id="permissionBox" type="text" size="30" maxlength="30" value="" name="permissionBox" />
							   <input type="button" id="perButton" name="submit" class="button" value="edit"/>
						</p>
					</fieldset>
					</div>
					<div id="formButtons">
							<input type="submit" name="submit" class="button" value="Add"> 
							<input type="button" name="submit" class="button" value="Search" onClick="location.href='updateUser.jsp';">
							<input type="reset" name="clear" class="button" value="Clear Screen"> 
					</div>
				</form>
			</div>
			</div>
            </div>
			
			<div id="locationsPopup">
				<a id="popupContactClose">x</a>
				<h1>Locations</h1>
				<div id="instructions">
					Closing the screen saves the selected items.
				</div>
				<div id="tableArea">
					<div class="userAdmin">
						<table class="sortable" id="userTable">
							<thead class="head">
								<tr class="headerRow">
									<th>Name</th>
									<th> </th>
								</tr>
							</thead>
							<tfoot class="foot">
								<tr class="headerRow">
									<th>Name</th>
									<th></th>
								</tr>
							</tfoot>
							<tbody>
								<% 
										LocationBroker broker = LocationBroker.getBroker();
										Location loc= null;
										
										loc = new Location("");
										Location[] locArray = broker.get(loc);
										for (Location printLoc : locArray)
										{
											System.out.println(printLoc);
										}
										for(int index = 0; index < locArray.length; index++)
										{
									%>
											<tr>
											<td>
														
												<a href="addLocation.jsp"><div id="locationImage"> <b> <%=locArray[index].getName()%> </b></div></a>
											</td>
											<td>
												<input type="radio" name="loc"> 
											</td>
									<% 
										}
									%>
								</tbody>
						</table>
					</div>
			</div> <!-- End Table Area -->
			</div>
			
			<div id="skillsPopup">
				<a id="popupContactClose2">x</a>
				<h1>Skills</h1>
				<div id="instructions">
						Closing the screen saves the selected items.
				</div>
				<div id="tableArea">
					<div class="userAdmin">
						<table class="sortable" id="userTable">
							<thead class="head">
								<tr class="headerRow">
									<th>Name</th>
									<td> <input type="checkbox" name="option"> </td>
						
								</tr>
							</thead>
					
							<tfoot class="foot">
								<tr class="headerRow">
									<th>Name</th>
									<td> <input type="checkbox" name="option"> </td>
								</tr>
							</tfoot>
							<tbody>
								<% 
									SkillBroker broker2 = SkillBroker.getBroker();
									
									Skill skill = new Skill("");
									Skill[] skillArray = broker2.get(skill);
									
									for(int index = 0; index <skillArray.length; index++)
									{
								%>
									<tr>
									   <td>
													
											<a href="newSkill.jsp?=<%=skillArray[index].getName()%>"> <b> <%=skillArray[index].getName()%> </b></a>
										
										</td>
										<td>
												<input type="checkbox" name="skill"> 
										</td>
									</tr>
								<% 
									}
								%>			
										
							</tbody>
						</table>
				   </div>
			</div> <!-- End Table Area -->
			</div>
			
			<div id="idPopup">
			<a id="idPopupClose">x</a>
					<h1>Employees</h1>
						<div id="instructions">
						Closing the screen saves the selected item.
					</div>
				<div id="tableArea">
					<div class="userAdmin">
					   <table class="sortable" id="userTable">
						 <thead class="head">
							 <tr class="headerRow">
								<th>Employee ID</th>
								<th>Family Name</th>
								<th>Given Name</th>
								<th></th>
							 </tr>
						  </thead>
						
						<tfoot class="foot">
							<tr class="headerRow">
								<th>Employee ID</th>
								<th>Family Name</th>
								<th>Given Name</th>
								<th></th>
							</tr>
						</tfoot>
						<tbody>
							<% 
								EmployeeBroker empBroker = EmployeeBroker.getBroker();
							
								int count = empBroker.getEmpCount();
								
								Employee emp = new Employee();
								emp.setActive(true);
								Employee[] empArray = empBroker.get(emp);
								
								for(int index = 0; index < count; index++)
								{
							%>
										<tr>
											<td><a href="updateUserProfile.jsp"><div id="profileImage"> <b> <%= empArray[index].getEmpID() %> </b></div></a></td>
											<td><a href="updateUserProfile.jsp?id<%= empArray[index].getEmpID() %>"><%= empArray[index].getFamilyName() %></a></td>
											<td><a href="updateUserProfile.jsp?id<%= empArray[index].getEmpID() %>"><%= empArray[index].getGivenName() %></a></td>
											<td> <input type="radio" name="supId"> </td>
										</tr>
							<% 
								}
							%>			
						
						</tbody>
					</table>
				</div>
				</div> <!-- End Table Area -->
			</div> <!-- End empPopup div -->
			
			<div id="positionsPopup">
					<a id="posPopupClose">x</a>
					<h1>Positions</h1>
						<div id="instructions">
						Closing the screen saves the selected item.
					</div>
					<div id="tableArea">
						<div class="userAdmin">
							<table class="sortable" id="userTable">
								<thead class="head">
									<tr class="headerRow">
										<th>Name</th>
										<th></th>
									</tr>
								</thead>
								<tfoot class="foot">
									<tr class="headerRow">
										<th>Name</th>
										<th></th>
									</tr>
								</tfoot>
								<tbody>
									<% 
										PositionBroker broker3 = PositionBroker.getBroker();
										Position pos = new Position("",null);
										Position[] posArray = broker3.get(pos);
										
										for(int index = 0; index <posArray.length; index++)
										{
									%>
										<tr>
										   <td>
														
												<a href="newPosition.jsp?=<%=posArray[index].getName()%>"> <b> <%=posArray[index].getName()%> </b></a>
											
											</td>
											<td>
													<input type="radio" name="pos" value=""> 
											</td>
										</tr>
									<% 
										}
									%>			
											
								</tbody>
							</table>
					</div> <!-- End User Admin div -->
				</div> <!-- End Table Area -->
			</div> <!-- End positionsPopup div -->
<div id="backgroundPopup"></div>
<div id="footer"></div>
</body>
</html>
