package com.zilker.servlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zilker.bean.Address;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.SharedDelegate;

/**
 * Servlet implementation class CancelBookingServlet
 */
@WebServlet("/CancelBookingServlet")
public class CancelBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelBookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = null;
		CustomerDelegate customerDelegate = null;
		ArrayList<Address> address = null;
		
		try {
			customerDelegate = new CustomerDelegate();

			address = customerDelegate.displayLocations();
			
			request.setAttribute("addressList", address);
			
			requestDispatcher = request.getRequestDispatcher("./pages/customer.jsp");
			requestDispatcher.forward(request, response);

		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in cancelling the ride.");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean check = false;
		SharedDelegate sharedDelegate = null;
		int bookingID = -1;
		RequestDispatcher requestDispatcher = null;
		CustomerDelegate customerDelegate = null;
		ArrayList<Address> address = null;
		
		try {
			sharedDelegate = new SharedDelegate();
			customerDelegate = new CustomerDelegate();

			bookingID = Integer.parseInt(request.getParameter("travelInvoiceBookingID"));
			
			check = sharedDelegate.cancelRide(bookingID);

			address = customerDelegate.displayLocations();
			
			request.setAttribute("addressList", address);
			
			requestDispatcher = request.getRequestDispatcher("./pages/customer.jsp");
			requestDispatcher.forward(request, response);

		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in cancelling the ride.");
		}
		
	}

}
