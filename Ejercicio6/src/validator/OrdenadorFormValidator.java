package validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import form.OrdenadorForm;

@Component
public class OrdenadorFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return OrdenadorForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errores) {
		OrdenadorForm ordenadorForm = (OrdenadorForm) obj;
		
		if (ordenadorForm.getInputNombre() == null || 
				ordenadorForm.getInputNombre().trim().equals(""))
			errores.reject("inputNombre", "Nombre inválido");
		
		if (ordenadorForm.getInputSerial() == null || 
				ordenadorForm.getInputSerial().trim().equals(""))
			errores.reject("inputSerial", "Serial inválido");
		
		if (ordenadorForm.getInputPersona() == null || 
				ordenadorForm.getInputPersona() <= 0)
			errores.reject("inputPersona", "Persona id inválida");
	}

}
