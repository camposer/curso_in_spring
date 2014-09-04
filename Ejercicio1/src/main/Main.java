package main;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import gui.PersonaGui;

public class Main {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("applicationContext.xml")
			.getBean("personaGui", PersonaGui.class).iniciar();
	}
}
