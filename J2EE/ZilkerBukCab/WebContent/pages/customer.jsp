<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Customer</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/style.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
	</head>
<body onload="riderviewbook()" id="rider">
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.User"%>
<%@ page import="com.zilker.servlet.RegistrationServlet"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.zilker.bean.Address"%>

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
  		<button class="button button-dark" type="submit" onclick="riderviewbook()">Book a ride</button>
  		<button class="button button-dark" type="submit" onclick="riderviewprofile()">Profile</button>
  		<button class="button button-dark" type="submit" onclick="riderviewtrips()">My Trips</button>
        <a class="logout" href="index.html">Logout</a>
	</div> 
 	<div class="container3 rider">

        <form id="bookride" onsubmit="return validateRideRegistration()" method="post">
            <h1>Book your Ride</h1>
            <label>Start Date </label> <input id="date" type="date" value="date" required><br>
            <label> Start Time </label><input id="time" type="time" value="time" required><br>
            <!-- <label for="Source">Source</label>
            <input id="source" type="text"  placeholder="Enter Source" required><br>
            <label for="dest">Destination</label>
            <input id="destination" type="text"  placeholder="Enter Destination" required><br> -->
            
            <label>Source</label><select id="source" style="width: 60%" required>
            
            <% 	ArrayList<Address> address = null;
            	Address object = null;
    			int size = -1;
    			int i = 0;
    		
    			try{
    				address = new ArrayList<Address>();
    				address = (ArrayList<Address>) request.getAttribute("addressList");
    			
    				size = address.size();
    				for(i=0;i<size;i++) {
    					object = address.get(i);
            	%>
            
            		<option><%= object.getAddress() + ", " + object.getZipCode()%></option>
            	<%}
    				}catch(Exception exception){
    					}%>
            </select>
            
            <label>Destination</label><select id="destination" style="width: 60%" required>
              
              <% for(i=0;i<size;i++) {
    					object = address.get(i);
    					%>
                          		<option><%= object.getAddress() + ", " + object.getZipCode()%></option>
              <%} %>
              
            </select>
            
            <label>Seats </label><select id="seats" required>
                <option>3</option>
                <option>4</option>
                <option>5</option>
                <option>6</option>
                <option>7</option>
                <option>8</option>
            </select>
            <br><br>
            <button class="bottomLogin button button-accent" type="submit" id="login">Search</button>
            <iframe src="https://www.google.com/maps/embed?pb=!1m10!1m8!1m3!1d15547.68989918154!2d80.2265543!3d13.04060655!3m2!1i1024!2i768!4f13.1!5e0!3m2!1sen!2sua!4v1549776191455" width="600" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>
        </form>

        <form id="riderprofile" onsubmit="return validateUpdateProfile()">
        	<img class="my-profile-icon" src="${Config.BASE_PATH}/img/4.png">
        	<h2 class="prof-title">User Name <span>123456789</span> </h2>
            <label for="email"><b>Email ID</b></label>
            <input id="riderEmail" type="email" placeholder="Username123@gmail.com" name="email" required>
    
            <label for="address"><b>Address</b></label>
            <textarea id="riderAddress" placeholder="123, Bank Street, Chennai, India" required></textarea>
            
            <label for="zipcode"><b>Zip Code</b></label>
            <input type="text" id="riderZipcode" required>
            <span id="updateZipcode"></span>

            <label for="psw"><b>Password</b></label>
            <input type="password" id="riderPassword" placeholder="abishaik123" name="psw" required> 
            <span id="updatePassword"></span>
            <br><br>
            <button class="bottomLogin button button-accent" type="submit" id="login">Save</button>
            <img class= "my-profile-img" src="${Config.BASE_PATH}/img/ride_1.jpg">
        </form>  
        
        <form id="ridertrips">
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


