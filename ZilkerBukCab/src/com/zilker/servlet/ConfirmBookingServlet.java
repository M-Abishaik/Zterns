package com.zilker.servlet;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zilker.bean.TravelInvoice;
import com.zilker.delegate.CustomerDelegate;

/**
 * Servlet implementation class confirmBookingServlet
 */
@WebServlet("/ConfirmBookingServlet")
public class ConfirmBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmBookingServlet() {
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
		
		/*int customerID = -1;
		int driverID = -1;
		int cabID = -1;
		int sourceID = -1;
		int destinationID = -1;
		int bookingID = -1;
		String startTimeDate = "";
		float price = 0.0f;
		float distance = 0.0f;
		TravelInvoice travelInvoice = null;
		CustomerDelegate customerDelegate = null;
		RequestDispatcher requestDispatcher = null;
		
		try {
			travelInvoice = new TravelInvoice();
			customerDelegate = new CustomerDelegate();

			customerID = Integer.parseInt((request.getParameter("customerID")));
			driverID = Integer.parseInt((request.getParameter("driverID")));
			cabID = Integer.parseInt((request.getParameter("cabID")));
			sourceID = Integer.parseInt((request.getParameter("sourceID")));
			destinationID = Integer.parseInt((request.getParameter("destinationID")));
			startTimeDate = request.getParameter("startTimeDate");
			price = Float.parseFloat((request.getParameter("price")));
			distance = Float.parseFloat((request.getParameter("distance")));
		
			distance = 100;
			
			travelInvoice = new TravelInvoice(customerID, driverID, cabID, sourceID, destinationID, startTimeDate, price, distance);
			
			bookingID = customerDelegate.calculateTravel(travelInvoice, 0);
			
			System.out.println(bookingID);
			//requestDispatcher = request.getRequestDispatcher("index.jsp");
			//requestDispatcher.forward(request, response);
			
			response.sendRedirect("index.jsp");
		}catch(Exception exception) {
			exception.printStackTrace();
		}*/
		
		TravelInvoice travelInvoice = null;
		CustomerDelegate customerDelegate = null;
		RequestDispatcher requestDispatcher = null;
		int customerID = -1;
		int driverID = -1;
		int cabID = -1;
		int sourceID = -1;
		int destinationID = -1;
		int bookingID = -1;
		String startTimeDate;
		float price = 0.0f;
		float distance = 0.0f;
		
		try {
			travelInvoice = new TravelInvoice();
			customerDelegate = new CustomerDelegate();
			
			customerID = Integer.parseInt(request.getParameter("travelInvoiceCustomerID"));
			driverID = Integer.parseInt(request.getParameter("travelInvoiceDriverID"));
			
			cabID = Integer.parseInt(request.getParameter("travelInvoiceCabID"));
			sourceID = Integer.parseInt(request.getParameter("travelInvoiceSourceID"));
			destinationID = Integer.parseInt(request.getParameter("travelInvoiceDestinationID"));
			startTimeDate = request.getParameter("travelInvoiceStartTimeDate");
			price =Float.parseFloat((request.getParameter("travelInvoicePrice")));
			distance = Float.parseFloat((request.getParameter("distance")));
			
			
			System.out.println(price + "" + distance);

			
			//price = 2000f;
			//distance = 100f;
						
			//travelInvoice = new TravelInvoice(customerID, driverID, cabID, sourceID, destinationID, startTimeDate, price, distance);
			
			//bookingID = customerDelegate.calculateTravel(travelInvoice, 0);
			
			//System.out.println(bookingID);
			
			//requestDispatcher = request.getRequestDispatcher("./pages/myTrips-customer.jsp");
			//requestDispatcher.forward(request, response);
			//System.out.println(customerID + " " + driverID + " " + cabID + " " + sourceID + " " + destinationID + " " + startTimeDate);
			
			
		}catch(Exception exception) {
			
		}
		
		

		
		
		
	}

}
