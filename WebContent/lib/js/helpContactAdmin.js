/*Author: Noorin Hasan
 * Date: April 10, 2010*/

$(document).ready(function() {
$('#helpIcon').click(function() {
	$.prompt("* This window is used for sending emails. <br /> " +
			"* You can only send e-mails to employees stored in the system.<br /> " +
			"* Make sure the SMTP ports are not blocked.<br /> " +
			"* For more information, please refer to the Online Help.");
	});
});
