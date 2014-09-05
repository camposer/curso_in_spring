package servlet.persona;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.IPersonaService;
import servlet.BaseServlet;

@WebServlet("/persona/Eliminar")
public class EliminarServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IPersonaService personaService = ctx.getBean("personaService", IPersonaService.class);
		
		List<String> errores = new ArrayList<String>();
		String sid = request.getParameter("id");
		Integer id = null;
		
		try {
			id = Integer.parseInt(sid);
		} catch (NumberFormatException ne) {
			errores.add("Id inválido");
			ne.printStackTrace();
		}
		
		try {
			if (id != null)
				personaService.eliminarPersona(id);
		} catch (Exception e) {
			errores.add("Error al eliminar la persona en BD");
			e.printStackTrace();
		}

		if (errores.size() > 0)
			request.getSession().setAttribute("errores", errores);
		
		response.sendRedirect("Inicio");
	}

}
