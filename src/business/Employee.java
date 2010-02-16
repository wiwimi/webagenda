/**
 * business - Employee.java
 */
package business;

import java.util.Date;
import business.schedule.*;

/**
 * @author peon-dev, Daniel Wehr
 * @version 0.2.0
 */
public class Employee extends Cachable
	{
	
	/*
	 * TODO: Getters and setters have been created for now. Any security related
	 * changes regarding access to information can be made in the future as
	 * needed. Many will likely be handled in the broker rather than the employee
	 * class itself.
	 */

	private static final long	serialVersionUID	= -1671744709578725501L;

	/**
	 * Identifier for the user. The name employee does not reflect their
	 * position. Cannot be negative.
	 */
	private Integer	employee_id				= null;
	
	/**
	 * Like employee_id, supervisor doesn't entail the position; it's the
	 * authoritative figure for the employee. Cannot be negative, if this user
	 * has no supervisor, this value should point to the employee_id.
	 */
	private Integer	supervisor				= null;
	
	/**
	 * User's first name, or preferred name. Name values cannot be changed by
	 * current user, they have to be changed by a higher authority with
	 * permissions to do so, they cannnot be changed by current user.
	 */
	private String		givenName				= null;
	
	/**
	 * User's last name, family name, or referenced name. May depend on culture
	 * how this is used. Has the same constraints as the firstname.
	 */
	private String		familyName				= null;
	
	/**
	 * User's e-mail address that they want information sent to. Is changeable by
	 * the user. It is highly recommended that this be set.
	 */
	private String		email						= null;
	
	/**
	 * Name that is entered into the username field when logging into the system;
	 * does not reflect personal data such as name or id, while allowing users to
	 * communicate with each other in a recognizable format. Cannot be null.
	 */
	private String		username					= null;
	
	/**
	 * Password for logging onto the system. This is only used when creating a
	 * new employee to assign the initial password. All other password
	 * verification and changing functions are handled by the employee broker.
	 */
	private String		password					= null;
	
	/**
	 * Location refers to the location in the business or company. If more than
	 * one locations exist, such as multiple buildings, instances, or groups of
	 * working units, this can be specified. Is not required, but if set may
	 * influence automatic schedule generation.
	 */
	private Location	preferred_location	= null;
	
	/**
	 * Position is the job that an Employee prefers to work at. This assumes that
	 * multiple positions for work are available. May influence the automatically
	 * generated schedule.
	 */
	private Position	preferred_position	= null;
	
	/**
	 * The permission set as a string. Both level and version can be parsed from
	 * it and represents the entirety of the user's permissions. Can only be
	 * changed in the broker by a user with higher permission levels and explicit
	 * permissions. This value must be equivalent to the key of an
	 * already-existing permissions set.
	 */
	private String		permission_level		= null;
	
	/**
	 * Boolean value that represents if the user can access their account; all
	 * accounts are saved in the database and can only be deleted when explicitly
	 * requested; since some records cannot be deleted (see tax records),
	 * employee entries will be kept and searches will generally only return
	 * active employees. An exception is when someone with proper permissions
	 * wants to browse inactive user, or generate reports on active/inactive
	 * users.
	 */
	private Boolean	active					= null;
	
	/**
	 * The date of birth for the employee. While not required by the system for
	 * general use, it can be made required by certain positions that may require
	 * users to be over the age of 16/18/21.
	 */
	private Date		birth_date				= null;
	
	/**
	 * The last time at which the employee logged into the system.
	 */
	private Date		lastLogin				= null;
	
	/**
	 * An object that contains the personal visual settings of a user; loaded
	 * upon login to set up their dashboard, retrieved by broker. Value can be
	 * null, in which case it will be set to the default built-in view. May not
	 * apply through mobile device/browser views (mobile device meaning native
	 * mobile apps.)
	 */
	private Settings	user_settings			= null;

	/**
	 * @return the employee_id
	 */
	public Integer getEmployee_id()
		{
		return employee_id;
		}

	/**
	 * @param employeeId the employee_id to set
	 */
	public void setEmployee_id(Integer employeeId)
		{
		employee_id = employeeId;
		}

	/**
	 * @return the supervisor
	 */
	public Integer getSupervisor()
		{
		return supervisor;
		}

	/**
	 * @param supervisor the supervisor to set
	 */
	public void setSupervisor(Integer supervisor)
		{
		this.supervisor = supervisor;
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
	public Location getPreferred_location()
		{
		return preferred_location;
		}

	/**
	 * @param preferredLocation the preferred_location to set
	 */
	public void setPreferred_location(Location preferredLocation)
		{
		preferred_location = preferredLocation;
		}

	/**
	 * @return the preferred_position
	 */
	public Position getPreferred_position()
		{
		return preferred_position;
		}

	/**
	 * @param preferredPosition the preferred_position to set
	 */
	public void setPreferred_position(Position preferredPosition)
		{
		preferred_position = preferredPosition;
		}

	/**
	 * @return the permission_level
	 */
	public String getPermission_level()
		{
		return permission_level;
		}

	/**
	 * @param permissionLevel the permission_level to set
	 */
	public void setPermission_level(String permissionLevel)
		{
		permission_level = permissionLevel;
		}

	/**
	 * @return the active
	 */
	public Boolean getActive()
		{
		return active;
		}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active)
		{
		this.active = active;
		}

	/**
	 * @return the birth_date
	 */
	public Date getBirth_date()
		{
		return birth_date;
		}

	/**
	 * @param birthDate the birth_date to set
	 */
	public void setBirth_date(Date birthDate)
		{
		birth_date = birthDate;
		}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin()
		{
		return lastLogin;
		}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin)
		{
		this.lastLogin = lastLogin;
		}

	/**
	 * @return the user_settings
	 */
	public Settings getUser_settings()
		{
		return user_settings;
		}

	/**
	 * @param userSettings the user_settings to set
	 */
	public void setUser_settings(Settings userSettings)
		{
		user_settings = userSettings;
		}

	@Override
	public boolean canDelete()
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public boolean canEdit()
		{
		// TODO Auto-generated method stub
		return false;
		}

	@Override
	public boolean canRead()
		{
		// TODO Auto-generated method stub
		return false;
		}
	
	}
