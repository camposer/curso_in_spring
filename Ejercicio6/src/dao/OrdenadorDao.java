package dao;

import java.util.List;

import org.springframework.stereotype.Component;

import model.Ordenador;

@Component // Hace que Spring cree una instancia de este bean (con id camelCase, por ejemplo: ordenadorDao)
public class OrdenadorDao 
		extends GenericDao<Ordenador, Integer> 
		implements IOrdenadorDao {
	
	@SuppressWarnings("unchecked")
	public List<Ordenador> obtenerTodosOrdenadosPorEdad() {
		return em
				.createQuery("FROM Ordenador o ORDER BY "
						+ "o.persona.fechanacimiento DESC")
				.getResultList();
	}
}
