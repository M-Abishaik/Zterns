<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="com.zilker.config.Config"%>
	<h2>Welcome to Registration</h2>
	<form action="${Config.BASE_PATH}RegistrationServlet" method="post">
		<label for="Name">Name: </label>
		<input type="text" id="Name" name="Name"><hr />
		<label for="Mail">E-mail: </label>
		<input type="text" id="Mail" name="Email"><hr />
		<label for="Contact">Contact: </label>
		<input type="text" id="Contact" name="Contact"><hr />
		<label for="Role">Role: </label>
		<input type="text" id="Role" name="Role"><hr />
		<label for="Password">Password: </label>
		<input type="text" id="Password" name="Password"><hr />
		<input type="Submit" value="Submit">
	</form>
</body>
</html>