<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
<form method="post" modelAttribute="login" action="http://localhost:8081/loginRegTest/loginUser">  
  
Contact:<input type="text" name="contact" required/><br/><br/>   
Password:<input type="password" name="password" required/><br/><br/>  
 
<br/><br/>  
<input type="submit" value="login"/>  
  
</form> 
</body>
</html>