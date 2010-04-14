<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.SkillBroker" %>
<%@ page import="business.Skill" %>
<%@ page import="business.Employee" %>
<%@ page import="business.permissions.PermissionLevel" %>
<%@ page import="business.permissions.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Skills</title>

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
<script type ="text/javascript" src ="../lib/js/jquery-1.3.2.min.js"> </script>

<!-- Plug-ins -->
<script type ="text/javascript" src="../lib/js/jquery.validate.js" type="text/javascript"></script>
<script type ="text/javascript" src ="../lib/js/jquery.flashmessenger.js"> </script>
<script type="text/javascript"  src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/cmxforms.js" ></script>
<script type="text/javascript" src= "../lib/js/val.js"> </script>
<script type="text/javascript" src="../lib/js/deleteSkill.js"></script>
<script type="text/javascript" src="../lib/js/helpSkill.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css"  href="../CSS/creationForm.css"></link>
<link rel="stylesheet" type="text/css"  href="CSS/icons.css"></link>
<link rel="stylesheet" type="text/css"  href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css"  href="../CSS/Validation/val.css" media="screen"/>
<link rel="stylesheet" type="text/css"  href="../CSS/Validation/screen.css" media="screen"/>
<link rel="stylesheet" type="text/css"  href="../CSS/Flash/flashmessenger.css" media="screen"/>
<link rel="stylesheet" type="text/css"  href="../CSS/Confirmation/confirm.css" media="screen"  />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />

</head>
<body>

			<div id="crumb">
				  <ul id="crumbsLonger">
				    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
				    <li><b><a href="#">New Skill</a></b></li>
				   </ul>
			</div>
		  <% 
					if(request.getParameter("message") != null)
					{
						if(request.getParameter("message").equals("true"))
						{
							//out.println("Location was added");
			%>
				              <script type="text/javascript">
		
								$(function()
								    {
										
										    $.flashMessenger("The skill has been successfully created", 
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
										
								       $.flashMessenger("The name you provided has already been used.",
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
		
		<div id="skillWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="skillUpperRectangle">
				<div class="widgetTitle" id="skillWidgetTitle">Skills <div id="helpIcon"></div> </div>
			</div>
			
		<div class="widgetLowerRectangle" id="skillLowerRectangle">
		
		<div id="instructions"> <br>
			Fields marked with <em class="asterisk" > *</em> are required.
		</div>	
				
		
        <div id ="creationForm">
			<form class="addSkillForm" action="../AddSkill" id="form" method="post">
			<div id="skill">
			
				<%
						String skillName = "", skillDesc ="";
					
					if (request.getParameter("skillDesc")!=null)
						skillDesc = request.getParameter("skillDesc");
					
					if (request.getParameter("skillName")!=null)
						skillName = request.getParameter("skillName");
					
					
					%>
			
			<div id="formButtons">
						<input type="submit" name="submit" class="button" value="Add"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='skillSearchResults.jsp?skillName=' + form.skillName.value + '&skillDesc=' + form.skillDesc.value;"> 
						<input type="reset" name="clear" class="button" value="Clear Screen"> 
						<br></br>
			</div>
				 <fieldset>
					<legend > Skill Details </legend>
					
							<p>	<label class="label"> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="skillName" class="required" size ="30" value="<%=skillName %>"> </p>
							<p>	<label class="label"> Description: </label></p>
							<textarea  name="skillDesc" cols="23" rows="6" tabindex="101"> <%=skillDesc %></textarea>
				</fieldset>
			
				</div>
				<div id="searchArea">
						<input type="submit" name="submit" class="button" value="Add"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='skillSearchResults.jsp?skillName=' + form.skillName.value + '&skillDesc=' + form.skillDesc.value;">
						<input type="reset" name="clear" class="button" value="Clear Screen"> 
						<br></br>
				</div>
				
				</form>
			</div>
			</div>

</div>
<div id="footer"></div>

</body>
</html>
