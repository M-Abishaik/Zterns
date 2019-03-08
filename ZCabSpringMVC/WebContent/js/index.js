function viewlogin() {
  var x = document.getElementById("signup");
  var y = document.getElementById("login");
  console.log(x);
  x.style.display = "none";
  y.style.display = "block";
}

function viewsignup() {
  var y = document.getElementById("signup");
  var x = document.getElementById("login");
  y.style.display = "block";
  x.style.display = "none";
}

function riderdriver(that) {
	if (that.value == "driver") {
		document.getElementById("driverlicense").style.display = "block";

	}
	else {
		document.getElementById("driverlicense").style.display = "none";
	}
}