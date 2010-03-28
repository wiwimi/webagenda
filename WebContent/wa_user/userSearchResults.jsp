<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="business.Employee" %>
<%@ page import="persistence.EmployeeBroker" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating User</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!-- Plug-ins -->
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Confirmation/confirm.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Flash/flashmessenger.css" />

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/deleteUser.js"></script>
<script type="text/javascript" src="../lib/js/helpUserSearchResults.js"></script>

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
												
												    $.flashMessenger("The user has been successfully deleted", 
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
										$.flashMessenger("An error occured while deleting the User. Please contact your admin to make sure you have the right set of permissions",
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
			<div class="widgetUpperRectangle" id="usersWidgetUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">Users <div id="helpIcon"></div> </div>
		</div>
			
		<div class="widgetLowerRectangle" id="usersWidgetLowerRectangle">

			<div id="usersIcon">
				<h3>Users</h3>
			</div>
			
			<div id="searchArea">
			<form id="form">
					<input type="text" size=30 name="randomSearch">
					<input type="button" name="submit"  class="button" value="Search" onClick="location.href='userSearchResults.jsp?randomSearch=' + form.randomSearch.value">
			</form>
			</div>
			<div id="tableArea">
				<div class="userAdmin">
						<% 
						
						    Employee emp = new Employee();
							Employee user = (Employee) session.getAttribute("currentEmployee");
							EmployeeBroker broker = EmployeeBroker.getBroker();
							int count = broker.getEmpCount();
							Employee[] empArray = null;
							
						    //TODO: Debug statements here						    
						    //System.out.println(request.getParameter("empId") + " empId");
						    //System.out.println(request.getParameter("familyName") + " family Name");
						    //System.out.println(request.getParameter("givenName") + " given name");
						    //System.out.println(request.getParameter("user") + " user");
						    //System.out.println(request.getParameter("email") + " email");
						    
						    // Search by Employee Id
						      if((request.getParameter("empId")!=(null)) 
							    		&& (request.getParameter("familyName")!=(null))
							    		&& (request.getParameter("givenName")!=(null))
							    		&& (request.getParameter("user")!=(null))
							    		&& (request.getParameter("email")!=(null)))
							{
						    
								    if((!request.getParameter("empId").equals("")) 
								    		&& (request.getParameter("familyName").equals(""))
								    		&& (request.getParameter("givenName").equals(""))
								    		&& (request.getParameter("user").equals(""))
								    		&& (request.getParameter("email").equals("")))
									{
								    		int empInteger = Integer.parseInt(request.getParameter("empId"));
										   emp.setEmpID(empInteger);
										   empArray = broker.get(emp, user);
									}
							    // Search by Enabled Users 
							    else if((request.getParameter("empId").equals("")) 
						    		&& (request.getParameter("familyName").equals("")) 
						    		&& (request.getParameter("givenName").equals("")) 
						    		&& (request.getParameter("user").equals("")) 
						    		&& (request.getParameter("email").equals(""))
						    		&& (request.getParameter("status").equalsIgnoreCase("enabled")))
							    {
							    	//out.println(request.getParameter("status"));
							    	emp.setActive(true);
									empArray = broker.get(emp, user);
							    }
							    // Search by disabled Users
							      else if((request.getParameter("empId").equals("")) 
						    		&& (request.getParameter("familyName").equals("")) 
						    		&& (request.getParameter("givenName").equals("")) 
						    		&& (request.getParameter("user").equals("")) 
						    		&& (request.getParameter("email").equals(""))
						    		&& (request.getParameter("status").equalsIgnoreCase("disabled")))
							    {
							    	emp.setActive(false);
									empArray = broker.get(emp, user);
							    }
								else
								{
									//System.out.println("not all null");
									// Search based on parameters that are not blank or null
									
									if(!request.getParameter("empId").equals(""))
									{
										int empInteger = Integer.parseInt(request.getParameter("empId"));
										emp.setEmpID(empInteger);
									}
									
									if(!request.getParameter("familyName").equals(""))
										emp.setFamilyName(request.getParameter("familyName"));
									
									if(!request.getParameter("givenName").equals(""))
										emp.setGivenName(request.getParameter("givenName"));
									
									if(!request.getParameter("user").equals(""))
										emp.setUsername(request.getParameter("user"));
									
										empArray = broker.get(emp, user);
									
								}
							}
						    else
						    {
						    	emp.setActive(true);
								empArray = broker.get(emp, user);
						    	  
						    }
							if(empArray==null)
							{
						%>
								<div id="instructions">
									Your search didn't match any users.<br>
						      		For better results try more general fields and make sure all words are spelled correctly.
						      	</div>
						      	
						<%
							}
							else
							{
						%>
								<table class="sortable" id="userTable">
								<thead class="head">
									<tr class="headerRow">
										<th>Username</th>
										<th>Employee ID</th>
										<th>Family Name</th>
										<th>Given Name</th>
										<th>Position</th>
										<th>Supervisor</th>
										<th>Location</th>
							
									</tr>
								</thead>
								
								<tfoot class="foot">
									<tr class="headerRow">
										<th>Username</th>
										<th>Employee ID</th>
										<th>Family Name</th>
										<th>Given Name</th>
										<th>Position</th>
										<th>Supervisor</th>
										<th>Location</th>
									</tr>
								</tfoot>
								<tbody>
						
						<%
								for(int index = 0; index<empArray.length; index++)
								{
						%>
									<tr>
										<td>
											<a href="updateUser.jsp?empId=<%=empArray[index].getEmpID()%>"> <div id="profileImage"> <b><%=empArray[index].getUsername()%> </b></div></a>
											<div class="row-actions"><span class='edit'>
											<a href="updateUser.jsp?empId=<%= empArray[index].getEmpID() %>"> Edit </a>   | </span>  <span class='delete'>
											<a href="javascript:;" onClick="removeUser('<%=empArray[index].getEmpID()%>');">
											Delete</a></span></div>
										</td>
										<td><a href="updateUser.jsp?empId=<%= empArray[index].getEmpID() %>"><%= empArray[index].getEmpID() %></a></td>
										<td><a href="updateUser.jsp?empId=<%= empArray[index].getEmpID() %>"><%= empArray[index].getFamilyName() %></a></td>
										<td> <a href="updateUser.jsp?empId=<%= empArray[index].getEmpID() %>"><%= empArray[index].getGivenName() %></a> </td>
										<td> <a href="updateUser.jsp?empId=<%= empArray[index].getEmpID() %>"><%= empArray[index].getPrefPosition() %></a> </td>
										<td> <a href="updateUser.jsp?empId=<%= empArray[index].getEmpID() %>"><%= empArray[index].getSupervisorID() %></a> </td>
										<td> <a href="updateUser.jsp?empId=<%= empArray[index].getEmpID() %>"><%= empArray[index].getPrefLocation() %></a> </td>
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
</div>
<div id="footer"></div>

</body>
</html>
