<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.BookingResponse"%>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Customer Trips</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/rider.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	</head>
</head>

<body id="bookridepage">


<%if(request.getAttribute("onGoingResponse")!=null){ %>
		<body onload="upcoming()">
		<%} %>
	
<%if(request.getAttribute("onCompleteResponse")!=null){ %>
		<body onload="completed()">
		<%} %>

<%if(request.getAttribute("onCancelResponse")!=null){ %>
		<body onload="cancelled()">
		<%} %>		

		
	<header>
		<a href="#"><img src="${Config.BASE_PATH}/img/logouber2.png" alt="Taxi logo" class="logo"></a>
	</header>
	<div class="vertical-menu">
  		<a class="riderProfile" style="cursor:pointer" onclick="riderviewbook()" href="${Config.BASE_PATH}LocationServlet">Book a ride</a> 
        <a class="riderProfile" style="cursor:pointer">My Trips</a> 
        <a class="riderProfile" onclick="riderviewprofile()" href="${Config.BASE_PATH}ProfileServlet">Profile</a> 
        <a class="riderProfile" href="${Config.BASE_PATH}LogoutServlet">Logout</a>
 </div> 
 	<div class="container3 rider">
 			<div class="filter-ridertrips">
                <label>Filter by : </label>
                <!-- <button class="button button-accent upcoming" type="submit" onclick="ongoing()">Ongoing</button> -->
                <a style="cursor:pointer;" class="button button-accent upcoming" onclick="upcoming()" href="${Config.BASE_PATH}OnGoingRidesServlet">Upcoming</a> 
                <a style="cursor:pointer;" class="button button-accent completed" onclick="completed()" href="${Config.BASE_PATH}CompletedRidesServlet">Completed</a>
                <a style="cursor:pointer;" class="button button-accent cancelled" onclick="cancelled()" href="${Config.BASE_PATH}CancelledRidesServlet">Cancelled</a>
 			</div>
 		

 			<div id="ridertrips">
                <div class="row">
                
                <% 	
			if(request.getAttribute("onGoingResponse")!=null){
			
			BookingResponse bookingResponse =  new BookingResponse();
			bookingResponse = (BookingResponse)request.getAttribute("onGoingResponse");
				%>
                
                    <div class="column" id="upcoming">
                        <button class="collapsible"><h2 id="datetime"><% if(bookingResponse!=null){%><%=bookingResponse.getStartTime()%><%}%></h2>
                        <h2 id="rs">Rs<% if(bookingResponse!=null){%><%=bookingResponse.getPrice()%><%}%> Fares might change</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Wallet</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with <% if(bookingResponse!=null){%><%=bookingResponse.getDriver()%><%}%></h3>
                            <h3 id="fromto">Source: <% if(bookingResponse!=null){%><%=bookingResponse.getSource()%><%}%></h3>
                            <h3 id="fromto">Destination: <% if(bookingResponse!=null){%><%=bookingResponse.getDestination()%><%}%></h3>
                            
                            <form action="${Config.BASE_PATH}UpdateBookingServlet" method="post">
                            <input type="hidden" name="travelInvoiceBookingID" value=<% if(bookingResponse!=null){%><%=bookingResponse.getBookingID()%><%}%>>
                            <button style="margin-bottom: 1em; float:left;" class="button button-accent update">Update</button>
                            </form>
                            
                            <form action="${Config.BASE_PATH}CancelBookingServlet" method="post">
                            <input type="hidden" name="travelInvoiceBookingID" value=<% if(bookingResponse!=null){%><%=bookingResponse.getBookingID()%><%}%>>
                            <button style="margin-bottom: 1em; margin-left: 20%;" type="submit" class="button button-accent cancel">Cancel</button>
                        	</form>
                        </div>
                    </div> 
                    <%} %>
                    
            		<%
            			ArrayList<BookingResponse> completedList = null;
            			BookingResponse completeRides = null;
            			
            			int size = -1;
            			int i = 0;
            			
            			if(request.getAttribute("onCompleteResponse")!=null){
            				
            			try{
            				completedList  = new ArrayList<BookingResponse>();
            				completedList = (ArrayList<BookingResponse>)request.getAttribute("onCompleteResponse");
            				
            				size = completedList.size();
            				for(i=0;i<size;i++) {
            					completeRides = completedList.get(i);
            		%>
                    
                    <div id="completed">
                    <div class="column">
                        <button class="collapsible"><h2 id="datetime"><% if(completeRides!=null){%><%=completeRides.getStartTime()%><%}%></h2>
                        <h2 id="rs">Rs. <% if(completeRides!=null){%><%=completeRides.getPrice()%><%}%></h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Cash</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with <% if(completeRides!=null){%><%=completeRides.getDriver()%><%}%></h3>
                            <h3 id="fromto">Source: <% if(completeRides!=null){%><%=completeRides.getSource()%><%}%></h3>
                           	<h3 id="fromto">Destination: <% if(completeRides!=null){%><%=completeRides.getDestination()%><%}%></h3>
                        </div>
                        
                        <form id="user-rating-form" method="post" action="${Config.BASE_PATH}RateRideServlet">
                        <input type="hidden" name="travelInvoiceBookingID" value=<% if(completeRides!=null){%><%=completeRides.getBookingID()%><%}%>>
                        
                            <span class="user-rating">
                            <input id="five" type="radio" name="rating" value="5"><span class="star"></span>

                                <input id="four" type="radio" name="rating" value="4"><span class="star"></span>

                                <input id="three" type="radio" name="rating" value="3"><span class="star"></span>

                                <input id="two" type="radio" name="rating" value="2"><span class="star"></span>

                                <input id="one" type="radio" name="rating" value="1"><span class="star"></span>
                            </span>
                            <button style="margin-bottom: 1em;" type="submit" class="button button-accent cancel">Rate</button>
                            
                        </form>
                            
                    </div>
                     </div> 
                    <%}%>  
                   
                    
                    <%}catch(Exception e){	
		e.printStackTrace();
	}}%>
                    
						
                    <%
            			ArrayList<BookingResponse> cancelledList = null;
            			BookingResponse cancelledRides = null;
            			
            			int length = -1;
            			int index = 0;
            			
            			if(request.getAttribute("onCancelResponse")!=null){ 
            			
            			try{
            				cancelledList = new ArrayList<BookingResponse>();
            				cancelledList = (ArrayList<BookingResponse>)request.getAttribute("onCancelResponse");
            				
            				length = cancelledList.size();
            				for(index=0;index<length;index++) {
            					cancelledRides = cancelledList.get(index);
            		%>
                    
                    <div class="column" id="cancelled">
                        <button class="collapsible"><h2 id="datetime"><% if(cancelledRides!=null){%><%=cancelledRides.getStartTime()%><%}%></h2>
                        <h2 id="rs">Rs. <% if(cancelledRides!=null){%><%=cancelledRides.getPrice()%><%}%></h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Cash</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with <% if(cancelledRides!=null){%><%=cancelledRides.getDriver()%><%}%></h3>
                            <h3 id="fromto">Source: <% if(cancelledRides!=null){%><%=cancelledRides.getSource()%><%}%></h3>
                           	<h3 id="fromto">Destination: <% if(cancelledRides!=null){%><%=cancelledRides.getDestination()%><%}%></h3>
                        </div>
                    </div>
                     <%}%>  
                   
                    
                    <%}catch(Exception e){	
		e.printStackTrace();
	}}%>  
                </div>
        </div>
    </div>
</body>
 <script src="${Config.BASE_PATH}/js/rider.js"></script>
</html>