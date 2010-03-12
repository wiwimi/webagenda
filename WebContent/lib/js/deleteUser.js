function removeUser(id)
{
	var txt = 'Are you sure you want to remove this User?<input type="hidden" id="empId" name="empName" value="'+ id +'" />';
	
	$.prompt(txt,{buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			
			if(v) 
			{
				window.location= '../DeleteUser?empId='+id;								
			}
			else
			{}
		}
	});
 }