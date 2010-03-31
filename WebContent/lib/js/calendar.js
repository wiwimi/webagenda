/*author:Noorin*/  
$(function()
		{
                
			$("#dob").datepick({showOn: 'button', buttonImageOnly: true, buttonImage:'../lib/images/icons/Color/calendar.gif'});
			
			$(".validatedForm fieldset").mouseover(function()
			{
				$(this).addClass("over");
			});
			
        });

$(function()
		{
                
			$("#schedStart").datepick({showOn: 'button', buttonImageOnly: true, buttonImage:'../lib/images/icons/Color/calendar.gif'});
			
			$(".validatedForm fieldset").mouseover(function()
			{
				$(this).addClass("over");
			});
			
        });

$(function()
		{
                
			$("#schedEnd").datepick({showOn: 'button', buttonImageOnly: true, buttonImage:'../lib/images/icons/Color/calendar.gif'});
			
			$(".validatedForm fieldset").mouseover(function()
			{
				$(this).addClass("over");
			});
			
        });