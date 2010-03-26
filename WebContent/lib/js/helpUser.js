/*Author: Noorin
 * Date: March 25, 2010*/

$(document).ready(function() {
$('#helpIcon').click(function() {
	$.prompt("* This window is used for searching and creating new users. <br /> " +
			"* Users IDs  names must be unique.<br /> " +
			"* To assign skills, positions and other items to a user: <br />" +
			" - Click on the edit button besides the relevant field." +
			"* For more information, please refer to the Online Help.");
	});
});