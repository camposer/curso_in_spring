import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import beans.HolaMundo;
import beans.HolaMundoFactory;
import beans.HolaMundoSingleton;
import beans.IHolaMundo;



public class Principal {
	public static void main(String[] args) {
		//conPatrones();
		conSpring();
	}

	private static void conSpring() {
		// Creando el Contenedor DI - IoC
		ApplicationContext ctx = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
		
		IHolaMundo hm1 = ctx.getBean("holaMundo", IHolaMundo.class); // Busca el bean según su id
		System.out.println(hm1.saludar()); // => Hola Juan
		
		IHolaMundo hm2 = (IHolaMundo)ctx.getBean("holaMundo"); // Busca el bean según su id
		System.out.println(hm2.saludar()); // => Hola Juan
		
		IHolaMundo hm3 = ctx.getBean("helloWorld", IHolaMundo.class);
		System.out.println(hm3.saludar()); // => Hello Pedro
		
		IHolaMundo hm4 = ctx.getBean("holaMundo2", IHolaMundo.class);
		System.out.println(hm4.saludar()); // => Hola null
		
		IHolaMundo hm5 = ctx.getBean("holaMundo3", IHolaMundo.class);
		System.out.println(hm5.saludar()); // => Hola María
		
		// SINGLETON Y PROTOTYPE
		IHolaMundo hm6 = ctx.getBean("helloWorld", IHolaMundo.class);
		System.out.println(hm6.saludar()); // => Hello Pedro
		hm6.setNombre("Pedrito");
		
		IHolaMundo hm7 = ctx.getBean("helloWorld", IHolaMundo.class);
		System.out.println(hm7.saludar()); // => Hello Pedrito
		
		// PROTOTYPE
		IHolaMundo hm8 = ctx.getBean("helloWorld2", IHolaMundo.class);
		System.out.println(hm8.saludar()); // => Hello Pedro
		hm8.setNombre("Pedrito");
		System.out.println(hm8.saludar()); // => Hello Pedrito
		
		IHolaMundo hm9 = ctx.getBean("helloWorld2", IHolaMundo.class);
		System.out.println(hm9.saludar()); // => Hello Pedro
		
		
	}

	private static void conPatrones() {
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
