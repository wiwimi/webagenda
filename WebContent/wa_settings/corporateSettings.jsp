<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>


<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>


<title>Corporate's Profile</title>
</head>
<body>

<div id="settingsWidget" class="fullWidget">
		<div class="widgetUpperRectangle" id="settingsUpperRectangle">
				<div class="widgetTitle" id="settingsWidgetTitle">Corporate's Profile</div>
		</div>
			
		<div class="widgetLowerRectangle" id="settingsLowerRectangle">
			<div id ="creationForm">
				<form class="validatedForm" action="" id="form" method="post">
					 <div id="corporate">
				 		<div id="formButtons">
							 	    <input type="submit" name="submit" class="button" value="Update"> 
									<input type="reset" name="clear" class="button" value="Clear Screen">
						</div>
						<fieldset>
				      		<legend>Your Company</legend>
								<p><label> Name:  </label> <input type="text"  name ="name"> </p>
						</fieldset>
						<fieldset>
							<legend>Schedule</legend>
									<p><label> Number of working days:</label> <select name="workdingDays">
									<option value="one">1</option>
									<option value="two">2</option>
									<option value="three">3</option>
									<option value="one">4</option>
									<option value="two">5</option>
									<option value="three">6</option>
									<option value="three">7</option>
									</select></p>
						</fieldset>
						<div id="formButtons">
							 	    <input type="submit" name="submit" class="button" value="Update"> 
									<input type="reset" name="clear" class="button" value="Clear Screen">
						</div>
					 </div>
				</form>
			</div>
		</div>
	</div>

<div id="footer"></div>


</body>
</html>
