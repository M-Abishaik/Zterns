<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Driver Trips</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/driver.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		
</head>

	
<%if(request.getAttribute("userProfile")!=null){ %>
		<body onload="riderviewprofile()">
		<%} %>
<body>
<%@ page import="com.zilker.config.Config"%>
<header>
		<a href="#"><img src="${Config.BASE_PATH}/img/logouber2.png" alt="Taxi logo" class="logo"></a>
	</header>
	<div class="vertical-menu">
        <a class="riderProfile" href="${Config.BASE_PATH}/pages/driver.jsp" onclick="driverviewlicense()" style="cursor:pointer">Add License Details</a>
 <a class="riderProfile" style="cursor:pointer" onclick="drivercompleted()">Complete a ride</a>       
  <a class="riderProfile" onclick="driverviewprofile()" href="${Config.BASE_PATH}DriverProfileServlet">Profile</a> 
        <a class="riderProfile" style="cursor:pointer">My Trips</a> 
       	<a class="riderProfile" href="${Config.BASE_PATH}LogoutServlet">Logout</a>
    </div> 
 </div> 
 	<div class="container3 driver">
 			<div class="filter-drivertrips">
                <label>Filter by : </label>
                <button class="button button-accent upcoming" type="submit" onclick="upcoming()">Upcoming</button>
                <button class="button button-accent completed" type="submit" onclick="completed()">Completed</button>
                <button class="button button-accent cancelled" type="submit" onclick="cancelled()">Cancelled</button>
 			</div>

 			<div id="drivertrips">
                <div class="row">
                    
                    <div class="column" id="upcoming">
                        <button class="collapsible"><h2 id="datetime">15 February 2019, 7.40pm</h2>
                        <h2 id="rs">Rs. 250 Fares might change</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Wallet</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with John</h3>
                            <h3 id="fromto">From XYZ to ABC</h3>
                            <button class="button button-accent update">Update</button>
                            <button class="button button-accent cancel">Cancel</button>
                            
							<form id="user-rating-form">
							<span class="user-rating">
							<input type="radio" name="rating" value="5"><span class="star"></span>

							    <input type="radio" name="rating" value="4"><span class="star"></span>

							    <input type="radio" name="rating" value="3"><span class="star"></span>

							    <input type="radio" name="rating" value="2"><span class="star"></span>

							    <input type="radio" name="rating" value="1"><span class="star"></span>
							</span>
							</form>


                        </div>
                    </div>  
                    <div id="completed">
                    <div class="column">
                        <button class="collapsible"><h2 id="datetime">13 February 2019, 6.02pm</h2>
                        <h2 id="rs">Rs.300</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Cash</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with Mike</h3>
                            <h3 id="fromto">From XYZ to ABC</h3>
                            <button class="button button-accent update">Update</button>
                            <button class="button button-accent cancel">Cancel</button>
                            <form id="user-rating-form">
							<span class="user-rating">
							<input type="radio" name="rating" value="5"><span class="star"></span>

							    <input type="radio" name="rating" value="4"><span class="star"></span>

							    <input type="radio" name="rating" value="3"><span class="star"></span>

							    <input type="radio" name="rating" value="2"><span class="star"></span>

							    <input type="radio" name="rating" value="1"><span class="star"></span>
							</span>
							</form>
                        </div>
                    </div>  
                    <div class="column" id="completed">
                        <button class="collapsible"><h2 id="datetime">09 February 2019, 5.15pm</h2>
                        <h2 id="rs">Rs.150</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Wallet</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with Will</h3>
                            <h3 id="fromto">From XYZ to ABC</h3>
                            <button class="button button-accent update">Update</button>
                            <button class="button button-accent cancel">Cancel</button>
                           	<form id="user-rating-form">
							<span class="user-rating">
							<input type="radio" name="rating" value="5"><span class="star"></span>

							    <input type="radio" name="rating" value="4"><span class="star"></span>

							    <input type="radio" name="rating" value="3"><span class="star"></span>

							    <input type="radio" name="rating" value="2"><span class="star"></span>

							    <input type="radio" name="rating" value="1"><span class="star"></span>
							</span>
							</form>
                        </div>
                    </div>     
                </div>
                    <div class="column" id="cancelled">
                        <button class="collapsible"><h2 id="datetime">09 February 2019, 5.00pm</h2>
                        <h2 id="rs">NIL</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">-</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with John</h3>
                            <h3 id="fromto">From XYZ to ABC</h3>
                            <button class="button button-accent update">Update</button>
                            <button class="button button-accent cancel">Cancel</button>
                            <form id="user-rating-form">
							<span class="user-rating">
							<input type="radio" name="rating" value="5"><span class="star"></span>

							    <input type="radio" name="rating" value="4"><span class="star"></span>

							    <input type="radio" name="rating" value="3"><span class="star"></span>

							    <input type="radio" name="rating" value="2"><span class="star"></span>

							    <input type="radio" name="rating" value="1"><span class="star"></span>
							</span>
							</form>
                        </div>
                    </div>  
                </div>
        </div>
    </div>


</body>
<script src="${Config.BASE_PATH}/js/driver.js"></script>
</html>