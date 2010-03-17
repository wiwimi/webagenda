function removeSkill(skillName, skillDesc)
{
	var txt = 'Are you sure you want to remove this Skill?<input type="hidden" id="skillName" name="skillName" value="'+ skillName +'" />';
	
	$.prompt(txt,{buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			
			if(v) 
			{
				window.location= '../DeleteSkill?skillName='+ skillName + "&skillDesc=" + skillDesc;								
			}
			else
			{}
		}
	});
 }