package com.zilker.contact.jdbc;

import com.zilker.contact.dao.*;
import com.zilker.contact.constants.*;
import com.zilker.contact.beanClass.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Gets the various user details.
 */

public class User_Detail {
	
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private static Scanner scanner = new Scanner(System.in);
	
	public User_Detail() {}
	
	public static void main(String[] args) {
		
		int choice=0;
	    User_Detail userDetail = new User_Detail();
	    	
	    do {
	    	LOGGER.log(Level.INFO, "Contact Application");
	    	LOGGER.log(Level.INFO,"1. Insert Details" + "\n" + "2. Display Details" + "\n" + "3. Update Details" + "\n" + "4. Delete Details" + "\n" + "5. Exit");
	    	LOGGER.log(Level.INFO, "Enter your choice: ");
	    	
	    	choice = scanner.nextInt();
	    		    	
	    	switch(choice) {
	    		case 1: userDetail.insertDetails();
	    				break;
	    		case 2: userDetail.displayDetails();
	    				break;
	   			case 3: userDetail.updateDetails();
	   					break;
	   			case 4: userDetail.deleteDetails();
	   					break;
    			case 5: System.exit(0);
	    	}
	   	}while(choice!=5);
	   } 
	
	public void insertDetails() {
		
		try {
			ContactDAO contactDAO = new ContactDAO();
			boolean check = false;
			int mobileExtension = 0, officeExtension = 0, areaCode=0, countryCode=0;
			long mobileNumber = 0, officeNumber = 0, homeNumber = 0;
			String mobileNum = "", officeNum = "", homeNum = "";
			
			scanner.nextLine();

			LOGGER.log(Level.INFO, "Enter first name: ");
			String firstName = scanner.nextLine();
			
			LOGGER.log(Level.INFO, "Enter Last name: ");
			String lastName = scanner.nextLine();
			
			if(firstName.isEmpty() || lastName.isEmpty()) {
				LOGGER.log(Level.SEVERE, "First name and Last name details are mandatory");
			} 
			
			LOGGER.log(Level.INFO, "Enter your mail: ");
			String mail = scanner.next();
			
			if(mail.isEmpty()) {
				LOGGER.log(Level.SEVERE, "Email is mandatory");
			}
			
			Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX .matcher(mail);
    		if(matcher.find()==false) {
    			LOGGER.log(Level.INFO, "Invalid e-mail.");
    		}
			
    		Contact_Detail contactDetail = new Contact_Detail(firstName, lastName, mail);
        	contactDAO.insertPersonalDetails(contactDetail);
        	
	
			LOGGER.log(Level.INFO, "Do you want to add mobile details?(y/n)");
			char choice = scanner.next().charAt(0);
			
			if(choice == 'Y' || choice == 'y'){
				LOGGER.log(Level.INFO, "How many mobile numbers do you wish to save?");
				int numbers = scanner.nextInt();
				for(int i=0;i<numbers;i++){		
					do{
						LOGGER.log(Level.INFO, "Enter your mobile extension: ");
						String mobileex = scanner.next();	
						
						matcher = Constants.VALID_MOBILE_EXTENSION_REGEX.matcher(mobileex);
						if(matcher.find() == true) {
							mobileExtension = Integer.parseInt(mobileex);
							check = true;
							break;
						}
						else {
							LOGGER.log(Level.SEVERE, "Invalid mobile extension. Enter a valid one");
						}
						}while(check != true);
					
				check = false;
			
					do{
						LOGGER.log(Level.INFO, "Enter a 10 digit Mobile number: ");
						mobileNum = scanner.next();
					
						if (mobileNum.length()==10){
							check = true;
							break;
						}
						else{
							LOGGER.log(Level.SEVERE, "Invalid mobile number. Enter a valid one");
						}
						}while(check != true);
					
				check = false;
					
					Mobile_Detail mobileDetail = new Mobile_Detail(mobileNum, mobileExtension, mail);
		        	contactDAO.insertMobileDetails(mobileDetail);					
				}
			}
			
			check = false;
			LOGGER.log(Level.INFO, "Do you want to add office details?(y/n)");
			choice = scanner.next().charAt(0);
			
			if(choice == 'Y' || choice == 'y'){
				LOGGER.log(Level.INFO, "How many office numbers do you wish to save?");
				int numbers = scanner.nextInt();
				for(int i=0;i<numbers;i++){
					
					do{
						LOGGER.log(Level.INFO, "Enter your office extension: ");
						String officeex = scanner.next();
						
						matcher = Constants.VALID_MOBILE_EXTENSION_REGEX.matcher(officeex);
						if(matcher.find() == true) {
							officeExtension = Integer.parseInt(officeex);
							check = true;
							break;
						}
						else {
							LOGGER.log(Level.SEVERE, "Invalid office extension. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
				
					do{
						LOGGER.log(Level.INFO, "Enter a 10 digit office number: ");
						officeNum = scanner.next();
						
						if (officeNum.length()==10){
							check = true;
							break;
						}
						else{
							LOGGER.log(Level.SEVERE, "Invalid office number. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
				
					Office_Detail officeDetail = new Office_Detail(officeNum, officeExtension, mail);
		        	contactDAO.insertOfficeDetails(officeDetail); 
				}
			}
			
			check = false;
			LOGGER.log(Level.INFO, "Do you want to add home details?(y/n)");
			choice = scanner.next().charAt(0);
			
			if(choice == 'Y' || choice == 'y'){
				LOGGER.log(Level.INFO, "How many home numbers do you wish to save?");
				int numbers = scanner.nextInt();
				for(int i=0;i<numbers;i++){
									
					do{
						LOGGER.log(Level.INFO, "Enter a 10 digit home number: ");
						homeNum = scanner.next();
						
						if (homeNum.length()==10){
							check = true;
							break;
						}
						else{
							LOGGER.log(Level.SEVERE, "Invalid home number. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
					
					do{
						LOGGER.log(Level.INFO, "Enter your area code: ");
						String aCode = scanner.next();
						
						matcher = Constants.VALID_AREA_CODE.matcher(aCode);
						if(matcher.find() == true) {
							areaCode = Integer.parseInt(aCode);
							check = true;
							break;
						}
						else {
							LOGGER.log(Level.SEVERE, "Invalid area code. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
					
					do{
						LOGGER.log(Level.INFO, "Enter your country code: ");
						String cCode = scanner.next();
						
						matcher = Constants.VALID_COUNTRY_CODE.matcher(cCode);
						if(matcher.find() == true) {
							countryCode = Integer.parseInt(cCode);
							check = true;
							break;
						}
						else {
							LOGGER.log(Level.SEVERE, "Invalid country code. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
					
					Home_Detail homeDetail = new Home_Detail(homeNum, areaCode, countryCode, mail);
		        	contactDAO.insertHomeDetails(homeDetail);
				}
			}
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} finally {
			LOGGER.log(Level.SEVERE, "Error communicating with the DAO");
		}
	}
	
	public void displayDetails() {
		
		LOGGER.log(Level.INFO, "Please enter your email: ");
		String email = scanner.next();
		
		ContactDAO contactDAO = new ContactDAO();
		contactDAO.displayDetails(email);
	}
	
	public void updateDetails() {
		
		try {
			ContactDAO contactDAO = new ContactDAO();
			boolean check = false;
			int mobileExtension = 0, officeExtension = 0, areaCode=0, countryCode=0;
			long mobileNumber = 0, officeNumber = 0, homeNumber = 0;
			String mobileNum = "", officeNum = "", homeNum = "";
			String oldMobile = "", oldOffice = "", oldHome = "";
			
			LOGGER.log(Level.INFO, "Enter the email of the contact to be updated: ");
			String mail = scanner.next();
			
			if(mail.isEmpty()) {
				LOGGER.log(Level.SEVERE, "Email is mandatory");
			}
			
			Matcher matcher = Constants.VALID_EMAIL_ADDRESS_REGEX .matcher(mail);
    		if(matcher.find()==false) {
    			LOGGER.log(Level.INFO, "Invalid e-mail.");
    		}
			
			scanner.nextLine();

			LOGGER.log(Level.INFO, "Enter first name: ");
			String firstName = scanner.nextLine();
			
			LOGGER.log(Level.INFO, "Enter Last name: ");
			String lastName = scanner.nextLine();
			
			if(firstName.isEmpty() || lastName.isEmpty()) {
				LOGGER.log(Level.SEVERE, "First name and Last name details are mandatory");
			} 
			
    		Contact_Detail contactDetail = new Contact_Detail(firstName, lastName, mail);
        	contactDAO.updatePersonalDetails(contactDetail);
        	
        	
        	LOGGER.log(Level.INFO, "Do you want to edit mobile details?(y/n)");
			char choice = scanner.next().charAt(0);
			
			if(choice == 'Y' || choice == 'y'){
				LOGGER.log(Level.INFO, "How many mobile numbers do you wish to edit?");
				int numbers = scanner.nextInt();
				for(int i=0;i<numbers;i++){		
					do{
						LOGGER.log(Level.INFO, "Enter the mobile number you wish to edit: ");
						oldMobile = scanner.next();
						
						
						LOGGER.log(Level.INFO, "Enter your mobile extension: ");
						String mobileex = scanner.next();	
						
						matcher = Constants.VALID_MOBILE_EXTENSION_REGEX.matcher(mobileex);
						if(matcher.find() == true) {
							mobileExtension = Integer.parseInt(mobileex);
							check = true;
							break;
						}
						else {
							LOGGER.log(Level.SEVERE, "Invalid mobile extension. Enter a valid one");
						}
						}while(check != true);
					
				check = false;
			
					do{
						LOGGER.log(Level.INFO, "Enter a 10 digit Mobile number: ");
						mobileNum = scanner.next();
					
						if (mobileNum.length()==10){
							check = true;
							break;
						}
						else{
							LOGGER.log(Level.SEVERE, "Invalid mobile number. Enter a valid one");
						}
						}while(check != true);
					
				check = false;
					
					Mobile_Detail mobileDetail = new Mobile_Detail(mobileNum, mobileExtension, mail);
		        	contactDAO.updateMobileDetails(mobileDetail, oldMobile);					
				}
			}
			
			check = false;
			LOGGER.log(Level.INFO, "Do you want to edit office details?(y/n)");
			choice = scanner.next().charAt(0);
			
			if(choice == 'Y' || choice == 'y'){
				LOGGER.log(Level.INFO, "How many office numbers do you wish to edit?");
				int numbers = scanner.nextInt();
				for(int i=0;i<numbers;i++){
					
					do{
						
						LOGGER.log(Level.INFO, "Enter the office number you wish to edit: ");
						oldOffice = scanner.next();
						
						LOGGER.log(Level.INFO, "Enter your office extension: ");
						String officeex = scanner.next();
						
						matcher = Constants.VALID_MOBILE_EXTENSION_REGEX.matcher(officeex);
						if(matcher.find() == true) {
							officeExtension = Integer.parseInt(officeex);
							check = true;
							break;
						}
						else {
							LOGGER.log(Level.SEVERE, "Invalid office extension. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
				
					do{
						LOGGER.log(Level.INFO, "Enter a 10 digit office number: ");
						officeNum = scanner.next();
						
						if (officeNum.length()==10){
							check = true;
							break;
						}
						else{
							LOGGER.log(Level.SEVERE, "Invalid office number. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
				
					Office_Detail officeDetail = new Office_Detail(officeNum, officeExtension, mail);
		        	contactDAO.updateOfficeDetails(officeDetail, oldOffice); 
				}
			}
			
			check = false;
			LOGGER.log(Level.INFO, "Do you want to edit home details?(y/n)");
			choice = scanner.next().charAt(0);
			
			if(choice == 'Y' || choice == 'y'){
				LOGGER.log(Level.INFO, "How many home numbers do you wish to edit?");
				int numbers = scanner.nextInt();
				for(int i=0;i<numbers;i++){
									
					do{
						
						LOGGER.log(Level.INFO, "Enter the home number you wish to edit: ");
						oldHome = scanner.next();
						
						LOGGER.log(Level.INFO, "Enter a 10 digit home number: ");
						homeNum = scanner.next();
						
						if (homeNum.length()==10){
							check = true;
							break;
						}
						else{
							LOGGER.log(Level.SEVERE, "Invalid home number. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
					
					do{
						LOGGER.log(Level.INFO, "Enter your area code: ");
						String aCode = scanner.next();
						
						matcher = Constants.VALID_AREA_CODE.matcher(aCode);
						if(matcher.find() == true) {
							areaCode = Integer.parseInt(aCode);
							check = true;
							break;
						}
						else {
							LOGGER.log(Level.SEVERE, "Invalid area code. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
					
					do{
						LOGGER.log(Level.INFO, "Enter your country code: ");
						String cCode = scanner.next();
						
						matcher = Constants.VALID_COUNTRY_CODE.matcher(cCode);
						if(matcher.find() == true) {
							countryCode = Integer.parseInt(cCode);
							check = true;
							break;
						}
						else {
							LOGGER.log(Level.SEVERE, "Invalid country code. Enter a valid one");
						}
						}while(check != true);
					
					check = false;
					
					Home_Detail homeDetail = new Home_Detail(homeNum, areaCode, countryCode, mail);
		        	contactDAO.updateHomeDetails(homeDetail, oldHome);
				}
			}
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} finally {
			scanner.close();
		}
		
		
	}
	public void deleteDetails() {
		
		LOGGER.log(Level.INFO, "Please enter your email: ");
		String email = scanner.next();
		
		ContactDAO contactDAO = new ContactDAO();
		contactDAO.deleteDetails(email);		
	}
}