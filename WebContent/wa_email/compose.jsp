<%@ page language="java" contentType="text/html; charset=ISO-8859-1"pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/email.css" type="text/css" media="screen" />

<!--  Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!--Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Agenda - Compose</title>

<!-- Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

</head>
<body>
	<form id="post-form" action="" method="post">
		<div id="message">
			<div id="mailWidget" class="fullWidget">
				<div class="widgetUpperRectangle" id="mailWidgetUpperRectangle">
					<div class="widgetTitle" id="mailWidgetTitle">E-mail</div>
				</div>
			<div class="widgetLowerRectangle" id="mailWidgetLowerRectangle">
				<div id= "emailOptions">
					<h6> 
						<a href ="" id="send"> Send </a> | 
						<a href ="" id="draft"> Draft </a> |
						<a href ="" id="close"> Close </a> |  <!-- Close must take the user to the inbox page -->
						<a href ="" id="clear"> Clear Screen </a> 
					</h6>
				</div>
				<div id="messageDetails">
					<p><label> To:  </label> <input type="text"  name ="recipent"> </p>
					<p><label> Subject:  </label> <input type="text"  name ="subject"></p>
				</div>
				
				<div id="messageContainer">
				        <textarea  name="message" cols="120" rows="17" tabindex="101"></textarea>
				</div>
			</div>
			</div>
		</div>	
	</form>

<div id="footer"></div>

</body>
</html>
