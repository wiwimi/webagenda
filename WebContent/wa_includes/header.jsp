<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<!--  CSS files -->
<link rel="stylesheet" href="CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../CSS/style.css" type="text/css" media="screen" />

<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

<!-- Javascript Files -->
<script type="text/javascript" src="js/dashboard.js"></script>


<title>Insert title here</title>
</head>
<body>

<div id="header">
<div id="headerTitle">Deerfoot Inn and Casino</div>
<div id="userArea">
	<h6>Welcome: <%session.getAttribute("username"); %> | <a href="#">Settings</a> | Logout</h6>
</div>
</div>