$(document).ready(function()
{	
	//$('#quickLinksWidget').draggable({ snapMode: 'outer' });
	
	    $('#helpUpperRectangle').click(function()
		{
			if($('#helpLowerRectangle').is(":hidden"))
			{
		
				$('#helpLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#helpLowerRectangle').slideUp('fast');
			
			}
		
		});
		
		$('#usersUpperRectangle').click(function()
		{
			if($('#usersLowerRectangle').is(":hidden"))
			{
		
				$('#usersLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#usersLowerRectangle').slideUp('fast');
			}
		
		});
		$('#mailUpperRectangle').click(function()
		{
			if($('#mailLowerRectangle').is(":hidden"))
			{
		
				$('#mailLowerRectangle').slideDown('fast');
			}
			else
			{
			
				$('#mailLowerRectangle').slideUp('fast');
			
			}
		
		});
		$('#scheduleWidgetUpperRectangle').click(function()
		{
			if($('#scheduleWidgetLowerRectangle').is(":hidden"))
			{
		
				$('#scheduleWidgetLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#scheduleWidgetLowerRectangle').slideUp('fast');
			
			}
		
		});
		$('#dashboardUpperRectangle').click(function()
		{
			if($('#dashboardLowerRectangle').is(":hidden"))
			{
		
				$('#dashboardLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#dashboardLowerRectangle').slideUp('fast');
			
			}
		
		});
		
		$('#quickLinksUpperRectangle').click(function(){
			if($('#quickLinksLowerRectangle').is(":hidden"))
			{
				$('#quickLinksLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#quickLinksLowerRectangle').slideUp('fast');
			}
		});
		
		$('#scheduleUpperRectangle').click(function(){
			if($('#scheduleLowerRectangle').is(":hidden"))
			{
				$('#scheduleLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#scheduleLowerRectangle').slideUp('fast');
			}
		});
		
		$('#notificationsUpperRectangle').click(function(){
			if($('#notificationsLowerRectangle').is(":hidden"))
			{
				$('#notificationsLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#notificationsLowerRectangle').slideUp('fast');
			}
		});	

		$('#mailWidgetUpperRectangle').click(function(){
			if($('#mailWidgetLowerRectangle').is(":hidden"))
			{
				$('#mailWidgetLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#mailWidgetLowerRectangle').slideUp('fast');
			}
		});
		
		$('#notificationCloseButton').click(function(){
			if($('#notification').is(":hidden"))
			{
				$('#notification').slideDown('fast');
			}
			else
			{
				$('#notification').slideUp('fast');
			}
		});
});