<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Includes -->
<jsp:include page="../wa_includes/pageLayout.jsp"/>


<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/email.css" type="text/css" media="screen" />

<!--  Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!--Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<form id="post-form" action="" method="post">

<div id="message">
	<div id="messageDetails">
				<p><label> To:  </label> <input type="text"  name ="recipent"> </p>
				<p><label> Subject:  </label> <input type="text"  name ="subject"></p>
	</div>
			<div id="messageContainer">
			        
			        <textarea  name="message" cols="92" rows="15" tabindex="101"></textarea>
			  
			</div>
			<input type="submit" name="submit" class="button" value="Send"> 
			<input type="submit" name="clear" class="button" value="Clear Screen">
			<input type="submit" name="cancel" class="button" value="Cancel"> 
</div>	
	
</form>



</body>
</html>