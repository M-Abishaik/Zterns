package com.zilker.servlet;

import java.io.IOException;
import java.util.*;
import javax.mail.*;
import javax.mail.Address;
import javax.mail.internet.*;
import javax.activation.*;

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
		
		
		String toMail = "";
		String fromMail = "";
		String host = "";
		Session session = null;
		
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
			
			 /*try{
		            host ="smtp.gmail.com" ;
		            String user = "dummymailer1234@gmail.com";
		            String pass = "extreme player@123";
		            String to = "m.abishaik97@gmail.com";
		            String from = "dummymailer1234@gmail.com";
		            String subject = "This is confirmation number for your expertprogramming account. Please insert this number to activate your account.";
		            String messageText = "Your Is Test Email :";
		            boolean sessionDebug = false;

		            Properties props = System.getProperties();

		            props.put("mail.smtp.starttls.enable", "true");
		            props.put("mail.smtp.host", host);
		            props.put("mail.smtp.port", "587");
		            props.put("mail.smtp.auth", "true");
		            props.put("mail.smtp.starttls.required", "true");

		            Session mailSession = Session.getDefaultInstance(props, null);
		            mailSession.setDebug(sessionDebug);
		            Message msg = new MimeMessage(mailSession);
		            msg.setFrom(new InternetAddress(from));
		            InternetAddress[] address = {new InternetAddress(to)};
		            msg.setRecipients(Message.RecipientType.TO, address);
		            msg.setSubject(subject); msg.setSentDate(new Date());
		            msg.setText(messageText);

		           Transport transport=mailSession.getTransport("smtp");
		           transport.connect(host, user, pass);
		           transport.sendMessage(msg, msg.getAllRecipients());
		           transport.close();
		           System.out.println("message send successfully");
		        }catch(Exception ex)
		        {
		            System.out.println(ex);
		        }*/

			
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
