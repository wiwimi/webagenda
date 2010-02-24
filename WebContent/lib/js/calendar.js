/*author:Noorin*/  
$(function()
		{
                
			$("#dob").datepick({showOn: 'button', buttonImageOnly: true, buttonImage:'../lib/images/icons/Color/calendar.gif'});
			
			$(".addUserForm fieldset").mouseover(function()
			{
				$(this).addClass("over");
			});
			
        });