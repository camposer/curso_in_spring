package beans;

public class HelloWorld implements IHolaMundo {
	private String nombre;
	
	@Override
	public String saludar() {
		return "Hello " + nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
