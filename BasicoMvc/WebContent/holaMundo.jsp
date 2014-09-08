<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Hola mundo</title>
</head>
<body>
	Hola mundo desde la JSP!
	<br/>
	Hola <%= request.getAttribute("nombre") %><br/>
	<c:out value="${mensaje}"/>
</body>
</html>