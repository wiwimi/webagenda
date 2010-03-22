<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Admin's Profile</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!-- Libraries -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<style type="text/css">@import "../lib/js/jquery.datepick.css";</style> 
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>

</head>
<body>
<div id="instructions">
	Fields marked with <em class="asterisk" > *</em> are required.
	Randomly generated passwords are going to be sent to the employee's e-mail. If that field was missing it will be sent to your e-mail.
</div>

		<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersWidgetTitle">Admin's Profile</div>
			</div>
		<div class="widgetLowerRectangle" id="usersLowerRectangle">
			<div id ="creationForm">
				<form class="validatedForm" action="" id="form" method="post">
					 <div id="personal">
					 		<div id="formButtons">
								 	    <input type="submit" name="submit" class="button" value="Update"> 
										<input type="reset" name="clear" class="button" value="Clear Screen">
							</div>
							<fieldset>
								<legend > Personal </legend>
									<p>	<label class ="label"> Given Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="givenName" size ="30"> </p>
									<p>	<label class ="label"> Family Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="familyName" size ="30"> </p>
									<p>	<label class ="label"> Date of Birth: <em class="asterisk"> * </em> </label> <input type="text" name ="dob" id="dob" size ="10"></p>
									<p>	<label class ="label"> Personal E-mail: </label> <input type="text"  name ="email" size ="30"> </p>
							</fieldset>
						</div>
						<div id="work">
						<fieldset>
							<legend > Account Settings </legend>
									<p> <label class ="label"> User Name: <em class="asterisk"> * </em> </label> <input type="text"  name ="userName" size ="30"> </p>
									<p> <label class ="label"> Old Password: <em class="asterisk"> * </em> </label> <input type="password"  name ="userName" size ="30"> </p>
									
									<p> <label class ="label"> New Password: <em class="asterisk"> * </em> </label> <input type="password"  name ="userName" size ="30"> </p>
									<p> <label class ="label"> New Password: <em class="asterisk"> * </em> </label> <input type="password"  name ="userName" size ="30"> </p>
						</fieldset>
						</div>
							<div id="formButtons">
								<input type="submit" name="submit" class="button" value="Update"> 
								<input type="reset" name="clear" class="button" value="Clear Screen"> 
							</div>
								 
						</form>
			</div>
		</div>
	</div>
<div id="footer"></div>

</body>
</html>
