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

import com.zilker.bean.BookingResponse;
import com.zilker.constants.Constants;
import com.zilker.delegate.DriverDelegate;
import com.zilker.delegate.SharedDelegate;

/**
 * Servlet implementation class CompleteRideServlet
 */
@WebServlet("/CompleteRideServlet")
public class CompleteRideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompleteRideServlet() {
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
		ArrayList<BookingResponse> completeList = null;
		
		HttpSession session = null;
		DriverDelegate driverDelegate = null;
		SharedDelegate sharedDelegate = null;
		String userPhone = "";
		String rideCompleteResponse = "";
		int bookingID = -1;
		int driverID = -1;
		boolean check = false;
		
		try {
			completeList = new ArrayList<BookingResponse>();
			session = request.getSession();
			driverDelegate = new DriverDelegate();
			sharedDelegate = new SharedDelegate();

			userPhone = (String)session.getAttribute("userPhone");

			bookingID = Integer.parseInt(request.getParameter("bookingID"));
						
			driverID = sharedDelegate.getUserID(userPhone);
			
			check = sharedDelegate.checkBookingExists(bookingID, driverID);
			
			if(check==true) {
				
				rideCompleteResponse = driverDelegate.completeRide(bookingID, driverID);
				
				if(rideCompleteResponse.equals(Constants.SUCCESS)) {
					LOGGER.log(Level.INFO, "Location successfully updated.");
					
					completeList = sharedDelegate.displayCompletedRides(userPhone, 1);
					
					request.setAttribute("onCompleteResponse", completeList);
					
					requestDispatcher = request.getRequestDispatcher("./pages/myTrips-driver.jsp");
					requestDispatcher.forward(request, response);
					
				} 
				
				LOGGER.log(Level.INFO, "Ride completed successfully.");
				
			}else {
				LOGGER.log(Level.INFO, "Error in updating location.");

			}
			
		} catch(Exception exception) {
			
		}
		
		
	}

}
