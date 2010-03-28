/**
 * business - Employee.java
 */
package business;

import java.sql.Date;
import java.sql.Timestamp;

import exception.DBException;

/**
 * @author Daniel Kettle, Daniel Wehr
 * @version 0.3.0
 */
public class Employee extends BusinessObject implements Comparable<Employee>
	{
	private static final long	serialVersionUID	= -1671744709578725501L;
	
	/**
	 * Identifier for the user. The name employee does not reflect their
	 * position. Cannot be negative in the database.
	 */
	private int				empID					= -1;
	
	/**
	 * Like employee_id, supervisor doesn't entail the position; it's the
	 * authoritative figure for the employee. Cannot be negative, if this user
	 * has no supervisor, this value should point to the employee_id.
	 */
	private int				supervisorID		= -1;
	
	/**
	 * User's first name, or preferred name. Name values cannot be changed by
	 * current user, they have to be changed by a higher authority with
	 * permissions to do so, they cannnot be changed by current user.
	 */
	private String					givenName			= null;
	
	/**
	 * User's last name, family name, or referenced name. May depend on culture
	 * how this is used. Has the same constraints as the firstname.
	 */
	private String					familyName			= null;
	
	/**
	 * The date of birth for the employee. While not required by the system for
	 * general use, it can be made required by certain positions that may require
	 * users to be over the age of 16/18/21.
	 */
	private Date					birthDate			= null;
	
	/**
	 * User's e-mail address that they want information sent to. Is changeable by
	 * the user. It is highly recommended that this be set.
	 */
	private String					email					= null;
	
	/**
	 * Name that is entered into the username field when logging into the system;
	 * does not reflect personal data such as name or id, while allowing users to
	 * communicate with each other in a recognizable format. Cannot be null.
	 */
	private String					username				= null;
	
	/**
	 * Password for logging onto the system. This is only used when creating a
	 * new employee to assign the initial password. All other password
	 * verification and changing functions are handled by the employee broker.
	 */
	private String					password				= null;
	
	/**
	 * The last time at which the employee logged into the system.
	 */
	private Timestamp				lastLogin			= null;
	
	/**
	 * Location refers to the location in the business or company. If more than
	 * one locations exist, such as multiple buildings, instances, or groups of
	 * working units, this can be specified. Is not required, but if set may
	 * influence automatic schedule generation.
	 */
	private String					prefLocation		= null;
	
	/**
	 * Position is the job that an Employee prefers to work at. This assumes that
	 * multiple positions for work are available. May influence the automatically
	 * generated schedule.
	 */
	private String					prefPosition		= null;
	
	/**
	 * Boolean value that represents if the user can access their account; all
	 * accounts are saved in the database and can only be deleted when explicitly
	 * requested; since some records cannot be deleted (see tax records),
	 * employee entries will be kept and searches will generally only return
	 * active employees. An exception is when someone with proper permissions
	 * wants to browse inactive user, or generate reports on active/inactive
	 * users.
	 */
	private boolean				active				= true;
	
	/**
	 * An object that contains the personal visual settings of a user; loaded
	 * upon login to set up their dashboard, retrieved by broker. Value can be
	 * null, in which case it will be set to the default built-in view. May not
	 * apply through mobile device/browser views (mobile device meaning native
	 * mobile apps.)
	 */
	private Settings				userSettings		= null;
	
	private char version								= ' ';
	private int level									= 0;
	
	/**
	 * Base constructor for a new employee object. Is required to create a new
	 * employee, but values can be null or invalid if using an employee object as
	 * an object to search criteria off of.
	 * 
	 * @param empID int employee id
	 * @param fname String first name
	 * @param lname String last (family) name
	 * @param date Date birthdate
	 * @param username String username for employee account
	 * @param password String password for employee account
	 * @param plevel String permission level (formats: #, ##, #c, ##c -- "0",
	 *           "43","3a","34i" are acceptable)
	 * @throws DBException 
	 */
	public Employee(int empID, String fname, String lname,
			String username, String password, String plevel) throws DBException
		{
		this.empID = empID;
		this.givenName = fname;
		this.familyName = lname;
		this.username = username;
		this.password = password;
		if(plevel == null) {
			this.level = 0;
			this.version = ' ';
		}
		else if(plevel.length() > 3 || plevel.length() == 0) throw new DBException("Invalid permission level format");
		try {
			if(Character.isLetter(plevel.charAt(plevel.length() - 1)) || plevel.charAt(plevel.length() - 1) == ' ')
			{
				this.version = plevel.charAt(plevel.length() - 1);
				try {
					int i = Integer.parseInt(plevel.substring(0,plevel.length() - 1));
					if(i < 0) throw new DBException("Cannot create an employee with a negative permission level");
					if(i > 99) throw new DBException("Level is too high to create. 0-99 are acceptable levels");
					this.level = i;
				}
				catch(NumberFormatException nfE) {
					throw new DBException("Permission Level " + plevel + " is in an invalid format");
				}
			}
			else {
				try {
					int i = Integer.parseInt(plevel);
					if(i < 0) throw new DBException("Cannot create an employee with a negative permission level");
					this.level = i;
					this.version = ' ';
				}
				catch(NumberFormatException nfE) {
					throw new DBException("Permission Level " + plevel + " is in an invalid format.");
				}
			}
		}
		catch(ArrayIndexOutOfBoundsException aioobE) {
			try {
				int i = Integer.parseInt(plevel);
				if(i < 0) throw new DBException("Cannot create an employee with a negative permission level");
				this.level = i;
				this.version = ' ';
			}
			catch(NumberFormatException nfE) {
				throw new DBException("Permission Level " + plevel + " is in an invalid format.");
			}
		}
		}
	
	public Employee(int empID, String fname, String lname,
			String username, String password, int lvl, char ver) throws DBException
		{
		this.empID = empID;
		this.givenName = fname;
		this.familyName = lname;
		this.username = username;
		this.password = password;
		if(lvl < 0) throw new DBException("Cannot create an employee with a negative permission level");
		if(lvl > 99) throw new DBException("Level is too high to create. 0-99 are acceptable levels");
		this.level = lvl;
		if(!Character.isLetter(ver) && ver != ' ') throw new DBException("Version specified is not a letter character or a non-version (space) character");
		this.version = ver;
		}
	
	/** Produces a blank template of an employee */
	public Employee()
		{
		this.level = 0;
		this.version = ' ';
		}
	
	/**
	 * @return the employee_id
	 */
	public int getEmpID()
		{
		return empID;
		}
	
	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmpID(int employeeId)
		{
		empID = employeeId;
		}
	
	/**
	 * @return the supervisor
	 */
	public int getSupervisorID()
		{
		return supervisorID;
		}
	
	/**
	 * @param supervisorId the supervisorId to set
	 */
	public void setSupervisorID(int supervisorId)
		{
		this.supervisorID = supervisorId;
		}
	
	/**
	 * @return the givenName
	 */
	public String getGivenName()
		{
		return givenName;
		}
	
	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName)
		{
		this.givenName = givenName;
		}
	
	/**
	 * @return the familyName
	 */
	public String getFamilyName()
		{
		return familyName;
		}
	
	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName)
		{
		this.familyName = familyName;
		}
	
	/**
	 * @return the email
	 */
	public String getEmail()
		{
		return email;
		}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
		{
		this.email = email;
		}
	
	/**
	 * @return the username
	 */
	public String getUsername()
		{
		return username;
		}
	
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
		{
		this.username = username;
		}
	
	/**
	 * @return the password
	 */
	public String getPassword()
		{
		return password;
		}
	
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
		{
		this.password = password;
		}
	
	/**
	 * @return the preferred_location
	 */
	public String getPrefLocation()
		{
		return prefLocation;
		}
	
	/**
	 * @param preferredLocation the preferred_location to set
	 */
	public void setPrefLocation(String preferredLocation)
		{
		prefLocation = preferredLocation;
		}
	
	/**
	 * @return the preferred_position
	 */
	public String getPrefPosition()
		{
		return prefPosition;
		}
	
	/**
	 * @param preferredPosition the preferred_position to set
	 */
	public void setPrefPosition(String preferredPosition)
		{
		prefPosition = preferredPosition;
		}
	
	/**
	 * @return the active
	 */
	public boolean getActive()
		{
		return active;
		}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active)
		{
		this.active = active;
		}
	
	/**
	 * @return the birth_date
	 */
	public Date getBirthDate()
		{
		return birthDate;
		}
	
	/**
	 * @param birthDate the birth_date to set
	 */
	public void setBirthDate(Date birthDate)
		{
		this.birthDate = birthDate;
		}
	
	/**
	 * @return the lastLogin
	 */
	public Timestamp getLastLogin()
		{
		return lastLogin;
		}
	
	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Timestamp lastLogin)
		{
		this.lastLogin = lastLogin;
		}
	
	/**
	 * @return the user_settings
	 */
	public Settings getUserSettings()
		{
		return userSettings;
		}
	
	/**
	 * @param userSettings the user_settings to set
	 */
	public void setUserSettings(Settings userSettings)
		{
		this.userSettings = userSettings;
		}
	
	/**
	 * @return the version
	 */
	public char getVersion() {
		return version;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/* (non-Javadoc)
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

	/* (non-Javadoc)
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

	@Override
	public String toString()
		{
		return empID + ";" + supervisorID + ";" + givenName + ";" + familyName +
				";" + birthDate + ";" + email + ";" + username + ";" + password +
				";" + prefLocation + ";" + prefPosition + ";" + level + version + ";" +
				lastLogin + ";" + active;
		}
	
	}
