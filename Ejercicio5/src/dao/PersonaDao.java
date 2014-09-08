package dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import model.Persona;

@Component
@SuppressWarnings("unchecked")
public class PersonaDao 
		extends GenericDao<Persona, Integer>
		implements IPersonaDao {

	public List<Persona> obtenerTodosOrdenadosPorNombreApellido() {
		return em.createQuery("SELECT p FROM Persona p "
				+ "ORDER BY upper(p.nombre) ASC, upper(p.apellido) ASC")
				.getResultList();
	}
	
	public List<Persona> obtenerTodosPorOrdenadorSerial(String serial) {
		Query q = em.createQuery("SELECT p FROM Persona p "
				+ "JOIN p.ordenadores o WHERE o.serial LIKE :serial");
		q.setParameter("serial", "%" + serial + "%");
				
		return q.getResultList();
	}
}