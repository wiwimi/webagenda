/**
 * 
 */
package application;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import exception.DBDownException;
import exception.DBException;
import exception.DayNotSundayException;
import persistence.EmployeeBroker;
import persistence.ScheduleBroker;
import business.Employee;
import business.schedule.Location;
import business.schedule.Schedule;
import business.schedule.ScheduleTemplate;
import business.schedule.Shift;
import business.schedule.ShiftPosition;
import business.schedule.ShiftTemplate;

/**
 * Provides functionality for auto-generating schedules.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 */
public class ScheduleGenerator
    {
    /**
     * Generates a new schedule based off of a schedule template. The returned
     * schedule will contain all available employees that matched the required
     * positions of the template, as well as the given location. <br>
     * <br>
     * If the system was unable to fully meet the requirements of the template
     * (E.g., only 4 people found available to work a shift, but 5 are needed),
     * then additional employees that are capable of working the position, but
     * have a different preferred will be inserted (grouped by shift and
     * position) into the given ArrayList parameter.
     * 
     * @param template The schedule template to generate a schedule from.
     * @param startDate The start date of the new schedule. Must be a Sunday.
     * @param prefLocation The preferred location to match employees for.
     * @param partialMatches An empty list that will be filled with recommended
     *            employees.
     * @param creator The employee that is generating the new schedule.
     * @return A filled schedule containing available employees.
     * @throws DBException If there is a problem getting information from the
     *             database
     * @throws DBDownException
     * @throws DayNotSundayException
     */
    public static Schedule generateSchedule(ScheduleTemplate template,
            Date startDate, Location prefLocation,
            ArrayList<Shift> partialMatches, Employee creator) throws DBException,
            DBDownException, DayNotSundayException
        {
        if (template == null)
            throw new NullPointerException("Template can not be null.");
        
        if (startDate == null)
            throw new NullPointerException("Start date can not be null.");
        
        if (prefLocation == null)
            throw new NullPointerException("Preferred location can not be null.");
        
        if (partialMatches == null)
            throw new NullPointerException("Partial matches can not be null.");
        
        if (creator == null)
            throw new NullPointerException("Creator can not be null.");
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startDate.getTime());
        
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            throw new DayNotSundayException();
        
        cal.add(Calendar.DATE, 6);
        Date endDate = new Date(cal.getTimeInMillis());
        
        Schedule genSched = new Schedule(-1, creator.getEmpID(), startDate,
                endDate);
        try
            {
            // Use a schedule based connection for all selects.
            ScheduleBroker sb = ScheduleBroker.getBroker();
            DBConnection conn = sb.getConnection();
            
            // Prepare select statements for auto generation.
            PreparedStatement selectStrict = conn
                    .getConnection()
                    .prepareStatement(
                            "SELECT * FROM `EMPLOYEE` "
                                    + "WHERE prefPosition = ? AND prefLocation = ? "
                                    + "AND empID NOT IN ("
                                    + "SELECT e.empID FROM `EMPLOYEE` e JOIN `SHIFTEMP` se ON e.empID = se.empID "
                                    + "JOIN `SHIFT` sh ON se.shiftID = sh.shiftID "
                                    + "JOIN `SCHEDULE` sched ON sh.schedID = sched.schedID "
                                    + "WHERE sched.startDate = ? AND sched.endDate = ? AND sh.day = ? "
                                    + "AND ((? BETWEEN sh.startTime AND sh.endTime) OR (? BETWEEN sh.startTime AND sh.endTime)))");
            
            PreparedStatement selectRecom = conn
                    .getConnection()
                    .prepareStatement(
                            "SELECT * FROM `EMPLOYEE` "
                                    + "WHERE prefPosition = ? "
                                    + "AND empID NOT IN ("
                                    + "SELECT e.empID FROM `EMPLOYEE` e JOIN `SHIFTEMP` se ON e.empID = se.empID "
                                    + "JOIN `SHIFT` sh ON se.shiftID = sh.shiftID "
                                    + "JOIN `SCHEDULE` sched ON sh.schedID = sched.schedID "
                                    + "WHERE sched.startDate = ? AND sched.endDate = ? AND sh.day = ? "
                                    + "AND ((? BETWEEN sh.startTime AND sh.endTime) OR (? BETWEEN sh.startTime AND sh.endTime)))");
            
            // Create empty schedule that matches the template structure.
            for (ShiftTemplate st : template.getShiftTemplates().toArray())
                {
                // Create shift to match shift template.
                Shift newShift = new Shift(-1, -1, st.getDay(), st.getStartTime(),
                        st.getEndTime());
                
                for (ShiftPosition shiftPos : st.getShiftPositions().toArray())
                    {
                    // Get available employees that meet all requirements.
                    selectStrict.setString(1, shiftPos.getPosName());
                    selectStrict.setString(2, prefLocation.getName());
                    selectStrict.setDate(3, startDate);
                    selectStrict.setDate(4, endDate);
                    selectStrict.setInt(5, st.getDay());
                    selectStrict.setTime(6, st.getStartTime());
                    selectStrict.setTime(7, st.getEndTime());
                    
                    // Run the select to get available employees.
                    Employee[] strictMatches = EmployeeBroker
                            .parseResultsStatic(selectStrict.executeQuery());
                    
                    // Add found employees to shift, up to requested number.
                    if (strictMatches != null)
                        {
                        for (int i = 0; i < strictMatches.length &&
                                i < shiftPos.getPosCount(); i++)
                            {
                            newShift.getEmployees().add(strictMatches[i]);
                            }
                        }
                    
                    if (strictMatches == null ||
                            strictMatches.length < shiftPos.getPosCount())
                        {
                        // Not enough employees returned to fill the shift
                        // positions.
                        // Get recommended employees as well.
                        selectRecom.setString(1, shiftPos.getPosName());
                        selectRecom.setDate(2, startDate);
                        selectRecom.setDate(3, endDate);
                        selectRecom.setInt(4, st.getDay());
                        selectRecom.setTime(5, st.getStartTime());
                        selectRecom.setTime(6, st.getEndTime());
                        
                        Employee[] recMatches = EmployeeBroker
                                .parseResultsStatic(selectRecom.executeQuery());
                        
                        // Add recommended employees to recommended shift, if
                        // any are
                        // returned.
                        if (recMatches != null)
                            {
                            Shift recShift = new Shift(-1, -1, st.getDay(), st
                                    .getStartTime(), st.getEndTime());
                            for (Employee recEmp : recMatches)
                                {
                                recShift.getEmployees().add(recEmp);
                                }
                            
                            // Add recommended employees to shift arrayList to
                            // be
                            // returned.
                            partialMatches.add(recShift);
                            }
                        }
                    }
                
                genSched.getShifts().add(newShift);
                }
            }
        catch (SQLException sqle)
            {
            throw new DBException("Error in sql statement or employee parsing.",
                    sqle);
            }
        
        return genSched;
        }
    }
