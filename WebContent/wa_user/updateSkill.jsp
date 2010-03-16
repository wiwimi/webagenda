<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.SkillBroker" %>
<%@ page import="business.Skill" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Skills</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!-- Plug-ins -->
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/deleteSkill.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/Validation/val.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="../CSS/Validation/screen.css" type="text/css" media="screen"/>
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>
</head>
<body>
		<div id="instructions">
			Fields marked with <em class="asterisk" > *</em> are required.
		</div>
			<% 
					if(request.getParameter("update") != null)
					{
						if(request.getParameter("update").equals("true"))
						{
							//out.println("Skill was added");
			%>
				              <script type="text/javascript">
		
								$(function()
								    {
										
										    $.flashMessenger("The skill has been successfully updated", 
											{ 	
												modal:true, 
												autoClose: false 
											});	
									});
								</script>
			
			    <% 			   
						}
						else if(request.getParameter("update").equals("false"))
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
				<div class="widgetTitle" id="skillWidgetTitle">Skills </div>
			</div>
			
		<div class="widgetLowerRectangle" id="skillLowerRectangle">
		
        <div id ="creationForm">
			<form class="addSkillForm" action="../UpdateSkill" id="form" method="post">
			<div id="skill">
			
			<div id="formButtons">
						<input type="submit" name="submit" class="button" value="Update"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='updateSkill.jsp';"> 
						<input type="reset" name="clear" class="button" value="Clear Screen"> 
						<br></br>
			</div>
				 <fieldset>
					<legend > Skill Details </legend>
					
					<%
						String skill = request.getParameter("skill");
					    
					    String skillName="", skillDesc="";
					    
					    if(skill!=null)
					    {
					    	StringTokenizer st = new StringTokenizer(skill);
						    String[] results = skill.split(",");
						    skillName= results[0];
						    if(results.length>1)
						    skillDesc= results[1];
						    Skill oldSkill = new Skill(skillName, skillDesc);
						    session.setAttribute("oldSkill",oldSkill);
					    }
                   %>
					
							<p>	<label class="label"> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="skillName" class="required" value="<%=skillName%>" size ="30"> </p>
							<p>	<label class="label"> Description: </label></p>
							<textarea  name="skillDesc" cols="23" rows="6" tabindex="101"> <%=skillDesc%> </textarea>
				</fieldset>
			
				</div>
				<div id="searchArea">
						<input type="submit" name="submit" class="button" value="Update"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='updatSkill.jsp';">
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
