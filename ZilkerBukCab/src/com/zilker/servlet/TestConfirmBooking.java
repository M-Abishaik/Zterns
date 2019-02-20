package com.zilker.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zilker.bean.TravelInvoice;
import com.zilker.delegate.CustomerDelegate;

/**
 * Servlet implementation class TestConfirmBooking
 */
@WebServlet("/TestConfirmBooking")
public class TestConfirmBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestConfirmBooking() {
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
		int customerID = -1;
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
		PrintWriter printWriter = null;
		
		try {
			printWriter = response.getWriter();
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
			//requestDispatcher = request.getRequestDispatcher("./pages/hi.jsp");
			//requestDispatcher.forward(request, response);
			
			if(bookingID!=(-1)) {
				System.out.println("Booking successfull");
				//response.sendRedirect("./pages/hi.jsp");
			}else {				
				//response.sendRedirect("index.jsp");
			}
		}catch(Exception exception) {
			exception.printStackTrace();
		}

	}

}
