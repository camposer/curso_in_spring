import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CalculadoraGui {
	private Scanner scanner;
	private ICalculadora calc;
	
	public void setCalculadora(ICalculadora calc) {
		this.calc = calc;
	}
	
	public CalculadoraGui() {
		this.scanner = new Scanner(System.in);
		/*
		this.calc = 
				new ClassPathXmlApplicationContext("applicationContext.xml")
				.getBean("calculadora", ICalculadora.class);
		*/
	}
	
	public void iniciar() {
		
		while (true) {
			System.out.println();
			System.out.println("1. Sumar");
			System.out.println("2. Restar");
			System.out.println("3. Multiplicar");
			System.out.println("4. Dividir");
			System.out.println("5. Salir");
			System.out.println("? ");
			String opcion = scanner.nextLine();

			if (opcion.equals("5")) // Saliendo 
				break;
			
			System.out.print("a? ");
			String sa = scanner.nextLine();
			float a = Float.parseFloat(sa);

			System.out.print("b? ");
			String sb = scanner.nextLine();
			float b = Float.parseFloat(sb);
			
			if (opcion.equals("1"))
				System.out.println(a + " + " + b + " = " + calc.sumar(a, b));
			else if (opcion.equals("2"))
				System.out.println(a + " - " + b + " = " + calc.restar(a, b));
			else if (opcion.equals("3"))
				System.out.println(a + " * " + b + " = " + calc.multiplicar(a, b));
			else if (opcion.equals("4"))
				System.out.println(a + " / " + b + " = " + calc.dividir(a, b));
			
		}
	}
	
	public static void main(String[] args) {
		/*
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		CalculadoraGui gui = ctx.getBean("calculadoraGui", 
				CalculadoraGui.class);
		gui.iniciar();
		*/
		new ClassPathXmlApplicationContext("applicationContext.xml")
			.getBean("calculadoraGui", CalculadoraGui.class)
			.iniciar();
	}
}
