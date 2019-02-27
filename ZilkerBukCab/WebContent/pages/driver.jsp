<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Driver</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/driver.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
</head>

	<%if(request.getAttribute("userProfile")!=null){ %>
		<body onload="driverviewprofile()">
		<%} %>
<body onload="driverviewlicense()" id="driver">
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.User"%>

	<header>
		<a href="#"><img src="${Config.BASE_PATH}img/logouber2.png" alt="Taxi logo" class="logo"></a>
	</header>
	
	<div class="vertical-menu">
        <a class="riderProfile" style="cursor:pointer"onclick="driverviewlicense()">Add License Details</a>
        <!--<a class="riderProfile"  style="cursor:pointer" onclick="drivercompleted()">Complete a ride</a> -->
        <a class="riderProfile" onclick="driverviewprofile()" href="${Config.BASE_PATH}DriverProfileServlet">Profile</a> 
        <a class="riderProfile" href="${Config.BASE_PATH}DriverOnGoingRidesServlet">My Trips</a> 
		<a class="riderProfile" href="${Config.BASE_PATH}LogoutServlet">Logout</a>
	
	</div> 
 	<div class="container3 driver">

        <form style="margin-top:10%;" action="${Config.BASE_PATH}AddLicenceServlet" id="driverlicense" method="post">
            <h1>Add License Details</h1>
            <label for="licno">License Number</label>
            <input id="licno" type="text" placeholder="Enter License Number" name="licenceNumber"><br><br>
            <label for="file-upload" class="custom-file-upload">
    		<i class="fa fa-cloud-upload"></i> Upload License</label>
			<input id="file-upload" type="file"/>
			<label for="pin">Enter the pincode of your current location</label>
            <input id="pin" type="text" placeholder="Enter Pincode" name="pinCode">
            <br><br>
            <button class="lic-submit button button-accent" type="submit" id="login">Submit</button>
        </form>

        <form action="${Config.BASE_PATH}CompleteRideServlet" id="drivercompleted" method="post">
            <h1>Completed a Ride?</h1>
            <label for="bkid">Enter the booking ID</label>
            <input id="bkid" type="text" placeholder="Enter the booking ID" name="bookingID"><br><br>
            <br><br><br>
            
            <button style="width: 70%;" class="ride-com button button-accent" type="submit" id="login">Complete Ride</button>
            <img class= "license-img" src="${Config.BASE_PATH}img/g2.jpg">
        </form>
        
         <% 	User user = new User();
        	user = (User)request.getAttribute("userProfile");%>

        <form id="driverprofile">
        	<img class="my-profile-icon" src="${Config.BASE_PATH}img/4.png">
        	<h2 class="prof-title"><% if(user!=null){%> <%=user.getUserName()%><%}%><br><span>+91 -<% if(user!=null){%> <%=user.getContact()%><%}%></span></br>
<%--                <% if(user!=null){%> <%=user.getUserName()%><%}%><br> --%>
                    </h2>
                    
            <label for="email"><b>Email ID</b></label>
            <input id="riderEmail" type="email" value=<% if(user!=null){%><%=user.getMail()%><%}%> name="email" required>
    
            <label for="address"><b>Address</b></label>
            <textarea name="address" id="riderAddress" placeholder="No. 1/111, Annex-15"><% if(user!=null){%> <%=user.getAddress()%><%}%></textarea>
            
            <label for="zipcode"><b>ZipCode</b></label>
            <input type="text" value=<% if(user!=null){%> <%=user.getZipCode()%><%}%> id="riderZipcode" name="zipCode" required>
            <span id="updateZipcode"></span>

            <label for="psw"><b>Password</b></label>
            <input type="password" id="riderPassword" value=<% if(user!=null){%> <%=user.getPassword()%><%}%> name="password" required> 
            <span id="updatePassword"></span>
            <br><br>
            <button class="bottomLogin button button-accent" type="submit" id="login">Save</button>
            <img class= "my-profile-img" src="${Config.BASE_PATH}img/ride_1.jpg">
        </form>  
	</div>
</body>
<script src="${Config.BASE_PATH}js/driver.js"></script>
</html>