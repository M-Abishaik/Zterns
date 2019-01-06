									/* PROGRAM 1 */
import java.util.*;
import java.io.*;

class concat{
       	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int tc = sc.nextInt();

		while(tc-->0){
			int i,n;
			n = sc.nextInt();
			StringBuffer sbr = new StringBuffer("");


			for(i=0;i<n;i++){
				String str = sc.nextLine();
				sbr.append(str);
				if(i<n)
					sbr.append("\n");
			}

			System.out.println();
			System.out.println("Output");
			System.out.println(sbr.toString());
		}
	}
}
					
