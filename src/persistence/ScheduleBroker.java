/**
 * persistence - ScheduleBroker.java
 */
package persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import application.DBConnection;
import business.Employee;
import business.Notification;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import utilities.DoubleLinkedList;
import exception.DBChangeException;
import exception.DBDownException;
import exception.DBException;
import exception.DayNotSundayException;
import exception.EmptyScheduleException;
import exception.InvalidPermissionException;
import business.schedule.Schedule;
import business.schedule.Shift;
import messagelog.Logging;

/**
 * Provides functionality for adding, updating and deleting schedules in the
 * database. This includes shifts and shift employees.
 * 
 * @author Daniel Wehr
 * @version 0.4.0
 */
public class ScheduleBroker extends Broker<Schedule>
    {
    /** ScheduleBroker object for singleton pattern */
    private static ScheduleBroker broker_schedule = null;
    
    /**
     * Constructor for ScheduleBroker, initializes the Broker Connection Monitor
     */
    private ScheduleBroker()
        {
        this.initConnectionThread();
        }
    
    /**
     * Returns a ScheduleBroker object, initialized if necessary.
     * 
     * @return
     */
    public static ScheduleBroker getBroker()
        {
        if (broker_schedule == null)
            {
            Logging.writeToLog(Logging.INIT_LOG, Logging.NORM_ENTRY, "Schedule Broker initialized");
            broker_schedule = new ScheduleBroker();
            }
        return broker_schedule;
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#create(business.BusinessObject,
     * business.Employee)
     */
    @Override
    public boolean create(Schedule createObj, Employee caller) throws DBException, DBDownException,
            InvalidPermissionException, DayNotSundayException,
            EmptyScheduleException
        {
        if (createObj == null)
            throw new NullPointerException("Can not create, given schedule is null.");
        
        if (createObj.getStartDate() == null)
            throw new NullPointerException("Start date required when creating a schedule.");
        
        // Ensure schedule has at least one employee assigned to a shift.
        if (createObj.getShifts().size() == 0)
            throw new EmptyScheduleException("Can not create schedule, no shifts found.");
        
        boolean hasEmployee = false;
        for (Shift s : createObj.getShifts().toArray())
            {
            if (s.getEmployees().size() > 0)
                hasEmployee = true;
            }
        
        if (!hasEmployee)
            throw new EmptyScheduleException(
                    "Can not create schedule, no assigned employees found.");
        
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(createObj.getStartDate().getTime());
        
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
            throw new DayNotSundayException("Sunday startDate required. " +
                    createObj.getStartDate() +
                    " detected as " +
                    Calendar.DAY_OF_WEEK +
                    ", Sunday is " + Calendar.SUNDAY + ".");
        
        cal.add(Calendar.DATE, 6);
        createObj.setEndDate(new Date(cal.getTimeInMillis()));
        
        DBConnection conn = null;
        try
            {
            // Get connection, disable autocommit.
            conn = this.getConnection();
            conn.getConnection().setAutoCommit(false);
            
            // Create prepared statements for inserts.
            PreparedStatement createSched = conn.getConnection().prepareStatement(
                    "INSERT INTO `WebAgenda`.`SCHEDULE` " + "(`startDate`,`endDate`,`creatorID`) "
                            + "VALUES " + "(?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            
            PreparedStatement createShift = conn.getConnection().prepareStatement(
                    "INSERT INTO `WebAgenda`.`SHIFT` " + "(`schedID`,`day`,`startTime`,`endTime`) "
                            + "VALUES "
                            + "(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            
            PreparedStatement createShiftEmp = conn.getConnection()
                    .prepareStatement(
                            "INSERT INTO `WebAgenda`.`SHIFTEMP` " + "(`shiftID`,`empID`) "
                                    + "VALUES "
                                    + "(?,?)");
            
            // Attempt to insert schedule template.
            createSched.setDate(1, createObj.getStartDate());
            createSched.setDate(2, createObj.getEndDate());
            createSched.setInt(3, createObj.getCreatorID());
            if (createSched.executeUpdate() != 1)
                throw new DBException("Failed to insert schedule template.");
            
            // Save the auto-generated schedule ID.
            ResultSet temp = createSched.getGeneratedKeys();
            if (temp.next())
                createObj.setSchedID(temp.getInt(1));
            
            // Insert each shift.
            for (Shift shift : createObj.getShifts().toArray())
                {
                insertShift(shift, createObj.getSchedID(), createShift, createShiftEmp);
                }
            
            // Create succeeded! Commit all inserts and reset connection.
            conn.getConnection().commit();
            conn.getConnection().setAutoCommit(true);
            createSched.close();
            createShift.close();
            createShiftEmp.close();
            conn.setAvailable(true);
            }
        catch (SQLException e)
            {
            try
                {
                conn.getConnection().rollback();
                conn.getConnection().setAutoCommit(true);
                }
            catch (SQLException e1)
                {
                throw new DBException("Failed to rollback connection.", e1);
                }
            conn.setAvailable(true);
            createObj.setSchedID(-1);
            throw new DBException("Failed to get schedules.", e);
            }
        
        notifyScheduleEmps(null, createObj, caller);
        
        return true;
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#delete(business.BusinessObject,
     * business.Employee)
     */
    @Override
    public boolean delete(Schedule deleteObj, Employee caller) throws DBException, DBDownException
        {
        if (deleteObj == null)
            throw new NullPointerException("Can not delete, given schedule is null.");
        
        if (deleteObj.getSchedID() == -1)
            throw new NullPointerException("Can not delete, no schedule ID in given object.");
        
        raceCheck(deleteObj, caller);
        
        try
            {
            DBConnection conn = this.getConnection();
            
            PreparedStatement deleteStmt = conn.getConnection().prepareStatement(
                    "DELETE FROM `WebAgenda`.`SCHEDULE` WHERE schedID = ?");
            
            deleteStmt.setInt(1, deleteObj.getSchedID());
            
            if (deleteStmt.executeUpdate() != 1)
                throw new DBException("Failed to delete schedule.");
            
            conn.setAvailable(true);
            }
        catch (SQLException e)
            {
            throw new DBException("Failed to delete schedule.", e);
            }
        
        return true;
        }
    
    /**
     * Gets all schedules in the database that match the given schedule. Only
     * one attribute from the given schedule will be used for the search, in the
     * following order:<br>
     * <br>
     * A) Schedule ID (schedTempID)<br>
     * B) Creator ID (creatorID)<br>
     * C) Start Date & End Date (startDate, endDate)<br>
     * <br>
     * Searches by start and end date will return all schedules that start or
     * end within the given dates.
     * 
     * @param searchTemplate the schedule template holding the attribute to be
     *            used for the search.
     * @param caller The employee currently logged into the system, used for
     *            permissions checks.
     * @throws DBException
     * @throws DBDownException
     * @see persistence.Broker#get(business.BusinessObject)
     */
    /*
     * (non-Javadoc)
     * @see persistence.Broker#get(business.BusinessObject, business.Employee)
     */
    @Override
    public Schedule[] get(Schedule searchTemplate, Employee caller) throws DBException,
            DBChangeException,
            DBDownException
        {
        // Ensure schedule search template is not null.
        if (searchTemplate == null)
            throw new NullPointerException("Cannot search with null template.");
        
        Schedule[] found;
        try
            {
            DBConnection conn = this.getConnection();
            
            PreparedStatement select = null;
            if (searchTemplate.getSchedID() != -1)
                {
                // Get the schedule with a matching ID.
                select = conn
                        .getConnection()
                        .prepareStatement(
                                "SELECT * FROM `WebAgenda`.`SCHEDULE` WHERE schedID = ? ORDER BY creatorID, startDate, endDate");
                select.setInt(1, searchTemplate.getSchedID());
                }
            else if (searchTemplate.getCreatorID() != -1)
                {
                // Get all schedules created by the given employee.
                select = conn
                        .getConnection()
                        .prepareStatement(
                                "SELECT * FROM `WebAgenda`.`SCHEDULE` WHERE creatorID = ? ORDER BY creatorID, startDate, endDate");
                select.setInt(1, searchTemplate.getCreatorID());
                }
            else if (searchTemplate.getStartDate() != null && searchTemplate.getEndDate() != null)
                {
                // Get all schedules that start or end within the given dates.
                select = conn
                        .getConnection()
                        .prepareStatement(
                                "SELECT * FROM `WebAgenda`.`SCHEDULE` WHERE (`startDate` BETWEEN ? AND ?) OR (`endDate` BETWEEN ? AND ?) ORDER BY creatorID, startDate, endDate");
                select.setDate(1, searchTemplate.getStartDate());
                select.setDate(2, searchTemplate.getEndDate());
                select.setDate(3, searchTemplate.getStartDate());
                select.setDate(4, searchTemplate.getEndDate());
                }
            else
                {
                select = conn.getConnection().prepareStatement(
                        "SELECT * FROM `WebAgenda`.`SCHEDULE`");
                }
            
            ResultSet schResults = select.executeQuery();
            found = parseResults(schResults);
            
            if (found != null)
                fillSched(found, conn);
            
            conn.setAvailable(true);
            }
        catch (SQLException e)
            {
            throw new DBException("Failed to get schedule(s).", e);
            }
        
        // Return schedule templates that matched search.
        return found;
        }
    
    /**
     * Returns all schedules that a given employee is part of, limited only to
     * schedules that have not yet ended, and sorted by the schedule start date.
     * 
     * @param emp The employee to get the schedules for.
     * @return All current and future schedules that the employee is part of.
     * @throws DBException if there was an error in getting the schedules from
     *             the database.
     * @throws DBDownException if the mysql server is currently down.
     */
    public Schedule[] getEmpSchedules(Employee emp) throws DBException, DBDownException
        {
        // Ensure the given emp is not null.
        if (emp == null)
            throw new NullPointerException(
                    "Emp can not be null. Required to find employee schedules.");
        
        if (emp.getEmpID() == -1)
            throw new NullPointerException("Given employee does not have an employee ID.");
        
        Schedule[] found;
        try
            {
            DBConnection conn = this.getConnection();
            
            // Get all schedules for the given employee.
            PreparedStatement select = conn
                    .getConnection()
                    .prepareStatement(
                            "SELECT * "
                                    + "FROM `WebAgenda`.`SCHEDULE` s JOIN `WebAgenda`.`SHIFT` sh ON s.schedID = sh.schedID "
                                    + "JOIN `WebAgenda`.`SHIFTEMP` se ON sh.shiftID = se.shiftID "
                                    + "WHERE se.empID = ? AND s.endDate > NOW() "
                                    + "GROUP BY s.schedID " + "ORDER BY s.startDate");
            
            select.setInt(1, emp.getEmpID());
            
            ResultSet schResults = select.executeQuery();
            found = parseResults(schResults);
            
            if (found != null)
                fillSched(found, conn);
            
            conn.setAvailable(true);
            }
        catch (SQLException e)
            {
            throw new DBException("Failed to get employee schedule(s).", e);
            }
        
        // Return schedule templates that matched search.
        return found;
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#update(business.BusinessObject,
     * business.BusinessObject, business.Employee)
     */
    @Override
    public boolean update(Schedule oldObj, Schedule updateObj, Employee caller) throws DBException,
            DBChangeException,
            DBDownException,
            InvalidPermissionException
        {
        // Ensure Old/Update objects aren't null.
        if (oldObj == null || oldObj.getSchedID() == -1)
            throw new NullPointerException("Old schedule with ID required for updates.");
        if (updateObj == null || updateObj.getSchedID() == -1)
            throw new NullPointerException("Update schedule with ID required for updates.");
        
        // Ensure old/update objects refer to the same schedule template.
        if (oldObj.getSchedID() != updateObj.getSchedID())
            throw new DBException("Old and Update schedule templates do not have the same ID.");
        
        // Run raceCheck on old object. Pass DBChangeException out of method if
        // applicable.
        raceCheck(oldObj, caller);
        
        DBConnection conn = null;
        try
            {
            // Get DB connection, disable auto-commit.
            conn = this.getConnection();
            conn.getConnection().setAutoCommit(false);
            
            // Prepare all statements used during update.
            PreparedStatement updateSched = conn
                    .getConnection()
                    .prepareStatement(
                            "UPDATE `WebAgenda`.`SCHEDULE` "
                                    + "SET startDate = ?, endDate = ? "
                                    + "WHERE schedID = ? AND creatorID = ? AND startDate = ? AND endDate = ?");
            
            PreparedStatement createShift = conn.getConnection().prepareStatement(
                    "INSERT INTO `WebAgenda`.`SHIFT` " + "(`schedID`,`day`,`startTime`,`endTime`) "
                            + "VALUES "
                            + "(?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            
            PreparedStatement updateShift = conn.getConnection().prepareStatement(
                    "UPDATE `WebAgenda`.`SHIFT` " + "SET day = ?, startTime = ?, endTime = ? "
                            + "WHERE shiftID = ? AND schedID = ? AND day = ? "
                            + "AND startTime = ? AND endTime = ?");
            
            PreparedStatement deleteShift = conn.getConnection().prepareStatement(
                    "DELETE FROM `WebAgenda`.`SHIFT` WHERE shiftID = ?");
            
            PreparedStatement createShiftEmp = conn.getConnection()
                    .prepareStatement(
                            "INSERT INTO `WebAgenda`.`SHIFTEMP` " + "(`shiftID`,`empID`) "
                                    + "VALUES "
                                    + "(?,?)");
            
            PreparedStatement deleteMultiShiftEmp = conn.getConnection().prepareStatement(
                    "DELETE FROM `WebAgenda`.`SHIFTEMP` WHERE shiftID = ?");
            
            // Compare old/update schedule attributes. If not equal, update DB.
            if (!oldObj.getStartDate().equals(updateObj.getStartDate()) ||
                    !oldObj.getEndDate().equals(updateObj.getEndDate()))
                {
                updateSched.setDate(1, updateObj.getStartDate());
                updateSched.setDate(2, updateObj.getEndDate());
                
                updateSched.setInt(3, oldObj.getSchedID());
                updateSched.setInt(4, oldObj.getCreatorID());
                updateSched.setDate(5, oldObj.getStartDate());
                updateSched.setDate(6, oldObj.getEndDate());
                
                // Attempt update.
                if (updateSched.executeUpdate() != 1)
                    throw new DBChangeException(
                            "Failed to update schedule start/end dates. May have been changed or deleted by another user.");
                }
            
            // Convert old/new shifts to array.
            Shift[] oldShiftArr = oldObj.getShifts().toArray();
            Shift[] updShiftArr = updateObj.getShifts().toArray();
            
            // Check for old shifts that are not used, and can be deleted.
            for (int i = 0; i < oldShiftArr.length; i++)
                {
                boolean found = false;
                for (int j = 0; j < updShiftArr.length && !found; j++)
                    {
                    // If old shift exists in new shift array, set found.
                    if (oldShiftArr[i].getShiftID() == updShiftArr[j].getShiftID())
                        found = true;
                    }
                
                // Old shift not in update, delete from database.
                if (!found)
                    {
                    deleteShift.setInt(1, oldShiftArr[i].getShiftID());
                    if (deleteShift.executeUpdate() != 1)
                        throw new DBException("Failed to delete shift during update.");
                    }
                }
            
            // Check updated shifts against old shifts, adding and updating as
            // necessary.
            for (int i = 0; i < updShiftArr.length; i++)
                {
                Shift updShift = updShiftArr[i];
                
                // If update shift doesn't have an ID, it is new.
                if (updShift.getShiftID() == -1)
                    {
                    // Add new shift.
                    insertShift(updShift, updateObj.getSchedID(), createShift, createShiftEmp);
                    }
                else
                    {
                    /*
                     * Confirm that shift has schedule ID. If not, shift ID was
                     * added in the front-end, which should not be done.
                     */
                    if (updShift.getSchedID() == -1)
                        throw new DBException("Shift ID was not assigned by backend.");
                    
                    // Shift template has an ID should match an old shift
                    // template.
                    int oldShiftIdx = -1;
                    for (int j = 0; j < oldShiftArr.length && oldShiftIdx == -1; j++)
                        {
                        if (oldShiftArr[j].getSchedID() == updShift.getSchedID() &&
                                oldShiftArr[j].getShiftID() == updShift.getShiftID())
                            {
                            // Matching shift template found.
                            oldShiftIdx = j;
                            }
                        }
                    
                    if (oldShiftIdx != -1)
                        {
                        Shift oldShift = oldShiftArr[oldShiftIdx];
                        
                        // If shift attributes different, update DB.
                        if (oldShift.getDay() != updShift.getDay() ||
                                oldShift.getStartTime() != updShift.getStartTime() ||
                                oldShift.getEndTime() != updShift.getEndTime())
                            {
                            // Add new parameters.
                            updateShift.setInt(1, updShift.getDay());
                            updateShift.setTime(2, updShift.getStartTime());
                            updateShift.setTime(3, updShift.getEndTime());
                            
                            // Add old parameters.
                            updateShift.setInt(4, oldShift.getShiftID());
                            updateShift.setInt(5, oldShift.getSchedID());
                            updateShift.setInt(6, oldShift.getDay());
                            updateShift.setTime(7, oldShift.getStartTime());
                            updateShift.setTime(8, oldShift.getEndTime());
                            
                            if (updateShift.executeUpdate() != 1)
                                throw new DBChangeException(
                                        "Failed to update shift. May have been changed or deleted by another user.");
                            }
                        
                        // Check if shifts have same employees.
                        Employee[] oldShiftEmpArr = oldShift.getEmployees().toArray();
                        Employee[] updShiftEmpArr = updShift.getEmployees().toArray();
                        
                        boolean shiftEmpChanged = false;
                        if (oldShiftEmpArr.length == updShiftEmpArr.length)
                            {
                            for (int j = 0; j < oldShiftEmpArr.length && !shiftEmpChanged; j++)
                                {
                                if (oldShiftEmpArr[j].getEmpID() != updShiftEmpArr[j].getEmpID())
                                    shiftEmpChanged = true;
                                }
                            }
                        else
                            shiftEmpChanged = true;
                        
                        if (shiftEmpChanged)
                            {
                            // Shift employees changed. Delete old shift
                            // employees.
                            deleteMultiShiftEmp.setInt(1, updShift.getShiftID());
                            if (deleteMultiShiftEmp.executeUpdate() < 1)
                                throw new DBChangeException(
                                        "Failed to delete old shift employees for: " +
                                                updShift);
                            
                            // Create new shift employees.
                            insertShiftEmployees(updShiftEmpArr, updShift.getShiftID(),
                                    createShiftEmp);
                            }
                        }
                    else
                        {
                        // Shift was given incorrect IDs, no matches found.
                        throw new DBException(
                                "Shift template has non-matching ID's, can not update. " +
                                        updShift);
                        }
                    }
                }
            
            // Update succeeded! Commit all changes and reset connection.
            conn.getConnection().commit();
            conn.getConnection().setAutoCommit(true);
            updateSched.close();
            createShift.close();
            updateShift.close();
            deleteShift.close();
            createShiftEmp.close();
            deleteMultiShiftEmp.close();
            conn.setAvailable(true);
            }
        catch (SQLException e)
            {
            try
                {
                conn.getConnection().rollback();
                conn.getConnection().setAutoCommit(true);
                }
            catch (SQLException e1)
                {
                throw new DBException("Failed to rollback connection.", e1);
                }
            conn.setAvailable(true);
            throw new DBException("Failed to get schedule templates.", e);
            }
        
        notifyScheduleEmps(oldObj, updateObj, caller);
        
        return true;
        }
    
    /**
     * Steps through a schedule and sorts all shifts, as well as all employees
     * within them, to match the order that would be returned by the database.
     * 
     * @param toSort the schedule to sort.
     * @return true when the sort is complete.
     */
    public static void sortSchedule(Schedule toSort)
        {
        // Get the list of shifts.
        DoubleLinkedList<Shift> shifts = toSort.getShifts();
        
        // Sort employees within shifts first.
        for (int i = 0; i < shifts.size(); i++)
            {
            // Get employees and sort.
            DoubleLinkedList<Employee> emps = shifts.get(i).getEmployees();
            Employee[] sortedEmps = emps.toArray();
            
            if (sortedEmps != null)
                {
                Arrays.sort(sortedEmps);
                
                // Add sorted employees back to list.
                emps.clear();
                for (int j = 0; j < sortedEmps.length; j++)
                    emps.add(sortedEmps[j]);
                }
            }
        
        // Employees sorted, now sort shifts.
        Shift[] sortedShifts = shifts.toArray();
        Arrays.sort(sortedShifts);
        
        // Add sorted shifts back to list.
        shifts.clear();
        for (int k = 0; k < sortedShifts.length; k++)
            shifts.add(sortedShifts[k]);
        }
    
    /*
     * (non-Javadoc)
     * @see persistence.Broker#parseResults(java.sql.ResultSet)
     */
    @Override
    protected Schedule[] parseResults(ResultSet rs) throws SQLException
        {
        // List will be returned as null if no results are found.
        Schedule[] schedList = null;
        
        if (rs.last())
            {
            // Results exist, get total number of rows to create array of same
            // size.
            int resultCount = rs.getRow();
            schedList = new Schedule[resultCount];
            
            // Return ResultSet to beginning to start retrieving schedules.
            rs.beforeFirst();
            for (int i = 0; i < resultCount && rs.next(); i++)
                {
                Schedule st = new Schedule(rs.getInt("schedID"), rs.getInt("creatorID"), rs
                        .getDate("startDate"), rs
                        .getDate("endDate"));
                
                schedList[i] = st;
                }
            
            }
        
        return schedList;
        }
    
    /**
     * Attempts to create shift templates out of a given result set.
     * 
     * @param rs the result set returned by a database search.
     * @return an array of shift templates retrieved from the result set, or
     *         null if the result set was empty.
     * @throws SQLException
     */
    protected Shift[] parseShifts(ResultSet rs) throws SQLException
        {
        // List will be returned as null if no results are found.
        Shift[] stList = null;
        
        if (rs.last())
            {
            // Results exist, get total number of rows to create array of same
            // size.
            int resultCount = rs.getRow();
            stList = new Shift[resultCount];
            
            // Return ResultSet to beginning to start retrieving shift
            // templates.
            rs.beforeFirst();
            for (int i = 0; i < resultCount && rs.next(); i++)
                {
                Shift st = new Shift(rs.getInt("shiftID"), rs.getInt("schedID"), rs.getInt("day"),
                        rs
                                .getTime("startTime"),
                        rs.getTime("endTime"));
                
                stList[i] = st;
                }
            
            }
        
        return stList;
        }
    
    /**
     * @param oldSched
     * @param newSched
     * @return
     * @throws InvalidPermissionException
     * @throws DBDownException
     * @throws DBException
     */
    private boolean notifyScheduleEmps(Schedule oldSched, Schedule newSched, Employee caller)
            throws DBException,
            DBDownException,
            InvalidPermissionException
        {
        NotificationBroker nb = NotificationBroker.getBroker();
        
        /*
         * If oldSched is null, a new schedule was created. Send notifications
         * to all employees to inform them of the new schedule.
         */
        if (oldSched == null)
            {
            // New schedule has been created.
            ArrayList<Employee> notifyEmps = new ArrayList<Employee>();
            
            // Get list of unique employees in the new schedule.
            for (Shift sh : newSched.getShifts().toArray())
                {
                for (Employee e : sh.getEmployees().toArray())
                    {
                    // Remove duplicates.
                    while (notifyEmps.remove(e))
                        ;
                    
                    // Add employee to list.
                    notifyEmps.add(e);
                    }
                }
            
            // Send notification to each employee.
            for (Employee e : notifyEmps)
                {
                Notification newNoti = new Notification(e.getEmpID(),
                        "You have a new schedule for the week of " +
                                newSched.getStartDate() + " to " +
                                newSched.getEndDate(), "schedule");
                
                nb.create(newNoti, caller);
                }
            }
        else
            {
            // Schedule has been updated.
            ArrayList<Employee> oldEmps = new ArrayList<Employee>();
            ArrayList<Employee> newEmps = new ArrayList<Employee>();
            
            // Get unique employees from old schedule.
            for (Shift sh : oldSched.getShifts().toArray())
                {
                for (Employee e : sh.getEmployees().toArray())
                    {
                    // Remove duplicates.
                    while (oldEmps.remove(e))
                        ;
                    
                    // Add employee to list.
                    oldEmps.add(e);
                    }
                }
            // Get unique employees from new schedule.
            for (Shift sh : newSched.getShifts().toArray())
                {
                for (Employee e : sh.getEmployees().toArray())
                    {
                    // Remove duplicates.
                    while (newEmps.remove(e))
                        ;
                    
                    // Add employee to list.
                    newEmps.add(e);
                    }
                }
            
            // Notify employees.
            for (Employee e : oldEmps)
                {
                if (newEmps.contains(e))
                    {
                    // Employee is in both schedules, has had their sched
                    // changed.
                    Notification newNoti = new Notification(e.getEmpID(),
                            "Your schedule for the week of " +
                                    newSched.getStartDate() + " to " +
                                    newSched.getEndDate() + "has been changed.", "schedule");
                    
                    nb.create(newNoti, caller);
                    }
                else
                    {
                    // Emp is in old but not new, was removed from sched.
                    Notification newNoti = new Notification(e.getEmpID(),
                            "You have been removed from a schedule for the week of " +
                                    newSched.getStartDate() + " to " + newSched.getEndDate() + ".",
                            "schedule");
                    
                    nb.create(newNoti, caller);
                    }
                }
            
            for (Employee e : newEmps)
                {
                if (!oldEmps.contains(e))
                    {
                    Notification newNoti = new Notification(e.getEmpID(),
                            "You have a new schedule for the week of " +
                                    newSched.getStartDate() + " to " +
                                    newSched.getEndDate(), "schedule");
                    
                    nb.create(newNoti, caller);
                    }
                }
            }
        
        return true;
        }
    
    /**
     * Fetches all shift templates and shift position objects from the database
     * for each shiftTemplate.
     * 
     * @param templates
     * @param conn
     */
    private void fillSched(Schedule[] schedules, DBConnection conn) throws SQLException
        {
        // Prepare the select statements to pull additional data.
        PreparedStatement shiftStmt = conn
                .getConnection()
                .prepareStatement(
                        "SELECT * FROM `WebAgenda`.`SHIFT` WHERE schedID = ? ORDER BY day, startTime, endTime");
        PreparedStatement shiftEmpStmt = conn
                .getConnection()
                .prepareStatement(
                        "SELECT e.* FROM `WebAgenda`.`EMPLOYEE` e JOIN `WebAgenda`.`SHIFTEMP` se ON e.empID = se.empID WHERE se.shiftID = ? ORDER BY se.shiftID, se.empID");
        
        for (Schedule sched : schedules)
            {
            // Get shifts for matching schedule.
            shiftStmt.setInt(1, sched.getSchedID());
            ResultSet shiftRS = shiftStmt.executeQuery();
            Shift[] shifts = parseShifts(shiftRS);
            
            for (Shift shift : shifts)
                {
                // Get shift employees for matching shift.
                shiftEmpStmt.setInt(1, shift.getShiftID());
                ResultSet shiftEmpRS = shiftEmpStmt.executeQuery();
                Employee[] shiftEmps = EmployeeBroker.getBroker().parseResults(shiftEmpRS);
                
                // Add employees to shift.
                if (shiftEmps != null)
                    {
                    for (Employee emp : shiftEmps)
                        {
                        shift.getEmployees().add(emp);
                        }
                    }
                
                // Add shifts templates to schedule template.
                sched.getShifts().add(shift);
                }
            }
        
        // Release resources used by prepared statements.
        shiftStmt.close();
        shiftEmpStmt.close();
        }
    
    /**
     * Compares a given schedule template against the database, ensuring that it
     * has not been changed by another user. This is used check for race
     * conditions when updating or deleting schedule templates in the database.
     * 
     * @param old The schedule template that was previously retrieved from the
     *            database.
     * @param caller The employee that is logged into the system.
     * @return
     * @throws DBChangeException
     * @throws DBException
     * @throws DBDownException
     */
    private boolean raceCheck(Schedule old, Employee caller) throws DBChangeException, DBException,
            DBDownException
        {
        if (old == null || old.getSchedID() == -1)
            throw new DBException(
                    "Unable to validate old schedule template, is null or has no schedTempID.");
        
        // Get schedule from DB with matching scheduleID.
        Schedule[] fromDB = this.get(old, caller);
        
        // If no schedule template returned, throw exception.
        if (fromDB == null)
            throw new DBChangeException("No matching record found, schedule may have been deleted.");
        
        Schedule fetched = fromDB[0];
        
        // Sort schedule before starting compares.
        sortSchedule(old);
        
        // Compare dates of old/fetched. SchedID and CreatorID do not need to be
        // checked.
        if (!old.getStartDate().equals(fetched.getStartDate()) ||
                !old.getEndDate().equals(fetched.getEndDate()))
            throw new DBChangeException("Schedule dates have been modified.");
        
        // Compare number of shifts.
        if (old.getShifts().size() != fetched.getShifts().size())
            throw new DBChangeException("Schedule num of shifts modified.");
        
        // Compare shift templates individually between old/fetched.
        for (int i = 0; i < old.getShifts().size(); i++)
            {
            Shift sh1 = old.getShifts().get(i);
            Shift sh2 = fetched.getShifts().get(i);
            
            // Compare shift template attributes.
            if (sh1.getShiftID() != sh2.getShiftID() || sh1.getDay() != sh2.getDay() ||
                    !sh1.getStartTime().equals(sh2.getStartTime()) ||
                    !sh1.getEndTime().equals(sh2.getEndTime()))
                throw new DBChangeException("Shift changed.");
            
            // Compare number of shift employees.
            if (sh1.getEmployees().size() != sh2.getEmployees().size())
                throw new DBChangeException("Shift employees changed.");
            
            // Compare shift employees individually between old/fetched.
            for (int j = 0; j < sh1.getEmployees().size(); j++)
                {
                Employee emp1 = sh1.getEmployees().get(j);
                Employee emp2 = sh2.getEmployees().get(j);
                
                // Only ID numbers need to be compared.
                if (!(emp1.getEmpID() == emp2.getEmpID()))
                    throw new DBChangeException("Shift employees changed: " + emp1.getEmpID() +
                            " vs " +
                            emp2.getEmpID());
                }
            }
        
        return true;
        }
    
    /**
     * Inserts the given shift, using the given connection, resultSet, and
     * schedule ID.
     * 
     * @param shift The shift to insert into the database.
     * @param createShift The resultSet defining the insert for a shift.
     * @param createShiftEmp The resultSet defining the insert for a shift
     *            employee.
     * @param schedID The ID number of the schedule the shift belongs to
     * @throws SQLException if there was an error with the insert statements.
     * @throws DBException if there was an error with executing the statements.
     */
    private void insertShift(Shift shift, int schedID, PreparedStatement createShift,
            PreparedStatement createShiftEmp)
            throws SQLException,
            DBException
        {
        // Add schedule ID before insert.
        shift.setSchedID(schedID);
        
        // Attempt to insert shift.
        createShift.setInt(1, shift.getSchedID());
        createShift.setInt(2, shift.getDay());
        createShift.setTime(3, shift.getStartTime());
        createShift.setTime(4, shift.getEndTime());
        if (createShift.executeUpdate() != 1)
            throw new DBException("Failed to insert shift.");
        
        // Save the auto-generated shift ID.
        ResultSet temp = createShift.getGeneratedKeys();
        if (temp.next())
            shift.setShiftID(temp.getInt(1));
        
        // Insert each shift employee.
        insertShiftEmployees(shift.getEmployees().toArray(), shift.getShiftID(), createShiftEmp);
        }
    
    /**
     * Attempts to insert an array of shift employees into the database.
     * 
     * @param shiftEmpArr The array to insert.
     * @param shiftID The shift template ID to use for all shift employees.
     * @param createShiftEmp The prepared statement used to execute the inserts.
     */
    private void insertShiftEmployees(Employee[] shiftEmpArr, int shiftID,
            PreparedStatement createShiftEmp)
            throws DBException, SQLException
        {
        if (shiftEmpArr != null)
            {
            for (Employee emp : shiftEmpArr)
                {
                // Attempt to insert shift employee.
                createShiftEmp.setInt(1, shiftID);
                createShiftEmp.setInt(2, emp.getEmpID());
                if (createShiftEmp.executeUpdate() != 1)
                    throw new DBException("Failed to insert shift employee");
                }
            }
        }
    }
