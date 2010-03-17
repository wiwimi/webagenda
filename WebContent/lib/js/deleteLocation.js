/*Author:Noorin*/

function removeLocation(locName, locDesc)
{
	var txt = 'Are you sure you want to remove this Location?<input type="hidden" id="locDesc" name="locDesc" value="'+ locDesc +'"/>';
	
	
	
	$.prompt(txt,{buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			
			if(v) 
			{
				window.location= '../DeleteLocation?locName='+ locName + "&locDesc=" + locDesc;		
				
			}
			else
			{}
		}
	});
 }