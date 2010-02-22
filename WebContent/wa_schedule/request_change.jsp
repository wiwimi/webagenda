<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->

<link rel="stylesheet" href="CSS/schedule.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/user.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/tabs.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/tabs.js"></script>



<title>Web Agenda- Request Shift Change</title>


<!-- Includes -->
<jsp:include page="../wa_includes/pageLayout.jsp"/>


</head>
<body>

	No shift change is guaranteed and it will only be granted if possible. 
	You will be notified whether the request was approved or denied.
	
	<br><br>
	
	<div id="requestWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="requestUpperRectangle">
				<div class="widgetTitle" id="requestWidgetTitle">Shift Change Request</div>
			</div>
			
			<div class="widgetLowerRectangle" id="requestWidgetLowerRectangle">
	
					
						<ul class="tabs">
						    <li><a href="#tab1">1- Shift Change</a></li>
						    <li><a href="#tab2">2- Find a Replacement</a></li>
						    <li><a href="#tab2">3- Confirm</a></li>
						</ul>
	               
					<div class="tab_container">
					    <div id="tab1" class="tab_content">
					        <!--Content-->
	        
	        
							<div id ="userForm">
							<form class="addUserForm" method="post">
							<div id="request">
				
					
								 <fieldset>
									<legend > Current Shift
											<p><label>  From:   <em class="asterisk"> * </em> </label>
											<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
											</select>
											</p>
											<p>	<label> To: <em class="asterisk"> * </em> </label>
											<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
											</select>
											
											</p>	
									</legend>
									</fieldset>
									
									<fieldset>
									<legend > Requested Shift
								
											<p>	<label> From: <em class="asterisk"> * </em> </label> 
						
											<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
											</select>
											</p>
											<p>	<label> To: <em class="asterisk"> * </em> </label>
											<select name="shifts">
																	<option value="1">shift</option>
																	<option value="2">shift</option>
																	<option value="3">shift</option>
											</select>
											
											</p>
									</legend>
									</fieldset>
								   <fieldset>
									<legend > Reason(s) for shift change
								  <p>
									<input type="checkbox" name="school" value="school"> School<br>
									<input type="checkbox" name="conflict" value="conflict"> Scheduling Conflict<br>
									<input type="checkbox" name="personal" value="personal"> Personal<br>
									<input type="checkbox" name="other" value="other"> Other, Explain<br>
								 </p>
									<textarea  name="descreption" cols="23" rows="6" tabindex="101"></textarea>
								 </legend>
								</fieldset>
									
										<input type="submit" name="clear" class="button" value="Clear Screen"> 
										<br></br>
								</div>
							</form>
						</div>
						</div>
	
				<div id="tab2" class="tab_content">
			       <!--Content-->
			       
			       <div id ="userForm">
						<form class="addUserForm" method="post">
						<div id="request">
						
						You can skip this step but it will help the system find a replacement
						<br></br> <br></br>
							 <fieldset>
								<legend > Alternative Employee
										<p><label>  Name:   <em class="asterisk"> * </em> </label>
										<select name="shifts">
																<option value="1">Test</option>
																<option value="2">Test</option>
																<option value="3">Test</option>
										</select>
										</p>
										<p>	<label> Position: <em class="asterisk"> * </em> </label>
										<select name="shifts">
																<option value="1">Test</option>
																<option value="2">Test</option>
																<option value="3">Test</option>
										</select>
										<p>	<label> ID: <em class="asterisk"> * </em> </label>
										<select name="shifts">
																<option value="1">Test</option>
																<option value="2">Test</option>
																<option value="3">Test</option>
										</select>
										
										</p>	
								</legend>
								</fieldset>
								
							
								
							</div>
							   </form>
			     
			     			</div>
	    
	     <div id="tab3" class="tab_content">
	       <!--Content-->
	         tab 3
	    </div>
	    </div>
	</div>
	</div>		
	</div>
		

<div id="footer"></div>



</body>
</html>