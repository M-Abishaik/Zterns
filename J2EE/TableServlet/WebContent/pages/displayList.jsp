<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Display Page</title>
</head>
<body>
	<%if(request.getAttribute("name")!=null){ %>
	<h2>Registration successful for user ${requestScope.name}</h2>
	<% }else{%>
	<h2>Request object not received</h2>
	<%}%>
	
	<table border="2">
		<tr>
			<td>Username</td>
			<td>Mail</td>
			<td>Contact</td>
			<td>Role</td>
			<td>Password</td>
		</tr>
	
	<%@ page import="com.zilker.bean.User"%>
	<%@ page import="com.zilker.servlet.RegistrationServlet"%>
	<%@ page import="java.util.ArrayList" %>
	
	<% 	
		ArrayList<User> user = null;
		User object = null;
		int size = -1;
		int i = 0;
		
		try{
			user = new ArrayList<User>();
			user = (ArrayList<User>) request.getAttribute("userList");
			
			size = user.size();
			for(i=0;i<size;i++) {
				object = user.get(i);
			
			
	%>
	
			<tr><td><%=object.getName()%></td>
			<td><%=object.getEmail()%></td>
			<td><%=object.getContact()%></td>
			<td><%=object.getRole()%></td>
			<td><%=object.getPassword()%></td></tr>
	<%}%>
</table>
	<%}catch(Exception e){	
		e.printStackTrace();
	}%>
	
</body>
</html>