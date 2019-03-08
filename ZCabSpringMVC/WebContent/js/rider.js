function riderviewbook() {
	var a = document.getElementById("bookride");
	var b = document.getElementById("riderprofile");
	var c = document.getElementById("ridertrips");
	// var d = document.getElementById("logout");
	a.style.display = "block";
	b.style.display = "none";
	c.style.display = "none";
	// d.style.display = "none";
}

function riderviewprofile() {
	var a = document.getElementById("bookride");
	var b = document.getElementById("riderprofile");
	var c = document.getElementById("ridertrips");
	// var d = document.getElementById("logout");
	a.style.display = "none";
	b.style.display = "block";
	c.style.display = "none";
	// d.style.display = "none";
}

function riderviewtrips() {
	var a = document.getElementById("bookride");
	var b = document.getElementById("riderprofile");
	var c = document.getElementById("ridertrips");
	// var d = document.getElementById("logout");
	a.style.display = "none";
	b.style.display = "none";
	c.style.display = "block";
	// d.style.display = "none";
}


function upcoming () {
	var a = document.getElementById("upcoming");
	var b = document.getElementById("completed");
	var c = document.getElementById("cancelled");
	a.style.display = "block";
	b.style.display = "none";
	c.style.display = "none";
}

function completed () {
	
	let getParams = "http://localhost:8081/ZCabSpringMVC/riderratedrides";
	
	if(window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
		          console.log(xmlhttp.responseText); 
		          let response = JSON.parse(xmlhttp.responseText);
		          for(let i=0;i<response.bookingid.length;i++) {
		        	  let rateButton = document.getElementById('rate-button-'+response.bookingid[i].bookingID);
		        	  rateButton.style.display='none';
		        	  for(let j=1;j<=response.bookingid[i].rating;j++) {
		        		  let elem = document.getElementById('booking-id-'+response.bookingid[i].bookingID+'-'+j);
		        		  elem.checked = true;
		        	  }
		          }		          
		}
	};
	
	xmlhttp.open('GET', getParams, true);
	xmlhttp.send();
	
	var a = document.getElementById("upcoming");
	var b = document.getElementById("completed");
	var c = document.getElementById("cancelled");
	a.style.display = "none";
	b.style.display = "block";
	c.style.display = "none";

}

function cancelled () {
	var a = document.getElementById("upcoming");
	var b = document.getElementById("completed");
	var c = document.getElementById("cancelled");
	a.style.display = "none";
	b.style.display = "none";
	c.style.display = "block";
}

