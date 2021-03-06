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

import com.zilker.bean.BookingResponse;
import com.zilker.delegate.SharedDelegate;

/**
 * Servlet implementation class OnGoingRidesServlet
 */
@WebServlet("/OnGoingRidesServlet")
public class OnGoingRidesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OnGoingRidesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookingResponse bookingResponse = null;
		SharedDelegate sharedDelegate = null;
		HttpSession session = null;
		RequestDispatcher requestDispatcher = null;
		String userPhone = "";
		
		try {
			sharedDelegate = new SharedDelegate();
			session = request.getSession();
			userPhone = (String)session.getAttribute("userPhone");
			
			//userPhone = "8888888888";
			bookingResponse = sharedDelegate.displayBookingDetails(userPhone, 0);
			
			request.setAttribute("onGoingResponse", bookingResponse);
			
			requestDispatcher = request.getRequestDispatcher("./pages/myTrips-customer.jsp");
			requestDispatcher.forward(request, response);
			
		}catch(Exception exception) {
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
