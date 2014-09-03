package beans;

public class HolaMundoSingleton {
	private static HolaMundoSingleton instance;
	private String nombre;
	private HolaMundoSingleton() { }
	public String saludar() {
		return "Hola " + nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public static HolaMundoSingleton getInstance() {
		if (instance == null)
			instance  = new HolaMundoSingleton();
		return instance;
	}

}
