<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page import="business.Skill" %>
<%@ page import="persistence.SkillBroker" %>
<%@ page import="business.Employee" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating Skill </title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!-- Plug-ins -->
<script type ="text/javascript" src ="../lib/js/jquery.flashmessenger.js"> </script>
<script type="text/javascript" src="../lib/js/jquery-impromptu.3.0.min.js"></script>

<!--  CSS files -->
<link rel="stylesheet" type="text/css" href="CSS/table.css"></link>
<link rel="stylesheet" type="text/css" href="../wa_dashboard/CSS/style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="../CSS/Confirmation/confirm.css" media="screen"/>
<link rel="stylesheet" type="text/css" href="../CSS/Flash/flashmessenger.css" media="screen"/>
<link rel="stylesheet" type="text/css"  href="CSS/icons.css"></link>

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->

<script type ="text/javascript" src="../lib/js/sorttable.js"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/deleteSkill.js"></script>
<script type="text/javascript" src="../lib/js/helpSkillSearchResults.js"></script>

</head>
<body>
<div id="instructions">
			Click on column headers to sort data through.
</div>
			 <% 
					if(request.getParameter("delete") != null)
					{
						if(request.getParameter("delete").equals("true"))
						{
			 %>
							<script type="text/javascript">
											$(function()
										    {
												
												    $.flashMessenger("The skill has been successfully deleted", 
													{ 	
														modal:true, 
														autoClose: false 
													});	
											});
							</script>
			   <% 			   
						 }
						else if(request.getParameter("delete").equals("false"))
						{
				%>
							<script type="text/javascript">
								$(function()
								    {
										$.flashMessenger("An error occured while deleting the Skill. Make sure the skill is not attached to a position",
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

	<div id="skillsWidget" class="fullWidget"> <div id="backIcon" > <a onClick="history.go(-1);return true;"> Back </a> </div>
			<div class="widgetUpperRectangle" id="skillsWidgetUpperRectangle">
				<div class="widgetTitle" id="skillsTitle">Skills <div id="helpIcon"></div> </div>
		</div>
		<div class="widgetLowerRectangle" id="skillsWidgetLowerRectangle">
		<div id="skillsIcon">
				<h3>Skills</h3>
			</div>
			<div id="searchArea">
			<form id="form">
				<input type="text" size=30/ name="randomSearch">
				<input type="button" name="submit"  class="button" value="Search" onClick="location.href='skillSearchResults.jsp?randomSearch=' + form.randomSearch.value"> 
			</form>
			</div>
			
						<% 
							Employee user = (Employee) session.getAttribute("currentEmployee");
							SkillBroker broker = SkillBroker.getBroker();
							Skill[] skillArray = null;
							Skill skill =null; 
							
							if(request.getParameter("skillName")!=null || request.getParameter("skillDesc")!=null )
							{
								skill = new Skill();
								if(!request.getParameter("skillName").equals(""))
								{
									skill.setName(request.getParameter("skillName"));
								}
								if (!request.getParameter("skillDesc").equals(""))
								{
									skill.setDesc(request.getParameter("skillDesc"));
								}
								
								else if (request.getParameter("skillName").equals("") && (request.getParameter("skillDesc").equals("")))
								{
									skill = new Skill("");
								}
								skillArray = broker.get(skill, user);
						 	}
							else if(request.getParameter("randomSearch")!=null)
							{
								if((!request.getParameter("randomSearch").equals("")))
								{
									// If it was a name
									Skill byName = new Skill(request.getParameter("randomSearch"));
									Skill[] skillArrayByName =  broker.get(byName, user);
									
									// If it was a desc
									Skill byDesc = new Skill();
									byDesc.setDesc(request.getParameter("randomSearch"));
									Skill[] skillArrayByDesc =  broker.get(byDesc, user);
									
									//If both are not empty Concat them
									if(skillArrayByDesc!=null && skillArrayByName!=null)
									{
										for (int i=0; i<skillArrayByName.length; i++)
										{
											for (int x=0; x<skillArrayByDesc.length; x++)
											{
												// If it is not refferring to the same object
												if(skillArrayByDesc[x].getName()!=skillArrayByName[i].getName())
												{
													int strlength = skillArrayByDesc.length + skillArrayByName.length;
													
													skillArray = new Skill[strlength];
													System.arraycopy(skillArrayByDesc, 0, skillArray, 0, skillArrayByDesc.length);
												    System.arraycopy(skillArrayByName, 0, skillArray, skillArrayByDesc.length, skillArrayByName.length);
													
												}
												else if (skillArrayByDesc[x].getName()==skillArrayByName[i].getName())
												{
													skillArray = skillArrayByName;
												}
											}
										}
									}
									else if(skillArrayByDesc!=null)
									{
										skillArray = skillArrayByDesc;
									}
									else if(skillArrayByName!=null)
									{
										skillArray = skillArrayByName;
										
									}
								}
								else if((request.getParameter("randomSearch").equals("")))
								{
									skill = new Skill("");
									skillArray = broker.get(skill, user);
								}
									
							}
							if(skillArray==null)
							{
							%>
								<div id="instructions">
					      		Your search didn't match any skills. <br>
					      		For better results try more general fields and make sure all words are spelled correctly.
						    	</div>
						   <% 
							}
							else
							{
							%>
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
									for(int index = 0; index<skillArray.length; index++)
									{
						%>
										<tr>
										   <td>
												<a href="updateSkill.jsp?skillName=<%=skillArray[index].getName()%>&skillDesc=<%= skillArray[index].getDesc() %>"> <b> <%=skillArray[index].getName()%> </b></a>
												<div class="row-actions"><span class='edit'>
												<a href="updateSkill.jsp?skillName=<%=skillArray[index].getName()%>&skillDesc=<%= skillArray[index].getDesc()%> "> Edit </a> </span>   <span class='delete'>
												<a href="javascript:;" onClick="removeSkill('<%=skillArray[index].getName()%>', '<%=skillArray[index].getDesc()%>' );">
													| Delete</a></span></div>
											</td>
											<td>
						<%
											     	if(skillArray[index].getDesc()!=null && !skillArray[index].getDesc().equals(""))
											     	{
						%>
											     		<a href="newSkill.jsp?skillName=<%= skillArray[index].getName() %>&skillDesc="> <%=skillArray[index].getDesc().trim() %> </a>
						<%			         }
											     	else
											     	{
						%>
											     		<a href="newSkill.jsp?skillName=<%= skillArray[index].getName() %>&skillDesc="> None </a>
						<%
											     	} 
										  }
									}
						%>
								  			</td>
								  		</tr>	
								</tbody>
							</table>
					</div>
			</div> <!-- End Table Area -->
		</div>
	</div>
<div id="footer"></div>
</body>
</html>

		
												
												
