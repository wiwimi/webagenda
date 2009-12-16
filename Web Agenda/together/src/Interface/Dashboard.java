/* Generated by Together */

package Interface;

/**
 * The main interface object of the system that provides access to all other main sections, such as schedule or report views.  Available sections will be determined by the permissions of the employee that is currently logged in. 
 */
public class Dashboard {
    /**
     * Displays the report view, loading and displaying the expanded version of the Schedule Widget that provides all functionality for managing schedules. 
     */
    public void dispSchedView(){}

    /**
     * Displays the report view, loading and displaying the Report Widget that provides all report functionality. 
     */
    public void dispReportView(){}

    /**
     * Displays the report view, loading and displaying the Event Widget that allows new events to be created and sent to workgroups and job types. 
     */
    public void dispEventView(){}

    /**
     * Displays the dashboard itself. 
     */
    public void dispDashboard() {
    }
	
	/**
     * Displays a Tab widget, a larger widget that contains a main widget area that does not interfere with other widget's main areas 
     */
    public void displayDashboardTab(TabWidget p0){}

    /**
     * Displays relevant options in the sidebar widget, related tasks based on the main widget. 
     */
    public void displayOptions(Widget p0){}

    /**
     * Displays a widget set by the method in the main widget area 
     */
    public void displayWidget(Widget p0){}

    /**
     * Logs user out along with displaying the page they are directed to after logging out from their dashboard. 
     */
    public void displayLogout(){}

    /**
     * Displays a specific schedule in the main widget 
     */
    public void dispSchedView(Schedule p0){}

    /**
     * Displays an array of schedules in a multi-view. All schedules are in a mini-view, a small widget that displays a simple overview of what the schedule contains. 
     */
    public void dispSchedMultiView(Schedule[] p0){}

    /**
     * Displays a schedule in a view that based on colours in a week/month/year form so that an understanding of shift intensity and employee density can be understood. 
     */
    public void dispMiniSchedView(Schedule p0){}

    /**
     * Displays the standalone schedule parameter in a detailed form in a larger widget with the rest seen as smaller simple view widgets. 
     */
    public void dispSchedMultiView(Schedule p0, Schedule[] p1){}

    /**
     * Displays a detailed view of a shift's properties 
     */
    public void dispShiftView(Shift p0){}

    public void dispEmployeeInfo(int p0){}
}
