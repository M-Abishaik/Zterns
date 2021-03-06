package com.zilker.servlet;

import java.io.IOException;
import java.util.ArrayList;

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
 * Servlet implementation class AfterBookCompletedRidesServlet
 */
@WebServlet("/AfterBookCompletedRidesServlet")
public class AfterBookCompletedRidesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AfterBookCompletedRidesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
ArrayList<BookingResponse> completeList = null;
		
		SharedDelegate sharedDelegate = null;
		HttpSession session = null;
		RequestDispatcher requestDispatcher = null;
		String userPhone = "";
		
		try {
			sharedDelegate = new SharedDelegate();
			session = request.getSession();
			completeList = new ArrayList<BookingResponse>();
			userPhone = (String)session.getAttribute("userPhone");
			
			
			//userPhone = "8888888888";			
			completeList = sharedDelegate.displayCompletedRides(userPhone, 0);
			
			request.setAttribute("onCompleteResponse", completeList);
			
			requestDispatcher = request.getRequestDispatcher("./pages/afterBookMyTrips.jsp");
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
