function riderdash() {
	var a = document.getElementById("riderdash");
	var b = document.getElementById("riderprofile");
	var c = document.getElementById("ridertrips");
	// var d = document.getElementById("logout");
	a.style.display = "block";
	b.style.display = "none";
	c.style.display = "none";
	// d.style.display = "none";
}

function riderviewprofile() {
	var a = document.getElementById("riderdash");
	var b = document.getElementById("riderprofile");
	var c = document.getElementById("ridertrips");
	// var d = document.getElementById("logout");
	a.style.display = "none";
	b.style.display = "block";
	c.style.display = "none";
	// d.style.display = "none";
}

function riderviewtrips() {
	var a = document.getElementById("riderdash");
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

