<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Formulario ModelAttribute</title>
</head>
<body>
	<h1>Form con método antiguo</h1>
	<form action="clic.do" method="post">
		Nombre: <input type="text" name="nombre" value="${personaForm.nombre}"/><br/>
		Apellido: <input type="text" name="apellido" value="${personaForm.apellido}"/><br/>
		Edad: <input type="text" name="edad" value="${personaForm.edad}"/><br/>
		<input type="submit" value="clic"/>
	</form>
	
	<h1>Form nuevo (con taglibs)</h1>
	<!-- 
		commandName: Nombre del ModelAttribute definido en el Controller
	 -->
	<form:form action="clic.do" commandName="personaForm">
		<form:errors path="*" cssStyle="border: red;"/>
	
		Nombre: <form:input path="nombre"/>
		<form:errors path="nombre" cssStyle="font-color: red;"/>
		<br/>
		Apellido: 
		<input type="text" name="apellido" value="${personaForm.apellido}"/>
		<form:errors path="apellido" cssStyle="font-color: red;"/>
		<br/>
		Edad: 
		<form:input path="edad"/>
		<form:errors path="edad" cssStyle="font-color: red;"/>		
		<br/>
		<input type="submit" value="clic"/>
	</form:form>
	
</body>
</html>