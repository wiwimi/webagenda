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
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/deleteUser.js"></script>
<script type="text/javascript" src="../lib/js/passwordGenerator.js"></script>
<script type="text/javascript" src="../lib/js/generatePwd.js"></script>
<script type="text/javascript" src="../lib/js/helpUser.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../CSS/tableEffects.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Confirmation/confirm.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Flash/flashmessenger.css" />
<link rel="stylesheet" type="text/css"  href="CSS/icons.css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/effects.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />

</head>
<body>
		<div id="crumb">
		  <ul id="crumbsLonger">
		    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
		    <li><b><a href="#">Reset Password (Step 1 of 2)</a></b></li>
		   </ul>
		</div>
		 <div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="passwordsUpperRectangle">
					<div class="widgetTitle" id="passwordTitle">Password <div id="helpIcon"> </div></div>
				</div>
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
		
			<div id="instructions">
				Select a user to reset the password for.
			</div>
			<div id="usersIcon">
				<h3>Users</h3>
			</div>
			
			
			<div id="tableArea">
				<div class="userAdmin">
						<% 
						
						    Employee emp = new Employee();
							EmployeeBroker broker = EmployeeBroker.getBroker();
							int count = broker.getEmpCount();
							Employee[] empArray = null;
							
						    //TODO: Debug statements here						    
						    //System.out.println(request.getParameter("empId") + " empId");
						    //System.out.println(request.getParameter("familyName") + " family Name");
						    //System.out.println(request.getParameter("givenName") + " given name");
						    //System.out.println(request.getParameter("user") + " user");
						    //System.out.println(request.getParameter("email") + " email");
						    
						   
					            if((request.getParameter("familyName")!=(null))
						    		&& (request.getParameter("givenName")!=(null))
						    		&& (request.getParameter("user")!=(null))
						    		&& (request.getParameter("email")!=(null)))
								{
						    
									    if((request.getParameter("familyName").equals(""))
									    		&& (request.getParameter("givenName").equals(""))
									    		&& (request.getParameter("user").equals(""))
									    		&& (request.getParameter("email").equals("")))
										{
									    	
								    			//out.println(request.getParameter("status"));
								    			emp.setActive(true);
												empArray = broker.get(emp, user);
								    	}
							            else
										{
											// Search based on parameters that are not blank or null
											
											if(!request.getParameter("familyName").equals(""))
												emp.setFamilyName(request.getParameter("familyName"));
											
											if(!request.getParameter("givenName").equals(""))
												emp.setGivenName(request.getParameter("givenName"));
											
											if(!request.getParameter("user").equals(""))
											    emp.setUsername(request.getParameter("user"));
											
											if(!request.getParameter("email").equals(""))
												emp.setEmail(request.getParameter("email"));
											
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
										<th>E-mail</th>
							
									</tr>
								</thead>
								
								<tfoot class="foot">
									<tr class="headerRow">
										<th>Username</th>
										<th>Employee ID</th>
										<th>Family Name</th>
										<th>Given Name</th>
										<th>Position</th>
										<th>E-mail</th>
									</tr>
								</tfoot>
								<tbody>
						
						<%
								for(int index = 0; index<empArray.length; index++)
								{
									String email ="NA", position="NA";
									if (empArray[index].getEmail()!=null)
									{
										email = empArray[index].getEmail();
									}
									if (empArray[index].getPrefPosition()!=null)
									{
										position = empArray[index].getPrefPosition();
									}
								
						%>
									<tr>
										<td>
											<a href="resetPasswordStepTwo.jsp?empUsername=<%=empArray[index].getUsername()%>"> <div id="profileImage"> <b><%=empArray[index].getUsername()%> </b></div></a>
											<div class="row-actions">
											   <span class='edit'>
												   <a href="resetPasswordStepTwo.jsp?empUsername=<%= empArray[index].getUsername() %>"> Reset Password </a>  
												</span>  
											</div>
										</td>
										<td><a href="resetPasswordStepTwo.jsp?empUsername=<%= empArray[index].getUsername() %>"><%= empArray[index].getEmpID() %></a></td>
										<td><a href="resetPasswordStepTwo.jsp?empUsername=<%= empArray[index].getUsername() %>"><%= empArray[index].getFamilyName() %></a></td>
										<td> <a href="resetPasswordStepTwo.jsp?empUsername=<%= empArray[index].getUsername() %>"><%= empArray[index].getGivenName() %></a> </td>
										<td> <a href="resetPasswordStepTwo.jsp?empUsername=<%= empArray[index].getUsername() %>"><%= position %></a> </td>
										<td> <a href="resetPasswordStepTwo.jsp?empUsername=<%= empArray[index].getUsername() %>"><%= email %></a> </td>
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
