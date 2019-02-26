package com.zilker.servlet;

import java.io.IOException;



import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zilker.bean.PostConfirm;
import com.zilker.bean.TravelInvoice;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.SharedDelegate;

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
		SharedDelegate sharedDelegate = null;
		RequestDispatcher requestDispatcher = null;
		PostConfirm postConfirm = null;
		int customerID = -1;
		int driverID = -1;
		int cabID = -1;
		int sourceID = -1;
		int destinationID = -1;
		int bookingID = -1;
		String startTimeDate;
		float price = 0.0f;
		float distance = 0.0f;
		String source = "";
		String destination = "";
		String cab = "";
		String driver = "";
		String sourceZipCode = "";
		String destinationZipCode = "";
		
		try {
			travelInvoice = new TravelInvoice();
			customerDelegate = new CustomerDelegate();
			sharedDelegate = new SharedDelegate();
			postConfirm = new PostConfirm();
			
			customerID = Integer.parseInt(request.getParameter("travelInvoiceCustomerID"));
			
			driverID = Integer.parseInt(request.getParameter("travelInvoiceDriverID"));
			
			cabID = Integer.parseInt(request.getParameter("travelInvoiceCabID"));
		
			sourceID = Integer.parseInt(request.getParameter("travelInvoiceSourceID"));

			destinationID = Integer.parseInt(request.getParameter("travelInvoiceDestinationID"));

			startTimeDate = request.getParameter("travelInvoiceStartTimeDate");
			
			price = Float.parseFloat(request.getParameter("travelInvoicePrice"));
			
			distance = Float.parseFloat(request.getParameter("travelInvoiceDistance"));
						
			travelInvoice = new TravelInvoice(customerID, driverID, cabID, sourceID, destinationID, startTimeDate, price, distance);
			
			bookingID = customerDelegate.calculateTravel(travelInvoice, 0);
			
			source = sharedDelegate.findLocation(sourceID);
			sourceZipCode = customerDelegate.extractLocation(source, 1);
			
			destination = sharedDelegate.findLocation(destinationID);
			destinationZipCode = customerDelegate.extractLocation(destination, 1);
			
			cab = sharedDelegate.findCabByID(cabID);
			
			driver = sharedDelegate.findDriverByID(driverID);
			
			
			postConfirm = new PostConfirm(bookingID, startTimeDate, source, destination, driver, cab, price);
			
			request.setAttribute("postConfirmInvoice", postConfirm);
			request.setAttribute("sourceZip", sourceZipCode);
			request.setAttribute("destinationZip",destinationZipCode);


			requestDispatcher = request.getRequestDispatcher("./pages/after-book.jsp");
			requestDispatcher.forward(request, response);
						
		}catch(Exception exception) {
			
		}
		
		

		
		
		
	}

}
