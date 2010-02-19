/*author: Noorin*/

$(function()
	{ 
		
		$(".strippedTable tr:nth-child(odd)").addClass("alt");
		
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
		
	} );