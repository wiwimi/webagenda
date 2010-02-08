<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/help.css" type="text/css" media="screen" />

<!--  Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<!-- Sorttable is under the X11 licence, it is an open source project.-->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>


<!--Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script src="../lib/js/zebraTable.js" type ="text/javascript"></script>

<title>Contact Admin Form</title>

<!-- Includes -->
<jsp:include page="../wa_includes/pageLayout.jsp"/>

</head>
<body>

 Fields marked with <em class="asterisk" > *</em> are required.
 <br></br>
  <br></br>
<div id="HelpWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="helpWidgetUpperRectangle">
				<div class="widgetTitle" id="helpWidgetTitle">Help</div>
			</div>
			
			<div class="widgetLowerRectangle" id="helpWidgetLowerRectangle">

			<div id="contactAdmin">
			<form action="">
				 <fieldset>
				
					<legend > Contact Admin
						<p><label> Supervisor: <em class="asterisk"> * </em> </label>
						<select>
							  <option>John James</option>
							  <option>Mike </option>
							  <option>Pauline Turnbull</option>
						</select>
						</p>
						
						<p>	
						<label> What is your enquiry?  <em class="asterisk"> * </em></label>
						<textarea  name="message" cols="60" rows="15" tabindex="101"></textarea>
						</p>
			  	  
						<input type="submit" name="submit" class="button" value="Send"> 
						<input type="submit" name="clear" class="button" value="Clear Screen"> 
						<br></br>
						
			    </legend>
				</fieldset>
				</form>
			</div>
			</div>
</div>
</body>
</html>