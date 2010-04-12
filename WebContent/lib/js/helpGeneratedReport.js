/*Author: Noorin Hasan
 * Date: April 10, 2010*/

$(document).ready(function() {
$('#helpIcon').click(function() {
	$.prompt("* This window is used for generating a selected report in the system. <br /> " +
			"* You can print the report by clicking on the printer icon.<br /> " +
			"* You can export the file to Excel MS by clicking on the green excel icon.<br /> " +
			"* For more information, please refer to the Online Help.");
	});
});
