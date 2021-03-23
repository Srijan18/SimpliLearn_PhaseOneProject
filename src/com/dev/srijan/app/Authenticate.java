package com.dev.srijan.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

//import javax.swing.JOptionPane;

public class Authenticate {
	//dbInput data
	private static Scanner userInput;
	private static Scanner dbInput;
	private static Scanner locker_Input_File;

	//dbOutput data 
	private static PrintWriter dbOutput;
	private static PrintWriter locker_Output_File;
	//model to store data.
	private static UserFormat users;
	private static CredentialsFormat userCredentials;
	private static CredentialsFormat dummyUser;
	
	
	public static void init_func() {
		File  dbFile = new File("UserFile.txt");
		File  lockerFile = new File("CredentialsDataBase.txt");
		
		try {
			//read data from db file
			dbInput = new Scanner(dbFile);
			
			//red data from locker file
			locker_Input_File = new Scanner(lockerFile);
			
			//read data from userInput
			userInput = new Scanner(System.in);
			
			//out put 
			dbOutput = new PrintWriter( new FileWriter(dbFile,true));
			locker_Output_File = new PrintWriter( new FileWriter(lockerFile,true));
			
			users = new UserFormat();
			userCredentials  = new CredentialsFormat();
			dummyUser  = new CredentialsFormat();
			dummyUser.Format();
			
			
		} catch (IOException e) {
			System.out.println("File not Found");
		}
		
	}
	
	
	public static void welcome_Message() {
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		System.out.println("****************************************");
		System.out.println("*        Welcome To LockedMe.com		*");
		System.out.println("*   An App that makes your life easier	*");
		System.out.println("****************************************");
		System.out.println("*   PLEASE PRESS ENETER TO CONTINUE     *");
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		userInput.nextLine();
		
	}
	


	public static void sign_In_Page() throws IOException {
		boolean menu = true;
		while(menu) {
		System.out.println("Please Select A for Login ");
		System.out.println("New User? Select B for Registration ");
		System.out.println("Exit program: X");
		String option = userInput.nextLine();
		switch(option) {
			case "A" :
				login_Page();
				menu = false;
				break;
			case "B" : 
				register_Page();
				menu = false;
				break;
			case "X": menu = false; break;
			default :
				System.out.println("Please select an Option (Case Sensitive)");
				System.out.println("");
				break;
		}
		}
	}
	
	public static void login_Page() throws IOException {
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		System.out.println("****************************************");
		System.out.println();
		System.out.println("*   Hey User, Welcome to LoginPage	 *");
		System.out.println();
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		System.out.println("****************************************");
		System.out.println("Whats you Username ? :");
		String dbInput_Username = userInput.next();
		boolean found = false;
		while(dbInput.hasNext() && !found) {
			if(dbInput.next().equals(dbInput_Username)) {
				System.out.println("Enter Password :");
				String inpPassword = userInput.next();
				if(dbInput.next().equals(inpPassword)) {
					System.out.println("Login Successful, Welcome back" +" " +dbInput_Username);
					found = true;
					locker_Page(dbInput_Username);
					break;
				}
			}
		}
		if(!found) {
			System.out.println("Looks like your details are not available with us");
		}
		
	}

	
	public static void locker_Page(String dbInput_Username) throws IOException{
		//boolean menu = true;
		System.out.println();	
		System.out.println("Press A if you want to see existing credentials ");
		System.out.println("Press B if you want to store new credentials ");
		System.out.println("Press C if you want to delete a credential ");
		System.out.println("Press D if you want to logout ");
		userInput.nextLine();
		String option = userInput.nextLine();
		switch(option) {
			case "A" : 
				fetch_Credentials(dbInput_Username);
				break;
			case "B" :
				save_Credentials(dbInput_Username);
				break;
			case "C":
				locker_Input_File.close();
				locker_Output_File.close();
				deleteCredential(dbInput_Username);
				break;
			case "D":
				init_func();
				sign_In_Page();
				break;
			default :
				System.out.println("Please select an option (Case Sensitive)");
				System.out.println();
				break;
		//locker_Input_File.close();
		}
	}
	
	public static void register_Page() throws IOException {
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		System.out.println("****************************************");
		System.out.println();
		System.out.println("*   Hey User, Welcome to RegistrationPage	 *");
		System.out.println();
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		System.out.println("****************************************");
		
		System.out.println("Please Enter a Suitable Username :");
		String username = userInput.next();
		users.setUsername(username);
		
		System.out.println("Please Enter a Suitable Password (Length 6 or More) :");
		String password = userInput.next();
		while(password.length() < 5) 
		{
			System.out.println("Please Enter a Password with 6 or more characters:");
			password = userInput.next();
		}
		users.setPassword(password);
		
		dbOutput.println(users.getUsername());
		dbOutput.println(users.getPassword());
		File newfile = new File(username+".txt"); 
		newfile.createNewFile();
		System.out.println("Congratulations, You are Successfully registered in our Domain");
		dbOutput.close();
		sign_In_Page();
		
	}
	
	//store credentails
	public static void save_Credentials(String loggedInUser) {
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		System.out.println("****************************************");
		System.out.println("*     Welcome To Your Secure Locker		*");
		System.out.println("*   Your Credentials are safe in Here	*");
		System.out.println("****************************************");
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		
		userCredentials.setLoggedInUser(loggedInUser);
		
		System.out.println("Enter Site Name :");
		String siteName = userInput.next();
		userCredentials.setSiteName(siteName);
		
		System.out.println("Enter Username :");
		String username = userInput.next();
		userCredentials.setUsername(username);
		
		System.out.println("Enter Password :");
		String password = userInput.next();
		userCredentials.setPassword(password);
		
		locker_Output_File.println(userCredentials.getLoggedInUser() + "  " +userCredentials.getSiteName() + "  " +userCredentials.getUsername()+ "  " +userCredentials.getPassword());
		System.out.println("YOUR CREDS ARE STORED AND SECURED!");
		locker_Output_File.close();		
	}
	
	//fetch credentials
	public static void fetch_Credentials(String dbInput_Username) {
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		System.out.println("****************************************");
		System.out.println("*            Secure Locker    		*");
		//System.out.println("*          " + dbInput_Username + "         *");
		System.out.println("*     " + dbInput_Username +", Your Credentials are     *");
		System.out.println("****************************************");
		System.out.println("!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@!-~@");
		System.out.println();
		System.out.println();
		boolean flag = false;
		String siteName;
		
		while(locker_Input_File.hasNext()) {
			if((siteName = locker_Input_File.nextLine()).contains(dbInput_Username)) {
				flag = true;
				System.out.println("-------------------------");
		    	System.out.println("Name  SiteName  Id  Password");
		    	System.out.println("-------------------------");
				System.out.println(siteName);
				System.out.println("=========================");
				System.out.println();
				}
			}
		if (flag == false) 
			{
			System.out.println("Sorry We could not find any related credentials");
			}
		else System.out.println("These were the only credentials found");
	}
	
	public static void deleteCredential(String dbInput_Username) throws IOException {
		
		
		String ID, record;
		
		
		File tempoDB = new File("naldrix_db_temp.txt");
		File realdb = new File("CredentialsDataBase.txt");
		
		
		BufferedReader br = new BufferedReader( new FileReader( realdb ) );
		BufferedWriter bw = new BufferedWriter( new FileWriter( tempoDB ) );
		
		
		System.out.println("Delete Credential Record\n");
		
		System.out.println("Enter the Site ");
		ID =  userInput.nextLine();
		
		
		while( ( record = br.readLine() ) != null ) {
			
			
			if( record.contains(ID) && record.contains(dbInput_Username)) 
				continue;
			
			
			System.out.println(record);
			bw.write(record);
			bw.flush();
			bw.newLine();

		}
		
		br.close();
		bw.close();
		
		boolean check1 = realdb.delete();
		
		boolean check2 = tempoDB.renameTo(realdb);
		
		System.out.println("chechk1 "+ check1 + "check2 " + check2);
		init_func();
		sign_In_Page();

}
//		String line1;
//		String line2;
//		String line3;
//		String line4;
//		String tempFile = "temp.txt";
//		File oldFile = new File("CredentialsDataBase.txt");
//		File newFile = new File(tempFile);
//		System.out.println("Enter Site Name :");
//		String siteName = userInput.next();
//		try {
//			FileWriter fw = new FileWriter(tempFile,true);
//			BufferedWriter bw = new BufferedWriter(fw); 
//			PrintWriter pw = new PrintWriter(bw);
//			Scanner x = new Scanner(oldFile);
//			while (x.hasNext()) {
//				line1 = x.next();
//				if(line1.equals(dbInput_Username)) {
//					line2 = x.next();
//					if(line2.equals(siteName)) {
//						line3 = x.next();
//						line4 = x.next();
//						pw.println(line1);
////						pw.println(line2);
////						pw.println(line3);
////						pw.println(line4);
//						System.out.println("FileSuccessfullyDeleted");
//						
//					}
//				}
//					
//			}
//			x.close();
//			pw.flush();
//			pw.close();
//			oldFile.delete();
//			File dump = new File("CredentialsDataBase.txt");
//			newFile.renameTo(dump);
//			locker_Page(dbInput_Username);
//		}
//		catch(Exception e) {
//			JOptionPane.showMessageDialog(null, "Error");
//		}
		
		
		
//		String line1;
//		String line2;
//		System.out.println("Enter Site Name :");
//		String siteName = userInput.next();
//		while(locker_Input_File.hasNext()) {
//			line1= locker_Input_File.next();
//		if(line1.trim().equals(dbInput_Username) && locker_Input_File.hasNext()) {
//			line2 = locker_Input_File.next();
//			System.out.println("Worked");
//			if(line2.trim().equals(siteName)) {
//				System.out.println("Worked");
//				locker_Output_File.println(dummyUser.getLoggedInUser());
//				locker_Output_File.println(dummyUser.getSiteName());
//				locker_Output_File.println(dummyUser.getUsername());
//				locker_Output_File.println(dummyUser.getPassword());
//				//line4 = locker_Input_File.next();
//			}							
//			}
//		}
//		
//		File dbInputFile = new File("CredentialsDataBase.txt"); 
//		File tempFile = new File("myTempFile.txt"); 
//		 
//		BufferedReader reader = new BufferedReader(new FileReader(dbInputFile)); 
//		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile)); 
//		 
//		System.out.println("Enter Site Name :");
//		String lineToRemove = userInput.next();
//		//String lineToRemove = "type here the line to remove from the text"; 
//		String currentLine; 
//		 
//		while((currentLine = reader.readLine()) != null) { 
//		 
//		// trim newline when comparing with lineToRemove 
//		    String trimmedLine = currentLine.trim(); 
//		    
//		    if(trimmedLine.contains(lineToRemove)) continue; 
//		 
//		    writer.write(currentLine + System.getProperty("line.separator")); 
//		} 
//		 
//		writer.close();  
//		reader.close();  
//		 
//		boolean successful = tempFile.renameTo(dbInputFile); 
//		System.out.println(successful);
//		
//		
//	}
	
	


}
