<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zilker.config.Config"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>ZCab</title>
		<link rel="stylesheet" href="./css/index.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	</head>
<body onload="viewlogin()" id="home">
<header>
		<a href="#"><img src="img/logouber2.png" alt="Taxi logo" class="logo"></a>
		<nav>	
			<ul class="navbar">
				<li><a href="#home">Home</a></li>
				<li><a href="#about">About</a></li>
				<li><a href="#gallery">Gallery</a></li>
				<li><a href="#support">Support</a></li>
				<li><a href="#contact">Contact</a></li>
			</ul>
		</nav>
</header>	

<section class="home-hero">
		<div class="container" class="main">
			<h1 class="title"> Travel the way you want !  <span>Book your ride. </span></h1>
		</div>

			<div class="container2">
        		<div class="wrapper">
        			<button class = "button button-accent" type="submit" onclick="viewlogin()">LOGIN</button>
        			<button class = "button button-accent" type="submit" onclick="viewsignup()">SIGNUP</button>
        		</div>
        		<form action="${Config.BASE_PATH}users/login" id="login" onsubmit="return validateLogin()" method="post">
            		<div class="login">
                	<label class="labelText" for="num"><b>Mobile Number</b></label>
					<input id="loginPhone" type="text" placeholder="Enter Mobile Number" name="loginMobile" required>
					<span id="loginPhoneMessage"></span>
					
					<label class="labelText" for="psw"><b>Password</b></label>
                	<input id="loginPassword" type="password" placeholder="Enter Password" name="loginPassword" required><br><br>
                	<label><input type="checkbox" checked="checked" name="remember"> Remember me</label>
                	<button class="bottomLogin button button-accent" type="submit" id="login">LOGIN</button>
	            	<%if(request.getAttribute("errorMessage")!=null){ %>
					<center><p style="color: red; margin-left: 1%; margin-top:1%; font-weight: bold;">Invalid login credentials.</p></center>
					<%}%>
	            	</div>
        		</form>
	        	<form action="${Config.BASE_PATH}users" id="signup" onsubmit="return validateRegistration()" method="post">
	            	<div class="signup">
	            		<label for="uname"><b>Name</b></label>
						<input type="text" id="registerUsername" name="registerUsername" placeholder="Enter your name" required>
						<span id="userName"></span>

	                	<label for="num"><b>Mobile number</b></label>
						<input type="text" id="registerMobile" name="registerContact" placeholder="Enter Mobile Number" required>
						<span id="userMobile"></span>

	                	<label for="email"><b>Email ID</b></label>
						<input type="email" id="registerEmail" name="registerMail" placeholder="Enter Email ID" required>
						<span id="userMail"></span>

						<label for="address"><b>Address</b></label>
						<input type="text" id="registerAddress" placeholder="Enter Address" name="registerAddress" required> 
						<span id="userAddress"></span>

						<label for="zipCode"><b>Zip Code</b></label>
						<input type="text" id="registerZipCode" placeholder="Enter Zipcode" name="registerZipcode" required>
						<span id="userZipCode"></span>

		                <label for="psw"><b>Password</b></label>
						<input type="password" id="registerPassword" placeholder="Enter Password" name="registerPassword" required> 
						<span id="userPassword"></span>

		                <label class="labelText" for="psw"><b>Re-enter Password</b></label>
						<input id="registerRePassword" type="password" placeholder="Re-enter Password" name="registerRePassword" required><br> <br>
						<span id="userRePassword"></span>

		               	<p><b>Sign up as</b> <select id="ridedriveadmin" name="registerRole" onchange="riderdriver(this);">
		        			<option>customer</option>
		        			<option>driver</option>
		        		</select></p>
		        		
		        		<div class="driverlicense" id="driverlicense">
					            <label for="licno">License Number</label>
					            <input id="licno" type="text" placeholder="Enter License Number" name="licenceNumber"><br><br>
					            <label for="file-upload" class="custom-file-upload">
					    		<i class="fa fa-cloud-upload"></i> Upload
								License</label>
								<input id="file-upload" type="file"/>
					            <br><br>
				        </div>
		                <button class="bottomLogin button button-accent" type="submit" id="register">REGISTER</button>
	           	 	</div>
	        	</form>
	    	</div>
	</section>
	
	<div id="about" class="container">
	<section class="home-about">
		<div class="home-about-textbox">
			<h1>About Us</h1>
			<br>
			<p><strong>We ignite opportunity by setting the world in motion </strong></p>
			<p>	Good things happen when people can move, whether across town or towards their dreams. Opportunities appear, open up, become reality. What started as a way to tap a button to get a ride has led to billions of moments of human connection as people around the world go all kinds of places in all kinds of ways with the help of our technology.  </p>
			<br>
			<a href="#" class="button button-accent">Get Details</a>
		</div>

	</section>
	</div>

	<section id="gallery" class="portfolio">
		<h1>Gallery</h1>

		<!-- 1 -->
		<figure class="port-item">
			<img src="img/g1.jpg" alt="portfolio item">
		</figure>

		<!-- 2 -->
		<figure class="port-item">
			<img src="img/g2.jpg" alt="portfolio item">
		</figure>

		<!-- 3 -->
		<figure class="port-item">
			<img src="img/g3.jpg" alt="portfolio item">
		</figure>

		<!-- 4 -->
		<figure class="port-item">
			<img src="img/g4.jpg" alt="portfolio item">
		</figure>

		<!-- 5 -->
		<figure class="port-item">
			<img src="img/g5.jpg" alt="portfolio item">
		</figure>

		<!-- 6 -->
		<figure class="port-item">
			<img src="img/g6.jpg" alt="portfolio item">
		</figure>
	</section>


<section class="cta" id="contact">
	<div class="container">
		<h1 class="title title-cta">We're here to help!
			 <span>Support is just a few taps away.</span>
		</h1>
		<div class="support" id="support"> 
			<ul class="unstyled-list">
				<li><a href=""> <i class="fas fa-home"></i> SVCE, Chennai </a></li>
				<li><a href=""> <i class="fas fa-phone"></i> 999 999 999 </a></li>
				<li><a href=""> <i class="fas fa-envelope"></i> support@uber.com </a></li>
			
		</div>	
	</div>
</section>

	<footer>
		
		<div class="col-1">
			<ul class="unstyled-list">
				<li><h2><strong>Quick links</strong></h2><br></li>
				<li>Jobs</li>
				<li>Brand Assets</li>
				<li>Investor Relations</li>
				<li>Terms of service</li>
			</ul>
		</div>
		<div class="col-1">
			<ul class="unstyled-list">
				<li><h2><strong>Features</strong></h2><br></li>
				<li>Jobs</li>
				<li>Brand Assets</li>
				<li>Investor Relations</li>
				<li>Terms of service</li>
			</ul>
		</div>
		<div class="col-1">
			<ul class="unstyled-list">
				<li><h2><strong>Resources</strong></h2><br></li>
				<li>Guides</li>
				<li>Research</li>
				<li>Experts</li>
				<li>Agencies</li>
			</ul>
		</div>
		<div class="col-1">
			<ul class="unstyled-list">
				<li><h2><strong>Follow Us</strong></h2><br></li>
				<li>Let us be social</li>
				<li><i class="fab fa-facebook-f"></i> <i class="fab fa-twitter"></i> <i class="fab fa-instagram"></i> <i class="fab fa-youtube"></i></li>
			</ul>
		</div>
	</footer>
</body>
	<script src="js/index.js"></script>
</html>