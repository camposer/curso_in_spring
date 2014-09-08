package service;

import java.util.ArrayList;
import java.util.List;

import model.Ordenador;
import model.Persona;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dao.IOrdenadorDao;
import dao.IPersonaDao;
import exception.AppDaoException;
import exception.AppServiceException;

@Transactional
public class PersonaService implements IPersonaService {
	@Autowired // Inyecta la dependencia (por tipo de dato)
	private IPersonaDao personaDao;
	@Autowired
	private IOrdenadorDao ordenadorDao;
	
	@Override
	public List<Persona> obtenerPersonasOrdenadasPorNombreApellido() {
		try {
			return personaDao
				.obtenerTodosOrdenadosPorNombreApellido();
			
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public List<Persona> obtenerPersonas() {
		try {
			return personaDao.obtenerTodos();
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public void agregarPersona(Persona p, Ordenador o ) {
		List<Ordenador> ordenadores = new ArrayList<Ordenador>();
		ordenadores.add(o);
		p.setOrdenadores(ordenadores);
		
		agregarPersona(p);
	}
	
	@Override
	public void agregarPersona(Persona p) {
		try {
			// Agregar persona
			personaDao.agregar(p);
			
			// Agregar ordenador
			List<Ordenador> ordenadores = p.getOrdenadores();
			if (ordenadores != null) for (Ordenador o : ordenadores) {
				o.setPersona(p);
				ordenadorDao.agregar(o);
			}
			
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public Persona obtenerPersona(Integer id) {
		try {
			return personaDao.obtener(id);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public void modificarPersona(Persona p) {
		// TODO Validar si la persona existe en BD
		try {
			personaDao.modificar(p);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public void eliminarPersona(Integer id) {
		try {
			// Buscando la persona
			Persona p = personaDao.obtener(id);

			// Eliminando los ordenadores de la persona
			List<Ordenador> ordenadores = p.getOrdenadores();
			if (ordenadores != null) for (Ordenador o : ordenadores) 
				ordenadorDao.eliminar(o.getId());

			// Eliminando la persona
			personaDao.eliminar(id); 
			
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}
	
}
