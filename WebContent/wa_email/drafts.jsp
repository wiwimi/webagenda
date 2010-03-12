<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Author: Noorin -->

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Web Agenda - Drafts</title>

<!-- Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<!--  CSS files -->
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/email.css" type="text/css" media="screen" />
<link rel="stylesheet" href="CSS/table.css" type="text/css" media="screen" />

<!--  Libraries -->
<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<!-- Sorttable is under the X11 licence, it is an open source project.-->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>


<!--Javascript Files -->
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
<script src="../lib/js/zebraTable.js" type ="text/javascript"></script>

</head>
<body>

<div id="mailWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="mailWidgetUpperRectangle">
				<div class="widgetTitle" id="mailWidgetTitle">E-mail</div>
			</div>
			
			<div class="widgetLowerRectangle" id="mailWidgetLowerRectangle">
			
			<div id="emailOptions">
			<h6> 
			<a href ="sent.jsp" id="sent"> Sent </a> | 
			<a href ="drafts.jsp" id="draft"> Drafts </a> |
			<a href ="compose.jsp" id="compose"> Compose </a> | 
			<a href ="" id="delete"> Delete </a>
			</h6>
			</div>
			
			<div id="tableArea">
							<div class="userAdmin">
				<table class="sortable" id="userTable">
					<thead class="head">
						<tr class="headerRow">
						    <th> <input type="checkbox"></input></th>
							<th>To</th>
							<th>Subject</th>
							<th>Date</th>
				    </tr>
					</thead>
					<tfoot class="foot">
						<tr class="headerRow">
						    <th> <input type="checkbox"></input></th>
							<th>To</th>
							<th>Subject</th>
							<th>Date</th>
						</tr>
					</tfoot>
					<tbody>
						<tr>
							<th> <input type="checkbox"></input> </th>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<th> <input type="checkbox"></input> </th>
							<td></td>
							<td></td>
							<td></td>
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
