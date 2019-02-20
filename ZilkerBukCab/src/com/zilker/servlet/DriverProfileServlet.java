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
import javax.servlet.http.HttpSession;

import com.zilker.bean.Address;
import com.zilker.bean.UpdateProfile;
import com.zilker.bean.User;
import com.zilker.constants.Constants;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.SharedDelegate;

/**
 * Servlet implementation class DriverProfileServlet
 */
@WebServlet("/DriverProfileServlet")
public class DriverProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriverProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = null;
		SharedDelegate sharedDelegate = null;
		HttpSession session = null;
		String userPhone = "";
		RequestDispatcher requestDispatcher = null;

		
		try {
			sharedDelegate = new SharedDelegate();
			session = request.getSession();
			userPhone = (String)session.getAttribute("userPhone");
			
			user = sharedDelegate.displayProfile(userPhone);
			request.setAttribute("userProfile", user);
			
			requestDispatcher = request.getRequestDispatcher("./pages/driver.jsp");
			requestDispatcher.forward(request, response);

		} catch(Exception exception) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = "";
		String address = "";
		String zipCode = "";
		String password = "";
		String phone = "";
		SharedDelegate sharedDelegate = null;
		HttpSession session = null;
		UpdateProfile updateProfile = null;
		String updateResponse = "";
		RequestDispatcher requestDispatcher = null;
		ArrayList<Address> addressList = null;
		CustomerDelegate customerDelegate = null;

		try {
			sharedDelegate = new SharedDelegate();
			updateProfile = new UpdateProfile();
			mail = request.getParameter("email");
			address = request.getParameter("address");
			zipCode  = request.getParameter("zipCode");
			password = request.getParameter("password");
			session = request.getSession();
			customerDelegate = new CustomerDelegate();

			addressList = customerDelegate.displayLocations();
			
			request.setAttribute("addressList", addressList);
			phone = (String)session.getAttribute("userPhone");
			
			updateProfile = new UpdateProfile(mail, address, zipCode, password, phone);
			
			updateResponse = sharedDelegate.updateProfile(updateProfile);

			if (updateResponse.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Account successfully updated.");
				requestDispatcher = request.getRequestDispatcher("./pages/driver.jsp");
				requestDispatcher.forward(request, response);
			}
			

		}catch(Exception exception) {
			
		}
	}

}
