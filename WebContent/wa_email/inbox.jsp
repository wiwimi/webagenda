<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/email.css" type="text/css" media="screen" />

<!--  Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<!-- Sorttable is under the X11 licence, it is an open source project.-->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>


<!--Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script src="../lib/js/zebraTable.js" type ="text/javascript"></script>

<title>Web Agenda - E-mail</title>

<!-- Includes -->
<jsp:include page="../wa_includes/pageLayout.jsp"/>


</head>
<body>

<div id= "email" class = "strippedTable">

<div id="mailWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="mailWidgetUpperRectangle">
				<div class="widgetTitle" id="mailWidgetTitle">E-mail</div>
			</div>
			
			<div class="widgetLowerRectangle" id="mailWidgetLowerRectangle">
			
			<div id="emailOptions">
			<h6> 
			<a href ="sent.jsp" id="sent"> Sent </a> | 
			<a href ="drafts.jsp" id="draft"> Drafts </a> |
			<a href ="" id="compose"> Compose </a> | 
			<a href ="" class="delete"> Delete </a>  <!--  Prompts the user to select a message if none of the checkboces were checked -->
			</h6>
			</div>
			
			<div class = "border" summary ="inbox">

				<table class = "sortable">
					<thead>
						<tr>
							<th> <input type=checkbox name="sort"> </input>  </th>
							<th> <b> From </b> </th>
							<th> <b>Subject </b> </th>
							<th> <b>Date </b> </th>
						</tr>
					</thead>
					<tbody>
						
						<tr>
							<th> <input type=checkbox name="sort"></input> </th>
							<td> Joseph </td>
							<td> Can you take my shift ? </td>
							<td> Feb/02/03 </td>
						
						</tr>
						<tr>
							<th> <input type=checkbox name="sort"></input> </th>
							<td> Noorin </td>
							<td> Late today ? </td>
							<td> Feb/01/03 </td>
						
						</tr>
						
						<tr>
							<th> <input type=checkbox name="sort"></input> </th>
							<td> Ali </td>
							<td> Sick leave ? </td>
							<td> Jan/02/03 </td>
						
						</tr>
						
						<tr>
							<th> </th>
							<td>  </td>
							<td>  </td>
							<td>  </td>
						
						</tr>
						
						<tr>
							<th>  </th>
							<td>  </td>
							<td>  </td>
							<td>  </td>
					
						
						</tr>
					</tbody>
	
				</table>
	</div>
	</div>
</div>
</div>
<div id="footer"></div>

</body>

</html>