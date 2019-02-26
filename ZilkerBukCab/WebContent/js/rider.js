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
	
	let getParams = "http://localhost:8081/ZilkerBukCab/RateRideServlet";
	
	if(window.XMLHttpRequest){
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
		           
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

function updateBooking(id) {
	console.log("clicked");
	console.log(id);
}

function cancelBooking(id){
	console.log("cancelled");
}



































































