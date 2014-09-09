package controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import validator.PersonaFormValidator;
import form.PersonaForm;

@Controller
public class FormularioController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new PersonaFormValidator());
	}
	
	// Se ejecuta siempre antes de cada acción
	@ModelAttribute("personaForm")
	public PersonaForm getPersonaForm() {
		PersonaForm p = new PersonaForm();
		p.setNombre("Vacío");
		p.setApellido("Vacío");
		p.setEdad(0);

		return p;
	}
	
	@RequestMapping("/form")
	public String inicio(Model model) {
		// Si no existe ModelAttribute se puede definir directamente en el model (request)
//		PersonaForm p = new PersonaForm();
//		p.setNombre("Vacío");
//		p.setApellido("Vacío");
//		p.setEdad(0);
//		
//		model.addAttribute("personaForm", p);
		
		return "form.jsp";
	}
	
	@RequestMapping(value="/clic", method=RequestMethod.POST)
	public String clic(
			@Valid @ModelAttribute("personaForm") PersonaForm personaForm,
			BindingResult result) {
		
		// TODO: Podemos jugar con result.hasErrors()
		return "form.jsp";
	}
	
}
