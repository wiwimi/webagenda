/**
 * 
 */
package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author dann
 *
 */
public class Test {

	public static void main(String[] args)
	{
		Connection conn = null;
		try {	
			String name = JOptionPane.showInputDialog("Enter db name");
			String pass = JOptionPane.showInputDialog("Enter password");		
			String url = "jdbc:mysql://localhost/webagenda";
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	        conn = DriverManager.getConnection (url, name, pass);
	        System.out.println ("Database connection established");
		}
		catch(Exception E) {
			E.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
