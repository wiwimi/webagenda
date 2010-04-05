/*author: Noorin*/


$(document).ready(function()
{
	$("tr:nth-child(odd)").addClass("odd");
	$("tr:nth-child(even)").addClass("even");
});

/**
$(function()
{ 
	
	$(".strippedTable tr:nth-child(odd)").addClass("odd");
	
	$(".strippedTable tr").mouseover(function()
	{
		$(this).addClass("over");
	});
	
	$(".strippedTable tr").mouseout(function()
	{
		$(this).removeClass("over");
	});
	
	 { 
    $("#strippedTable").tablesorter(); 
} 
	
} );**/

