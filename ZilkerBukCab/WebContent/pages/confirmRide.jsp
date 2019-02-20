<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<body onload="setValue()" id="bookridepage">
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.TravelInvoice"%>
<%@ page import="com.zilker.bean.DisplayInvoice"%>


	<header>
		<a href="index.html"><img src="${Config.BASE_PATH}/img/logouber2.png" alt="Taxi logo" class="logo"></a>
	</header>
	
    <div id="map">    
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
    	<h2> Price :<span id="price">2000</span></h2>
    	<br> <br>
    	<form action="${Config.BASE_PATH}ConfirmBookingServlet" method="post">
		<input type="hidden" name="travelInvoiceCustomerID" value=<%=travelInvoice.getCustomerID()%>>
		<input type="hidden" name="travelInvoiceDriverID" value=<%=travelInvoice.getDriverID()%>>
		<input type="hidden" name="travelInvoiceCabID" value=<%=travelInvoice.getCabID()%>>
		<input type="hidden" name="travelInvoiceSourceID" value=<%=travelInvoice.getSourceID()%>>
		<input type="hidden" name="travelInvoiceDestinationID" value=<%=travelInvoice.getDestinationID()%>>
		<input type="hidden" name="travelInvoiceStartTimeDate" value="${travelInvoice.getFormattedTime()}">
		<input type="hidden" id="cost" name="travelInvoicePrice" value="">
		<input type="hidden" id="distance" name="travelInvoiceDistance" value="">
    	<button class="button button-accent bookride-confirm" type="submit">Confirm Booking</button>
    	</form>
    	<button class="button button-accent bookride-cancel">Cancel Booking</button>
    </div>
        	
	<!-- <div class="hideMe">
		<span id="customerID"><%= travelInvoice.getCustomerID()%></span>
		<span id="driverID"><%= travelInvoice.getDriverID()%></span>
		<span id="cabID"><%= travelInvoice.getCabID()%></span>
		<span id="sourceID"><%= travelInvoice.getSourceID()%></span>
		<span id="destinationID"><%= travelInvoice.getDestinationID()%></span>
		<span id="startTimeDate"><%= travelInvoice.getFormattedTime()%></span>
	</div> -->

	<script>
	
	function setValue(){
	document.getElementById("cost").value = 2000;
	document.getElementById("distance").value = 100;
	}
	
    let distance = "";

	
	function confirmBooking() {
		
		let xmlhttp = "";
		let customerID = document.getElementById("customerID").innerHTML;
		let driverID = document.getElementById("driverID").innerHTML;
		let cabID = document.getElementById("cabID").innerHTML;
		let sourceID = document.getElementById("sourceID").innerHTML;
		let destinationID = document.getElementById("destinationID").innerHTML;
		let startTimeDate = document.getElementById("startTimeDate").innerHTML;
		let price = document.getElementById("price").innerHTML;
		
		
		distance = 1000;
		let postParams = "http://localhost:8081/ZilkerBukCab/TestConfirmBooking?customerID=" + customerID + "&driverID=" + driverID +
				"&cabID=" + cabID + "&sourceID=" + sourceID + "&destinationID=" + destinationID + "&startTimeDate=" + startTimeDate + "&price="
				+ price + "&distance=" + distance;
				
		if(window.XMLHttpRequest){
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange = function(){
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
			            console.log(this.responseText);
			}
		};		
		
		xmlhttp.open('POST', postParams, true);
		xmlhttp.send();
	}
	
	 function initMap() {

		 /* var src = document.getElementById("source").innerHTML;
		 if(src!==null){
		 console.log(src);
		 }else {
			 console.log(null);
		 } */
		 	 
		 // Map options
         let options = {
             zoom: 8,
             center: { lat: 13.0827, lng: 80.2707 }
         }

         // Add Map
         let map = new google.maps.Map(document.getElementById("map"), options);
         
         let source = document.getElementById("source").innerHTML;
         let destination =document.getElementById("destination").innerHTML;
         let spanPrice = document.getElementById("price");
         
         
         let sourceLatitude = "";
         let sourceLongitude = "";
         let destinationLatitude = "";
         let destinationLongitude = "";
         let price = "";
         let apiKey = "AIzaSyB_BbPL8SS692YzrmF3vFPyn5errmpo4Yg";
         
         fetch('https://maps.googleapis.com/maps/api/geocode/json?address='+source +'&key='+apiKey).then(function(res){
             console.log(res);

             res.json().then(response => {
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
                                 
                               if(distance>=100000 && distance<=110000){
                                	 price = 1000;
                                 } else if (distance>=110001 && distance<=115000){
                                	 price = 1500;
                                 } else{
                                	 price = 2000;
                                 }
                              	spanPrice.innerHTML = price;   
                              	
                              //Array of markers
                                var markers = [
                                    {
                                        coords: { lat: sourceLatitude, lng: sourceLongitude },
                                        iconImage: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png',
                                        content: '<h1>' + source + '</h1>'
                                    },
                                    {
                                        coords: { lat: destinationLatitude, lng: destinationLongitude },
                                        iconImage: 'https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png',
                                        content: '<h1>' + destination + '</h1>'
                                    }
                                ];
                              
                             // Loop through markers
                                for (var i = 0; i < markers.length; i++) {
                                    // Add marker
                                    addMarker(markers[i]);
                                }

                                // Add Marker Function
                                function addMarker(props) {
                                    var marker = new google.maps.Marker({
                                        position: props.coords,
                                        map: map,
                                        //icon:props.iconImage
                                    });

                                    // Set icon image
                                    marker.setIcon(props.iconImage);

                                    // Check content
                                    var infoWindow = new google.maps.InfoWindow({
                                        content: props.content
                                    });

                                    marker.addListener('click', function () {
                                        infoWindow.open(map, marker);
                                    });
                                }
                              
                             });
                             
                         });
                     });
                 });       
             });

     }).catch(function(error){
         console.log(error);
     });
         
         
	 }
     
	</script>

</body>
   	<script src="${Config.BASE_PATH}/js/rider.js"></script>
 	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyB_BbPL8SS692YzrmF3vFPyn5errmpo4Yg&callback=initMap"
        async defer></script>
</html>