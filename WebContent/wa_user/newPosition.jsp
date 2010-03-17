<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.SkillBroker" %>
<%@ page import="business.Skill" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Positions</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/deletePosition.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Flash/flashmessenger.css" />

</head>
<body>
	<div id="instructions">
		Fields marked with <em class="asterisk" > *</em> are required.
	</div>
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
										
										    $.flashMessenger("The position has been successfully created", 
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
						else if(request.getParameter("message").equals("skill"))
						{
				%>
				
						<script type="text/javascript">
								$(function()
								    {
										
								       $.flashMessenger("Please select at least 1 skill using the 'edit' button.",
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
	
		<div id="positionWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="positionUpperRectangle">
				<div class="widgetTitle" id="positionsWidgetTitle">Positions </div>
			</div>
		<div class="widgetLowerRectangle" id="positionsLowerRectangle">
		<div id ="creationForm">
			<form class="addPositionForm" action="../AddPosition" id="form" method="post">
			<div id="position">
				<div id="formButtons">
						<input type="submit" name="submit" class="button" value="Add"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='posSearchResults.jsp';"> 
						<input type="reset" name="clear" class="button" value="Clear Screen"> 
						<br></br>
			</div>
				<fieldset>
					<legend > Position Details </legend>
						<p>	<label class="label"> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="posName" class="required" size ="30"> </p>
							
								<!--This should be populated from MaintainSkills use case -->
							 	<div id="skillsButton">
								<p>
										<label id="theSelect" class="label"> Required Skills: <em class="asterisk"> * </em> </label>	
										<input id="prefSkillsBox" type="text" class ="required" size="30" maxlength="30" disabled="disabled" value="skills go here" name="prefSkillsBox" />
										<input type="button" name="submit" class="button" value="edit"/>
								</p>
								</div>	
							
							<p>	<label class="label"> Description: </label></p>
							<textarea  name="posDesc" cols="23" rows="6" tabindex="101"></textarea>
				</fieldset>
						<div id="formButtons">
						<input type="submit" name="submit" class="button" value="Add"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='posSearchResults.jsp';"> 
						<input type="reset" name="clear" class="button" value="Clear Screen"> 
						<br></br>
			          </div>
				  </div>
			<!--  Popup Section -->	 
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
										<th>Description</th>
									</tr>
								</thead>
								<tfoot class="foot">
									<tr class="headerRow">
										<th>Name</th>
										<th>Description</th>
									</tr>
								</tfoot>
								<tbody>
									<% 
										SkillBroker broker = SkillBroker.getBroker();
										Skill skill = new Skill("");
										Skill[] skillArray = broker.get(skill);
										
										for(int index = 0; index<skillArray.length; index++)
										{
									%>
										<tr>
										   <td>
												<a href="newSkill.jsp?=<%=skillArray[index].getName()%>"> <b> <%=skillArray[index].getName()%> </b></a>
											</td>
											<td>
													<input type="checkbox" name="skill" value="<%=skillArray[index].getName()%>"> 
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
			</form>
			</div>
		 </div>
       </div>
<div id="backgroundPopup"></div>
<div id="footer"></div>

</body>
</html>
