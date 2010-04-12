/*Author: Noorin Hasan
 * Date: April 10, 2010*/

$(document).ready(function() {
$('#helpIcon').click(function() {
	$.prompt("* This window is used for activating the generated schedule. <br /> " +
			"* If there are no employees available, the system will not generate a schedule.<br /> " +
			"* In case there are no employees, try using different date ranges or different locations for better results.<br /> " +
			"* For more information, please refer to the Online Help.");
	});
});
