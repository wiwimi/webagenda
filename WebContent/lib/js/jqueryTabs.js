 /*Updated by: Noorin*/
$(function() {

	// enable tabs that are contained within the wizard
	$("ul.tabs", wizard).tabs("div.panes > div", function(event, index) {

		/* now we are inside the onBeforeClick event */

		// ensure that the "terms" checkbox is checked.
		return true;

		// everything is ok. remove possible red highlight from the terms
		
	});

	

	// get handle to the tabs API
	var api = $("ul.tabs", wizard).tabs(0);

	// "next tab" button
	$("button.next", wizard).click(function() {
		api.next();
	});

	// "previous tab" button
	$("button.prev", wizard).click(function() {
		api.prev();
	});

});