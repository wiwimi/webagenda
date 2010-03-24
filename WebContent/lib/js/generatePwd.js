/*Author: Noorin*/

$(document).ready(function() {
    $('#pwd').click(generate);
});

function generate() 
{
	var $generated=$("false");
	if($generated.val()!=$("false"))
	{
		$('#pwd').generatePassword();
		$generated= $("true");
	}
	else
	{
	}
	
}