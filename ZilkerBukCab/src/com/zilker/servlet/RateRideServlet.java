package com.zilker.servlet;

import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zilker.delegate.SharedDelegate;
import com.zilker.bean.CompleteRating;
import com.zilker.constants.Constants;


/**
 * Servlet implementation class RateRideServlet
 */
@WebServlet("/RateRideServlet")
public class RateRideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RateRideServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<CompleteRating> ratingCompleteList = null;
		CompleteRating completeRating = null;
		SharedDelegate sharedDelegate = null;
		
		JSONObject json      = new JSONObject();
		JSONArray  bookingID = new JSONArray();
		JSONObject ratedBookingID;
		
		int size=-1;
		int i=0;
		
		try {
		ratingCompleteList = new ArrayList<CompleteRating>();
		sharedDelegate = new SharedDelegate();

		String userPhone = "8888888888";
		ratingCompleteList = sharedDelegate.displayCompletedRatedRides(userPhone);
		
		size = ratingCompleteList.size();
		
		for(i=0;i<size;i++) {
			completeRating = ratingCompleteList.get(i);
			
			
			ratedBookingID = new JSONObject();
			
			ratedBookingID.put("bookingID", completeRating.getBookingID());
			ratedBookingID.put("rating", completeRating.getRating());
		    bookingID.put(ratedBookingID);
		}
		
		json.put("bookingid", bookingID);
		
		}catch(Exception jse) {
			
		}
		response.setContentType("application/json");
		response.getWriter().write(json.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int bookingID = -1;
		float rating = 0.0f;
		String ratingResponse = "";
		String userPhone = "";
		SharedDelegate sharedDelegate = null;
		HttpSession session = null;
		RequestDispatcher requestDispatcher = null;

		
		try {
			session = request.getSession();
			sharedDelegate = new SharedDelegate();

			bookingID = Integer.parseInt(request.getParameter("travelInvoiceBookingID"));
			rating = Float.parseFloat(request.getParameter("rating"));
			
			userPhone = (String)session.getAttribute("userPhone");

			ratingResponse = sharedDelegate.rateTrip(rating, bookingID, userPhone);
			
			if(ratingResponse.equals(Constants.SUCCESS)) {
				requestDispatcher = request.getRequestDispatcher("./pages/myTrips-customer.jsp");
				requestDispatcher.forward(request, response);
			}

		}catch(Exception exception) {
			
		}
	}

}
