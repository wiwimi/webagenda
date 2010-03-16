/*Author:Noorin*/

function removeLocation(locName, locDesc)
{
	var txt = 'Are you sure you want to remove this Location?<input type="hidden" id="locDesc" name="locDesc" value="'+ locDesc +'"/>';
	
	// The input type hidden is not working but if we get it to work, it would be better than splitting the string in the servlet.
	
	$.prompt(txt,{buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			
			if(v) 
			{
				window.location= '../DeleteLocation?loc='+ locName + "," + locDesc;								
			}
			else
			{}
		}
	});
 }