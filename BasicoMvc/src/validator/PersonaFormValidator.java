package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import form.PersonaForm;

@Component
public class PersonaFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(PersonaForm.class);
	}

	@Override
	public void validate(Object obj, Errors errores) {
		PersonaForm personaForm = (PersonaForm)obj;
		
		if (personaForm.getNombre() == null ||
				personaForm.getNombre().trim().equals(""))
			errores.rejectValue("nombre", "Nombre es inválido");
		
		if (personaForm.getApellido() == null ||
				personaForm.getApellido().trim().equals(""))
			errores.rejectValue("apellido", "Apellido es inválido");
		
		if (personaForm.getEdad() == null ||
				personaForm.getEdad() <= 0)
			errores.rejectValue("edad", "Edad es inválido");
		
	}

}
