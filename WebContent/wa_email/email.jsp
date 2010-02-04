<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="CSS/style.css" type="text/css"
	media="screen" />
<title>E-mail</title>

<script src ="../lib/js/jquery-1.3.2.min.js"   type ="text/javascript"> </script>
<!-- Sorttable is under the X11 licence, it is an open source project.-->
<script src="../lib/js/sorttable.js" type ="text/javascript"></script>
<script type="text/javascript" src="../lib/js/dashboard.js"></script>
</head>
<body>

<script>

	$(function()
	{ 
		
		$(".usersTable tr:nth-child(odd)").addClass("alt");
		
		$(".usersTable tr").mouseover(function()
		{
			$(this).addClass("over");
		});
		
		$(".usersTable tr").mouseout(function()
		{
			$(this).removeClass("over");
		});
		
		 { 
        $("#usersTable").tablesorter(); 
    } 
		
	} ); 
		

</script>


<b> Please click the headers to sort the records: </b>

</br>

<div id= "usersTable" class = "usersTable">

	<div class = "border" summary ="Records of employees">

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
					<td> Can you take my shif ? </td>
					<td> Feb/02/03 </td>
				
				</tr>
				<tr>
					<th> <input type=checkbox name="sort"></input> </th>
					<td> Joseph </td>
					<td> Can you take my shif ? </td>
					<td> Feb/02/03 </td>
				
				</tr>
				
				<tr>
					<th> <input type=checkbox name="sort"></input> </th>
					<td> Joseph </td>
					<td> Can you take my shif ? </td>
					<td> Feb/02/03 </td>
				
				</tr>
				
				<tr>
					<th> </th>
					<td>  </td>
					<td>  </td>
				
				</tr>
				
				
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
</body>

</html>