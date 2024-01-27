<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registration</title>
	<link rel="stylesheet" type="text/css" href="/webjars/bootstrap/css/bootstrap.min.css"/>
	
</head>
<body>
	<div class="container text-center">
		<div><h1>User Registration - Sign up</h1></div>
		<div>
			<form:form action="/registered" method="POST" style="max-width: 600px; margin: 0 auto; " modelAttribute="user">
				
				<div class="m-3">
					<div class="form-group row">
						<form:label path="username" class="col-form-label col-4">Username</form:label>
						<div class="col-6">
							<form:input type="text" class="form-control"  required="required" path="username"/>
						</div>
					</div>
				
				
				
					<div class="form-group row">
						<form:label path="password" class="col-form-label col-4">Password</form:label>
						<div class="col-6">
							<form:input required="required" type="password" class="form-control" path="password"
							 minlength="3" maxlength="10"/>
						</div>
					</div>
				
				
				
					<div class="form-group row">
						<form:label path="role" class="col-form-label col-4">User Role</form:label>
						<div class="col-6">
							<form:input type="text" class="form-control" required="required" path="role"
							minlength="3" maxlength="10"/>
						</div>
					</div>
				
				
				

				
				<div>
					<button type="submit" class="btn btn-primary">Sign Up</button>
				</div>
					
				</div>
				
				
					
			</form:form>
			
		</div>
		
		
	</div>
	
	
	
</body>
</html>