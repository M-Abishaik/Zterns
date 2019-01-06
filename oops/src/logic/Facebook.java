package logic;

import java.util.logging.Level;
import java.util.logging.Logger;

import loginInterface.Login;
import user.Constants;

/*
 * Facebook login functionality
 */

public class Facebook implements Login{
	
	public Facebook() {}
	
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	public void login(String mailId, String password) {
		LOGGER.log(Level.INFO, "Facebook login");
		
		if(mailId.equals(Constants.MAIL) && password.equals(Constants.PASSWORD))
			LOGGER.log(Level.INFO, "Successful login");
		else
			LOGGER.log(Level.INFO, "Invalid credentials");	
		
	}

}
