function removeSkill(id)
{
	var txt = 'Are you sure you want to remove this Skill?<input type="hidden" id="skillName" name="skillName" value="'+ id +'" />';
	
	$.prompt(txt,{buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			
			if(v) 
			{
				window.location= '../DeleteSkill?skillName='+id;								
			}
			else
			{}
		}
	});
 }