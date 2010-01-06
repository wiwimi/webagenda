$(document).ready(function(){

	$(".sidebarHeader").click(function(){
		if($(".sidebarContent").is(":hidden"))
		{
			$(".sidebarContent").slideDown("medium");
		}
		else
		{
			$(".sidebarContent").slideUp("medium");
		}
	});
});
