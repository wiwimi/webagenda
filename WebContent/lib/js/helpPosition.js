/*Auhor: Noorin Hasan
 * Date: March 25, 2010*/

$(document).ready(function() {
$('#helpIcon').click(function() {
	$.prompt("* This window is used for searching and creating new positions. <br /> " +
			"* When creating positions, the names must be unique.<br /> " +
			"* To assign skills to a position: <br />" +
			"  - Click on the Edit button besides " +
			" the Required Skills field.<br />"  +
			"* For more information, please refer to the Online Help.");
	});
});
