package beans;

public class HolaMundo implements IHolaMundo {
	private String nombre;
	
	public HolaMundo() { }
	
	public HolaMundo(String nombre) {
		this.nombre = nombre;
	}
	
	@Override
	public String saludar() {
		return "Hola " + nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
