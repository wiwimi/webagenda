/**
 * Updated by Noorin
 * Some code was changed to prevent interference with the flash plugin.
 * Rules were added to add more validition features to the screen.
 */
$().ready(function() {
	// validate the comment form when it is submitted
	$("#commentForm").validate();
	
	// validate signup form on keyup and submit
	$("#form").validate({
		rules: {
			firstname: "required",
			lastname: "required",
			username: {
				required: true,
				minlength: 2
			},
			
			// Validate that the user's Id is required and should contain digits only
			empId:{
				required: true,
				digits: true
			
			},
			password: {
				required: true,
				minlength: 6,
				maxlength:8
			    
			},
			confirm_password: {
				required: true,
				minlength: 6,
				maxlength:8,
				
				equalTo: "#password"
			},
			email: {
				required: false,
				email: true
			},
			topic: {
				required: "#newsletter:checked",
				minlength: 2
			},
			agree: "required"
		},
		messages: {
			firstname: "Please enter your firstname",
			lastname: "Please enter your lastname",
			username: {
				required: "Please enter a username",
				minlength: "Your username must consist of at least 2 characters"
			},
			empId: {
				
				required: "This field is required",
				digits: "You may only enter digits for the user's Id"
			},
		
			password: {
				required: "Please provide a password",
				minlength: "Your password must be at least 6 characters long"
			},
			confirm_password: {
				required: "Please provide a password",
				minlength: "Your password must be at least 6 characters long",
				equalTo: "Please enter the same password as above"
			},
			email: "Please enter a valid email address",
			agree: "Please accept our policy"
		}
	});
	
	// propose username by combining first- and lastname
	$("#username").focus(function() {
		var firstname = $("#firstname").val();
		var lastname = $("#lastname").val();
		if(firstname && lastname && !this.value) {
			this.value = firstname + "." + lastname;
		}
	});
	
	//code to hide topic selection, disable for demo
	var newsletter = $("#newsletter");
	// newsletter topics are optional, hide at first
	var inital = newsletter.is(":checked");
	var topics = $("#newsletter_topics")[inital ? "removeClass" : "addClass"]("gray");
	var topicInputs = topics.find("input").attr("disabled", !inital);
	// show when newsletter is checked
	newsletter.click(function() {
		topics[this.checked ? "removeClass" : "addClass"]("gray");
		topicInputs.attr("disabled", !this.checked);
	});
});