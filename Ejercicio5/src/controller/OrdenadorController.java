package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.Ordenador;
import model.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.IOrdenadorService;
import service.IPersonaService;
import exception.AppServiceException;

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
	public String inicio(Model model) {
		
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
		model.addAttribute("ordenadores", ordenadores);
		model.addAttribute("personas", personas);

		// Redireccionando a la vista
		return "forward:/jsp/ordenador/inicio.jsp";

	}
	
	@RequestMapping(value="agregar", method=RequestMethod.POST)
	public String agregar(
				@RequestParam("inputNombre") String nombre,
				@RequestParam("inputSerial") String serial,
				@RequestParam("inputPersona") Integer pId,
				HttpSession session
			) {
		
		List<String> errores = new ArrayList<String>();
		
		// Validaciones
		if (nombre.trim().equals("")) // Validando nombre
			errores.add("Nombre inválido"); 
		if (serial.trim().equals("")) // Validando serial
			errores.add("Serial inválido"); 
		if (pId == null || pId <= 0)
			errores.add("Persona id inválida");
		
		if (errores.size() == 0) { // No hay errores
			// Agregando el ordenador
			Persona p = new Persona();
			p.setId(pId);
			
			Ordenador o = new Ordenador();
			o.setNombre(nombre);
			o.setSerial(serial);
			o.setPersona(p);
			
			try {
				ordenadorService.agregarOrdenador(o);
			} catch (AppServiceException e) {
				errores.add("Error de acceso a datos");
				e.printStackTrace();
			}
		}
		
		if (errores.size() > 0) 
			session.setAttribute("errores", errores);
		
		// Redireccionando (ejecuta el cliente!!!)
		return "redirect:/ordenador/inicio.per";
	}
}
