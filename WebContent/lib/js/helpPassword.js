/*Author: Noorin Hasan
 * Date: April 10, 2010*/

$(document).ready(function() {
$('#helpIcon').click(function() {
	$.prompt("* This window is used for changing the password. <br /> " +
			"* Make sure the old password is correct.<br /> " +
			"* Make sure the new password and the confirm password match.<br /> " +
			"* For more information, please refer to the Online Help.");
	});
});
