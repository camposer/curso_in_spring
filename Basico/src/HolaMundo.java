
public class HolaMundo implements IHolaMundo {
	private String nombre;
	
	@Override
	public String saludar() {
		return "Hola " + nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
