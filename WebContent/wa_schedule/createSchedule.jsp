<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!--  Daniel Kettle (implemented calendars onto start and end fields with Noorin's help) -->
<!--  Libraries -->
<script type ="text/javascript" src ="../lib/js/jquery-1.3.2.min.js"> </script>

<!-- Plug-ins -->
<script src="../lib/js/jquery.validate.js" type="text/javascript"></script>
<script src ="../lib/js/jquery.flashmessenger.js"   type ="text/javascript"> </script>


<!-- Javascript Files -->
<script src="../lib/js/cmxforms.js" type="text/javascript"></script>
<script src= "../lib/js/val.js" type="text/javascript"> </script>
<script type="text/javascript" src="../lib/js/jquery.datepick.js"></script>
<script type="text/javascript" src="../lib/js/calendar.js"></script>
<script type="text/javascript" src="../lib/js/popup.js"></script>
<script type="text/javascript" src="../lib/js/deleteUser.js"></script>

<!--  CSS files -->
<link rel="stylesheet" href="../CSS/creationForm.css" type="text/css"></link>
<link rel="stylesheet" href="CSS/table.css" type="text/css"></link>
<link rel="stylesheet" href="../CSS/Popup/popup.css" type="text/css"></link>
<link rel="stylesheet" href="../wa_dashboard/CSS/style.css" type="text/css" media="screen" />
<style type="text/css">@import "../CSS/jquery.datepick.css";</style> 
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/val.css" />
<link rel="stylesheet" type="text/css" media="screen" href="../CSS/Validation/screen.css" />
<link rel="stylesheet" href="../CSS/Flash/flashmessenger.css" type="text/css" media="screen"/>

<title>Web Agenda- Schedule</title>

<!-- Includes -->
<jsp:include page="../wa_includes/pageLayoutAdmin.jsp"/>

<script type="text/javascript">
var i = 1;

function addElement()
{
	if(i == 1)
	{
	// FIXME	
		//this is where we need to perform the add shift secion, once the shift area has been created, we will add it to the shift template
		  var ni = document.getElementById('myDiv');
		  var numi = document.getElementById('theValue');
		  var num = (document.getElementById('theValue').value -1)+ 2;
		  numi.value = num;
		  var newdiv = document.createElement('div');
		  var divIdName = 'my'+num+'Div';
		  newdiv.setAttribute('id',num);

		  var days=new Array();

		  days[0] = 'sunday';
		  days[1] = 'monday';
		  days[2] = 'tuesday';
		  days[3] = 'wednesday';
		  days[4] = 'thursday';
		  days[5] = 'friday';
		  days[6] = 'saturday';
		  
		  var weekOptions = '';
		  for(var index = 0; index < 7; index++)
		  {
			  weekOptions = weekOptions + '<option value='+index+'>'+days[index]+'</option>';
		  }
		  
		  newdiv.innerHTML = 'Start Time: <input type="text" size=10 name=startTime'+num+'/><br> End Time:<input type="text" size=10 name=endTime'+num+'/>'
		  +'<br> Employee: <select type="option" name="employeeId"><option value="emp1">Emp 1</option></select><br>Shift Date:<select name="weekSelect">'+weekOptions+'</select><br><button onClick="submit('+num+')">Confirm</button><hr>';
		  ni.appendChild(newdiv);
		  i++;
	}
}

function submit(divNum) 
{
	  var d = document.getElementById('myDiv');
	  var olddiv = document.getElementById(divNum);
	  d.removeChild(olddiv);
	  --i;
}
</script>

</head>
<body>

<div id="scheduleWidget" class="fullWidget">
			<div class="widgetUpperRectangle" id="scheduleUpperRectangle">
				<div class="widgetTitle" id="scheduleWidgetTitle">Schedule</div>
			</div>
			
			<div class="widgetLowerRectangle" id="scheduleWidgetLowerRectangle">
				<h2>Add Schedule</h2>
				<p>	<label class ="label"> Start Date: </label> <input type="text" name ="schedStart" id="schedStart" size ="10" value=""/></p>
				<p>	<label class ="label"> End Date:   </label> <input type="text" name ="schedEnd" id="schedEnd" size ="10" value=""/></p>  
				
				<button onclick="addElement()">Add Shift</button>
				<hr />
				
				<input type="hidden" value="0" id="theValue" />

				<div id="myDiv"> </div>
			</div>
</div>
<div id="footer"></div>
</body>
</html>