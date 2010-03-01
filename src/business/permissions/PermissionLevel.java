/**
 * business - PermissionLevel.java
 */
package business.permissions;

import business.BusinessObject;


/**
 * @author Daniel Kettle
 * @version 0.01.00
 * <br>
 * README: <p>
 * Because of the volatile and secure nature of permissions, and to reduce the change of accidental permission changing either by
 * expected or unexpected events, a series of Permissions (Permissions object) can only be assigned if the PermissionLevel 
 * object has a null Permissions object in the class when the setter is called. User created widgets (if they are integrated)
 * will not have access to the ability to null a permission in order to change it. All access will be via validation classes
 * that access this class.
 * 
 *
 */
public class PermissionLevel extends BusinessObject {

	/** Permission set that makes use of Permissions object. The reason that level is not included in the
	 * Permissions object is because this way multiple Permission Levels can make use of one set of
	 * Permissions. As the actual level determines authority, it may be desirable to have someone
	 * as an authority figure, even if they only have very limited creation and editing ability in the
	 * system. Trusted permission works as well. Editing of basic permissions that affect all
	 * lowest-level employees (and their p. level) is now extremely easy and can be done in one edit,
	 * rather than n. */
	private Permissions level_permissions 						= null;
	
	/** Level attribute determining the user's level; valid entries are 0-99, no 3 digit values will be allowed.
	 * In tandem with the version of the level which is appended to the level in a 3 character char array in the
	 * backend, there are 100 * 26 different combinations totalling 2600 allowable unique permission sets.
	 * With the limited number of permissions, this should cover potential needs and more. */
	private int level											= 0; // Basic default low-level permission level
	/** Version attribute is appended onto the level int and contains a char from a-z, or 26 different values.
	 * Any non-letter character generates an error, although a space character is ignored. */
	private char version										= ' '; 
	/** A description of the permission level. Since so many different combinations of levels are possible, a
	 * user may desire to have a note of the purpose of the level. Especially when multiple variations of one
	 * level occur (4a, 4b, 5t, 9r), it's recommended to define the purpose of the level.
	 * An example is if two users makes up an entire position in the company and instead of having one act as
	 * a supervisor, they can both be lower-level employees and respond to a company manager, but have one
	 * be able to create the schedules while not having any more abilities.
	 * Another example is having one employee or supervisor user be able to make reports. Sometimes managers
	 * may want to designate their work to another employee. Description could be "Report-Generating Employee of Level 0". */
	private String description									= null;
	
	private PermissionLevel(Permissions p, int level, char version)
	{
		this(p,level,version,null);
	}
	
	private PermissionLevel(Permissions p, int level, char version, String description)
	{
		this.level_permissions = p;
		this.level = level;
		this.version = version;
		this.description = description;
	}
	
	/**
	 * Returns a permission level based on the most restrictive default level,
	 * also referred to as a template or blank permission level as no changes have
	 * been made that should negatively affect the functioning of the system.
	 * @return
	 */
	protected static PermissionLevel getDefault()
	{
		return new PermissionLevel(new Permissions(), 0, ' ');
	}
	
	public Permissions getLevel_permissions() {
		return level_permissions;
	}
	public int getLevel() {
		return level;
	}
	public char getVersion() {
		return version;
	}
	public String getDescription() {
		return description;
	}
	
	protected void setLevel(int i)
	{
		this.level = i;
	}
	
	protected void setVersion(char c)
	{
		this.version = c;
	}
	
	protected void setDescription(String s)
	{
		this.description = s;
	}
	
	protected void invalidatePermission()
	{
		level_permissions = null;
	}
	
	/**
	 * Method that will set the new Permissions for this PermissionLevel object.
	 * In order to re-assign permissions, the level and version must be confirmed
	 * so that the permission level will be overwritten or saved as a different
	 * permission level.
	 * 
	 * @param p Permissions object
	 * @param level int level (0-99)
	 * @param version char version (blank as default, or 'a' to 'z')
	 */
	protected void setPermission(Permissions p,int level, char version)
	{
		if(level_permissions == null) this.level_permissions = p;
	}
	
}
