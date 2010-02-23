/**
 * application.dbrequests - DbCall.java
 */
package oldClasses.application.dbrequests;

/**
 * @author dann
 * @version 0.01.00
 * @license GPL 2
 * 
 * Abstract class to specifiy database request statements in a uniformed and consistent manner.
 * Is the parent class for all other Cached Broker classes. Used for modularity. Contains
 * variables that prevent hardcoding of sql statements.
 * 
 */
public abstract class DbCall {

	public static final String DATABASE_NAME = "`WebAgenda`.";
	public static final String SELECT_COM = "SELECT ";
	public static final String UPDATE_COM = "UPDATE ";
	public static final String DELETE_COM = "DELETE ";
	public static final String INSERT_COM = "INSERT ";
	public static final String WHERE_COM = "WHERE ";
	public static final String FROM_COM = "FROM ";
	
	public String returnSelect(String[] columns, String[] tables, String[] where)
	{
		StringBuilder sb = new StringBuilder(100);
		sb.append(SELECT_COM);
		sb = getMultipleItemsInForm(sb, columns);
		sb.append(FROM_COM);
		sb = getMultipleItemsInForm(sb, tables);
		if(where != null)
		{
			sb.append(WHERE_COM);
			sb = getMultipleItemsInForm(sb, where);
		}
		return sb.toString();
		
	}
	
	/**
	 * Private method that parses through all string arrays for possible values for
	 * each select, from, and where clause.
	 * 
	 * @param sb String Builder that makes the sql statement
	 * @param loop_through String[] that contains strings to add
	 * @return StringBuilder with the appropriate strings appended to sql statement
	 */
	private StringBuilder getMultipleItemsInForm(StringBuilder sb, String[] loop_through)
	{
		if(loop_through == null) return sb;
		
		for(int i = 0; i < loop_through.length; i++)
		{
			if(i < loop_through.length - 1)
				sb.append(loop_through[i] + ", ");
			else sb.append(loop_through[i] + " ");
		}
		return sb;
	}
}
