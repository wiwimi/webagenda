function removePosition(id)
{
	var txt = 'Are you sure you want to remove this Position?<input type="hidden" id="posName" name="posName" value="'+ id +'" />';
	
	$.prompt(txt,{buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			
			if(v) 
			{
				window.location= '../DeletePosition?posName='+id;								
			}
			else
			{}
		}
	});
 }