<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Driver</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/style.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
	</head>

<body onload="driverviewlicense()" id="rider">
<%@ page import="com.zilker.config.Config"%>


	<header>
		<a href="index.html"><img src="${Config.BASE_PATH}/img/logouber2.png" alt="Taxi logo" class="logo"></a>
		<!--<nav>	
			<ul class="home">
				<li><a href="index.html#home">Home</a></li>
				<li><a href="index.html#about">About</a></li>
				<li><a href="index.html#gallery">Gallery</a></li>
				<li><a href="index.html#support">Support</a></li>
				<li><a href="index.html#contact">Contact</a></li>
			</ul>
		</nav>-->
	</header>
	<div class="vertical-menu">
  		<!--  <button class="button button-dark" type="submit" onclick="driverviewlicense()">License Details</button>
  		<button class="button button-dark" type="submit" onclick="driverviewprofile()">Profile</button>
  		<button class="button button-dark" type="submit" onclick="driverviewtrips()">My Trips</button>-->
  		
  		<a class="riderProfile" onclick="driverviewlicense()">License Details</a> 
  		<a class="riderProfile" onclick="driverviewprofile()" href="${Config.BASE_PATH}ProfileServlet">Profile</a> 
  		<a class="riderProfile" onclick="driverviewtrips()">My Trips</a> 

        <a class="logout" href="index.html">Logout</a>
<!-- 		<button class="button button-dark" type="submit">Logout</button>
 -->	</div> 
 	<div class="container3 driver">

        <form id="driverlicense">
            <h1>Add License Details</h1>
            <label for="licno">License Number</label>
            <input id="licno" type="text" placeholder="Enter License Number"><br><br>
            <label for="uplic">Upload License</label>
            <input id="uplic" type="file">
            <!-- <input type="submit" value="submit"> -->
            <br><br>
            
            <button class="lic-submit button button-accent" type="submit" id="login">Submit</button>

            <h1>Completed a Ride ? </h1>
            <label for="bkid">Booking ID</label>
            <input id="bkid" type="text" placeholder="Enter Booking Number"><br><br>
            <label for="pin">Pincode</label>
            <input id="pin" type="text" placeholder="Enter Pincode">
            <!-- <input type="submit" value="submit"> -->
            <br><br>
            
            <button class="ride-com button button-accent" type="submit" id="login">Ride Completed</button>
            
        </form>

        <form action="${Config.BASE_PATH}DriverProfileServlet" id="driverprofile" onsubmit="return validateUpdateProfile()" method="post">
        	<img class="my-profile-icon" src="${Config.BASE_PATH}/img/4.png">
        	<h2 class="prof-title">Driver Name <span>123456789</span> 
                <br><span class="fa fa-star checked"></span>
                    <span class="fa fa-star checked"></span>
                    <span class="fa fa-star checked"></span>
                    <span class="fa fa-star checked"></span>
                    <span class="fa fa-star"></span></h2>
            <label for="email"><b>Email ID</b></label>
            <input id="riderEmail" type="email" placeholder="Username123@gmail.com" name="email" required>
        	<!-- <label for="location">Location</label>
        	<select id="location">
        		<option>India</option>
        		<option>United States of America</option>
        		<option>United Kingdom</option>
        		<option>Canada</option>
        		<option>Australia</option>
        		<option>Singapore</option>
        		<option>China</option>
        		<option>Japan</option>
        		<option>Switzerland</option>
        	</select> -->

        	<!-- <label for="lang">Language</label>
        	<select id="lang">
        		<option>English</option>
        		<option>Tamil</option>
        		<option>Hindi</option>
        		<option>Chinese</option>
        		<option>Germany</option>
        		<option>Japanese</option>
        		<option>French</option>
        	</select> -->
        	<!-- <label for="invite-code">Invite Code</label>
            <input id="invite-code" type="text" placeholder="Username123" name="inv-code" readonly="readonly"> -->
            <label for="address"><b>Address</b></label>
            <textarea name="address" id="riderAddress" placeholder="123, Bank Street, Chennai, India" required></textarea>

            <label for="zipcode"><b>Zip Code</b></label>
            <input name="zipCode" type="text" id="riderZipcode" required>
            <span id="updateZipcode"></span>

            <label for="psw"><b>Password</b></label>
            <input type="password" id="riderPassword" placeholder="abishaik123" name="password" required>
            <span id="updatePassword"></span>
            
            <br> <br>
            <button class="bottomLogin button button-accent" type="submit" id="login">Save</button>
            <img class= "my-profile-img" src="${Config.BASE_PATH}/img/ride_1.jpg">
        </form>  
        
        <form id="drivertrips">
                <div class="row">
                    
                    <div class="column">
                        <button class="collapsible"><h2 id="datetime">09 February 2019, 5.42pm</h2>
                        <h2 id="rs">Rs.200</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Cash</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with John</h3>
                            <h3 id="fromto">From XYZ to ABC</h3>
                            <button class="button button-accent update">Update</button>
                            <button class="button button-accent cancel">Cancel</button>
                            <h4>Rate    
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star"></span>
                            <span class="fa fa-star"></span> </h4>
                        </div>
                    </div>  
                    <div class="column">
                        <button class="collapsible"><h2 id="datetime">09 February 2019, 5.42pm</h2>
                        <h2 id="rs">Rs.200</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Cash</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with John</h3>
                            <h3 id="fromto">From XYZ to ABC</h3>
                            <button class="button button-accent update">Update</button>
                            <button class="button button-accent cancel">Cancel</button>
                            <h4>Rate    
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star"></span>
                            <span class="fa fa-star"></span> </h4>
                        </div>
                    </div>  
                    <div class="column">
                        <button class="collapsible"><h2 id="datetime">09 February 2019, 5.42pm</h2>
                        <h2 id="rs">Rs.200</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Cash</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with John</h3>
                            <h3 id="fromto">From XYZ to ABC</h3>
                            <button class="button button-accent update">Update</button>
                            <button class="button button-accent cancel">Cancel</button>
                            <h4>Rate    
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star"></span>
                            <span class="fa fa-star"></span> </h4>
                        </div>
                    </div>     
                    <div class="column">
                        <button class="collapsible"><h2 id="datetime">09 February 2019, 5.42pm</h2>
                        <h2 id="rs">Rs.200</h2>
                        <h4 id="place">Chennai</h4>
                        <h4 id="cash">Cash</h4></button>
                        <div class="content">
                            <h3 id="with">Your trip with John</h3>
                            <h3 id="fromto">From XYZ to ABC</h3>
                            <button class="button button-accent update">Update</button>
                            <button class="button button-accent cancel">Cancel</button>
                            <h4>Rate    
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star checked"></span>
                            <span class="fa fa-star"></span>
                            <span class="fa fa-star"></span> </h4>
                        </div>
                    </div>  
                </div>
        </form>
	</div>


</body>

    <script src="${Config.BASE_PATH}/js/logSign.js"></script>
</html>
