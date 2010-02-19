/*author:Noorin, Mark you added to this so feel free to put your name where appropriate*/

$(document).ready(function()
{	
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
		
		$('#settingsUpperRectangle').click(function()
				{
					if($('#settingsLowerRectangle').is(":hidden"))
					{
				
						$('#settingsLowerRectangle').slideDown('fast');
					}
					else
					{
					
						$('#settingsLowerRectangle').slideUp('fast');
					
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
		
		$('#usersWidgetUpperRectangle').click(function(){
			if($('#usersWidgetLowerRectangle').is(":hidden"))
			{
				$('#usersWidgetLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#usersWidgetLowerRectangle').slideUp('fast');
			}
		});
		
		$('#locationsUpperRectangle').click(function(){
			if($('#locationsWidgetLowerRectangle').is(":hidden"))
			{
				$('#locationsWidgetLowerRectangle').slideDown('fast');
			}
			else
			{
				$('#locationsWidgetLowerRectangle').slideUp('fast');
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