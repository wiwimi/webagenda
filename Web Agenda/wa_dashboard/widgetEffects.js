$(document).ready(function(){

	$("#widgetHeader").click(function(){
		if($("#widgetBody").is(":hidden"))
		{
			$("#widgetBody").slideDown("medium");
		}
		else
		{
			$("#widgetBody").slideUp("medium");
		}
	});
});