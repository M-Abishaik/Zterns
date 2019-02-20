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

var slider = document.getElementById("myRange");
var output = document.getElementById("demo");
output.innerHTML = slider.value; // Display the default slider value

// Update the current slider value (each time you drag the slider handle)
slider.oninput = function() {
  output.innerHTML = this.value;
} 