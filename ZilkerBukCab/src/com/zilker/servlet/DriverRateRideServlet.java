package com.zilker.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zilker.bean.CompleteRating;
import com.zilker.delegate.SharedDelegate;

/**
 * Servlet implementation class DriverRateRideServlet
 */
@WebServlet("/DriverRateRideServlet")
public class DriverRateRideServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DriverRateRideServlet() {
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
		HttpSession session = null;
		String userPhone = "";
		
		JSONObject json      = new JSONObject();
		JSONArray  bookingID = new JSONArray();
		JSONObject ratedBookingID;
		
		int size = -1;
		int i=0;
		int flag = 1;
		
		try {
		ratingCompleteList = new ArrayList<CompleteRating>();
		sharedDelegate = new SharedDelegate();
		session = request.getSession();

		userPhone = (String)session.getAttribute("userPhone");
		ratingCompleteList = sharedDelegate.displayCompletedRatedRides(userPhone, flag);
		
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
