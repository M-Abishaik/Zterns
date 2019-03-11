<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.User"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.zilker.bean.Address"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Rider</title>
<link rel="stylesheet" href="${Config.BASE_PATH}/css/rider.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
	integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>


<%
	if (request.getAttribute("userProfile") != null) {
%>
<body onload="riderviewprofile()">
	<%
		}
	%>

<body onload="riderviewbook()" id="rider">
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		if (session.getAttribute("userPhone") == null) {
			response.sendRedirect("http://localhost:8081/ZilkerBukCab/index.jsp");
		}
	%>

	<header>
		<a href="#"><img src="${Config.BASE_PATH}/img/logouber2.png"
			alt="Taxi logo" class="logo"></a>
	</header>
	<div class="vertical-menu">
		<a class="ridernav" onclick="riderviewbook()"
			href="${Config.BASE_PATH}locations">Book a ride</a> <a
			class="ridernav" onclick="riderviewprofile()"
			href="${Config.BASE_PATH}riders">Profile</a> <a class="ridernav"
			href="${Config.BASE_PATH}rider/rides/completed">My Trips</a> <a
			class="ridernav" href="${Config.BASE_PATH}logout">Logout</a>
	</div>
	<div class="container3 rider">

		<form action="${Config.BASE_PATH}book/ride" id="bookride"
			method="post">
			<h1>Book your Ride</h1>
			<label>Start Date </label> <input id="date" type="date"
				name="startDate" required><br> <span id="RideDate"></span>
			<label> Start Time </label><input id="time" type="time"
				name="startTime" required><br> <span id="RideTime"></span>
			<label>Source</label><select id="source" style="width: 60%"
				name="source" required>

				<%
					ArrayList<Address> address = null;
					Address object = null;
					int size = -1;
					String userPhone = "";
					int i = 0;

					try {
						address = new ArrayList<Address>();
						address = (ArrayList<Address>) request.getAttribute("addressList");
						userPhone = (String) session.getAttribute("userPhone");

						size = address.size();
						for (i = 0; i < size; i++) {
							object = address.get(i);
				%>

				<option><%=object.getAddress() + ", " + object.getZipCode()%></option>
				<%
					}
					} catch (Exception exception) {
					}
				%>
			</select> <label>Destination</label><select id="destination"
				style="width: 60%" name="destination" required>

				<%
					for (i = 0; i < size; i++) {
						object = address.get(i);
				%>
				<option><%=object.getAddress() + ", " + object.getZipCode()%></option>
				<%
					}
				%>

			</select> <label>Seats </label><select id="seats" name="seats" required>
				<option>3</option>
				<option>4</option>
				<option>5</option>
				<option>6</option>
				<option>7</option>
				<option>8</option>
			</select> <br>
			<br>
			<button class="bottomLogin button button-accent" type="submit"
				id="login">Book</button>
			<%
				if (request.getAttribute("errorMessage") != null) {
			%>
			<center>
				<p
					style="color: red; margin-left: -50%; margin-top: 3%; font-weight: bold;"><%=request.getAttribute("errorMessage")%></p>
			</center>
			<%
				}
			%>
			<img class="bookride-img-1" src="${Config.BASE_PATH}/img/g1.jpg">
		</form>

		<%
			User user = new User();
			user = (User) request.getAttribute("userProfile");
		%>

		<form action="${Config.BASE_PATH}users/update" id="riderprofile"
			onsubmit="return validateUpdateProfile()" method="post">
			<input type="hidden" name="profileType" value="0" /> <img
				class="my-profile-icon" src="${Config.BASE_PATH}/img/4.png">
			<h2 class="prof-title">
				<%
					if (user != null) {
				%>
				<%=user.getUserName()%>
				<%
					}
				%><br>
				<span>
					<%
						if (user != null) {
					%> <%=user.getContact()%>
					<%
						}
					%>
				</span>
			</h2>
			<label for="email"><b>Email ID</b></label> <input id="riderEmail"
				type="email" value=<%if (user != null) {%> <%=user.getMail()%> <%}%>
				name="email" required> <label for="address"><b>Address</b></label>
			<textarea name="address" id="riderAddress"
				placeholder="No. 1/111, Annex-15">
				<%
					if (user != null) {
				%> <%=user.getAddress()%>
				<%
					}
				%>
			</textarea>

			<label for="zipcode"><b>ZipCode</b></label> <input type="text"
				value=<%if (user != null) {%> <%=user.getZipCode()%> <%}%>
				id="riderZipcode" name="zipCode" required> <span
				id="updateZipcode"></span> <label for="psw"><b>Password</b></label>
			<input type="password" id="riderPassword" value=<%if (user != null) {%>
				<%=user.getPassword()%> <%}%> name="password" required> <span
				id="updatePassword"></span> <br>
			<br>
			<button class="bottomLogin button button-accent" type="submit"
				id="login">Save</button>
			<img class="my-profile-img" src="${Config.BASE_PATH}/img/ride_1.jpg">
		</form>

	</div>


</body>

<script src="${Config.BASE_PATH}/js/rider.js"></script>
</html>


