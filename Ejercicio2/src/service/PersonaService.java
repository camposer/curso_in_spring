package service;

import java.util.ArrayList;
import java.util.List;

import model.Ordenador;
import model.Persona;
import transaction.TransactionManager;
import dao.IPersonaDao;
import dao.OrdenadorDao;
import dao.PersonaDao;
import exception.AppDaoException;
import exception.AppServiceException;

public class PersonaService implements IPersonaService {
	private IPersonaDao personaDao;
	
	public void setPersonaDao(IPersonaDao personaDao) {
		this.personaDao = personaDao;
	}
	
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
		TransactionManager tm = null;
		try {
			tm = new TransactionManager();
			PersonaDao personaDao = new PersonaDao(false);
			OrdenadorDao ordenadorDao = new OrdenadorDao(false);
			tm.join(personaDao);
			tm.join(ordenadorDao);
			
			// Agregar persona
			personaDao.agregar(p);
			
			// Agregar ordenador
			List<Ordenador> ordenadores = p.getOrdenadores();
			if (ordenadores != null) for (Ordenador o : ordenadores) {
				o.setPersona(p);
				ordenadorDao.agregar(o);
			}
			
			tm.commit();
		} catch (AppDaoException e) {
			if (tm != null)
				tm.rollback();
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
		// TODO Validar si la persona existe en BD
		TransactionManager tm = null;
		try {
			tm = new TransactionManager(); // new em y tx.begin()
			PersonaDao personaDao = new PersonaDao(false); // El EntityManager no lo maneja el DAO (lo maneja un externo)
			OrdenadorDao ordenadorDao = new OrdenadorDao(false); // em = null
			tm.join(personaDao); // em = tm.getEntityManager()
			tm.join(ordenadorDao);
			
			// Buscando la persona
			Persona p = personaDao.obtener(id);

			// Eliminando los ordenadores de la persona
			List<Ordenador> ordenadores = p.getOrdenadores();
			if (ordenadores != null) for (Ordenador o : ordenadores) 
				ordenadorDao.eliminar(o.getId());

			// Eliminando la persona
			personaDao.eliminar(id); 
			
			tm.commit();
		} catch (AppDaoException e) {
			if (tm != null)
				tm.rollback();
			throw new AppServiceException(e);
		}
	}
	
}
