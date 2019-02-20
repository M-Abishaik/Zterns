package com.zilker.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zilker.constants.Constants;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.DriverDelegate;
import com.zilker.delegate.SharedDelegate;

/**
 * Servlet implementation class AddLicenceServlet
 */
@WebServlet("/AddLicenceServlet")
public class AddLicenceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddLicenceServlet() {
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
		
		RequestDispatcher requestDispatcher = null;
		HttpSession session = null;
		DriverDelegate driverDelegate = null;
		SharedDelegate sharedDelegate = null;
		String licenceNumber = "";
		String pinCode = "";
		String userPhone = "";
		String addLicenceResponse = "";
		int driverID = -1;
		
		try {
			session = request.getSession();
			driverDelegate = new DriverDelegate();
			sharedDelegate = new SharedDelegate();

			userPhone = (String)session.getAttribute("userPhone");

			licenceNumber = request.getParameter("licenceNumber");
			pinCode = request.getParameter("pinCode");
			
			driverID = sharedDelegate.getUserID(userPhone);
			
			addLicenceResponse = driverDelegate.addLicenceDetails(licenceNumber, userPhone, pinCode);
			
			if(addLicenceResponse.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab successfully allocated!");
			} 
			
		} catch(Exception exception) {
			
		}
	}

}
