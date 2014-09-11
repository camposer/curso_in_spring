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
			errores.reject("inputNombre", "Nombre inválido"); 
		
		if (personaForm.getInputApellido() == null || 
				personaForm.getInputApellido().trim().equals("")) // Validando apellido
			errores.reject("inputApellido", "Apellido inválido");
		
		if (personaForm.getInputFecha() == null)
			errores.reject("inputFecha", "Fecha inválida");

		if (personaForm.getInputAltura() == null || 
				personaForm.getInputAltura() <= 0)
			errores.reject("inputAltura", "Altura inválida");

		if (personaForm.getInputNombreOrdenador() == null ||
				personaForm.getInputNombreOrdenador().trim().equals("")) // Validando nombre de ordenador
			errores.reject("inputNombreOrdenador", "Nombre de ordenador inválido");
		
		if (personaForm.getInputSerialOrdenador() == null ||
				personaForm.getInputSerialOrdenador().trim().equals("")) // Validando serial de ordenador
			errores.reject("inputSerialOrdenador", "Serial de ordenador inválido");
	}
}
