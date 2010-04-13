<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.SkillBroker" %>
<%@ page import="persistence.PositionBroker" %>
<%@ page import="business.Skill" %>
<%@ page import="business.schedule.Position" %>
<%@ page import="business.Skill" %>
<%@ page import="java.util.*" %>
<%@ page import="business.Employee" %>
<%@ page import="business.permissions.PermissionBroker" %>
<%@ page import="business.permissions.PermissionLevel" %>
<%@ page import="business.permissions.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Positions</title>

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

<!-- Plug-ins -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!--Javascript files -->
<script type ="text/javascript" src= "../lib/js/val.js"> </script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type ="text/javascript" src ="../lib/js/jquery.flashmessenger.js"> </script>
<script type="text/javascript" src="../lib/js/deletePosition.js"></script>
<script type="text/javascript" src="../lib/js/helpUpdatePosition.js"></script>


<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="../CSS/creationForm.css" ></link>
<link rel="stylesheet" type="text/css" href="CSS/table.css" ></link>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" href="../CSS/Validation/screen.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../CSS/Popup/popup.css" media="screen"></link>
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen"/>

</head>
<body>
	
			<% 
					if(request.getParameter("update") != null)
					{
						if(request.getParameter("update").equals("true"))
						{
							//out.println("Position was added");
			 %>
				              <script type="text/javascript">
		
								$(function()
								    {
										
									    $.flashMessenger("The position has been successfully updated", 
										{ 	
											modal:true, 
											autoClose: false 
										});	
									});
								</script>
			<% 			   
						}
							if(request.getParameter("update").equals("noChanges"))
							{
								//out.println("Position was added");
				 %>
					              <script type="text/javascript">
			
									$(function()
									    {
											
										    $.flashMessenger("The current position already matches your changes", 
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
										
								       $.flashMessenger("An error occurred while updating the position. Contact your admin",
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
				<div class="widgetTitle" id="positionWidgetTitle">Positions <div id="helpIcon"></div> </div>
			</div>
		<div class="widgetLowerRectangle" id="positionLowerRectangle">
		
		<div id="instructions">
			Fields marked with <em class="asterisk" > *</em> are required.
		</div>
		<div id ="creationForm">
			<form class="addPositionForm" action="../UpdatePosition" id="form" method="post">
			<div id="position">
				<div id="formButtons">
						<input type="submit" name="submit" class="button" value="Update"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='posSearchResults.jsp?posName=' + form.posName.value + '&posDesc=' + form.posDesc.value"> 
						<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='updatePostion.jsp?posName=&posDesc='"> 
						<br></br>
			</div>
				<fieldset>
					<legend > Position Details </legend>
					
					<%
						String posName = request.getParameter("posName");
					    String posDesc = request.getParameter("posDesc");
					    
					   	Position oldPos = new Position(posName);
					   	PositionBroker posBroker = PositionBroker.getBroker();
						posBroker.initConnectionThread();
						
						Position[] results = posBroker.get(oldPos, user);
					   	session.setAttribute("oldPos",oldPos);
					 %>
					
						<p>	<label class="label"> Name: <em class="asterisk"> * </em> </label> <input type="text" value="<%=posName %>" name ="posName" class="required" size ="30"> </p>
							
								<!--This should be populated from MaintainSkills use case -->
							 	<div id="skillsButton">
								<p>
										<label id="theSelect" class="label"> Required Skills: <em class="asterisk"> * </em> </label>	
										<input id="prefSkillsBox" type="text" class ="required" size="30" maxlength="30" disabled="disabled" value="skills go here" name="prefSkillsBox" />
										<input type="button" name="submit" class="button" value="edit"/>
								</p>
								</div>	
						<p>	<label class="label"> Description: </label></p>
						<textarea  name="posDesc" cols="23" rows="6" tabindex="101"><%=posDesc%></textarea>
				</fieldset>
						<div id="formButtons">
						<input type="submit" name="submit" class="button" value="Update"> 
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='posSearchResults.jsp?posName=' + form.posName.value + '&posDesc=' + form.posDesc.value">
						<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='updatePostion.jsp?posName=&posDesc='">  
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
									    Skill[] pos_skills = results[0].getPos_skills();
									  	
										SkillBroker broker = SkillBroker.getBroker();
										Skill skill = new Skill("");
										Skill[] skillArray = broker.get(skill, user);
										
										for(int index = 0; index < skillArray.length; index++)
										{
											for (int x=0; x<pos_skills.length; x++)
											{
									%>
												<tr>
													<td>
														<div id="skillImage"> <b> <%=skillArray[index].getName()%> </b></div>
													</td>
											<%
												// Checking the assigned skills
												if (pos_skills!=null)
												{
													if (pos_skills[x].getName().equals(skillArray[index].getName()))
													{
											%>
														<td>
															<input type="checkbox" name="skillGroup" checked value="<%=skillArray[index].getName()%>"> 
														</td>
														
											<% 				
													}
													else 
													{
											%>			
														<td>
															<input type="checkbox" name="skillGroup" value="<%=skillArray[index].getName()%>" > 
														</td>
											<%
													}
												}
												else
												{
											 %>
										 			<td>
														<input type="checkbox" name="skillGroup" value="<%=skillArray[index].getName()%>" > 
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
			   </form>
			</div>
		 </div>
       </div>
<div id="backgroundPopup"></div>
<div id="footer"></div>
</body>
</html>
