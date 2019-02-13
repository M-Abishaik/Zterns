function viewlogin() {
	let x = document.getElementById("signup");
	let y = document.getElementById("login");
	console.log(x);
	x.style.display = "none";
	y.style.display = "block";
}

function viewsignup() {
	let y = document.getElementById("signup");
	let x = document.getElementById("login");
	y.style.display = "block";
	x.style.display = "none";
}

function riderviewbook() {
	let a = document.getElementById("bookride");
	let b = document.getElementById("riderprofile");
	let c = document.getElementById("ridertrips");
	a.style.display = "block";
	b.style.display = "none";
	c.style.display = "none";
}

function riderviewprofile() {
	let a = document.getElementById("bookride");
	let b = document.getElementById("riderprofile");
	let c = document.getElementById("ridertrips");
	a.style.display = "none";
	b.style.display = "block";
	c.style.display = "none";
}

function riderviewtrips() {
	let a = document.getElementById("bookride");
	let b = document.getElementById("riderprofile");
	let c = document.getElementById("ridertrips");
	a.style.display = "none";
	b.style.display = "none";
	c.style.display = "block";
}

function driverviewlicense() {
	let a = document.getElementById("driverlicense");
	let b = document.getElementById("driverprofile");
	let c = document.getElementById("drivertrips");
	a.style.display = "block";
	b.style.display = "none";
	c.style.display = "none";
}

function driverviewprofile() {
	let a = document.getElementById("driverlicense");
	let b = document.getElementById("driverprofile");
	let c = document.getElementById("drivertrips");
	a.style.display = "none";
	b.style.display = "block";
	c.style.display = "none";
	
}

function driverviewtrips() {
	let a = document.getElementById("driverlicense");
	let b = document.getElementById("driverprofile");
	let c = document.getElementById("drivertrips");
	a.style.display = "none";
	b.style.display = "none";
	c.style.display = "block";
}

function validateRegistration() {

	let userName = document.getElementById("registerUsername").value;
	let userNameRegex = /^[a-zA-Z0-9]+$/;

	let userMobile = document.getElementById("registerMobile").value;
	let userMobileRegex = /^[6789]\d{9}$/;

	let userMail = document.getElementById("registerEmail").value;
	let userAddress = document.getElementById("registerAddress").value;

	let userZipCode = document.getElementById("registerZipCode").value;
	let userZipCodeRegex =  /^[1-9][0-9]{5}$/;

	let userPassword = document.getElementById("registerPassword").value;
	let userRePassword = document.getElementById("registerRePassword").value;

	if (!userNameRegex.test(userName)) {
		document.getElementById("userName").innerHTML = "*Please enter a username.";
		return false;
	}

	if (!userMobileRegex.test(userMobile)) {
		document.getElementById("userMobile").innerHTML = "*Please enter a valid phone number.";
		return false;
	}

	if (!userZipCodeRegex.test(userZipCode)) {
		document.getElementById("userZipCode").innerHTML = "*Please enter a valid zip code.";
		return false;
	}

	if (userPassword.length < 8 || userRePassword.length < 8) {
		document.getElementById("userPassword").innerHTML = "*Please enter a valid password.";
		return false;
	}

	if (userPassword !== userRePassword) {
		document.getElementById("userRePassword").innerHTML = "*Passwords don't match.";
		return false;
	}
	return true;
}

function validateLogin() {

	let userPhone = document.getElementById("loginPhone").value;
	let userPassword = document.getElementById("loginPassword").value;

	let userMobileRegex = /^[6789]\d{9}$/;

	if (!userMobileRegex.test(userPhone)) {
		document.getElementById("loginPhoneMessage").innerHTML = "*Please enter a valid phone number.";
		return false;
	}

	return true;
}

/*function validateRideRegistration() {

	let startDate = document.getElementById("date").value;
	
	let startTime = document.getElementById("time").value;
	
	//console.log(startDate);
	//console.log(startTime);
	
	if(!startDateRegex.test(startDate)){
		document.getElementById("RideDate").innerHTML = "*Please enter a valid date.";
		return false;
	}
	
	if(!startTimeRegex.test(startTime)){
		document.getElementById("RideTime").innerHTML = "*Please enter a valid time.";
		return false;
	}
	return true;
}*/

function validateUpdateProfile() {
	let updateEmail = document.getElementById("riderEmail").value;
	let updateAddress = document.getElementById("riderAddress").value;

	let updateZipcode = document.getElementById("riderZipcode").value;
	let riderZipCodeRegex = /^[1-9][0-9]{5}$/;

	let updatePassword = document.getElementById("riderPassword").value;

	if (!riderZipCodeRegex.test(updateZipcode)) {
		document.getElementById("updateZipcode").innerHTML = "*Please enter a valid zip code.";
		return false;
	}
	if (updatePassword.length < 8) {
		document.getElementById("updatePassword").innerHTML = "*Please enter a valid password.";
		return false;
	}

	return true;
}