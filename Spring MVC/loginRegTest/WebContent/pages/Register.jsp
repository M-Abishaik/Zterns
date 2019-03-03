<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Registration</title>
        </head>
        <body>
           <form id="register" modelAttribute="user" method="post" action="http://localhost:8081/loginRegTest/registerUser">
				<label for="Name">Name: </label>
				<input type="text" id="Name" name="username"><hr />
		
				<label for="Mail">E-mail: </label>
				<input type="text" id="Mail" name="email"><hr />
		
				<label for="Contact">Contact: </label>
				<input type="text" id="Contact" name="contact"><hr />
		
				<label for="Role">Role: </label>
				<input type="text" id="Role" name="role"><hr />
		
				<label for="Password">Password: </label>
				<input type="text" id="Password" name="password"><hr />
		
				<input type="submit" value="Submit">
		</form>
        </body> 
</html>