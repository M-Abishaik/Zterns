package com.zilker.servlet;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zilker.constants.Constants;

import com.zilker.delegate.SharedDelegate;
import com.zilker.bean.User;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationServlet() {
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
		String userName = "";
		String contact = "";
		String mail = "";
		String userRole = "";
		String password = "";
		String rePassword = "";
		String address = "";
		String zipCode = "";
		SharedDelegate sharedDelegate = null;
		String registerResponse = "";
		RequestDispatcher requestDispatcher = null;
		User user = null;
		
		try {
			sharedDelegate = new SharedDelegate();
			user = new User();
			
			userName = request.getParameter("registerUsername");
			contact = request.getParameter("registerContact");
			
			mail = request.getParameter("registerMail");
			mail.toLowerCase();
			
			userRole = request.getParameter("registerRole");
			password = request.getParameter("registerPassword");
			rePassword = request.getParameter("registerRePassword");
			
			address = request.getParameter("registerAddress");
			address = address.toLowerCase();
			
			zipCode = request.getParameter("registerZipcode");
			
			user = new User(userName, mail, contact, userRole, password, address, zipCode);
			registerResponse = sharedDelegate.register(user);

			if(registerResponse.equals(Constants.SUCCESS)) {
				
				if(userRole.equals(Constants.CUSTOMER)) {
					
					requestDispatcher = request.getRequestDispatcher("./pages/customer.jsp");
					requestDispatcher.forward(request, response);
					
				} else if(userRole.equals(Constants.DRIVER)) {
					
				} else if(userRole.equals(Constants.ADMIN)) {
					
				}
			
			} else {
				response.sendRedirect("index.jsp");
			}
	
		}catch(Exception exception) {
			response.sendRedirect("index.jsp");
		}
	}

}
