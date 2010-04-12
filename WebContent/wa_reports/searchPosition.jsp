<!-- Author: Noorin -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="persistence.SkillBroker" %>
<%@ page import="persistence.EmployeeBroker" %>
<%@ page import="business.Skill" %>
<%@ page import="business.Employee" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Positions</title>

<%
	Employee user = (Employee) request.getSession().getAttribute("currentEmployee");
	if (user.getLevel()==99)
	{
%>
	<!--  Includes -->
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
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>

<!-- Plug-ins -->
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!-- Java-script Files -->
<script type="text/javascript" src="../lib/js/cmxforms.js"></script>
<script type="text/javascript" src= "../lib/js/val.js" > </script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src ="../lib/js/jquery.flashmessenger.js"> </script>
<script type="text/javascript" src="../lib/js/deletePosition.js"></script>
<script type="text/javascript" src="../lib/js/helpPosition.js"></script>



<!--  CSS files -->
<link  type="text/css" rel="stylesheet" href="../CSS/creationForm.css"></link>
<link  type="text/css" rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link  type="text/css" rel="stylesheet" href="../wa_dashboard/CSS/style.css" media="screen" />
<link  type="text/css" rel="stylesheet" href="../CSS/Validation/val.css" media="screen"/>
<link  type="text/css" rel="stylesheet" href="../CSS/Validation/screen.css" media="screen"/>
<link  type="text/css" rel="stylesheet" href="../CSS/Popup/popup.css"></link>
<link  type="text/css" rel="stylesheet" href="../CSS/Flash/flashmessenger.css" />
<link  type="text/css" rel="stylesheet" href="../CSS/Confirmation/confirm.css" />
<link rel="stylesheet" href="../CSS/breadcrumb.css" type="text/css" media="screen" />



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
	
	 <div id="crumb">
	  <ul id="crumbsLonger">
	    <li><a href="../wa_dashboard/dashboard.jsp">Home</a></li>
	    <li><a href="reports.jsp">Reports</a></li>
	    <li><b><a href="#">Search a Position to Report</a></b></li>
	    
	   </ul>
     </div>
     
	<div id="positionWidget" class="fullWidget">
		<div class="widgetUpperRectangle" id="positionUpperRectangle">
			<div class="widgetTitle" id="positionsWidgetTitle">Positions  <div id="helpIcon"></div></div>
		</div>
		<div class="widgetLowerRectangle" id="positionsLowerRectangle">
		<div id ="creationForm">
			<form class="addPositionForm" action="../AddPosition" id="form" method="post">
				<div id="position">
				     <div id="instructions">
						Search a position to report.
					</div>
					<div id="formButtons">
						<input type="button" name="submit" class="button" value="Search" onClick="location.href='../wa_user/posSearchResults.jsp?posName=' + form.posName.value + '&posDesc=' + form.posDesc.value"> 
						<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='newPostion.jsp?posName=&posDesc='"> 
					</div>
					
					
					
					<%
						String posName = "", posDesc ="";
					
					if (request.getParameter("posDesc")!=null)
						posDesc = request.getParameter("posDesc");
					
					if (request.getParameter("posName")!=null)
						posName = request.getParameter("posName");
					
					
					%>
					<fieldset>
						<legend > Position Details </legend>
							<p>	<label class="label"> Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="posName" class="required" size ="30"/ value="<%=posName %>"> </p>
								
								<!--This should be populated from MaintainSkills use case -->
							 	
							 	<div id="skillsButton">
									<p>
										<label id="theSelect" class="label"> Required Skills: </label>	
										<input id="prefSkillsBox" type="text" size="30" maxlength="30" disabled="disabled" value="skills go here" name="prefSkillsBox" />
										<input type="button" name="submit" class="button" value="edit"/>
									</p>
								</div>	
							<p>	<label class="label"> Description: </label></p>
							<textarea  name="posDesc" cols="23" rows="6" tabindex="101" > <%=posDesc %> </textarea>
					</fieldset>
					<div id="formButtons">
							<input type="button" name="submit" class="button" value="Search" onClick="location.href='../wa_user/posSearchResults.jsp?posName=' + form.posName.value + '&posDesc=' + form.posDesc.value"> 
		 					<input type="reset" name="clear" class="button" value="Clear Screen" onClick="location.href='newPostion.jsp?posName=&posDesc='"> 
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
											Skill[] skillArray = broker.get(skill, user);
											
											for(int index = 0; index<skillArray.length; index++)
											{
										%>
												<tr>
												   <td>
														<a href="newSkill.jsp?=<%=skillArray[index].getName()%>"> <b> <%=skillArray[index].getName()%> </b></a>
													</td>
													<td>
														<input type="checkbox" name="skillCheck" value="<%=skillArray[index].getName()%>"> 
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
