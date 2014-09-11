package converter;

import org.springframework.core.convert.converter.Converter;

public class FloatToString implements Converter<Float, String> {

	@Override
	public String convert(Float valor) {
		return (valor == null)?"":valor.toString();
	}

}
