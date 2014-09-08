package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Ordenador;
import model.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import service.IOrdenadorService;
import service.IPersonaService;

@Controller
@RequestMapping(value="/ordenador/")
public class OrdenadorController {
	@Autowired
	private IPersonaService personaService;
	@Autowired
	private IOrdenadorService ordenadorService;

	// http://localhost:8080/Ejercicio5/ordenador/inicio.per
	// http://localhost:8080/Ejercicio5/ordenador/.per
	@RequestMapping(
		value={"inicio", ""},
		method= { RequestMethod.GET, RequestMethod.POST }
	)
	public void inicio(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Recuperas los ordenadores de la BD
		List<Ordenador> ordenadores = null;
		try {
			ordenadores = ordenadorService
				.obtenerOrdenadoresOrdenadosPorEdad();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Recuperas las personas de la BD
		List<Persona> personas = null;
		try {
			personas = personaService.obtenerPersonas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Pasar las personas a la vista
		request.setAttribute("ordenadores", ordenadores);
		request.setAttribute("personas", personas);

		// Redireccionando a la vista
		request
			.getRequestDispatcher("/jsp/ordenador/inicio.jsp")
			.forward(request, response);

	}
}
