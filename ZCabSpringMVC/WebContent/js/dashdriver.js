function driverdash () {
	var a = document.getElementById("driverdash");
	var b = document.getElementById("driverprofile");
	var d = document.getElementById("drivercompleted");
	a.style.display = "block";
	b.style.display = "none";
	d.style.display = "none";
}

function driverviewprofile() {
	var a = document.getElementById("driverdash");
	var b = document.getElementById("driverprofile");
	var d = document.getElementById("drivercompleted");
	a.style.display = "none";
	b.style.display = "block";
	d.style.display = "none";
}



function drivercompleted() {
	var a = document.getElementById("driverdash");
	var b = document.getElementById("driverprofile");
	var d = document.getElementById("drivercompleted");
	a.style.display = "none";
	b.style.display = "none";
	d.style.display = "block";
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

