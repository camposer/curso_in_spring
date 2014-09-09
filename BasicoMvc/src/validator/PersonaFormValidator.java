package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import form.PersonaForm;

public class PersonaFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PersonaForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errores) {
		PersonaForm personaForm = (PersonaForm)obj;
		
		if (personaForm.getNombre() == null ||
				personaForm.getNombre().trim().equals(""))
			errores.reject("nombre", "Nombre es inválido");
		
		if (personaForm.getApellido() == null ||
				personaForm.getApellido().trim().equals(""))
			errores.reject("apellido", "Apellido es inválido");
		
		if (personaForm.getEdad() == null ||
				personaForm.getEdad() <= 0)
			errores.reject("edad", "Edad es inválido");
		
	}

}
