/**
 * business - Employee.java
 */
package business;

import java.util.Date;
import business.schedule.*;

/*Ignore the error for now :D*/


/**
 * @author peon-dev
 * @version 0.01.00
 *
 */
public class Employee {

	/* TODO: I won't be creating getters and setter for this class just yet. I'd prefer to know what context they are
	 * needed in before allowing public access; especially since this is personal information, they should probably
	 * be class level or protected at most. Username may be public though.*/
	
	/** Identifier for the user. The name employee does not reflect their position. Cannot be negative. */
	private int employee_id										= -1;
	/** Like employee_id, supervisor doesn't entail the position; it's the authoritative figure for the employee.
	 * Cannot be negative, if this user has no supervisor, this value should point to the employee_id. */
	private int supervisor										= -1;
	/** User's first name, or preferred name. Name values cannot be changed by current user, they have to be
	 * changed by a higher authority with permissions to do so, they cannnot be changed by current user. */
	private String firstname									= null;
	/** User's last name, family name, or referenced name. May depend on culture how this is used.
	 * Has the same constraints as the firstname. */
	private String lastname										= null;
	/** User's e-mail address that they want information sent to. Is changeable by the user. It is highly
	 * recommended that this be set. */
	private String e_mail										= null;
	/** Name that is entered into the username field when logging into the system; does not reflect personal data
	 * such as name or id, while allowing users to communicate with each other in a recognizable format. Cannot
	 * be null.  */
	private String username										= null;
	//FIXME: This has to be encrypted, even in the object! Getter will have to be an unencryption algorithm as well as returning password, only accessible by Broker!
	/** Password for logging onto the system. This is an encrypted value that is unencyrpted by the Broker and can 
	 * only be set and retrieved by the Broker. */
	private String password										= null;
	/** Location refers to the location in the business or company. If more than one locations exist, such as 
	 * multiple buildings, instances, or groups of working units, this can be specified. Is not required, but
	 * if set may influence automatic schedule generation. */
	private Location preferred_location							= null;
	/** Position is the job that an Employee prefers to work at. This assumes that multiple positions for work are
	 * available. May influence the automatically generated schedule. */
	private Position preferred_position							= null;
	/** The permission set as its character array. Both level and version can be parsed from it and represents the
	 * entirety of the user's permissions. Can only be changed in the broker by a user with higher permission levels
	 * and explicit permissions. */
	private char[] permission_level								= new char[3]; // Must be synced with business.permissions.PermissionLevel
	/** Boolean value that represents if the user can access their account; all accounts are saved in the database
	 * and can only be deleted when explicitly requested; since some records cannot be deleted (see tax records), 
	 * employee entries will be kept and searches will generally only return active employees. An exception is
	 * when someone with proper permissions wants to browse inactive user, or generate reports on active/inactive
	 * users. */
	private boolean active										= false;
	/** Self-explainatory: the date of birth for the employee. While not required by the system for general use, it can
	 * be made required by certain positions that may require users to be over the age of 16/18/21. */
	private Date birth_date										= null;
	/** An object that contains the personal visual settings of a user; loaded upon login to set up their dashboard,
	 * retrieved by broker. Value can be null, in which case it will be set to the default built-in view. May
	 * not apply through mobile device/browser views (mobile device meaning native mobile apps.) */
	private Settings user_settings								= null;
	
	
	
	private void setPassword(String newPassword)
	{
		this.password = newPassword;
	}
	
}
