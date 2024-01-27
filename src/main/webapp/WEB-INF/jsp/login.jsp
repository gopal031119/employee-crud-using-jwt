<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div class="container text-center">
		<div><h1>Login / Signin</h1></div>
		<div>
			<form action="/authenticate" method="POST" style="max-width: 600px; margin: 0 auto; " modelAttribute="newlogin" >
				<div class="m-3">
					<div class="form-group row">
						<label path="username" class="col-form-label col-4">Username</label>
						<div class="col-6">
							<input type="text" class="form-control"  required="required" name="username"/>
						</div>
					</div>
					<div class="form-group row">
						<label path="password"  class="col-form-label col-4">Password</label>
						<div class="col-6">
							<input required="required" type="password"  class="form-control" name="password"
							 minlength="3" maxlength="10"/>
						</div>
					</div>
				<div>
					<button type="submit" class="btn btn-primary">Login</button>
				</div>
				</div>		
			</form>
			
			
			
			
		</div> 
	</div>
</body>
</html>