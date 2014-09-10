<%@page import="model.Persona"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Ordenador"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Ordenadores</title>
	<link rel="stylesheet" type="text/css" href="../css/comun.css">
	<style>
		.errores {
			background-color: red; 
			color: white; 
			margin-left: auto;
			margin-right: auto;
			width: 60%		
		}
	</style>
	<script>
		const AGREGAR = 0;
		const MODIFICAR = 1;
	
		var confirmar = function() {
			var confirmado = confirm("Está seguro de que desea eliminar la persona seleccionada?");
			return confirmado; // true o false
		};
		
		var guardar = function(op) {
			var form = document.forms.formOrdenador;
			
			if (op == AGREGAR)
				form.action = "agregar.per";
			else if (op == MODIFICAR)
				form.action = "modificar.per";
			
			form.submit();
		};
	</script>
</head>
<body>
	<h1>Ordenadores</h1>
	<%@ include file="/jsp/comun/cabecera.jsp" %>
	
	<form:form name="formOrdenador" method="post" commandName="ordenadorForm">
	<div class="errores">
		${errores}<!-- Estos errores vienen del model -->
		<form:errors path="*"/><!-- Estos errores vienen del BindingResult -->
	</div>

	<form:hidden path="inputId"/>
	<table class="tablaCentrada tablaFormulario">
		<tr>
			<td>Nombre:</td>
			<td>
				<input 
					type="text" 
					name="inputNombre" 
					value="${ordenadorForm.inputNombre}"/>
			</td>
		</tr>
		<tr>
			<td>Serial:</td>
			<td>
				<form:input path="inputSerial"/>
			</td>
		</tr>
		<tr>
			<td>Dueño:</td>
			<td>
				<form:select path="inputPersona">
					<form:option value="-1" label="Seleccione un dueño"/>
					<form:options 
						items="${personas}" 
						itemLabel="nombreCompleto"
						itemValue="id"/>
				</form:select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<c:choose>
					<c:when test="${empty esModificar}"><!-- Valida si ordenador es igual a null -->
						<input type="button" value="Agregar" onclick="guardar(AGREGAR)"/>
					</c:when>
					<c:otherwise>
						<!-- En cancelar: forma alternativa de realizar una petición con JS -->
						<input type="button" value="Cancelar" onclick="window.location.href='inicio.per'"/>
						<input type="button" value="Modificar" onclick="guardar(MODIFICAR)"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
	
	</form:form>
	<br/>

	<table id="tablaOrdenadores" class="tablaCentrada tablaDatos">
		<thead>
			<tr>
				<th>#</th>
				<th>Id</th>
				<th>Nombre</th>
				<th>Serial</th>
				<th>Dueño</th>
				<th>Mostrar</th>
				<th>Eliminar</th>			
			</tr>
		</thead>

		<tbody id="tbodyOrdenadores">
			<c:set var="num" value="1"/><!-- int num = 1; -->
			<c:forEach var="o" items="${requestScope.ordenadores}">
				<tr>
					<td>${num}</td>
					<td>${o.id}</td>
					<td>${o.nombre}</td>
					<td>${o.serial}</td>
					<td>${o.persona.nombre} ${o.persona.apellido} (<c:out value="${o.persona.edad}"/>)</td>
					<td><a
							href="mostrar.per?id=${o.id}"> 
								mostrar
						</a>
					</td>
					<td><a 
							href="<%= getServletContext().getContextPath() %>/ordenador/eliminar.per?id=${o.id}" 
							onclick="return confirmar()">
								eliminar
						</a>
					</td>
				</tr>
				
				<c:set var="num" value="${num + 1}"/>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>