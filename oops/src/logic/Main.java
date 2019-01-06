package logic;

import java.util.logging.Level; 
import java.util.logging.Logger;

import user.User;

import java.util.Scanner;

/*
 *	Inputs the type of login from the user  
 */

public class Main{
			
	private final static Logger LOGGER =  
            Logger.getLogger(Main.class.getName()); 
	
	public static void main(String args[]) {
			
			Scanner scanner = new Scanner(System.in);
			LOGGER.log(Level.INFO, "Login methods:" + "\n" + "1. Google." + "\n" + "2. Facebook." + "\n" + "3. Linkedin.");
			LOGGER.log(Level.INFO, "Please choose a method.");
			
			int choice = scanner.nextInt();
						
			String userMail = scanner.next();
			String userPass = scanner.next();
						
			User user = new User(userMail, userPass);
			
			switch(choice) {
				case 1: Google google = new Google();
						google.login(userMail, userPass);
						break;
				case 2: Facebook facebook = new Facebook();
						facebook.login(userMail, userPass);
						break;
				case 3: Linkedin linkedin = new Linkedin();
						linkedin.login(userMail, userPass);
						break;
				default : LOGGER.log(Level.INFO, "Invalid choice");
						break;
			}				
	}
}
