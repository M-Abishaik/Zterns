package com.zilker.servlet;

import java.io.IOException;


import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zilker.delegate.SharedDelegate;
import com.zilker.bean.Address;
import com.zilker.bean.BookingResponse;
import com.zilker.bean.PostConfirm;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.constants.Constants;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userPhone = "";
		String userPassword = "";
		SharedDelegate sharedDelegate = null;
		CustomerDelegate customerDelegate = null;
		ArrayList<Address> address = null;
		ArrayList<BookingResponse> completeList = null;
		String loginResponse = "";
		int bookingID = -1;
		String sourceExtract = "";
		String destinationExtract = "";
		String zipCodeSource = "";
		String zipCodeDestination = "";
		RequestDispatcher requestDispatcher = null;
		PostConfirm postConfirm = null;
		
		HttpSession session = null;
		
		try {
			completeList = new ArrayList<BookingResponse>();
			session = request.getSession();
			customerDelegate = new CustomerDelegate();
			sharedDelegate = new SharedDelegate();
			postConfirm = new PostConfirm();
			userPhone = request.getParameter("loginMobile");
			userPassword = request.getParameter("loginPassword");
			
									
			loginResponse = sharedDelegate.login(userPhone, userPassword);
						
			if(!loginResponse.equals(Constants.FAILURE)) {
				session.setAttribute("userPhone", userPhone);
				if(loginResponse.equals(Constants.CUSTOMER)) {
					
					bookingID = sharedDelegate.checkBookingStatus(userPhone,0);
					
					if(bookingID!=(-1)) {
						
						postConfirm = sharedDelegate.getBookingDetails(bookingID);
						
						sourceExtract = customerDelegate.extractLocation(postConfirm.getSource(), 0); 
						destinationExtract = customerDelegate.extractLocation(postConfirm.getDestination(), 0);
							
						zipCodeSource = customerDelegate.extractLocation(postConfirm.getSource(), 1);
						zipCodeDestination = customerDelegate.extractLocation(postConfirm.getDestination(), 1);
							
						request.setAttribute("postConfirmInvoice", postConfirm);
						request.setAttribute("sourceZip", zipCodeSource);
						request.setAttribute("destinationZip", zipCodeDestination);

					
						requestDispatcher = request.getRequestDispatcher("./pages/after-book.jsp");
						requestDispatcher.forward(request, response);
					} else {
					
					address = customerDelegate.displayLocations();
										
					request.setAttribute("addressList", address);
								
					requestDispatcher = request.getRequestDispatcher("./pages/customer.jsp");
					requestDispatcher.forward(request, response);
					}
					
				} else if(loginResponse.equals(Constants.DRIVER)) {
					
					bookingID = sharedDelegate.checkBookingStatus(userPhone,1);

					
					if(bookingID!=(-1)) {
						
						postConfirm = sharedDelegate.getBookingDetails(bookingID);
						
						sourceExtract = customerDelegate.extractLocation(postConfirm.getSource(), 0); 
						destinationExtract = customerDelegate.extractLocation(postConfirm.getDestination(), 0);
							
						zipCodeSource = customerDelegate.extractLocation(postConfirm.getSource(), 1);
						zipCodeDestination = customerDelegate.extractLocation(postConfirm.getDestination(), 1);
							
						request.setAttribute("postConfirmInvoice", postConfirm);
						request.setAttribute("sourceZip", zipCodeSource);
						request.setAttribute("destinationZip", zipCodeDestination);

					
						requestDispatcher = request.getRequestDispatcher("./pages/afterBookDriver.jsp");
						requestDispatcher.forward(request, response);
					} else {
					
						completeList = sharedDelegate.displayCompletedRides(userPhone, 1);
						
						request.setAttribute("onCompleteResponse", completeList);
						
						requestDispatcher = request.getRequestDispatcher("./pages/myTrips-driver.jsp");
						requestDispatcher.forward(request, response);
					}
					
				}
					
			} else {
				LOGGER.log(Level.INFO,"Invalid login credentials.");
				request.setAttribute("errorMessage", "Invalid login credentials.");	
				response.sendRedirect("index.jsp");
				
			}
			
		} catch(Exception exception) {
			LOGGER.log(Level.WARNING,"Exception in login credentials.");
			request.setAttribute("errorMessage", "Invalid login credentials.");	
			response.sendRedirect("index.jsp");
		}
		
	}
		
	
	
	//DRIVER
	
	/*bookingResponse = sharedDelegate.checkBookingStatus(userPhone,1);
	
	if(bookingResponse==true) {
		requestDispatcher = request.getRequestDispatcher("./pages/after-book.jsp");
		requestDispatcher.forward(request, response);
	} else {
	
	requestDispatcher = request.getRequestDispatcher("./pages/driver.jsp");
	requestDispatcher.forward(request, response);
	}*/

}
