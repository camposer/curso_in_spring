package dao;

import java.util.List;

import model.Persona;

public class PersonaDao 
	extends GenericDao<Persona, Integer> 
	implements IPersonaDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Persona> obtenerPersonasOrdenadorPorEdad() {
		// FIXME Agregar query válida!
		return em.createQuery("...").getResultList();
	}
}