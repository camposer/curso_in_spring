package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import form.PersonaForm;

@Component
public class PersonaFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PersonaForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errores) {
		PersonaForm personaForm = (PersonaForm)obj;
		
		if (personaForm.getInputNombre() == null || 
				personaForm.getInputNombre().trim().equals("")) // Validando nombre
			errores.rejectValue("inputNombre", "persona.error.nombre"); 
		
		if (personaForm.getInputApellido() == null || 
				personaForm.getInputApellido().trim().equals("")) // Validando apellido
			errores.rejectValue("inputApellido", "persona.noexiste", "Apellido inválido");
		
		if (personaForm.getInputFecha() == null)
			errores.rejectValue("inputFecha", "persona.error.fecha");

		if (personaForm.getInputAltura() == null || 
				personaForm.getInputAltura() <= 0)
			errores.rejectValue("inputAltura", "persona.error.altura");

		if (personaForm.getInputNombreOrdenador() == null ||
				personaForm.getInputNombreOrdenador().trim().equals("")) // Validando nombre de ordenador
			errores.rejectValue("inputNombreOrdenador", "persona.error.nombreOrdenador");
		
		if (personaForm.getInputSerialOrdenador() == null ||
				personaForm.getInputSerialOrdenador().trim().equals("")) // Validando serial de ordenador
			errores.rejectValue("inputSerialOrdenador", "persona.error.serialOrdenador");
	}
}
