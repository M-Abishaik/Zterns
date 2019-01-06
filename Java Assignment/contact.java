									/* THIRD QUESTION */
import java.util.*;
import java.io.*;

class contact{
	private	String name, mail, number;

	public contact(String name, String mail, String number){
		this.name = name;
		this.mail = mail;
		this.number = number;
	}

	@Override
  	public boolean equals(Object otherObject) {

    	if (this == otherObject) 
      		return true;
   
    	if (otherObject instanceof contact) {
      		contact that = (contact) otherObject;
		
      		return (name == null && that.name == null)
          		|| name.equals(that.name);
    	}
    	return false;
  	}

	public static void main(String args[]){
			Scanner sc = new Scanner(System.in);
			
			String name1 = sc.nextLine();
			String mail1 = sc.nextLine();
			String number1 = sc.nextLine();

			contact obj1 = new contact(name1,mail1,number1);

			String name2 = sc.nextLine();
                        String mail2 = sc.nextLine();
                        String number2 = sc.nextLine();

                        contact obj2 = new contact(name2,mail2,number2);

			if(obj1.equals(obj2))
				System.out.println("Equal");
			else
				System.out.println("Inequal");
	}
}

				
