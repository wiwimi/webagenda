$(document).ready(function(){

            //validation implementation will go here.

            $("#TestForm").validate({

                rules: {
                pwd: {

                        required: true

                  },

                     pwdc: {

                       required: true,
                       minlength: 2

                    }

                },

                 messages: {
                 pwd: {
                 required: "* Required"

                     },

                    pwdc: 
                    {

                        required: "* Required",
                        minlength: "* 2 Characters Required."
                    }
                 }
            });
        })