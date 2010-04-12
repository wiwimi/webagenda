<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.LocationBroker" %>
<%@ page import="business.schedule.Location" %>
<%@ page import="business.schedule.Position" %>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.Employee" %>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->
<link rel="stylesheet" href="CSS/schedule.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/user.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/tabs.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/tabs.js"></script>
<script type="text/javascript" src="../lib/js/popup.js"></script>

<title>Web Agenda- Request Shift Change</title>

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
		        response.sendRedirect("wa_login/login.jsp");
		        return;
			}
        }
	%>

</head>
<body>
	<div id="instructions">
		No shift change is guaranteed and it will only be granted if possible. 
		You will be notified whether the request was approved or denied.
	
	</div>	
	
	<div id="requestWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="requestUpperRectangle">
				<div class="widgetTitle" id="requestWidgetTitle">Shift Change Request</div>
			</div>
			
			<div class="widgetLowerRectangle" id="requestWidgetLowerRectangle">
	
					
						<ul class="tabs">
						    <li><a href="#tab1">1- Shift Change</a></li>
						    <li><a href="#tab2">2- Find a Replacement</a></li>
						    <li><a href="#tab3">3- Confirm</a></li>
						</ul>
			    <form class="addUserForm" method="post" id=userForm>
	               
					<div class="tab_container">
					    <div id="tab1" class="tab_content">
					        <!--Content-->
	                        <div id="request">
				
					
								 <fieldset>
									<legend > Current Shift </legend>
											<p><label>  From:   <em class="asterisk"> * </em> </label>
											<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
											</select>
											</p>
											<p>	<label> To: <em class="asterisk"> * </em> </label>
											<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
											</select>
											
											</p>	
									</fieldset>
									
									<fieldset>
									<legend > Requested Shift </legend>
								
											<p>	<label> From:</label> 
						
											<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
											</select>
											</p>
											<p>	<label> To: </label>
											<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
											</select>
											
											</p>
									
									</fieldset>
								   <fieldset>
									<legend > Reason(s) for shift change </legend>
								  <p>
									<input type="checkbox" name="school" value="school"> School<br>
									<input type="checkbox" name="conflict" value="conflict"> Scheduling Conflict<br>
									<input type="checkbox" name="personal" value="personal"> Personal<br>
									<input type="checkbox" name="other" value="other"> Other, Explain<br>
								 </p>
									<textarea  name="descreption" cols="23" rows="6" tabindex="101"></textarea>
								</fieldset>
									
										<input type="submit" name="clear" class="button" value="Clear Screen"> 
										
										<ul class="tabs">
											 <li>
												<a href="#tab2">
													<input type="submit" name="submit" class="button" value="Next >>">
												</a>
										   </li>
										</ul>
								</div>
						</div>
						</div> <!--End Tab One-->
	
				<div id="tab2" class="tab_content">
			       <!--Content-->
						
						You can skip this step but it will help the system find a replacement
						<br></br> <br></br>
							 <fieldset>
								<legend > Alternative Employee 	</legend>
												<!--This should be populated from MaintainJobType use case -->
										<div id="empButton">
										<p>
											<label id="theSelect" class="theSelect" for="empId">Employee Id: <em class="asterisk"> * </em></label>
											<input type="text" name="empId" size="30" maxLength="30" disabled="disabled" value=""/>
											<input type="button" name="submit" class="button" value="edit"/>
										</p>
										</div>
										
										<p>	<label> Employee's Preferred Locations: </label>
										<select name="shifts">
																<option value="1">Test</option>
																<option value="2">Test</option>
																<option value="3">Test</option>
										</select>
										</p>	
								</fieldset>
								
								 <input type="submit" name="button" class="button" value="Back">
								 <input type="button" name="button" class="button" value="Search" onClick="location.href='../wa_user/update_user.jsp'">
				                 <a href="#tab3" ><input type="submit" name="clear" class="button" value="Next"> </a>
				           
			     
			               </div>  <!--End Tab Two-->
			    	
				     <div id="tab3" class="tab_content">
				       <!--Content-->
				          <input type="submit" name="button" class="button" value="Back">
				         <input type="submit" name="clear" class="button" value="Confirm"> 
					 </div> <!--End Tab Three-->
				    </form>
	             </div>     <!--End Widget Lower Rectangle-->	                              
		    </div>                  <!--End Request Widget-->         
                                                    

<div id="idPopup">
			<a id="idPopupClose">x</a>
					<h1>Employees</h1>
				<div id="tableArea">
								<div class="userAdmin">
					<table class="sortable" id="userTable">
						<thead class="head">
							<tr class="headerRow">
								<th>Employee ID</th>
								<th>Family Name</th>
								<th>Given Name</th>
								<th>Position</th>
								<td> <input type="checkbox" name="option"> </td>
							 </tr>
						</thead>
						
						<tfoot class="foot">
							<tr class="headerRow">
								<th>Employee ID</th>
								<th>Family Name</th>
								<th>Given Name</th>
								<th>Position</th>
								<td> <input type="checkbox" name="option"> </td>
							</tr>
						</tfoot>
						<tbody>
							<% 
								EmployeeBroker empBroker = EmployeeBroker.getBroker();
								user = (Employee) session.getAttribute("currentEmployee");
								int count = empBroker.getEmpCount();
								
								Employee emp = new Employee();
								emp.setActive(true);
								Employee[] empArray = empBroker.get(emp, user);
								
								for(int index = 0; index < count; index++)
								{
							%>
										<tr>
											<td><a href="updateUserProfile.jsp"><div id="profileImage"> <b> <%= empArray[index].getEmpID() %> </b></div></a></td>
											<td><a href="updateUserProfile.jsp?id<%= empArray[index].getEmpID() %>"><%= empArray[index].getFamilyName() %></a></td>
											<td><a href="updateUserProfile.jsp?id<%= empArray[index].getEmpID() %>"><%= empArray[index].getGivenName() %></a></td>
											<td><a href="updateUserProfile.jsp?id<%= empArray[index].getEmpID() %>"><%= empArray[index].getPrefPosition() %></a></td>
											<td> <input type="checkbox" name="option"> </td>
										</tr>
							<% 
								}
							%>			
						
						</tbody>
					</table>
					
				</div>
				</div> <!-- End Table Area -->
					<p>
						<input type="button" name="save" class="button" value="Save"/>
					</p>
			</div> <!-- End empPopup div -->
<div id="footer"></div>
</body>
</html>
