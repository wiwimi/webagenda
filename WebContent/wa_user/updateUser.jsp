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
<title>Web Agenda- Updating a User</title>

    <%
		Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
	if (user.getLevel()==99)
	{
	%>
		<!-- Includes -->
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
<script type="text/javascript" src="../lib/js/helpUpdateUser.js"></script>

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
										
										    $.flashMessenger("The user has been successfully updated", 
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
										$.flashMessenger("A problem had occured while updating the user, contact your admin to make sure you have the right set of permissions",
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
		<div id="instructions">
			Fields marked with <em class="asterisk" > *</em> are required.
			Randomly generated passwords are going to be sent to the employee's e-mail. If that field was missing it will be sent to your e-mail.
		</div>

        <div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">Users <div id="helpIcon"></div></div>
			</div>
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
		<div id ="creationForm">
			<form class="validatedForm" action="../UpdateUser" id="form" method="post">
				 <div id="personal">
					 	<div id="formButtons">
					 	    <input type="submit" name="submit" class="button" value="Update"> 
								<input type="button" name="submit" class="button" value="Search" onClick="location.href='userSearchResults.jsp?familyName='+ form.familyName.value + '&empId=' + form.empId.value + '&dob=' + form.dob.value + '&givenName=' + form.givenName.value + '&user=' + form.user.value + '&status=' + form.status.value + '&email=' + form.email.value">
							<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='updateUser.jsp?empId=&familyName=&status=&username=&dob='">
						</div>
				<%
				 	String givenName = "", familyName ="", dob ="" , username ="", email="", password ="", empId="";
					String supId ="", location ="", position="";
					EmployeeBroker empBroker = EmployeeBroker.getBroker();
					int supIdInt=0;
					
					
					if(request.getParameter("empId")!=null)
					{
						if(!request.getParameter("empId").equals(""))
						{
							empId = request.getParameter("empId");
							Employee searchEmp = new Employee();
							int empIdInt = Integer.parseInt(empId);
							searchEmp.setEmpID(empIdInt);
							 
							Employee[] results = empBroker.get(searchEmp, user);
							
							// Retrive the not null fields
							givenName = results[0].getGivenName();
							familyName = results[0].getFamilyName();
							username = results[0].getUsername();
							
							//If these fields are not empty, store them
							if (results[0].getBirthDate()!=null)
							{
								dob = results[0].getBirthDate().toString();
								dob = dob.substring(5, 7) + '/' + dob.substring(8, dob.length())
								+'/' + dob.substring(0, 4);		// Reversing the date so that it matches the style used in the calendar
								
							}
							
							if(results[0].getPrefPosition()!=null)
								position = results[0].getPrefPosition();
							
							if (results[0].getEmail()!=null)
								email = results[0].getEmail();
							
							if(results[0].getSupervisorID()!=-1)
							{
								supIdInt = results[0].getSupervisorID();
								//out.println(supIdInt);
							}
							
							if(results[0].getPrefLocation()!=null)
							{
								location = results[0].getPrefLocation();
							}
							
							Employee oldEmp = new Employee();
							oldEmp.setEmpID(empIdInt);
							
							session.setAttribute("oldEmp", oldEmp);
						}
					}
					
				
				%>
				<fieldset>
					<legend > Personal </legend>
						<p>	<label class ="label"> Given Name:  <em class="asterisk"> * </em> </label> <input type="text" name="givenName" class="required" size="30" maxLength="30" value="<%=givenName%>"/> </p>
						<p>	<label class ="label"> Family Name: <em class="asterisk"> * </em> </label> <input type="text" name="familyName" class="required" size="30" maxLength="30" value="<%=familyName%>"/> </p>
						<p>	<label class ="label"> Date of Birth: </label> <input type="text" name ="dob" id="dob" size ="10" value="<%=dob%>"/></p>
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
						<p>	<label id="theSelect" class="theSelect"> Status: <em class="asterisk"> * </em> </label> 
								<select name="status">
									<option value="enabled"> Enabled</option> 
									<option value="disabled" >Disabled</option>  
								</select> 
						</p>
										
						<!--This should be populated from the database -->
					    <p>
							<label id="theSelect" class="theSelect" for="empId">Employee Id: <em class="asterisk"> * </em> </label>
							<input type="text" size="30" name="empId" class="required" maxLength="30" value="<%=empId%>"/>
						</p>
						
						<!--This should be populated from the database -->
					    <p>
							<label id="theSelect" class="theSelect" for="supervisorId">Supervisor Id:</label>
							<input type="text" size="30" disabled="disabled" value="Edit supervisor Id" maxLength="30" value=""/>
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
						
						<!--This should be populated from MaintainPermission use case -->
						<div id="permButton">
						<p>
							<label id="theSelect" class="theSelect"> Permission level: </label>  
							<input id="permissionBox" type="text" size="30" maxlength="30" value="Edit permission level" disabled ="disabled" name="permissionBox" />
							   <input type="button" name="submit" class="button" value="Edit"/>
						</p>
						</div>
					</fieldset>
					</div>
					<div id="formButtons">
							<input type="submit" name="submit" class="button" value="Update">
								<input type="button" name="submit" class="button" value="Search" onClick="location.href='userSearchResults.jsp?familyName='+ form.familyName.value + '&empId=' + form.empId.value + '&dob=' + form.dob.value + '&givenName=' + form.givenName.value + '&user=' + form.user.value + '&status=' + form.status.value + '&email=' + form.email.value">
							<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='updateUser.jsp?empId=&familyName=&status=&username=&dob='">	
					</div>
					
					<!-- The Pop up Screens -->
					
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
									LocationBroker locBroker = LocationBroker.getBroker();
									Location loc= null;
									loc = new Location("");
									Location[] locArray = locBroker.get(loc, user);
									
											for(int index = 0; index < locArray.length; index++)
											{
										%>
												<tr>
													<td>
														<div id="locationImage"> <b> <%=locArray[index].getName()%> </b></div>
													</td>
										<%
												// Checking the radio button that belongs to the Employees Preferred Location
												if (location!=null)
												{
													if (location.equals(locArray[index].getName()))
													{
											%>
														<td>
															<input type="radio" name="locationGroup" checked value="<%=locArray[index].getName()%>"> 
														</td>
														
											<% 				
													}
													else 
													{
											%>
														<td>
															<input type="radio" name="locationGroup" value="<%=locArray[index].getName()%>" > 
														</td>
											<%
													}
											 %>
											 	</tr>
											 <%
												}
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
				<div id="tableArea" >
						<div class="userAdmin">
						<table class="sortable" id="userTable" >
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
									Employee emp = new Employee();
									emp.setActive(true);
									Employee[] empArray = empBroker.get(emp, user);
									int count = empBroker.getEmpCount();
									for (int index =0; index<count; index++)
									{
								%>
										<tr>
											<td><div id="profileImage"> <b> <%= empArray[index].getEmpID() %> </b></div></td>
											<td><%= empArray[index].getFamilyName() %></td>
											<td><%= empArray[index].getGivenName() %></td>
								<% 
										// Checking the radio button that belongs to the Employees Supervisor Id
										if (supId!=null)
										{
											if (supIdInt == (empArray[index].getSupervisorID()))
											{
								%>
											   <td> <input type="radio" name="employeeGroup" checked value="<%= empArray[index].getEmpID() %>"> </td>
								<%
											}
											else // Supplier ID is not assigned yet
											{
								%>
												<td> <input type="radio" name="employeeGroup" value="<%= empArray[index].getEmpID() %>"> </td>
								<%
											}
										}
								 %>
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
										PositionBroker posBroker = PositionBroker.getBroker();
										Position pos = new Position("",null);
										Position[] posArray = posBroker.get(pos, user);
										
										for(int index = 0; index <posArray.length; index++)
										{
									%>
										<tr>
										   <td> <b> <%=posArray[index].getName()%> </b> </td>
										<%
										    // Checking the radio button that belongs to the Employees Preferred Location
												if (position!=null)
												{
													if (position.equals(posArray[index].getName()))
													{
											%>
														<td>
															<input type="radio" name="positionGroup" checked value="<%=posArray[index].getName()%>"> 
														</td>
											<% 				
													}
													else 
													{
											%>
														<td>
															<input type="radio" name="positionGroup" value="<%=posArray[index].getName()%>" > 
														</td>
											<%
													}
											 %>
											 		</tr>
											 <%
												}
										   }
									%>			
								</tbody>
							</table>
					</div> <!-- End User Admin div -->
				</div> <!-- End Table Area -->
			</div> <!-- End positionsPopup div -->
			
			<div id="permPopup">
					<a id="permPopupClose">x</a>
					<h1>Permissions</h1>
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
										PermissionBroker permBroker = PermissionBroker.getBroker();
										int level = user.getLevel();
										PermissionLevel[] permArray = permBroker.getAllBelow(level);
									%>
										<tr>
										   	<% if (permArray!=null)
										   	{
											   		for(int index = 0; index <permArray.length; index++)
													{
											   	%>
											   			<td>
															<b> <%=permArray[index].getLevel()%> </b>
														</td>
														<td>
															<input type="checkbox" name="perm" value="<%=permArray[index].getLevel()%>"> 
														</td>
											   	<%
													}
										   	}
										   	else
										   	{
											   	%>
											   		<td>
														No permissions accessible.
													</td>
												<%
										   	}
											%>
										</tr>
								</tbody>
							</table>
					   </div>
				</div> <!-- End Table Area -->
			</div>
			
		
<div id="backgroundPopup"></div>
			</form>
		</div> <!-- End Creation Form -->
	</div>
</div>
<div id="footer"></div>
</body>
</html>
