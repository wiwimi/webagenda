function removeLocation(id)
{
	var txt = 'Are you sure you want to remove this Location?<input type="hidden" id="locName" name="locName" value="'+ id +'" />';
	
	$.prompt(txt,{buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			
			if(v) 
			{
				window.location= '../DeleteLocation?locName='+id;								
			}
			else
			{}
		}
	});
 }