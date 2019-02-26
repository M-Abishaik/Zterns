<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.zilker.config.Config"%>
    <%@ page import="com.zilker.bean.PostConfirm"%>
    
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Happy Trip</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/rider.css">
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
	</head>
	<body id="bookridepage">

	<header>
		<a href="index.html"><img src="${Config.BASE_PATH}/img/logouber2.png" alt="Taxi logo" class="logo"></a>
	</header>
	
    <div style="position: relative; float: left; margin-top: 10em; margin-left: 5em; width:800px; height:400px;" id="map">
    </div>
    
    <% 	PostConfirm postConfirm = new PostConfirm();
	postConfirm = (PostConfirm)request.getAttribute("postConfirmInvoice");%>

    <div class="bookride-container">
        <h2> Booking ID :<span><%=postConfirm.getBookingID()%></span></h2>
    	<h2> Start DateTime :<span><%=postConfirm.getStartTime()%></span></h2>
    	<h2> Source :<span><%=postConfirm.getSource()%></span></h2>
    	<h2> Destination :<span><%=postConfirm.getDestination()%></span></h2>
    	<h2> Driver :<span><%=postConfirm.getDriver()%></span></h2>
    	<h2> Cab :<span><%=postConfirm.getCab()%></span></h2>
    	<h2> Price :<span><%=postConfirm.getPrice()%></span></h2>
    	<br> <br>
    	<button class="button button-accent bookride-cancel">Cancel Booking</button>
    </div>
    
    
    <div class="hideMe">
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
    <script src="${Config.BASE_PATH}/js/rider.js"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDh4Tci8prXxO2o2aof6IMmKcPBsEsrb6o&callback=initMap"
        async defer></script>
</html>