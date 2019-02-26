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

import com.zilker.bean.Address;
import com.zilker.delegate.CustomerDelegate;

/**
 * Servlet implementation class LocationServlet
 */
@WebServlet("/LocationServlet")
public class LocationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<Address> addressList = null;
		RequestDispatcher requestDispatcher = null;
		HttpSession session = null;
		CustomerDelegate customerDelegate = null;

		
		try {
			session = request.getSession();
			customerDelegate = new CustomerDelegate();
			addressList = customerDelegate.displayLocations();
			
			request.setAttribute("addressList", addressList);
			requestDispatcher = request.getRequestDispatcher("./pages/customer.jsp");
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
