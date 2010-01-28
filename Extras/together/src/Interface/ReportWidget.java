/* Generated by Together */

package Interface;

/**
 * Provides the user interface for distributing and managing events that exist within the system. 
 */
public class ReportWidget extends Widget {
    /**
     * Shows a list allowing the user to choose what type of report they wish to generate. 
     */
    public void showTypeList(){}

    /**
     * Generates a schedule report. 
     */
    public void genSchRep(){}

    /**
     * Generates a resource report. 
     */
    public void genResRep(){}

    /**
     * Generates an employee usage report. 
     */
    public void genUseRep(){}

    /**
     * Displays a report after it has been generated. 
     */
    public void dispReport(){}

    /**
     * Collects schedule statistics as part of generating a schedule report. 
     */
    public void getSchStats(){}

    /**
     * Collects resource statistics as part of generating a resource report. 
     */
    public void getResStats(){}

    /**
     * Collects usage statistics as part of generating an employee usage report. 
     */
    public void getUseStats(){}

    /**
     * Display options for distributing a report after it has been generated. 
     */
    public void dispDistOpt(){}

    /**
     * Generates the print-friendly version of a currently displayed report. 
     */
    public void genPrintRep(){}

    /**
     * Initializes the print wizard of the browser that is currently being used to report. 
     */
    public void initPrint(){}

    /**
     * Displays the email entry page where the user may enter whih emails the report should be sent to. 
     */
    public void dispEmail(){}

    /**
     * Validates entered emails ensuring they are properly formated before trying to send emails to them. 
     */
    public void validateEmail(){}

    /**
     * Enables the send button so that reports can be sent to the entered email address.  This will only be possible after the entered emails have been validated 
     */
    public void enableSend(){}

    /**
     * Sends the report to the target emails. 
     */
    public void sendEmail(){}
}