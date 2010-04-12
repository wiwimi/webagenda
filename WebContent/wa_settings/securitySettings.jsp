<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="business.Employee" %>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.permissions.PermissionLevel" %>
<%@ page import="business.permissions.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Security Settings</title>

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
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />

</head>
<body>

<div id="crumb">
		  <ul id="crumbsLonger">
		    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
		    <li><b><a href="#">Security Settings</a></b></li>
		   </ul>
</div>
	<div id="usersWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="passwordsUpperRectangle">
					<div class="widgetTitle" id="passwordTitle">Security Settings</div>
				</div>
				
			<div class="widgetLowerRectangle" id="passwordLowerRectangle">
				<div id ="creationForm">
				<form action="securitySettings.jsp?load=y" method="post" style="text-align: right">
									 <input type="submit" id="getSearch" name="Search" value="Load Employees"/>
				</form>
				<form action="securitySettings.jsp?clear=y" method="post" style="text-align: right">
									 <input type="submit" id="getClear" name="Clear" value="Unload Employees"/>
				</form>
					<form class="validatedForm" action="securitySettings.jsp" id="form" method="post">
						<div id="security">
					 		<div id="formButtons">
							 	    <input type="submit" name="submit" class="button" value="Update" /> 
									<input type="reset" name="clear" class="button" value="Clear Screen" />
							</div>
							<fieldset>
								<legend > Security Settings </legend>
								<% 
								
								if(request.getParameter("clear") != null && !request.getParameter("clear").equals(""))
								{
									
								}
								else if(request.getParameter("secUser") != null && !request.getParameter("secUser").equals(""))
								{
									int empId = -1;
									try {
										empId = Integer.parseInt(request.getParameter("secUser"));
									}
									catch(NumberFormatException nfE) {
										response.sendRedirect("securitySettings.jsp?clear=y");
									}
									%>
									<input type="text" name="empId" value="<% out.println(empId); %>" id="empid" readonly />"
									<%
									out.println("<br /><br /><input type=\"submit\" name=\"update\" value=\"Update Permissions\" id=\"update\" />");
								}
								else {
									String load = request.getParameter("load");
									if(load != null)
										if(load.equals("y")) {
											user = (Employee) request.getSession().getAttribute("currentEmployee");
											Employee e = new Employee();
											
											Employee[] emps = EmployeeBroker.getBroker().get(e,user);
											for(int i = 0; i < emps.length; i++)
											{
												out.println("<a href=\"securitySettings.jsp?secUser=" + emps[i].getEmpID() + " \"> " + emps[i].getGivenName() + " " + 
														emps[i].getFamilyName() + " </a><br /> ");
											}
											
										}
								}
								%>	 
								<br />
								<br />"
						   </fieldset>
					   </div>
				 </form>
				</div>                  
	         </div>                     
	    </div>                             
<div id="footer"></div>
</body>
</html>