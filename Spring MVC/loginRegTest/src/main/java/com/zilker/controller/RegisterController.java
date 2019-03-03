package com.zilker.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zilker.bean.User;
import com.zilker.delegate.UserDelegate;

@Controller
public class RegisterController {
	
	@Autowired
	UserDelegate userDelegate;
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.GET)
	  public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView("Register");
	    mav.addObject("user", new User());
	    return mav;
	  }
	

	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	 public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response, 
			 @ModelAttribute("user") User user) {
			  		 		
		 		try {
		 			userDelegate.register(user);
				  	return new ModelAndView("welcome", "userName", user.getUsername());
		 			
		 			
		 		}catch(Exception exception) {
		 			return null;
		 		}
	}
}
