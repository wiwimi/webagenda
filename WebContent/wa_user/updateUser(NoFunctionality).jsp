<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda- Updating User</title>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!--  CSS files -->
<link rel="stylesheet" href="CSS/user.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>


<!-- Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>	

</head>
<body>
<br></br>
		
		<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="usersWidgetUpperRectangle">
				<div class="widgetTitle" id="usersTitle">Users</div>
		</div>
			
		<div class="widgetLowerRectangle" id="usersWidgetLowerRectangle">

			<div id="usersIcon">
				<h3>Users</h3>
			</div>
			
			<div id="searchArea">
				<input type="text" value="" size=30></input><button type="submit" value="Search">Search</button>
			</div>
			
		
			
			<div id="tableArea">
							<div class="userAdmin">
				<table class="sortable" id="userTable">
					<thead class="head">
						<tr class="headerRow">
							<th>Username</th>
							<th>Employee ID</th>
							<th>Family Name</th>
							<th>Given Name</th>
							<th>Position</th>
							<th>Supervisor</th>
				
						</tr>
					</thead>
					
					<tfoot class="foot">
						<tr class="headerRow">
							<th>Username</th>
							<th>Employee ID</th>
							<th>Family Name</th>
							<th>Given Name</th>
							<th>Position</th>
							<th>Supervisor</th>
						</tr>
					</tfoot>
					
					<tbody>

									<tr>
										<td>
											
											<a href="addUser.jsp"><div id="profileImage">Test</div></a>
											<br />
										    <div class="row-actions"><span class='edit'>
										    <a href="#">Edit</a> |</span><span class='delete'>
										    <a class='submitdelete' href='#'>Delete</a></span></div>
										</td>
									<td><a href="addUser.jsp"> Test </a>
									
									
									</td>
									<td><a href="add_user.jsp">Test</a></td>
									<td> <a href="add_user.jsp">Test</a> </td>
									<td> <a href="add_user.jsp">Test</a> </td>
									<td> <a href="add_user.jsp">Test</a> </td>
									</tr>
									
									<tr>
									<td><a href="add_user.jsp"><div id="profileImage">Test</div></a></td>
									<td><a href="add_user.jsp">Test</a></td>
									<td><a href="add_user.jsp">Test</a></td>
									<td> <a href="add_user.jsp">Test</a>  </td>
									<td> <a href="add_user.jsp">Test</a> </td>
									<td> <a href="add_user.jsp">Test</a> </td>
									</tr>
								
					</tbody>
				</table>
				
			</div>
			
			</div> <!-- End Table Area -->
			
		</div>
</div>
<div id="footer"></div>

</body>
</html>