<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.TravelInvoice"%>
<%@ page import="com.zilker.bean.DisplayInvoice"%>
<!DOCTYPE html>
<html>
<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Customer</title>
		<link rel="stylesheet" href="${Config.BASE_PATH}/css/rider.css">
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
</head>
<body id="bookridepage">
	<header>
		<a href="index.html"><img src="${Config.BASE_PATH}/img/logouber2.png" alt="Taxi logo" class="logo"></a>
	</header>
	
    <div style="position: relative; float: left; margin-top: 10em; margin-left: 5em; width:800px; height:400px;" id="map">    
    </div>
    
    <% 	DisplayInvoice displayInvoice =  new DisplayInvoice();
	displayInvoice = (DisplayInvoice)request.getAttribute("displayInvoice");%>
	
	
	<% 	TravelInvoice travelInvoice =  new TravelInvoice();
	travelInvoice = (TravelInvoice)request.getAttribute("travelInvoice");%>
    
    
    <div class="bookride-container">
    	<h2> Start DateTime :<span id="dateTime"><%=displayInvoice.getStartTime()%></span></h2>
    	<h2> Source :<span id="source"><%= displayInvoice.getSource()%></span></h2>
    	<h2> Destination :<span id="destination"><%= displayInvoice.getDestination()%></span></h2>
    	<h2> Driver Details :<span><%= displayInvoice.getDriver()%></span></h2>
    	<h2> Cab Details :<span><%= displayInvoice.getCab()%></span></h2>
    	<h2> Seats :<span><%= displayInvoice.getSeats()%></span></h2>
    	<h2> Distance :<span id="distance"></span> miles</h2>
    	<h2> Price :<span id="price"></span></h2>
    	<br> <br>
    	<form id="confirmRideForm" action="${Config.BASE_PATH}ride/confirm" method="post">
		<input type="hidden" name="travelInvoiceCustomerID" value=<%=travelInvoice.getCustomerID()%>>
		<input type="hidden" name="travelInvoiceDriverID" value=<%=travelInvoice.getDriverID()%>>
		<input type="hidden" name="travelInvoiceCabID" value=<%=travelInvoice.getCabID()%>>
		<input type="hidden" name="travelInvoiceSourceID" value=<%=travelInvoice.getSourceID()%>>
		<input type="hidden" name="travelInvoiceDestinationID" value=<%=travelInvoice.getDestinationID()%>>
		<input type="hidden" name="travelInvoiceStartTimeDate" value="${travelInvoice.getFormattedTime()}"> 
		<input type="hidden" name="travelInvoiceDistance" id="travelDistance"/> 
		<input type="hidden" name="travelInvoicePrice" id="travelPrice"/> 
    	<button class="button button-accent bookride-confirm" onclick="confirmBooking()" type="submit">Confirm Booking</button>
    	</form>
 		<form action="${Config.BASE_PATH}ride/cancel" method="get">
    	<button class="button button-accent bookride-cancel">Cancel Booking</button>
    	</form>
    </div>
        	
	<div class="hideMe">
		<span id="customerID"><%= travelInvoice.getCustomerID()%></span>
		<span id="driverID"><%= travelInvoice.getDriverID()%></span>
		<span id="cabID"><%= travelInvoice.getCabID()%></span>
		<span id="sourceID"><%= travelInvoice.getSourceID()%></span>
		<span id="destinationID"><%= travelInvoice.getDestinationID()%></span>
		<span id="startTimeDate"><%= travelInvoice.getFormattedTime()%></span>
		<span id="sourceZip"><%=request.getAttribute("source")%></span>
		<span id="destinationZip"><%=request.getAttribute("destination")%></span>
	</div>
	
		<script>
	
	function initMap() {
	 	 
		 // Map options
        let options = {
            zoom: 8,
            center: { lat: 13.0827, lng: 80.2707 }
        }

        // Add Map
        let map = new google.maps.Map(document.getElementById("map"), options);
        
        let source = document.getElementById("source").innerHTML;
        let destination = document.getElementById("destination").innerHTML;
        let sourceZip = document.getElementById("sourceZip").innerHTML;
        let destinationZip = document.getElementById("destinationZip").innerHTML;
        let spanPrice = document.getElementById("price");   
        let spanDistance = document.getElementById("distance");   
        
        let sourceLatitude = "";
        let sourceLongitude = "";
        let destinationLatitude = "";
        let destinationLongitude = "";
        let price = "";
        let apiKey = "AIzaSyA-tHqrPlMWQqYYYGzu6s7u5TeQbwxtrbg";
        
        fetch('https://maps.googleapis.com/maps/api/geocode/json?address='+source +'&key='+apiKey).then(function(res){
            console.log(res);

            res.json().then(response =>{
                console.log("Response ", response);
                sourceLatitude = response.results[0].geometry.location.lat;
                sourceLongitude = response.results[0].geometry.location.lng;
           
                console.log( sourceLatitude + " " + sourceLongitude);
                
                fetch('https://maps.googleapis.com/maps/api/geocode/json?address='+destination +'&key='+apiKey).then(function(res){
                    console.log(res);

                    res.json().then(response => {
                        console.log("Response ", response);
                        destinationLatitude = response.results[0].geometry.location.lat;
                        destinationLongitude = response.results[0].geometry.location.lng;
                   
                        console.log(destinationLatitude + " " + destinationLongitude);
                        
                        fetch("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + sourceLatitude + "," + sourceLongitude + "&destinations=" + destinationLatitude + "," + destinationLongitude + "&key=" + apiKey, {
                            method: "GET",
                            headers: {
                                "Access-Control-Allow-Origin": "*",
                                "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
                            },
                        }).then(function(res){
                            console.log(res);

                            res.json().then(response =>
                            {
                                console.log("Response ", response);
                                distance = response.rows[0].elements[0].distance.value;
                           
                                console.log(distance);
                                
                            	spanDistance.innerHTML = distance/100;
                                
                              if(distance>=100000 && distance<=110000){
                               	 price = 1000;
                                } else if (distance>=110001 && distance<=115000){
                               	 price = 1500;
                                } else{
                               	 price = 2000;
                                }
                             	spanPrice.innerHTML = price;   
                               
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
                             
                            });
                            
                        });
                    });
                });       
            });

    }).catch(function(error){
        console.log(error);
    });
        
        
	 }

	function confirmBooking() {
		
		let customerID = document.getElementById("customerID").innerHTML;
		let driverID = document.getElementById("driverID").innerHTML;
		let cabID = document.getElementById("cabID").innerHTML;
		let sourceID = document.getElementById("sourceID").innerHTML;
		let destinationID = document.getElementById("destinationID").innerHTML;
		let startTimeDate = document.getElementById("startTimeDate").innerHTML;
		let distance = document.getElementById("distance").innerHTML;
		let price = document.getElementById("price").innerHTML;
		
		console.log(customerID, driverID, cabID, sourceID, destinationID, startTimeDate, distance, price);
		
		let hiddenDistance = document.getElementById("travelDistance");
		let hiddenPrice = document.getElementById("travelPrice");
		
		hiddenDistance.value = distance;
		hiddenPrice.value = price;
		
		let form = document.getElementById("confirmRideForm");
		form.submit();
		
	}     
	</script>
    
</body>
 	<script src="${Config.BASE_PATH}/js/rider.js"></script>
 	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA-tHqrPlMWQqYYYGzu6s7u5TeQbwxtrbg&callback=initMap"
        async defer></script>
</html>