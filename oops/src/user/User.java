package user;

/*
 *	User bean class.
 */

public class User {
	private String userMail;
	private String userPassword;
	
	public User(String userMail, String userPassword) {
		this.userMail = userMail;
		this.userPassword = userPassword;
	}
	
	public void setUserMail(String mail) {
		this.userMail = mail;
	}
	
	public void setUserPassword(String pass) {
		this.userPassword = pass;
	}
	
	public String getUserMail() {
		return userMail;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
}
