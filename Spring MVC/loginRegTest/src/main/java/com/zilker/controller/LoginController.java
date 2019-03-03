package com.zilker.controller;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zilker.bean.Login;
import com.zilker.delegate.UserDelegate;

@Controller
public class LoginController {
	
	@Autowired
	UserDelegate userDelegate;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	@RequestMapping(value = "/loginUser", method = RequestMethod.GET)
	  public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView("Login");
	    mav.addObject("login", new Login());
	    return mav;
	  }
	

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	 public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, 
			 @ModelAttribute("login") Login login) {
			  		 
			String role = "";
		 		try {
		 			role = userDelegate.login(login);
		 			if(!role.equals("")) {
		 				return new ModelAndView("welcome", "userName", role);
		 			}
		 			
					LOGGER.log(Level.WARNING, "Invalid user.");	
		 			return null;
		 		}catch(Exception exception) {
		 			return null;
		 		}
	}

}
