<%@page import="form.PersonaForm"%>
<%@page import="model.Ordenador"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="model.Persona"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
			var confirmado = confirm("Está seguro de que desea eliminar la persona seleccionada?");
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
	</script>
</head>
<body>
	<h1>Personas</h1>
	<%@ include file="/jsp/comun/cabecera.jsp" %>
	
	<form:form name="formPersona" method="post" commandName="personaForm">
		<div class="errores">
			${errores}
			<form:errors path="*"/>
		</div>
	
	<form:hidden path="inputId"/>
	<table class="tablaCentrada tablaFormulario">
		<tr>
			<td>Nombre:</td>
			<td>
				<form:input path="inputNombre"/>
			</td>
		</tr>
		<tr>
			<td>Apellido:</td>
			<td>
				<form:input path="inputApellido"/>
			</td>
		</tr>
		<tr>
			<td>Fecha:</td>
			<td>
				<form:input path="inputFecha"/>
			</td>
		</tr>
		<tr>
			<td>Altura:</td>
			<td>
				<form:input path="inputAltura"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<!-- Ordenador -->
				<hr/>
				<div id="tituloOrdenadores">Ordenadores:</div>			
				<table>
					<tr>
						<td>Nombre:</td>
						<td>
							<form:input path="inputNombreOrdenador"/>
						</td>
					</tr>
					<tr>
						<td>Serial:</td>
						<td>
							<form:input path="inputSerialOrdenador"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<% 
					if (true) { // TODO: Incluir lógica
				%>
				<input type="button" value="Agregar" onclick="guardar(AGREGAR)"/>
				<% 
					} else {
				%>
				<!-- En cancelar: forma alternativa de realizar una petición con JS -->
				<input type="button" value="Cancelar" onclick="window.location.href='inicio.per'"/>
				<input type="button" value="Modificar" onclick="guardar(MODIFICAR)"/>
				<% 
					}
				%>
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
				<th>Eliminar</th>			
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
									mostrar
							</a>
						</td>
						<td><a 
								href="<%= getServletContext().getContextPath() %>/persona/eliminar.per?id=<%= p.getId() %>" 
								onclick="return confirmar()">
									eliminar
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