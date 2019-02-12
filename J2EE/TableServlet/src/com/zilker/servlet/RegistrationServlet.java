package com.zilker.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zilker.bean.User;
import com.zilker.delegate.UserDelegate;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
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
		String name = "";
		String mail = "";
		String contact = "";
		String role = "";
		String password = "";
		User user = null;
		UserDelegate userDelegate = null;
		boolean check = false;
		ArrayList<User> userList = null;
		
		try {
			name = request.getParameter("Name"); 
			mail = request.getParameter("Email");
			contact = request.getParameter("Contact");
			role = request.getParameter("Role");
			password = request.getParameter("Password");
			
			userList = new ArrayList<User>();
			user = new User(name, mail, contact, role, password);
			userDelegate = new UserDelegate();
			
			check = userDelegate.registerUser(user);
			if(check==true) {
				userList = userDelegate.fetchDetails();				
				request.setAttribute("name", name);
				request.setAttribute("userList", userList);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("./pages/displayList.jsp");
				requestDispatcher.forward(request, response);
			}else {
				response.sendRedirect("./pages/error.jsp");
			}
			
			
		} catch(Exception e) {
			
		}
	}
	
	/*
	 * Fetches user profile.
	 */
	
	public ArrayList<User> fetchDetails() {
		ArrayList<User> user = null;
		UserDelegate userDelegate = null;
		
		try {
			user = new ArrayList<User>();
			userDelegate = new UserDelegate();
			
			user = userDelegate.fetchDetails();
			return user;
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error fetching details from Delegate");
			return null;
		}
	}

}
