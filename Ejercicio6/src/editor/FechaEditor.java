package editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class FechaEditor extends PropertyEditorSupport {
	public static final String PATRON = "yyyy-MM-dd";
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Date fecha = null;
		try {
			fecha = new SimpleDateFormat(PATRON).parse(text);
		} catch (ParseException e) { }
		
		super.setValue(fecha);
	}
	
	@Override
	public String getAsText() {
		String fecha = "";
		
		if (super.getValue() != null)
			fecha = new SimpleDateFormat(PATRON)
							.format(super.getValue());
		
		return fecha;
	}
}
