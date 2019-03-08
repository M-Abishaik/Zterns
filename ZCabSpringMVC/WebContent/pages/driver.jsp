<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.User"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Driver</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/driver.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	</head>
	
<%if(request.getAttribute("userProfile")!=null){ %>
		<body onload="driverviewprofile()">
		<%} %>
<body id="driver">
<%response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			if(session.getAttribute("userPhone")==null){
				response.sendRedirect("http://localhost:8081/ZilkerBukCab/index.jsp");
			}
		%>
			
	<header>
		<a href="#"><img src="img/logouber2.png" alt="Taxi logo" class="logo"></a>
	</header>
	
	<div class="vertical-menu">
  		<a class="drivernav" onclick="driverviewprofile()">Profile</a>
        <a class="drivernav" href="${Config.BASE_PATH}driver/rides/completed">My Trips</a>
        <a class="logout" href="${Config.BASE_PATH}logout">Logout</a>
	</div> 
 	<div class="container3 driver">

       <% 	User user = new User();
        	user = (User)request.getAttribute("userProfile");%>
        	
        <form action="${Config.BASE_PATH}users/update" id="driverprofile" onsubmit="return validateUpdateProfile()" method="post">
        	<input type="hidden" name="profileType" value="1"/>
        	<img class="my-profile-icon" src="${Config.BASE_PATH}/img/4.png">
        	<h2 class="prof-title"><% if(user!=null){%> <%=user.getUserName()%><%}%><br><span><% if(user!=null){%> <%=user.getContact()%><%}%></span></h2>
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
            <img class= "my-profile-img" src="${Config.BASE_PATH}/img/ride_1.jpg">
        </form> 
	</div>
</body>
<script src="js/driver.js"></script>
</html>