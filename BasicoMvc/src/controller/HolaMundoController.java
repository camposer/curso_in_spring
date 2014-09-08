package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import to.Persona;

@Controller
public class HolaMundoController {
	// http://localhost:8080/BasicoMvc/holaMundo.do
	@RequestMapping("/holaMundo")
	public @ResponseBody String holaMundo() {
		String html = "<html>"
				+ "<head><title>Hola mundo!</title></head>"
				+ "<body>Hola mundo!</body>"
				+ "</html>";
		
		return html;
	}
	
	// Como Spring MVC
	@RequestMapping("/holaMundo1")
	public String holaMundo1() {
		return "/holaMundo.jsp"; // forward
	}
	
	// Como lo veníamos haciendo hasta ahora
	@RequestMapping("/holaMundo2")
	public void holaMundo2(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("nombre", "Juan");

		request
			.getRequestDispatcher("/holaMundo.jsp")
			.forward(request, response);
	}
	
	@RequestMapping("/holaMundo3")
	public String holaMundo3(HttpServletRequest request) {
		request.setAttribute("nombre", "Pedro");
		
		return "/holaMundo.jsp"; // forward
	}

	@RequestMapping("/holaMundo4")
	public String holaMundo4(Model model) {
		model.addAttribute("nombre", "Pedro"); // => request.setAttribute
		
		return "/holaMundo.jsp"; // forward
	}
	
	@RequestMapping("/holaMundo5")
	public ModelAndView holaMundo5() {
		return new ModelAndView("/holaMundo.jsp", "nombre", "María"); 
	}

	@RequestMapping("/holaMundo6")
	public String holaMundo6(HttpServletRequest request, Model model) {
		String nombre = request.getParameter("nombre");
		
		model.addAttribute("nombre", nombre);
		
		return "/holaMundo.jsp"; 
	}

	@RequestMapping("/holaMundo7")
	public String holaMundo7(
			@RequestParam("nombre") String n,
			@RequestParam(required=false) Integer edad,
			Model model) {
		
		model.addAttribute("nombre", n);
		
		if (edad != null && edad >= 18)
			model.addAttribute("mensaje", "Ya puedes conducir");
		else
			model.addAttribute("mensaje", "Te toca esperar");
		
		return "/holaMundo.jsp"; 
	}

	@RequestMapping("/holaMundo8")
	public String holaMundo7(
			Persona persona,
			Model model) {
		
		model.addAttribute("nombre", persona.getNombre());
		
		if (persona.getEdad() != null && persona.getEdad() >= 18)
			model.addAttribute("mensaje", "Ya puedes conducir");
		else
			model.addAttribute("mensaje", "Te toca esperar");
		
		return "forward:/holaMundo.jsp"; 
	}

	@RequestMapping("/holaMundo9")
	public String holaMundo9() {
		return "redirect:/holaMundo1.do"; // response.sendRedirect
	}

}
