<% 
if(session.getAttribute("username") == null)
{
	response.sendRedirect("../wa_login/login.jsp");
}
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<!--  CSS files -->
<link rel="stylesheet" href="../CSS/headerStyle.css" type="text/css" media="screen" />


<title>Insert title here</title>
</head>
<body>

<div id="container">
<div id="header">
<div id="headerTitle">Deerfoot Inn and Casino</div>
</div>
</div>
