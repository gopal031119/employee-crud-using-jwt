<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file="home.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All Users</title>
	
	<script type="text/javascript">
		function confirmUser(){
			var confirmMessage = "Are sure you want to delete the user?";
			var go = "http://localhost:8080"+document.getElementById("delete").value;
			var message = "Action Was Cancelled By User";
			if (confirm(confirmMessage)) {
				 
			      window.location = go;
			 
			  } else {
			 
			       alert(message);
			 
			  }
		}
	</script>
	
	
</head>
<body>
	<div class="container text-center">
		<div><h1>All Users</h1></div>
		<div>
			
			
		</div>
		<div>
			<table class="table table-striped table-bordered">
				
				<thead class="thead-dark"></thead>
					<tr>
						<th>User Id</th>
						<th>First Name</th>
						<th></th>
						<th></th>
					</tr>
					<c:forEach var="user" items="${userList}" varStatus="status">
						<tr>
							<td>${user.id}</td>
							<td>${user.username}</td>
							<td>
								<a href="/update?id=${user.id}">update</a>
							</td>
							<td><button value="/delete?id=${user.id}" id="delete" onclick="confirmUser()">delete</button></td>
						</tr>
					</c:forEach>
			</table>
		</div>
		
		
	</div>
	
	
	
</body>
</html>