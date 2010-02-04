<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />

<title>User Admin Update</title>

<!--  Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>

<!--  Includes -->
<jsp:include page="../wa_includes/pageLayout.jsp"/>


<!-- Sorttable is under the X11 licence, it is an open source project.-->
<!-- Javascript Files -->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script src="../lib/js/zebraTable.js" type ="text/javascript"></script>

</head>
<body>



</br>

<div id="usersWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="quickLinksUpperRectangle">
				<div class="widgetTitle" id="usersTitle">Users</div>
			</div>
			
			<div class="widgetLowerRectangle" id="quickLinksLowerRectangle">

			<div id= "strippedTable" class = "strippedTable">
			<b> Please click the headers to sort the records: </b>
			<div class = "border" summary ="Records of employees">
			<div id=center>
			<table class = "sortable" id="center">
			<caption> <b> Employees </b> </caption>
				<thead>
					<tr>
				
						<th> <b> Emp. ID </b> </th>
						<th> <b>First Name </b> </th>
						<th> <b>Last Name </b> </th>
						<th> <b>Position </b> </th>
						<th> <b> Supervisor </b> </th>
					
					</tr>
				</thead>
				
				<tbody>
					
					<tr>
						<th> 450607 </th>
						<td> Joseph </td>
						<td> Smith </td>
						<td> Bartender </td>
						<td> Williamss </td>
					
					</tr>
					<tr>
						<th> 450602 </th>
						<td> Noor </td>
						<td> Jack </td>
						<td> Driver </td>
						<td> Mike </td>
					
					</tr>
					
					<tr>
						<th> 33333 </th>
						<td> Dann </td>
						<td> Liston </td>
						<td> Singer </td>
						<td> Ali Ala</td>
					
					</tr>
					
					<tr>
						<th> 12345 </th>
						<td> Jessica </td>
						<td> Gall </td>
						<td> Waitress </td>
						<td> Jack Jack</td>
					
					</tr>
					<tr>
						<th> 450607 </th>
						<td> Joseph </td>
						<td> Smith </td>
						<td> Bartender </td>
						<td> Williams </td>
					
					</tr>
					<tr>
						<th> 450602 </th>
						<td> Noor </td>
						<td> Jack </td>
						<td> Driver </td>
						<td> Mike </td>
					
					</tr>
					
					<tr>
						<th> 33333 </th>
						<td> Dann </td>
						<td> Liston </td>
						<td> Singer </td>
						<td> Ali Ala</td>
					
					</tr>
					
					<tr>
						<th> 12345 </th>
						<td> Jessica </td>
						<td> Gall </td>
						<td> Waitress </td>
						<td> Jack Jack</td>
					
					</tr>
				</tbody>
		</table>
		</div>
		</div>
		</div>
	</div>

</div>
</body>

</html>