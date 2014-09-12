package converter;

import org.springframework.core.convert.converter.Converter;

public class StringToFloat implements Converter<String, Float> {

	@Override
	public Float convert(String text) {
		Float valor = null;
		
		try {
			valor = Float.parseFloat(text);
		} catch (NumberFormatException e) {	}
		
		return valor;
	}

}
