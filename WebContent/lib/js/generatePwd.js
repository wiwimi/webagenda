/*Author: Noorin*/

$(document).ready(function() {

    $('#pwd').click(generate());

});

function generate() 
{
    var $generated=($(0));

    if($generated.val()!=$(0))

    {

        $('#pwd').generatePassword();

        $generated++;

    }

    else

    {

        

    }
} 