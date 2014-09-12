package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import model.Ordenador;
import model.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import service.IPersonaService;
import validator.PersonaFormValidator;
import editor.FechaEditor;
import exception.AppServiceException;
import form.PersonaForm;

@Controller
@RequestMapping("/persona/*")
public class PersonaController {
	@Autowired private IPersonaService personaService;
	@Autowired private PersonaFormValidator personaFormValidator;
	@Autowired private FechaEditor fechaEditor;
	
	@ModelAttribute("personaForm")
	public PersonaForm getPersonaForm() {
		return new PersonaForm();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(personaFormValidator);
		binder.registerCustomEditor(Date.class, fechaEditor);
	}
	
	@RequestMapping(value={"inicio", ""}, method={ RequestMethod.GET, RequestMethod.POST })
	public String inicio(Model model) {
		inicializar(model);
		
		// Redireccionando a la vista
		return "/jsp/persona/inicio.jsp";
	}

	private void inicializar(Model model) {
		// Recuperas las personas de la BD
		List<Persona> personas = null;
		try {
			personas = personaService
				.obtenerPersonasOrdenadasPorNombreApellido();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Pasar las personas a la vista
		model.addAttribute("personas", personas);
	}
	
	@RequestMapping(value="agregar", method=RequestMethod.POST)
	public String agregar(
			Model model, 
			@Valid @ModelAttribute("personaForm") PersonaForm personaForm,
			BindingResult result
			) {
		
		
		if (!result.hasErrors()) { // No hay errores
			Ordenador o = new Ordenador();
			o.setNombre(personaForm.getInputNombreOrdenador());
			o.setSerial(personaForm.getInputSerialOrdenador());
			
			// Agregando a la persona
			Persona p = new Persona(
					personaForm.getInputNombre(), 
					personaForm.getInputApellido(), 
					personaForm.getInputAltura(), 
					personaForm.getInputFecha());
			try {
				personaService.agregarPersona(p, o);
			} catch (AppServiceException e) {
				result.reject("bd", "Error de acceso a datos");
				e.printStackTrace();
			}
		}
		
		if (result.hasErrors()) {
			inicializar(model);
			return "forward:/jsp/persona/inicio.jsp";
		} else {
			return "redirect:/persona/inicio.per";
		}
	}
	
	@RequestMapping("eliminar")
	public String eliminar(Model model, @RequestParam Integer id) {
		List<String> errores = new ArrayList<String>();
		
		try {
			personaService.eliminarPersona(id);
		} catch (Exception e) {
			errores.add("Error al eliminar la persona en BD");
			e.printStackTrace();
		}

		if (errores.size() > 0) {
			model.addAttribute("errores", errores);
			inicializar(model);
			return "forward:/jsp/persona/inicio.jsp";
		} else {
			return "redirect:/persona/inicio.per";
		}
	}
	
	@RequestMapping("mostrar")
	public String mostrar(Model model, @RequestParam Integer id) {
		List<String> errores = new ArrayList<String>();
		
		PersonaForm personaForm = getPersonaForm();
		try {
			Persona p = personaService.obtenerPersona(id);
			
			personaForm.setInputId(p.getId());
			personaForm.setInputNombre(p.getNombre());
			personaForm.setInputApellido(p.getApellido());
			personaForm.setInputAltura(p.getAltura());
			personaForm.setInputFecha(p.getFechanacimiento());
			
			Ordenador o = null;
			if (p.getOrdenadores() != null && p.getOrdenadores().size() > 0) {
				o = p.getOrdenadores().get(0); // ANTI-PATRÓN!
				
				personaForm.setInputNombreOrdenador(o.getNombre());
				personaForm.setInputSerialOrdenador(o.getSerial());
			}
		} catch (Exception e) {
			errores.add("Error al consultar la persona en BD");
			e.printStackTrace();
		}

		if (errores.size() > 0) {
			model.addAttribute("errores", errores);
		} 
		
		model.addAttribute("personaForm", personaForm);
		model.addAttribute("esModificar", true);
		inicializar(model);
		return "forward:/jsp/persona/inicio.jsp";
	}
	
	@RequestMapping(value="modificar", method=RequestMethod.POST)
	public String cualquierCosa(
			Model model, 
			@Valid @ModelAttribute("personaForm") PersonaForm personaForm,
			BindingResult result
			) {
		
		// Validaciones
		if (personaForm.getInputId() == null) 
			result.reject("inputId", "Id inválido");
		
		if (!result.hasErrors()) { // No hay errores
			// Agregando a la persona
			Persona p = new Persona(
					personaForm.getInputNombre(), 
					personaForm.getInputApellido(), 
					personaForm.getInputAltura(), 
					personaForm.getInputFecha());
			p.setId(personaForm.getInputId());
			
			try {
				personaService.modificarPersona(p);
			} catch (AppServiceException e) {
				result.reject("bd", "Error de acceso a datos");
				e.printStackTrace();
			}
		}
		
		if (result.hasErrors()) {
			inicializar(model);
			return "forward:/jsp/persona/inicio.jsp";
		} else {
			return "redirect:/persona/inicio.per";
		}
	}	
}
