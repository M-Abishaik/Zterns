									/* PASSWORD CHECKER */
import java.util.*;
import java.util.regex.*;
import java.io.*;

class passcheck {
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		int tc = sc.nextInt();

		while(tc-->0){
			String pass = sc.next();
			String pattern  = "[a-zA-Z]{0,}[-!@_$]{0,}[a-zA-Z]{2,}[0-9]{1,}";
                        Pattern pat = Pattern.compile(pattern);
                        Matcher mat = pat.matcher(pass);

			if((pass.length()>=8 && pass.length()<=15) && mat.matches()) {
				int finalCheck=0, upperCheck=0, lowerCheck=0, count=0, rule1=0, rule2=0, rule3=0, i, length = pass.length();
					
				for(i=0;i<length;i++) {
					char ch = pass.charAt(i);
					if(Character.isDigit(ch))
						count++;
					else if(Character.isUpperCase(ch))
						upperCheck=1;
					else if(Character.isLowerCase(ch))
                                                lowerCheck=1;
					else if(ch=='-' || ch=='!' || ch=='$' || ch=='@' || ch=='_')
						finalCheck=1;
				}

				if(count>=3)
					rule1=1;
				if(upperCheck==1 && lowerCheck==1)
					rule3=1;
				if(finalCheck==1)
					rule2=1;

				int sum = rule1+rule2+rule2;
				if(sum==3)
					System.out.println("Strong");
				else if(sum==2)
					System.out.println("Moderate");
				else if(sum==1)
					System.out.println("Good");
				else
					System.out.println("Weak");
			}
			else
				System.out.println("false");
		}
	}
}
