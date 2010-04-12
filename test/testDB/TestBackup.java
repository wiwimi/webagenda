/**
 * 
 */
package testDB;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import application.Backup;

/**
 * 
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class TestBackup
	{
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
		{
		}
	
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
		{
		}
	
	/**
	 * Test method for {@link application.Backup#backupDB()}.
	 */
	@Test
	public void testBackupDB()
		{
		String newFile = Backup.backupDB();
		
		System.out.println("New backup saved as: "+newFile);
		assertTrue(true);
		}
	
	}
