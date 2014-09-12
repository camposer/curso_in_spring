<%@page import="model.Persona"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Ordenador"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tag" uri="http://www.springframework.org/tags" %>
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
		
		// Cómo pasar valores desde Java a JS
		<%= "var init = function() {" %>
			var select = document.getElementById("inputPersona");
			var texto = '<tag:message code="ordenador.form.persona.opcion.0"/>';
			var opcion = "<option value='-1'>" + texto + "</option>";
			var selectAnterior = select.innerHTML;
			select.innerHTML = opcion + selectAnterior;
		};
	</script>
</head>
<body onload="init()">
	<h1><tag:message code="ordenador.titulo"/></h1>
	<%@ include file="/jsp/comun/cabecera.jsp" %>
	
	<form:form name="formOrdenador" method="post" commandName="ordenadorForm">
	<div class="errores">
		${errores}<!-- Estos errores vienen del model -->
		<form:errors path="*"/><!-- Estos errores vienen del BindingResult -->
	</div>

	<form:hidden path="inputId"/>
	<table class="tablaCentrada tablaFormulario">
		<tr>
			<td><tag:message code="ordenador.form.nombre"/>:</td>
			<td>
				<input 
					type="text" 
					name="inputNombre" 
					value="${ordenadorForm.inputNombre}"/>
			</td>
		</tr>
		<tr>
			<td><tag:message code="ordenador.form.serial"/>:</td>
			<td>
				<form:input path="inputSerial"/>
			</td>
		</tr>
		<tr>
			<td><tag:message code="ordenador.form.persona"/>:</td>
			<td>
				<form:select path="inputPersona">
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
						<input type="button" 
							value="<tag:message code="ordenador.form.btnAgregar"/>" 
							onclick="guardar(AGREGAR)"/>
					</c:when>
					<c:otherwise>
						<!-- En cancelar: forma alternativa de realizar una petición con JS -->
						<input type="button" value="<tag:message code="ordenador.form.btnCancelar"/>" onclick="window.location.href='inicio.per'"/>
						<input type="button" value="<tag:message code="ordenador.form.btnModificar"/>" onclick="guardar(MODIFICAR)"/>
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
				<th><tag:message code="ordenador.tabla.id"/></th>
				<th><tag:message code="ordenador.tabla.nombre"/></th>
				<th><tag:message code="ordenador.tabla.serial"/></th>
				<th><tag:message code="ordenador.tabla.persona"/></th>
				<th><tag:message code="ordenador.tabla.mostrar"/></th>
				<th><tag:message code="ordenador.tabla.eliminar"/></th>			
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
								<tag:message code="ordenador.tabla.mostrarLink"/>
						</a>
					</td>
					<td><a 
							href="<%= getServletContext().getContextPath() %>/ordenador/eliminar.per?id=${o.id}" 
							onclick="return confirmar()">
								<tag:message code="ordenador.tabla.eliminarLink"/>
						</a>
					</td>
				</tr>
				
				<c:set var="num" value="${num + 1}"/>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>