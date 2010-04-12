<!-- DOCTYPE is always recommended. see: http://www.quirksmode.org/css/quirksmode.html -->
<%@ page import="business.Employee" %>
<%@ page import="business.permissions.*" %>
<%@page import="persistence.PermissionBroker"%>
<!-- Author: Noorin -->
<html>
<head>

<title>Web Agenda - Request Shift Change </title>
	
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
	
	<!-- CSS Files -->
	<link rel="stylesheet" type="text/css" href="http://static.flowplayer.org/css/tabs.css"/> <!-- tab styling -->
	<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
	
	<!-- Javascript Files -->
	<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
	<script src="../lib/js/jquery.tools.min.js"></script> <!-- default set of jQuery Tools + jQuery 1.3.2 -->
	<script src="../lib/js/jqueryTabs.js"></script>
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
	
		  <div id="wizard">
			
				<!-- tabs -->
				<ul class="tabs">
					<li><a href="#" >Shift Change</a></li>
					<li><a href="#" >Alternative</a></li>
					<li><a href="#" >Confirm</a></li>
				</ul>
			    
				<!-- panes -->
				<div class="panes" id="creationForm">
					<div id="tab1">
					    <fieldset>
							<legend > Current Shift</legend>
									<p>
										<label>  From:   <em class="asterisk"> * </em> </label>
													<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
													</select>
									</p>
									<p>	
										<label> To: <em class="asterisk"> * </em> </label>
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
									<input type="checkbox" name="school" value="school"/> School<br></br>
									<input type="checkbox" name="conflict" value="conflict"/> Scheduling Conflict<br></br>
									<input type="checkbox" name="personal" value="personal"/> Personal<br></br>
									<input type="checkbox" name="other" value="other"/> Other, Explain<br></br>
								 </p>
				    	</fieldset>
						<p>
									<input type="submit" name="clear" class="button" value="Clear Screen"/> 
									<button class="next">Next &raquo;</button>
						</p>
				</div>

				<div id="tab2">
					<!--Content-->
							       	<p id="instructions">
								       You can skip this step but it will help the system find a replacement
								  	</p>
									 <fieldset>
										<legend > Alternative Employee 	</legend>
														<!--This should be populated from MaintainJobType use case -->
										
										<p id="empButton">
											<label id="theSelect" class="theSelect" for="empId">Employee Id: <em class="asterisk"> * </em></label>
											<input type="text" name="empId" size="30" maxLength="30" disabled="disabled" value=""/>
											<input type="button" name="submit" class="button" value="edit"/>
										</p>
										
										
										<p>	<label> Employee's Preferred Locations: </label>
										<select name="shifts">
																<option value="1">Test</option>
																<option value="2">Test</option>
																<option value="3">Test</option>
										</select>
										</p>	
								</fieldset>
								<p>
									<input type="button" name="button" class="button" value="Search" onClick="location.href='../wa_user/update_user.jsp'"/>
									<button class="prev">&laquo; Prev</button>
									<button class="next">Next &raquo;</button>
								</p>
			</div>
		    <div id="tab3">
					<p>
						<button class="prev">&laquo; Prev</button> 
						<button class="next">Confirm &raquo;</button>
		            </p>
			</div>
		</div>
	</div>
	</div>
</div>

<div id="footer"></div>
</body>
</html>
