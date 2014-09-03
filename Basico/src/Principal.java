

public class Principal {
	public static void main(String[] args) {
		// FACTORIES (prototype)
		IHolaMundo hm1 = HolaMundoFactory.createHolaMundo(); // new
		hm1.setNombre("Juan");
		IHolaMundo hm2 = HolaMundoFactory.createHolaMundo(); // new
		hm2.setNombre("Pedro");
		System.out.println(hm1.saludar()); // => Hola Juan
		System.out.println(hm2.saludar()); // => Hola Pedro
		
		// SINGLETON
		HolaMundoSingleton hm3 = HolaMundoSingleton.getInstance();
		hm3.setNombre("Juan");
		HolaMundoSingleton hm4 = HolaMundoSingleton.getInstance();
		hm4.setNombre("María");
		HolaMundoSingleton hm5 = HolaMundoSingleton.getInstance();
		hm5.setNombre("Pedro");
		
		System.out.println(hm3.saludar()); // => Hola Pedro
		System.out.println(hm4.saludar()); // => Hola Pedro
		System.out.println(hm5.saludar()); // => Hola Pedro
		
	}
}
