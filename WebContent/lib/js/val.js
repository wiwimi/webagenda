/*Updated by: Noorin Hasan*/

$.fn.copyTo = function(to) {
    var to = $(to);
    for ( var i = 1; i < arguments.length; i++ )
        to.set( arguments[i], this.get(0)[ arguments[i] ] );
    return this;
};

new function() {
       // $.fn.validate = validate() {};
    $.fn.validate = {
        init: function(o) {
          if(o.name == 'username') { this.username(o) };
          if(o.name == 'password') { this.password(o) };
          if(o.name == 'email') { this.email(o) };
        },
        
        email: function(o) {
            var email  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
             if (o.value.match(email)) {
                doSuccess(o);
              } else {
                doError(o,'not a valid email');
              };
          },
        
        password: function(o) {
          var pass = /[(\*\(\)\[\]\+\.\,\/\?\:\;\'\"\`\~\\#\$\%\^\&\<\>)+]/;
           if (!o.value.match(pass)) {
             doValidate(o);
            } else {
             doError(o,'no special characters allowed');
            };
        }
     };
 


     function doSuccess(o) {
              $('#' + o.id + '_img').html('<img src="../lib/images/icons/Color/001_062.png" border="0" style="float:left;" />');
              $('#' + o.id + '_div').removeClass("error");
              $('#' + o.id + '_msg').html("");
              $('#' + o.id + '_div').addClass("success");
     }

     function doError(o,m) {
              $('#' + o.id + '_img').html('<img src="../lib/images/icons/Color/001_112.png" border="0" style="float:left;" />');
              $('#' + o.id + '_div').addClass("error");
              $('#' + o.id + '_msg').html(m);
              $('#' + o.id + '_div').removeClass("success");
     }
     //private helper, validates each type after check
     function doValidate(o) {
        	$('#' + o.id + '_img').html('<img src="images/loading.gif" border="0" style="float:left;" />');
        	$.post('ajax.php', { id: o.id, value: o.value }, function(json) {
                  	eval("var args = " + json);
                        if (args.success == true)
                  	{
                  	  doSuccess(args);
                  	}
                  	else
                  	{
                          doError(args,args.msg);
                  	}
                  });
    };

};
$.fn.match = function(m) {
	$('#' + this.get(0).id + '_img').html('<img src="images/loading.gif" border="0" style="float:left;" />');
	if ($(this).get(0).val() == $(m.match).val() && $(this).get(0).val().length >=6) 
	{
          $('#' + this.get(0).id + '_img').html('<img src="../lib/images/icons/Color/001_062.png" border="0" style="float:left;" />');
          $(m.error).removeClass("error");
          $(m.error).addClass("success");
          $('#' + this.get(0).id + '_msg').html("");
    } else if ($(this).get(0).val() != $(m.match).val())
    {
          $('#' + this.get(0).id + '_img').html('<img src="../lib/images/icons/Color/001_112.png" border="0" style="float:left;" />');
          $(m.error).addClass("error");
          $(m.error).removeClass("success");
          $('#' + this.get(0).id + '_msg').html("The passwords don't match, please try again");
     }
     else if ($(this).get(0).val().length <6)
     {
    	 $('#' + this.get(0).id + '_img').html('<img src="../lib/images/icons/Color/001_112.png" border="0" style="float:left;" />');
         $(m.error).addClass("error");
         $(m.error).removeClass("success");
         $('#' + this.get(0).id + '_msg').html("Make sure the password is 6-8 alphanumeric characters");
     };
};
$(document).ready(function()
{

  $("//[@class=validated]/input").blur(function() {
          $(this).validate.init(this);
  });
  

  $("#confirmpass").blur(function() {
          $(this).match({match: '#password', error: '#confirmpass_li'});
  });

  
});