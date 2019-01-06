									/* CURRENCY FORMATS */
import java.util.*;
import java.io.*;
import java.text.*;

class Currency {
	public static void main (String args[]) {
			Scanner scanner = new Scanner(System.in);
			int testCases = scanner.nextInt();

			while(testCases-->0) {
				String amount = scanner.next();
				float cost = Float.parseFloat(amount);

				System.out.println("US format");
				System.out.println(NumberFormat.getCurrencyInstance(new Locale("en","US")).format(cost));

				System.out.println("German format");
				System.out.println(NumberFormat.getCurrencyInstance(new Locale("de","LU")).format(cost));

				System.out.println("Indian format");
                                System.out.println(NumberFormat.getCurrencyInstance(new Locale("en","IN")).format(cost));

				System.out.println("France format");
                                System.out.println(NumberFormat.getCurrencyInstance(new Locale("fr","FR")).format(cost));

				System.out.println("Italian format");
                                System.out.println(NumberFormat.getCurrencyInstance(new Locale("it","IT")).format(cost));
			}
	}
}


