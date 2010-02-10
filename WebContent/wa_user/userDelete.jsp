<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayout.jsp"/>

<!-- Libraries -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js"></script>
<style type="text/css">@import "../lib/js/jquery.datepick.css";</style> 
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>

<!-- Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/userAddStyle.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<title>Adding User</title>
</head>
<body>
Fields marked with <em class="asterisk" > *</em> are required.
<br></br>
Randomly generated passwords are going to be sent to the employee's e-mail. If that field was missing it will be sent to your e-mail.
<br></br>
<br></br>

		<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersUpperRectangle">
				<div class="widgetTitle" id="usersTitle">Users</div>
			</div>
			
		<div class="widgetLowerRectangle" id="quickLinksLowerRectangle">

			<div id="usersIcon">
				<h3>Users</h3>
			</div>
			
			<div id="searchArea">
				<input type="text" value="" size=30></input><button type="submit" value="Search">Search</button>
			</div>
			
			<div id="tableArea">
							<div class="userAdmin">
				<table class="userTable">
					<thead class="head">
						<tr class="headerRow">
							<th>Username</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Address</th>
							<th>Country</th>
							<th>Workgroup</th>
						</tr>
					</thead>
					
					<tfoot class="foot">
						<tr class="headerRow">
							<th>Username</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Address</th>
							<th>Country</th>
							<th>Workgroup</th>
						</tr>
					</tfoot>
					
					<tbody>

									<tr>
									<td><a href="#"><div id="profileImage">Test</div></a></td>
									<td><a href="#">Test</a></td>
									<td><a href="#">Test</a></td>
									<td>Test</td>
									<td>Test</td>
									<td>Test</td>
									</tr>
									
									<tr>
									<td><a href="#"><div id="profileImage">Test</div></a></td>
									<td><a href="#">Test</a></td>
									<td><a href="#">Test</a></td>
									<td>Test</td>
									<td>Test</td>
									<td>Test</td>
									</tr>
					</tbody>
				</table>
			</div>
			</div>
			
		</div>

<div id="footer"></div>
</div>
</body>
</html>