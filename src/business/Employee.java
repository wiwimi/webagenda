/**
 * business - Employee.java
 */
package business;

import java.sql.Date;
import java.sql.Timestamp;
import exception.DBException;

/**
 * Holds all information for an employee, matching a record in the database.
 * 
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.3.0
 */
public class Employee extends BusinessObject implements Comparable<Employee>
    {
    /**
     * Serial UID so that class can be written and restored from a datafile.
     * Unused, but as this extends classes that use this value, it is included.
     * Randomly generated.
     */
    private static final long serialVersionUID = -1671744709578725501L;
    
    /**
     * Identifier for the user. The name employee does not reflect their
     * position. Cannot be negative in the database.
     */
    private int               empID            = -1;
    
    /**
     * Like employee_id, supervisor doesn't entail the position; it's the
     * authoritative figure for the employee. Cannot be negative, if this user
     * has no supervisor, this value should point to the employee_id.
     */
    private int               supervisorID     = -1;
    
    /**
     * User's first name, or preferred name. Name values cannot be changed by
     * current user, they have to be changed by a higher authority with
     * permissions to do so, they cannnot be changed by current user.
     */
    private String            givenName        = null;
    
    /**
     * User's last name, family name, or referenced name. May depend on culture
     * how this is used. Has the same constraints as the firstname.
     */
    private String            familyName       = null;
    
    /**
     * The date of birth for the employee. While not required by the system for
     * general use, it can be made required by certain positions that may
     * require users to be over the age of 16/18/21.
     */
    private Date              birthDate        = null;
    
    /**
     * User's e-mail address that they want information sent to. Is changeable
     * by the user. It is highly recommended that this be set.
     */
    private String            email            = null;
    
    /**
     * Name that is entered into the username field when logging into the
     * system; does not reflect personal data such as name or id, while allowing
     * users to communicate with each other in a recognizable format. Cannot be
     * null.
     */
    private String            username         = null;
    
    /**
     * Password for logging onto the system. This is only used when creating a
     * new employee to assign the initial password. All other password
     * verification and changing functions are handled by the employee broker.
     */
    private String            password         = null;
    
    /**
     * The last time at which the employee logged into the system.
     */
    private Timestamp         lastLogin        = null;
    
    /**
     * Location refers to the location in the business or company. If more than
     * one locations exist, such as multiple buildings, instances, or groups of
     * working units, this can be specified. Is not required, but if set may
     * influence automatic schedule generation.
     */
    private String            prefLocation     = null;
    
    /**
     * Position is the job that an Employee prefers to work at. This assumes
     * that multiple positions for work are available. May influence the
     * automatically generated schedule.
     */
    private String            prefPosition     = null;
    
    /**
     * Boolean value that represents if the user can access their account; all
     * accounts are saved in the database and can only be deleted when
     * explicitly requested; since some records cannot be deleted (see tax
     * records), employee entries will be kept and searches will generally only
     * return active employees. An exception is when someone with proper
     * permissions wants to browse inactive user, or generate reports on
     * active/inactive users.
     */
    private boolean           active           = true;
    
    /**
     * Users that have a default system assigned password will be notified to
     * change it.
     */
    private boolean           passChanged      = false;
    
    /**
     * Version of a PermissionLevel that depicts having the same authority of
     * the equivalent permission level, but whose job has different permissions
     * and therefore a different goal. Defaults to a space character, or basic
     * version.
     */
    private char              version          = 'a';
    
    /**
     * Permission level value that depicts the authority of this Employee over
     * other employees. When a higher level employee makes a decision, it cannot
     * be overruled by one with a lower permission. Basic hierarchical
     * permission functionality.
     */
    private int               level            = 0;
    
    /**
     * Base constructor for a new employee object. Is required to create a new
     * employee, but values can be null or invalid if using an employee object
     * as an object to search criteria off of.
     * 
     * @param empID int employee id
     * @param fname String first name
     * @param lname String last (family) name
     * @param date Date birthdate
     * @param username String username for employee account
     * @param password String password for employee account
     * @param lvl the permission level for employee account
     * @param ver the permission version for employee account
     * @throws DBException
     */
    public Employee(int empID, String fname, String lname,
            String username, String password, int lvl, char ver) throws DBException
        {
        this.empID = empID;
        this.givenName = fname;
        this.familyName = lname;
        this.username = username;
        this.password = password;
        if (lvl < 0)
            throw new DBException("Cannot create an employee with a negative permission level");
        if (lvl > 99)
            throw new DBException("Level is too high to create. 0-99 are acceptable levels");
        this.level = lvl;
        if (!Character.isLetter(ver))
            throw new DBException(
                    "Version specified is not a letter character or a non-version (space) character");
        this.version = ver;
        }
    
    /**
     * Base constructor for a new employee object. Is required to create a new
     * employee, but values can be null or invalid if using an employee object
     * as an object to search criteria off of.
     * 
     * @param empID int employee id
     * @param fname String first name
     * @param lname String last (family) name
     * @param date Date birthdate
     * @param username String username for employee account
     * @param password String password for employee account
     * @throws DBException
     */
    public Employee(int empID, String fname, String lname,
    		String username, String password)
    {
    	this.empID = empID;
        this.givenName = fname;
        this.familyName = lname;
        this.username = username;
        this.password = password;
        this.version = 'a';
        this.level = 0;
    }
    
    /** Produces a blank template of an employee */
    public Employee()
        {
        }
    
    /**
     * Get the Employee's id number
     * 
     * @return the employee_id
     */
    public int getEmpID()
        {
        return empID;
        }
    
    /**
     * Set the Employee's id number
     * 
     * @param employeeId the employeeId to set
     */
    public void setEmpID(int employeeId)
        {
        empID = employeeId;
        }
    
    /**
     * Get the id of Employee's supervisor
     * 
     * @return the supervisor
     */
    public int getSupervisorID()
        {
        return supervisorID;
        }
    
    /**
     * Sets the id of Employee's supervisor
     * 
     * @param supervisorId the supervisorId to set
     */
    public void setSupervisorID(int supervisorId)
        {
        this.supervisorID = supervisorId;
        }
    
    /**
     * Gets the given name of an Employee
     * 
     * @return the givenName
     */
    public String getGivenName()
        {
        return givenName;
        }
    
    /**
     * Sets the given name of an Employee
     * 
     * @param givenName the givenName to set
     */
    public void setGivenName(String givenName)
        {
        this.givenName = givenName;
        }
    
    /**
     * Gets the family name of the Employee
     * 
     * @return the familyName
     */
    public String getFamilyName()
        {
        return familyName;
        }
    
    /**
     * Sets the family name of the employee
     * 
     * @param familyName the familyName to set
     */
    public void setFamilyName(String familyName)
        {
        this.familyName = familyName;
        }
    
    /**
     * Gets the Employee's email
     * 
     * @return the email
     */
    public String getEmail()
        {
        return email;
        }
    
    /**
     * Sets the Employee's email.
     * 
     * @param email the email to set
     */
    public void setEmail(String email)
        {
        this.email = email;
        }
    
    /**
     * Gets the Employee's username
     * 
     * @return the username
     */
    public String getUsername()
        {
        return username;
        }
    
    /**
     * Sets the Employee's username
     * 
     * @param username the username to set
     */
    public void setUsername(String username)
        {
        this.username = username;
        }
    
    /**
     * Gets the Employee' password
     * 
     * @return the password
     */
    public String getPassword()
        {
        return password;
        }
    
    /**
     * Sets the Employee's password
     * 
     * @param password the password to set
     */
    public void setPassword(String password)
        {
        this.password = password;
        }
    
    /**
     * Gets the Employee's Preferred Location
     * 
     * @return the preferred_location
     */
    public String getPrefLocation()
        {
        return prefLocation;
        }
    
    /**
     * Sets the Employee's Preferred Location
     * 
     * @param preferredLocation the preferred_location to set
     */
    public void setPrefLocation(String preferredLocation)
        {
        prefLocation = preferredLocation;
        }
    
    /**
     * Gets the Employee's Preferred Position
     * 
     * @return the preferred_position
     */
    public String getPrefPosition()
        {
        return prefPosition;
        }
    
    /**
     * Sets the Employee's preferred Position
     * 
     * @param preferredPosition the preferred_position to set
     */
    public void setPrefPosition(String preferredPosition)
        {
        prefPosition = preferredPosition;
        }
    
    /**
     * Gets the active status of an Employee
     * 
     * @return the active
     */
    public boolean getActive()
        {
        return active;
        }
    
    /**
     * Sets the active status of an Employee
     * 
     * @param active the active to set
     */
    public void setActive(boolean active)
        {
        this.active = active;
        }
    
    /**
     * Gets the attribute for a password changed (false means that an Employee
     * must change their password).
     * 
     * @return the passChanged
     */
    public boolean getPassChanged()
        {
        return passChanged;
        }
    
    /**
     * Sets the attribute for a password changed (false means that an Employee
     * must change their password).
     * 
     * @param passChanged the passChanged to set
     */
    public void setPassChanged(boolean passChanged)
        {
        this.passChanged = passChanged;
        }
    
    /**
     * Gets the birth date of an Employee.
     * 
     * @return the birth_date
     */
    public Date getBirthDate()
        {
        return birthDate;
        }
    
    /**
     * Sets the birth date of the Employee
     * 
     * @param birthDate the birth_date to set
     */
    public void setBirthDate(Date birthDate)
        {
        this.birthDate = birthDate;
        }
    
    /**
     * Gets the last login time of an Employee
     * 
     * @return the lastLogin
     */
    public Timestamp getLastLogin()
        {
        return lastLogin;
        }
    
    /**
     * Sets the last login time of an Employee
     * 
     * @param lastLogin the lastLogin to set
     */
    public void setLastLogin(Timestamp lastLogin)
        {
        this.lastLogin = lastLogin;
        }
    
    /**
     * Gets the version of the Employee's Permission Level
     * 
     * @return the version
     */
    public char getVersion()
        {
        return version;
        }
    
    /**
     * Gets the level of the Employee's Permission Level
     * 
     * @return the level
     */
    public int getLevel()
        {
        return level;
        }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Employee o)
        {
        if (this.getEmpID() < o.getEmpID())
            return -1;
        else if (this.getEmpID() > o.getEmpID())
            return 1;
        
        return 0;
        }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
        {
        if (!(obj instanceof Employee))
            return false;
        
        Employee emp = (Employee)obj;
        
        // Compare each attribute. Passwords and login times are not included.
        if (this.empID != emp.empID ||
                this.supervisorID != emp.supervisorID ||
                (this.givenName == null ? emp.givenName != null : !this.givenName
                        .equals(emp.givenName)) ||
                (this.familyName == null ? emp.familyName != null : !this.familyName
                        .equals(emp.familyName)) ||
                (this.birthDate == null ? emp.birthDate != null : !this.birthDate
                        .equals(emp.birthDate)) ||
                (this.email == null ? emp.email != null : !this.email.equals(emp.email)) ||
                (this.username == null ? emp.username != null : !this.username.equals(emp.username)) ||
                (this.prefLocation == null ? emp.prefLocation != null : !this.prefLocation
                        .equals(emp.prefLocation)) ||
                (this.prefPosition == null ? emp.prefPosition != null : !this.prefPosition
                        .equals(emp.prefPosition)) ||
                this.active != emp.active ||
                this.passChanged != emp.passChanged ||
                this.version != emp.version ||
                this.level != emp.level)
            return false;
        
        return true;
        }
    
    /*
     * (non-Javadoc)
     * @see business.BusinessObject#clone()
     */
    @Override
    public Employee clone()
        {
        Employee clone = (Employee)super.clone();
        if (this.birthDate != null)
            clone.birthDate = (Date)this.birthDate.clone();
        if (this.lastLogin != null)
            clone.lastLogin = (Timestamp)this.lastLogin.clone();
        
        return clone;
        }
    
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
        {
        return empID + ";" + supervisorID + ";" + givenName + ";" + familyName +
                ";" + birthDate + ";" + email + ";" + username + ";" + password +
                ";" + prefLocation + ";" + prefPosition + ";" + level + version + ";" +
                lastLogin + ";" + active;
        }
    
    }
