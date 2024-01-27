<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> 
<%@ include file="home.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update User</title>
</head>
<body>
	<div class="container text-center">
        <h2>Update User</h2>
        <form:form action="/saveUpdated" method="post" modelAttribute="user">
            <table class="table table-striped table-bordered"">
                <tr>
                    <td>ID: </td>
                    <td>${updateUser.id}
                        <form:input readonly="readonly" path="id" value="${updateUser.id}"/>
                    </td>
                </tr>        
                <tr>
                    <td>Name: </td>
                    <td><form:input path="username" value="${updateUser.username}" /></td>
                </tr>
                <tr>
                    <td>Password: </td>
                    <td><form:input path="password" value="${updateUser.password}"/></td>
                </tr>
                
                <tr>
                    <td>Role: </td>
                    <td><form:input path="role" value="${updateUser.role}"/></td>
                </tr>
                
                <tr>
                    <td colspan="2"><input type="submit" value="Save"></td>
                </tr>                    
            </table>
        </form:form>
    </div>
</body>
</html>