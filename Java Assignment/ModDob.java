									/* MODIFIED DOB FORMAT */
import java.util.*;
import java.io.*;

class ModDob {

	static int dayOfWeek(int d, int m, int y) { 
    		int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 }; 
    		y -= (m < 3) ? 1 : 0; 
    		return ( y + y/4 - y/100 + y/400 + t[m-1] + d) % 7; 
	} 

	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int testCases = scanner.nextInt();

		while(testCases-->0) {
			String dob = scanner.next();
			StringBuffer sbr = new StringBuffer("");

			int i,stringLen = dob.length();
			int index = 0, ddmmyyyy[] = new int[3];

			for(i=0;i<stringLen;i++) {
				char ch = dob.charAt(i);
				
				if(ch!='-')
					sbr.append(ch);
				
				if(ch=='-' || i==(stringLen-1)) {
					ddmmyyyy[index++] = Integer.parseInt(sbr.toString());
					sbr.setLength(0);
					continue;
				}
			}

			int day = dayOfWeek(ddmmyyyy[0], ddmmyyyy[1], ddmmyyyy[2]);
			String str = "";

			switch(day) {
				case 1: str = "Monday";
					break;
				case 2: str = "Tuesday";
                                        break;
				case 3: str = "Wednesday";
                                        break;
				case 4: str = "Thursday";
                                        break;
				case 5: str = "Friday";
                                        break;
				case 6: str = "Saturday";
                                        break;
				case 7: str = "Sunday";
                                        break;
				default:System.out.println("Invalid dob");
					break;
			}

			System.out.println("Current Date: " + str + " " + ddmmyyyy[2] + "." + ddmmyyyy[1] + "." + ddmmyyyy[0]);
		}
	}
}
