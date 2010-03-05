<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    	               "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="CSS/loginStyle.css" type="text/css" media="screen" />

<title>Web Agenda Login</title>
</head>
<body>
<div id="container">
<div id="header">
<div id="headerTitle">Deerfoot Inn and Casino</div>
</div>

<div id="middle">
	<div id="loginBox">
		<div id="loginBoxTitle">Web Agenda Login</div>
		<div id="loginLine"></div>
		
		<% 
			if(request.getParameter("LoginAttempt") != null)
			{
				out.println("<div id=\"errorMessage\">");
				out.println("There was an error with your username and password combination. Please try again");
				out.println("</div>");
			}
		%>
		
		<div id="loginArea">
			<form action="../login" method="POST">
			
				<label for="username">Username:</label>
				<input type="text" name="username" size=20/><br>
				<label for="password">Password: </label>
				<input type="password" name="password" size=20></input><br>
				<div id="rememberMe">Remember Me?<input type="checkbox" name="rememberMe"></div>
				<p class="submit"><input type="submit" value="Login" /></p>
			</form>
		</div>
			<div id="forgotPassword">
				Forgot your password?
			</div>		
	</div>
</div>

<div id="footer"></div>
</div>
</body>
</html>
