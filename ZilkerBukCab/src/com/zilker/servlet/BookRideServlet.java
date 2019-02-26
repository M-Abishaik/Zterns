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

import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.SharedDelegate;
import com.zilker.bean.DisplayInvoice;
import com.zilker.bean.TravelInvoice;
import com.zilker.constants.Constants;

/**
 * Servlet implementation class BookRideServlet
 */
@WebServlet("/BookRideServlet")
public class BookRideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookRideServlet() {
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
		
		String startDate = "";
		String startTime = "";
		String source = "";
		String destination = "";
		String numSeats = "";
		
		int cabID = -1;
		int sourceID = -1;
		int destinationID = -1;
		int customerID = -1;
		int driverID = -1;
		int seats = -1;
		int bookingID = -1;
		String cab = "";
		String driver = "";
		String startTimeDate = "";
		String sourceExtract = "";
		String destinationExtract = "";
		String zipCodeSource = "";
		String zipCodeDestination = "";
		String availabilityResponse = "";
		String userPhone = "";
		
		
		CustomerDelegate customerDelegate = null;
		SharedDelegate sharedDelegate = null;
		TravelInvoice travelInvoice = null;
		DisplayInvoice displayInvoice = null;
		RequestDispatcher requestDispatcher = null;
		HttpSession session = null;
		
		try {
			startDate = request.getParameter("startDate");
			startTime = request.getParameter("startTime");
			source  = request.getParameter("source");
			destination = request.getParameter("destination");
			numSeats = request.getParameter("seats");
			session = request.getSession();
			
			userPhone = (String)session.getAttribute("userPhone");

			customerDelegate = new CustomerDelegate();
			sharedDelegate = new SharedDelegate();
			travelInvoice = new TravelInvoice();
			displayInvoice = new DisplayInvoice();
						
			customerID = sharedDelegate.getUserID(userPhone);
			
			seats = Integer.parseInt(numSeats);
			startTimeDate = startDate + " " + startTime;
						
			
			sourceExtract = customerDelegate.extractLocation(source, 0); 
			destinationExtract = customerDelegate.extractLocation(destination, 0);
			
			zipCodeSource = customerDelegate.extractLocation(source, 1);
			zipCodeDestination = customerDelegate.extractLocation(destination, 1);
			
			sourceID = customerDelegate.findLocationID(sourceExtract, zipCodeSource);
			destinationID = customerDelegate.findLocationID(destinationExtract, zipCodeDestination);
			
			cabID = customerDelegate.findNearestCab(zipCodeSource, 0);
			
			if (cabID == (-1)) {
				LOGGER.log(Level.INFO, "No nearest cab found. Please try later.");
				return;
			}
			
			driverID = customerDelegate.getDriverID(cabID);

			availabilityResponse = customerDelegate.checkCabSeats(cabID, seats);

			if (availabilityResponse .equals(Constants.FAILURE)) {
				LOGGER.log(Level.INFO,
						"The cab with the requested number of seats is not yet available. Please try later or wait for some time.");
				return;
			} else {
				LOGGER.log(Level.INFO, "The cab with the requested number of seats is available.");
			}
			
			if (driverID != (-1) && cabID != (-1)) {
				
				cab = sharedDelegate.findCabByID(cabID);
				
				driver = sharedDelegate.findDriverByID(driverID);
				
				displayInvoice = new DisplayInvoice(driver, cab, source, destination, startTimeDate, seats);
				
				travelInvoice = new TravelInvoice(customerID, driverID, cabID, sourceID, destinationID, startTimeDate, 0.0f, 0.0f);

				
				request.setAttribute("travelInvoice", travelInvoice);
				request.setAttribute("displayInvoice", displayInvoice);
				request.setAttribute("source", zipCodeSource);
				request.setAttribute("destination", zipCodeDestination);

				
				requestDispatcher = request.getRequestDispatcher("./pages/confirmRide.jsp");
				requestDispatcher.forward(request, response);
			}
			
			
			/*if (driverID != (-1) && cabID != (-1)) {

				// Computes the estimated finish time and price corresponding to the distance
				// between source and destination.

				travelInvoice = new TravelInvoice(customerID, driverID, cabID, sourceID, destinationID, startTimeDate);

				bookingID = customerDelegate.calculateTravel(travelInvoice, 0);

				LOGGER.log(Level.INFO, "Please note down the booking ID: " + bookingID);

				LOGGER.log(Level.INFO, "Ride successfully booked.");

				cab = sharedDelegate.findCabByID(cabID);

				driver = sharedDelegate.findDriverByID(driverID);

				LOGGER.log(Level.INFO, "Driver and cab details: ");

				LOGGER.log(Level.INFO, driver + "\n" + cab);

			} else {
				LOGGER.log(Level.INFO, "All rides are busy. Please try after sometime.");
			}*/
			

		}catch(Exception exception) {
			LOGGER.log(Level.SEVERE,"Error in booking the ride.");
		}
	}

}
