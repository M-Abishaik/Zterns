								/* HTML VALIDATOR USING REGEX */
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class HtmlRegex {
	
	private static Pattern pattern;
	private static Matcher matcher;
	//private static final String HtmlPattern = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";
	private static final String HtmlPattern = "<.*?>([^<]+)</.*?>";
       	
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int testCases = scanner.nextInt();
		scanner.nextLine();

		while(testCases-->0) {
			String input = scanner.nextLine();
			boolean valid = validate(input);
			if(valid==true)
				System.out.println("Valid tag");
			else
				System.out.println("Invalid tag");
		}
	}

	public static boolean validate(String input) {
		pattern = Pattern.compile(HtmlPattern);
		matcher = pattern.matcher(input);
	  	return matcher.matches();
	}
}
       			       
