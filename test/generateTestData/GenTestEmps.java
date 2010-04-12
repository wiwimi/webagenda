/**
 * 
 */
package generateTestData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Generate SQL script to insert 517 people including employees, supervisors,
 * managers and a CEO.
 * 
 * @author Daniel Wehr
 * @version 0.1.0
 *
 */
public class GenTestEmps
	{
	
	public GenTestEmps() throws IOException
		{
		System.out.println("Generating employee sql.");
		
		File inCSV = new File("test/gen/testEmps.csv");
		File outSQL = new File("test/gen/testEmps.sql");
		
		if (inCSV.exists())
			System.out.println("File Exists!");
		else
			System.out.println("File not found!");
		
		BufferedReader in = new BufferedReader(new FileReader(inCSV));
		PrintWriter out = new PrintWriter(outSQL);
		
		//Throw away first line of headers.
		in.readLine();
		
		int[] managers = new int[4];
		int[] supervisors = new int[32];
		
		String nextLine = null;
		for (int i = 1; (nextLine = in.readLine()) != null; i++)
			{
			String[] split = nextLine.split(",");
			String unP1 = split[0].toLowerCase().substring(0, (split[0].length() < 5 ? split[0].length() : 5));
			String unP2 = split[1].substring(0, (split[1].length() < 5 ? split[1].length() : 5));
			String username = unP1 + unP2 + i;
			String[] splitBD = split[4].split("/");
			String birthdate = splitBD[2]+"-"+(splitBD[0].length() == 1 ? "0"+splitBD[0] : splitBD[0])+"-"+(splitBD[1].length() == 1 ? "0"+splitBD[1] : splitBD[1]);
			
			/*
			 * insert into `WebAgenda`.`EMPLOYEE` (`empID`, `supID`, `givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) values (12314, NULL, 'Chaney', 'Henson', NULL, 'noorin671@gmail.com', 'user1', 'password', NULL, 'General Manager', 'Mohave Grill', 99, 'a', true, 1);
			 */
			
			if (i == 1)
				{
				out.println("----- INSERT CEO -----");
				out.println(String.format(
						"insert into `WebAgenda`.`EMPLOYEE` " +
						"(`empID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) " +
						"values " +
						"(%d,'%s', '%s', '%s', '%s', '%s', '%s', NULL, 'CEO', 'TestLocation1', 99, 'a', true, 1);",
						i,
						split[0],
						split[1],
						birthdate,
						split[2],
						username,
						split[3]));
				out.println("");
				}//End of create CEO.
			else if (i <= 5)
				{
				if (i == 2)
					out.println("----- INSERT MANAGERS -----");
				
				String location = "TestLocation" + (1 + ((i - 2)*2));
				String position = "Manager" + (i % 2 == 0 ? 1 : 2);
				
				out.println(String.format(
						"insert into `WebAgenda`.`EMPLOYEE` " +
						"(`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) " +
						"values " +
						"(%d,1,'%s', '%s', '%s', '%s', '%s', '%s', NULL, '%s', '%s', 2, 'a', true, 1);",
						i,
						split[0],
						split[1],
						birthdate,
						split[2],
						username,
						split[3],
						position,
						location));
				
				managers[i-2] = i;
				
				if (i == 5)
					out.println("");
				
				}
			else if (i <= 37)
				{
				if (i == 6)
					out.println("----- INSERT SUPERVISORS -----");
				
				//Assign test locations 1-8
				String location = "TestLocation" + ((int)(((i - 5) / 4.01))+1);
				//Assign supervisor position 1-4
				String position = "Supervisor" + (((int)(((i - 5) / 4.01))+1) - (i > 21 ? 4 : 0));
				//Assign supervisor.
				int supervisor = managers[(int)((i - 5)/8.01)];
				
				out.println(String.format(
						"insert into `WebAgenda`.`EMPLOYEE` " +
						"(`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) " +
						"values " +
						"(%d,%d,'%s', '%s', '%s', '%s', '%s', '%s', NULL, '%s', '%s', 2, 'a', true, 1);",
						i,
						supervisor,
						split[0],
						split[1],
						birthdate,
						split[2],
						username,
						split[3],
						position,
						location));
				
				supervisors[i-6] = i;
				
				if (i == 37)
					out.println("");
				}
			else
				{
				if (i == 38)
					out.println("----- INSERT EMPLOYEES --------");
				
				//Assign test locations 1-8
				String location = "TestLocation" + ((int)(((i - 37) / 60.01))+1);
//				System.out.println(location);
				//Assign employee position 1-4
				String position = "TestPosition" + (((int)(((i - 37) / 5.01))+1) - (i > 157 ? 24 : 0) - (i > 277 ? 24 : 0) - (i > 397 ? 24 : 0));
//				System.out.println(position);
				//Assign supervisor.
				int supervisor = supervisors[(int)((i - 37)/15.01)];
//				System.out.println(supervisor);
				
				out.println(String.format(
						"insert into `WebAgenda`.`EMPLOYEE` " +
						"(`empID`,`supID`,`givenName`, `familyName`, `birthDate`, `email`, `username`, `password`, `lastLogin`, `prefPosition`, `prefLocation`, `plevel`, `pversion`, `active`, `passChanged`) " +
						"values " +
						"(%d,%d,'%s', '%s', '%s', '%s', '%s', '%s', NULL, '%s', '%s', 1, 'a', true, 1);",
						i,
						supervisor,
						split[0],
						split[1],
						birthdate,
						split[2],
						username,
						split[3],
						position,
						location));
				
				if (i == 517)
					out.println("");
				}
			
			
			
			}//End of for loop.
		
		in.close();
		out.close();
		}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
		{
		new GenTestEmps();
		}
	
	}
