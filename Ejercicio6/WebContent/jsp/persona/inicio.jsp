<%@page import="form.PersonaForm"%>
<%@page import="model.Ordenador"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Persona"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tag" uri="http://www.springframework.org/tags" %>

<% Object esModificar = request.getAttribute("esModificar"); %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Personas</title>
	<link rel="stylesheet" type="text/css" href="../css/comun.css">
	<link rel="stylesheet" type="text/css" href="../css/persona.css">
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
			var confirmado = confirm("<tag:message code="persona.confirmacionEliminar"/>");
			return confirmado; // true o false
		};
		
		var guardar = function(op) {
			var form = document.forms.formPersona;
			
			if (op == AGREGAR)
				form.action = "agregar.per";
			else if (op == MODIFICAR)
				form.action = "modificar.per";
			
			form.submit();
		};
		
		var init = function(){
			var inputNombreOrdenador = document.getElementById("inputNombreOrdenador");
			var inputSerialOrdenador = document.getElementById("inputSerialOrdenador");
			var esModificarJs = <%= (esModificar != null)?"true":"false" %>;
			
			if (esModificarJs) {
				inputNombreOrdenador.readOnly = true;
				inputSerialOrdenador.readOnly = true;
			} 
		};
	</script>
</head>
<body onload="init()">

	<h1><tag:message code="persona.titulo"/></h1>
	<%@ include file="/jsp/comun/cabecera.jsp" %>
	
	<form:form name="formPersona" method="post" commandName="personaForm">
		<div class="errores">
			${errores}
			<form:errors path="*"/>
		</div>
	
	<form:hidden path="inputId"/>
	<table class="tablaCentrada tablaFormulario">
		<tr>
			<td><tag:message code="persona.form.nombre"/>:</td>
			<td>
				<form:input path="inputNombre"/>
			</td>
		</tr>
		<tr>
			<td><tag:message code="persona.form.apellido"/>:</td>
			<td>
				<form:input path="inputApellido"/>
			</td>
		</tr>
		<tr>
			<td><tag:message code="persona.form.fecha"/>:</td>
			<td>
				<form:input path="inputFecha"/>
			</td>
		</tr>
		<tr>
			<td><tag:message code="persona.form.altura"/>:</td>
			<td>
				<form:input path="inputAltura"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<!-- Ordenador -->
				<hr/>
				<div id="tituloOrdenadores"><tag:message code="ordenador.titulo"/>:</div>			
				<table>
					<tr>
						<td><tag:message code="ordenador.form.nombre"/>:</td>
						<td>
							<form:input path="inputNombreOrdenador" />
						</td>
					</tr>
					<tr>
						<td><tag:message code="ordenador.form.serial"/>:</td>
						<td>
							<form:input path="inputSerialOrdenador" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<% if (esModificar == null) { %>
				<input type="button" value="<tag:message code="persona.form.btnAgregar"/>" onclick="guardar(AGREGAR)"/>
				<% } else { %>
				<!-- En cancelar: forma alternativa de realizar una petición con JS -->
				<input type="button" value="<tag:message code="persona.form.btnCancelar"/>" onclick="window.location.href='inicio.per'"/>
				<input type="button" value="<tag:message code="persona.form.btnModificar"/>" onclick="guardar(MODIFICAR)"/>
				<% } %>
			</td>
		</tr>
	</table>
	</form:form>

	<br/>

	<table id="tablaPersonas" class="tablaCentrada tablaDatos">
		<thead>
			<tr>
				<th>#</th>
				<th>Id</th>
				<th>Nombre</th>
				<th>Apellido</th>
				<th>Fecha</th>
				<th>Altura</th>
				<th>Ordenadores</th>
				<th>Mostrar</th>
				<th>Elimordenador/inicio.per?lang=eninar</th>			
			</tr>
		</thead>

		<tbody id="tbodyPersonas">
		<%
			// Obteniendo personas que son enviadas desde el Controlador
			List<Persona> personas = (List<Persona>)
				request.getAttribute("personas");
		
			if (personas != null) {
				int num = 1;
				for (Persona p : personas) {
		%>
					<tr>
						<td><%= num++ %></td>
						<td><%= p.getId() %></td>
						<td><%= p.getNombre() %></td>
						<td><%= p.getApellido() %></td>
						<td><%= p.getFechanacimiento() %></td>
						<td><%= p.getAltura() %></td>
						<td>
							<% 
								List<Ordenador> ordenadores = (List<Ordenador>)
									p.getOrdenadores(); // ANTI-PATRÓN!
								if (ordenadores != null) for (Ordenador o : ordenadores) { 
							%>
								<a href="../ordenador/mostrar.per?id=<%= o.getId() %>">
									<%= o.getNombre() %> - <%= o.getSerial() %><br/>
								</a>
							<%
								}
							%>
						</td>
						<td><a
								href="mostrar.per?id=<%= p.getId() %>"> 
									<tag:message code="persona.tabla.mostrarLink"/>
							</a>
						</td>
						<td><a 
								href="<%= getServletContext().getContextPath() %>/persona/eliminar.per?id=<%= p.getId() %>" 
								onclick="return confirmar()">
									<tag:message code="persona.tabla.eliminarLink"/>
							</a>
						</td>
					</tr>
		<%
				}
			}
		%>
		</tbody>
	</table>
</body>
</html>