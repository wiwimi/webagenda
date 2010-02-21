<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/schedule.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/user.css" type="text/css" media="screen" />

<title>Web Agenda- Request Change</title>


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
					<input type="checkbox" name="babysitting" value="babysitting"> Babysitting Issues<br>
					<input type="checkbox" name="personal" value="personal"> Personal<br>
					<input type="checkbox" name="other" value="other"> Other, Explain<br>
				 </p>
					<textarea  name="descreption" cols="23" rows="6" tabindex="101"></textarea>
				 </legend>
				</fieldset>
					
					
			
				        <input type="submit" name="submit" class="button" value="Submit"> 
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
				</div>
				</form>
			</div>
			</div>

</div>
	

<div id="footer"></div>
</body>
</html>