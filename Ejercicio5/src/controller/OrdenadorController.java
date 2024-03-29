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
import form.OrdenadorForm;

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
		
		// Con los datos previamente introducidos
		Ordenador ordenadorForm = new Ordenador();
		ordenadorForm.setNombre(nombre);
		ordenadorForm.setSerial(serial);
		Persona pForm = new Persona();
		pForm.setId(pId);
		ordenadorForm.setPersona(pForm);
		
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
		
		if (errores.size() > 0) {
			session.setAttribute("errores", errores);
			session.setAttribute("ordenador", ordenadorForm);
		}
		
		// Redireccionando (ejecuta el cliente!!!)
		return "redirect:/ordenador/inicio.per";
	}
	
	@RequestMapping("eliminar")
	public String eliminar(@RequestParam Integer id, HttpSession session) {
		List<String> errores = new ArrayList<String>();

		if (id == null)
			errores.add("Id de ordenador inválido");
		
		try {
			if (id != null)
				ordenadorService.eliminarOrdenador(id);
		} catch (Exception e) {
			errores.add("Error al eliminar la ordenador en BD");
			e.printStackTrace();
		}

		if (errores.size() > 0)
			session.setAttribute("errores", errores);
		
		return "redirect:/ordenador/inicio.per";
	}
	
	@RequestMapping("mostrar")
	public String mostrar(@RequestParam Integer id, HttpSession session) {
		List<String> errores = new ArrayList<String>();
		Ordenador o = null;
		
		if (id == null)
			errores.add("Id inválido");
		
		try {
			if (id != null)
				o = ordenadorService.obtenerOrdenador(id);
		} catch (Exception e) {
			errores.add("Error al consultar la ordenador en BD");
			e.printStackTrace();
		}

		if (errores.size() > 0)
			session.setAttribute("errores", errores);
		else if (o != null)
			session.setAttribute("ordenador", o);
		
		return "redirect:/ordenador/inicio.per"; 
	}
	
	@RequestMapping(value="modificar", method=RequestMethod.POST)
	public String modificar(OrdenadorForm ordenadorForm, HttpSession session) {
		List<String> errores = new ArrayList<String>();
		
		// Validaciones
		if (ordenadorForm.getInputId() == null)
			errores.add("Id inválido");
		if (ordenadorForm.getInputNombre().trim().equals("")) // Validando nombre
			errores.add("Nombre inválido"); 
		if (ordenadorForm.getInputSerial().trim().equals("")) // Validando apellido
			errores.add("Serial inválido"); 
		if (ordenadorForm.getInputPersona() == null)
			errores.add("Persona id inválido");
		
		if (errores.size() == 0) { // No hay errores
			// Agregando a la persona
			Persona p = new Persona();
			p.setId(ordenadorForm.getInputPersona());
			
			Ordenador o = new Ordenador();
			o.setId(ordenadorForm.getInputId());
			o.setNombre(ordenadorForm.getInputNombre());
			o.setSerial(ordenadorForm.getInputSerial());
			o.setPersona(p);
			
			try {
				ordenadorService.modificarOrdenador(o);
			} catch (AppServiceException e) {
				errores.add("Error de acceso a datos");
				e.printStackTrace();
			}
		}
		
		if (errores.size() > 0) {
			session.setAttribute("errores", errores);
		}
		
		// Redireccionando (ejecuta el cliente!!!)
		return "redirect:/ordenador/inicio.per"; 
	}
}
