package com.zilker.servlet;

import java.io.IOException;


import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zilker.delegate.SharedDelegate;
import com.zilker.bean.Address;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.constants.Constants;

import java.util.ArrayList;
import java.util.logging.Level;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userPhone = "";
		String userPassword = "";
		SharedDelegate sharedDelegate = null;
		CustomerDelegate customerDelegate = null;
		ArrayList<Address> address = null;
		String loginResponse = "";
		RequestDispatcher requestDispatcher = null;
		
		
		HttpSession session = null;
		
		try {
			session = request.getSession();
			customerDelegate = new CustomerDelegate();
			sharedDelegate = new SharedDelegate();
			userPhone = request.getParameter("loginMobile");
			userPassword = request.getParameter("loginPassword");
			
									
			loginResponse = sharedDelegate.login(userPhone, userPassword);
						
			if(!loginResponse.equals(Constants.FAILURE)) {
				session.setAttribute("userPhone", userPhone);
				if(loginResponse.equals(Constants.CUSTOMER)) {
					
					address = customerDelegate.displayLocations();
										
					request.setAttribute("addressList", address);
					
					
					requestDispatcher = request.getRequestDispatcher("./pages/customer.jsp");
					requestDispatcher.forward(request, response);
					
				} else if(loginResponse.equals(Constants.DRIVER)) {
					
					requestDispatcher = request.getRequestDispatcher("./pages/driver.jsp");
					requestDispatcher.forward(request, response);
					
				} else if(loginResponse.equals(Constants.ADMIN)) {
					
				}
				
			} else {
				LOGGER.log(Level.INFO,"Invalid login credentials.");
				request.setAttribute("errorMessage", "Invalid login credentials.");	
				response.sendRedirect("index.jsp");
				
			}
			
		} catch(Exception exception) {
			LOGGER.log(Level.WARNING,"Exception in login credentials.");
			request.setAttribute("errorMessage", "Invalid login credentials.");	
			response.sendRedirect("index.jsp");
		}
		
	}

}
