/*Author: Noorin Hasan
 * Date: April 10, 2010*/

$(document).ready(function() {
$('#helpIcon').click(function() {
	$.prompt("* This window is used for resetting the password for a selected user. <br /> " +
			"* The password is emailed to the email found in the form.<br /> " +
			"* In case the e-mail is empty the password will be emailed to you.<br /> " +
			"* Make sure your SMTP ports are not blocked before sending." +
			"* Attempting to send will consume more time. <br /> " +
			"* For more information, please refer to the Online Help.");
	});
});
