<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.zilker.config.Config"%>
<%@ page import="com.zilker.bean.BookingResponse"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Driver Trips</title>
<link rel="stylesheet" href="${Config.BASE_PATH}/css/driver.css">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.1/css/all.css"
	integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
	<c:if test="${onGoingResponse != null}">
		<body onload="upcoming()">
	</c:if>

	<c:if test="${onCompleteResponse != null}">
		<body onload="completed()">
	</c:if>

	<c:if test="${onCancelResponse != null}">
		<body onload="cancelled()">
	</c:if>

	<header>
		<a href="#"><img src="${Config.BASE_PATH}/img/logouber2.png"
			alt="Taxi logo" class="logo"></a>
	</header>
	<div class="vertical-menu">
		<a class="drivernav" style="cursor: pointer"
			href="${Config.BASE_PATH}dashboard/driver">Dashboard</a> <a
			class="drivernav" style="cursor: pointer">My Trips</a> <a
			class="drivernav" href="${Config.BASE_PATH}dashboard/driver/profile">Profile</a>
		<a class="drivernav" href="${Config.BASE_PATH}logout">Logout</a>
	</div>

	<div class="container3 driver">
		<div class="filter-drivertrips">
			<label>Filter by: </label> <a
				style="cursor: pointer; text-align: center;"
				class="button button-accent upcoming" onclick="upcoming()"
				href="${Config.BASE_PATH}dashboard/driver/rides/ongoing">Ongoing</a>
			<a style="cursor: pointer; text-align: center;"
				class="button button-accent completed" onclick="completed()"
				href="${Config.BASE_PATH}dashboard/driver/rides/completed">Completed</a>
			<a style="cursor: pointer; text-align: center;"
				class="button button-accent cancelled" onclick="cancelled()"
				href="${Config.BASE_PATH}dashboard/driver/rides/cancelled">Cancelled</a>
		</div>

		<div id="drivertrips">
			<div class="row">

				<c:if test="${onGoingResponse != null}">

					<div class="column" id="upcoming">
						<button class="collapsible">
							<h2 id="datetime">
								<c:out value="${onGoingResponse.getStartTime()}" />
							</h2>
							<h2 id="rs">
								Rs
								<c:out value="${onGoingResponse.getPrice()}" />
								Fares might change
							</h2>
							<h4 id="place">Chennai</h4>
							<h4 id="cash">Wallet</h4>
						</button>
						<div class="content">
							<h3 id="with">
								Your trip with
								<c:out value="${onGoingResponse.getDriver()}" />
							</h3>
							<h3 id="fromto">
								Source:
								<c:out value="${onGoingResponse.getSource()}" />
							</h3>
							<h3 id="fromto">
								Destination:
								<c:out value="${onGoingResponse.getDestination()}" />
							</h3>
							<form action="${Config.BASE_PATH}CancelBookingServlet"
								method="post">
								<input type="hidden" name="travelInvoiceBookingID"
									value=<c:out value="${onGoingResponse.getBookingID()}"/>>
							</form>
						</div>
					</div>
				</c:if>


				<c:if test="${onCompleteResponse != null}">

					<c:forEach items="${onCompleteResponse}" var="completeRides">
						<div id="completed">
							<div class="column">
								<button class="collapsible">
									<h2 id="datetime">
										<c:out value="${completeRides.getStartTime()}" />
									</h2>
									<h2 id="rs">
										Rs.
										<c:out value="${completeRides.getPrice()}" />
									</h2>
									<h4 id="place">Chennai</h4>
									<h4 id="cash">Cash</h4>
								</button>
								<div class="content">
									<h3 id="with">
										Your trip with
										<c:out value="${completeRides.getDriver()}" />
									</h3>
									<h3 id="fromto">
										Source:
										<c:out value="${completeRides.getSource()}" />
									</h3>
									<h3 id="fromto">
										Destination:
										<c:out value="${completeRides.getDestination()}" />
									</h3>
								</div>

								<form id="user-rating-form" method="post"
									action="${Config.BASE_PATH}ride/rate">
									<input type="hidden" name="travelInvoiceBookingID"
										value=<c:out value="${completeRides.getBookingID()}"/>>

									<span class="user-rating"> <input
										id="booking-id-<c:out value="${completeRides.getBookingID()}"/>-5"
										type="radio" name="rating" value="5"><span
										class="star"></span> <input
										id="booking-id-<c:out value="${completeRides.getBookingID()}"/>-4"
										type="radio" name="rating" value="4"><span
										class="star"></span> <input
										id="booking-id-<c:out value="${completeRides.getBookingID()}"/>-3"
										type="radio" name="rating" value="3"><span
										class="star"></span> <input
										id="booking-id-<c:out value="${completeRides.getBookingID()}"/>-2"
										type="radio" name="rating" value="2"><span
										class="star"></span> <input
										id="booking-id-<c:out value="${completeRides.getBookingID()}"/>-1"
										type="radio" name="rating" value="1"><span
										class="star"></span>
									</span>
								</form>

							</div>
						</div>
					</c:forEach>
				</c:if>

				<c:if test="${onCancelResponse != null}">
					<c:forEach items="${onCancelResponse}" var="cancelledRides">

						<div class="column" id="cancelled">
							<button class="collapsible">
								<h2 id="datetime">
									<c:out value="${cancelledRides.getStartTime()}" />
								</h2>
								<h2 id="rs">
									Rs.
									<c:out value="${cancelledRides.getPrice()}" />
								</h2>
								<h4 id="place">Chennai</h4>
								<h4 id="cash">Cash</h4>
							</button>
							<div class="content">
								<h3 id="with">
									Your trip with
									<c:out value="${cancelledRides.getDriver()}" />
								</h3>
								<h3 id="fromto">
									Source:
									<c:out value="${cancelledRides.getSource()}" />
								</h3>
								<h3 id="fromto">
									Destination:
									<c:out value="${cancelledRides.getDestination()}" />
								</h3>
							</div>
						</div>
					</c:forEach>

				</c:if>
			</div>
		</div>
	</div>
</body>
<script src="${Config.BASE_PATH}/js/driver.js"></script>
</html>