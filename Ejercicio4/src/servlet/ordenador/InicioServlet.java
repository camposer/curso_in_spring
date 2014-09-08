package servlet.ordenador;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Ordenador;
import model.Persona;
import service.IOrdenadorService;
import service.IPersonaService;
import servlet.BaseServlet;

@WebServlet("/ordenador/Inicio")
public class InicioServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IPersonaService personaService = ctx.getBean("personaService", IPersonaService.class);
		IOrdenadorService ordenadorService = ctx.getBean("ordenadorService", IOrdenadorService.class);
		
		// Recuperas los ordenadores de la BD
		List<Ordenador> ordenadores = null;
		try {
			ordenadores = ordenadorService
				.obtenerOrdenadoresOrdenadosPorEdad();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Recuperas las personas de la BD
		List<Persona> personas = null;
		try {
			personas = personaService.obtenerPersonas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Pasar las personas a la vista
		request.setAttribute("ordenadores", ordenadores);
		request.setAttribute("personas", personas);

		// Redireccionando a la vista
		request
			.getRequestDispatcher("/jsp/ordenador/inicio.jsp")
			.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
