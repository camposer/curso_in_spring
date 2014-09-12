<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" uri="http://www.springframework.org/tags" %>
	<div id="cabecera">
		<a href="<%= getServletContext().getContextPath() %>/persona/inicio.per"><tag:message code="cabecera.persona" /></a>
		<a href="<%= getServletContext().getContextPath() %>/ordenador/inicio.per"><tag:message code="cabecera.ordenador" /></a>
	</div>
