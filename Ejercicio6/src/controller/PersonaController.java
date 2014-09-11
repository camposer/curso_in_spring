package controller;

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
	
}
