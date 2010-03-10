/***************************/
//@Author: Adrian "yEnS" Mato Gondelle
//@Update by: Noorin Hasan
//@website: www.yensdesign.com
//@email: yensamg@gmail.com
//@license: Feel free to use it, but keep this credits please!					
/***************************/

//SETTING UP OUR POPUP
//0 means disabled; 1 means enabled;
var popupStatus = 0;

//loading popup with jQuery magic!
function loadPopup(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.7"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#locationsPopup").fadeIn("slow");
		popupStatus = 1;
	}
}

//loading popup with jQuery magic!
function loadIdPopup(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.7"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#idPopup").fadeIn("slow");
		popupStatus = 1;
	}
}

//loading popup with jQuery magic!
function loadPopup2(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.7"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#skillsPopup").fadeIn("slow");
		popupStatus = 1;
	}
}
//loading popup with jQuery magic!
function loadPopup3(){
	//loads popup only if it is disabled
	if(popupStatus==0){
		$("#backgroundPopup").css({
			"opacity": "0.7"
		});
		$("#backgroundPopup").fadeIn("slow");
		$("#positionsPopup").fadeIn("slow");
		popupStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disablePopup(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#locationsPopup").fadeOut("slow");
		popupStatus = 0;
	}
}
//disabling popup with jQuery magic!
function disablePopup2(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#skillsPopup").fadeOut("slow");
		popupStatus = 0;
	}
}
//disabling popup with jQuery magic!
function disablePopup3(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#positionsPopup").fadeOut("slow");
		popupStatus = 0;
	}
}

//disabling popup with jQuery magic!
function disableIdPopup(){
	//disables popup only if it is enabled
	if(popupStatus==1){
		$("#backgroundPopup").fadeOut("slow");
		$("#idPopup").fadeOut("slow");
		popupStatus = 0;
	}
}

//centering popup
function centerPopup(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#popupContact").height();
	var popupWidth = $("#popupContact").width();
	//centering
	$("#locationsPopup").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
}

//centering popup
function centerIdPopup(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#idPopup").height();
	var popupWidth = $("#idPopup").width();
	//centering
	$("#idPopup").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
}

//centering popup
function centerPopup2(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#skillsPopup").height();
	var popupWidth = $("#skillsPopup").width();
	//centering
	$("#skillsPopup").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}

//centering popup
function centerPopup3(){
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $("#positionsPopup").height();
	var popupWidth = $("#positionsPopup").width();
	//centering
	$("#positionsPopup").css({
		"position": "absolute",
		"top": windowHeight/2-popupHeight/2,
		"left": windowWidth/2-popupWidth/2
	});
	//only need force for IE6
	
	$("#backgroundPopup").css({
		"height": windowHeight
	});
	
}


//CONTROLLING EVENTS IN jQuery
$(document).ready(function(){
	
	//LOADING POPUP
	//Click the button event!
	$("#locationsButton").click(function(){
		//centering with css
		centerPopup();
		//load popup
		loadPopup();
	});
	
	$("#skillsButton").click(function(){
		//centering with css
		centerPopup2();
		//load popup
		loadPopup2();
	});
	
	$("#posButton").click(function(){
		//centering with css
		centerPopup3();
		//load popup
		loadPopup3();
	});
	
	$("#empButton").click(function(){
		//centering with css
		centerIdPopup();
		//load popup
		loadIdPopup();
	});
				
	//CLOSING POPUP
	//Click the x event!
	$("#popupContactClose").click(function(){
		disablePopup();
	});
	
	//CLOSING POPUP
	//Click the x event!
	$("#popupContactClose2").click(function(){
		disablePopup2();
	});
	
	//CLOSING POPUP
	//Click the x event!
	$("#posPopupClose").click(function(){
		disablePopup3();
	});
	
	//CLOSING POPUP
	//Click the x event!
	$("#idPopupClose").click(function(){
		disableIdPopup();
	});
	
	
	//Click out event!
	$("#backgroundPopup").click(function(){
		disablePopup();
		disablePopup2();
		disablePopup3();
		disableIdPopup();
	});
	
	
	//Press Escape event!
	$(document).keypress(function(e){
		if(e.keyCode==27 && popupStatus==1){
			disablePopup();
			disablePopup2();
			disablePopup3();
			disableIdPopup();
		}
	});

});