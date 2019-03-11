function driverviewlicense() {
	var a = document.getElementById("driverlicense");
	var b = document.getElementById("driverprofile");
	// var c = document.getElementById("drivertrips");
	var d = document.getElementById("drivercompleted");
	a.style.display = "block";
	b.style.display = "none";
	// c.style.display = "none";
	d.style.display = "none";
}

function driverviewprofile() {
	var a = document.getElementById("driverlicense");
	var b = document.getElementById("driverprofile");
	// var c = document.getElementById("drivertrips");
	var d = document.getElementById("drivercompleted");
	a.style.display = "none";
	b.style.display = "block";
	// c.style.display = "none";
	d.style.display = "none";
}

function driverviewtrips() {
	var a = document.getElementById("driverlicense");
	var b = document.getElementById("driverprofile");
	// var c = document.getElementById("drivertrips");
	var d = document.getElementById("drivercompleted");
	a.style.display = "none";
	b.style.display = "none";
	// c.style.display = "block";
	d.style.display = "none";
}

function drivercompleted() {
	var a = document.getElementById("driverlicense");
	var b = document.getElementById("driverprofile");
	// var c = document.getElementById("drivertrips");
	var d = document.getElementById("drivercompleted");
	a.style.display = "none";
	b.style.display = "none";
	// c.style.display = "none";
	d.style.display = "block";
}

// function all () {
// var a = document.getElementById("upcoming");
// var b = document.getElementById("completed");
// var c = document.getElementById("cancelled");
// a.style.display = "block";
// b.style.display = "block";
// c.style.display = "block";
// var d = document.getElementById("all");
// d.style.display = "block";
// }

function upcoming() {
	var a = document.getElementById("upcoming");
	var b = document.getElementById("completed");
	var c = document.getElementById("cancelled");
	a.style.display = "block";
	b.style.display = "none";
	c.style.display = "none";
}

function completed() {

	let getParams = "http://localhost:8081/ZCabSpringMVC/driverratedrides";

	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			console.log(xmlhttp.responseText);
			let response = JSON.parse(xmlhttp.responseText);
			for (let i = 0; i < response.bookingid.length; i++) {
				for (let j = 1; j <= response.bookingid[i].rating; j++) {
					let elem = document.getElementById('booking-id-'
							+ response.bookingid[i].bookingID + '-' + j);
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

function cancelled() {
	var a = document.getElementById("upcoming");
	var b = document.getElementById("completed");
	var c = document.getElementById("cancelled");
	a.style.display = "none";
	b.style.display = "none";
	c.style.display = "block";
}
