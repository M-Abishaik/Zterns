package com.zilker.delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zilker.bean.Login;
import com.zilker.bean.User;
import com.zilker.dao.UserDAO;

@Service
public class UserDelegate {
	
	@Autowired
	UserDAO userDAO;
	
	
	public void register(User user) {
		
		try {
			userDAO.register(user);
		}catch(Exception exception) {
			
		}
	}
	
	public String login(Login login) {
		
		String role = "";
		try {
			role = userDAO.login(login);
			return role;
		}catch(Exception exception) {
			return "";
		}
		
	}

}
