<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.PostConfirm"%>
<%@ page import="com.zilker.bean.User"%>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Rider Dashboard</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/rider.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	</head>
	
	<%if(request.getAttribute("userProfile")!=null){ %>
		<body onload="riderviewprofile()">
		<%} %>
<body onload="riderdash()" id="rider">
	
<header>
		<a href="#"><img src="img/logouber2.png" alt="Taxi logo" class="logo"></a>
	</header>
	<div class="vertical-menu">
  		<a class="ridernav" onclick="riderdash()">Dashboard</a>
  		<a class="ridernav" onclick="riderviewprofile()" href="${Config.BASE_PATH}dashboard/rider">Profile</a>
        <a class="ridernav" href="${Config.BASE_PATH}dashboard/rider/rides/ongoing">My Trips</a>
		<a class="logout" href="${Config.BASE_PATH}logout">Logout</a>
	</div> 
 	<div class="container3 rider"> 		
        <div id="riderdash">
	         <div style="position: relative; float: left; margin-top: 8em; margin-left: 2em; width:500px; height:400px;" id="map">
	   	</div> 
	   		
	   		<% 	if(request.getAttribute("postConfirmInvoice")!=null){

	   			PostConfirm postConfirm = new PostConfirm();
				postConfirm = (PostConfirm)request.getAttribute("postConfirmInvoice");%> 
				 
	   		<div class="dashboard-container" style="margin-top:7%;">
		    	<h2> Booking ID :<span><% if(postConfirm!=null){%><%=postConfirm.getBookingID()%><%}%></span></h2>
    			<h2> Start DateTime :<span><% if(postConfirm!=null){%><%=postConfirm.getStartTime()%><%}%></span></h2>
    			<h2> Source :<span><% if(postConfirm!=null){%><%=postConfirm.getSource()%><%}%></span></h2>
    			<h2> Destination :<span><% if(postConfirm!=null){%><%=postConfirm.getDestination()%><%}%></span></h2>
    			<h2> Driver :<span><% if(postConfirm!=null){%><%=postConfirm.getDriver()%><%}%></span></h2>
    			<h2> Cab :<span><% if(postConfirm!=null){%><%=postConfirm.getCab()%><%}%></span></h2>
    			<h2> Price :<span><% if(postConfirm!=null){%><%=postConfirm.getPrice()%><%}%></span></h2>
		    	<br><br> 
				<form action="${Config.BASE_PATH}ride/cancel" method="post">
    			<input type="hidden" name="travelInvoiceBookingID" value=<%=postConfirm.getBookingID()%>>
    			<button class="button button-accent bookride-cancel" type="submit">Cancel Booking</button>
    			</form>		 
    		</div>
        </div>
        
        <%}%>

			<% 	User user = new User();
        	user = (User)request.getAttribute("userProfile");%>
        	
        <form action="${Config.BASE_PATH}users/update" id="riderprofile" onsubmit="return validateUpdateProfile()" method="post">
        	<input type="hidden" name="profileType" value="0"/>
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
	
	<div class="hideMe" style="display: none;S">
		<span id="sourceZip"><%= request.getAttribute("sourceZip")%></span>
		<span id="destinationZip"><%= request.getAttribute("destinationZip")%></span>
	</div>
	
	 <script>
    
    function initMap() {
    	
    	let sourceZip = document.getElementById("sourceZip").innerHTML;
        let destinationZip = document.getElementById("destinationZip").innerHTML;
        
        // Map options
        let options = {
            zoom: 8,
            center: { lat: 13.0827, lng: 80.2707 }
        }

        // Add Map
        let map = new google.maps.Map(document.getElementById("map"), options);
        
        let display = new google.maps.DirectionsRenderer();
        let services = new google.maps.DirectionsService();
        display.setMap(map);
        
        let request ={
				origin : sourceZip,
				destination:destinationZip,
				travelMode: 'DRIVING'
			};
			services.route(request,function(result,status){
				if(status =='OK'){
					display.setDirections(result);
				}
			});
    }
    </script>
	

</body>
    <script src="${Config.BASE_PATH}/js/dashrider.js"></script>
     <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-tHqrPlMWQqYYYGzu6s7u5TeQbwxtrbg&callback=initMap"
        async defer></script>
</html>