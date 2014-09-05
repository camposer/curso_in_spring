package service;

import java.util.ArrayList;
import java.util.List;

import model.Ordenador;
import model.Persona;
import transaction.ITransactionManager;
import dao.IOrdenadorDao;
import dao.IPersonaDao;
import exception.AppDaoException;
import exception.AppServiceException;

public class PersonaService implements IPersonaService {
	private IPersonaDao personaDao;
	private IOrdenadorDao ordenadorDao;
	private ITransactionManager transactionManager;
	
	public void setPersonaDao(IPersonaDao personaDao) {
		this.personaDao = personaDao;
	}
	
	public void setOrdenadorDao(IOrdenadorDao ordenadorDao) {
		this.ordenadorDao = ordenadorDao;
	}

	public void setTransactionManager(ITransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void init() {
		transactionManager.join(personaDao);
		transactionManager.join(ordenadorDao);
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
		try {
			transactionManager.begin();
			
			// Agregar persona
			personaDao.agregar(p);
			
			// Agregar ordenador
			List<Ordenador> ordenadores = p.getOrdenadores();
			if (ordenadores != null) for (Ordenador o : ordenadores) {
				o.setPersona(p);
				ordenadorDao.agregar(o);
			}
			
			transactionManager.commit();
		} catch (AppDaoException e) {
			if (transactionManager != null)
				transactionManager.rollback();
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
			transactionManager.begin();
			personaDao.modificar(p);
			transactionManager.commit();
		} catch (AppDaoException e) {
			if (transactionManager != null)
				transactionManager.rollback();
			throw new AppServiceException(e);
		}
	}

	@Override
	public void eliminarPersona(Integer id) {
		try {
			transactionManager.begin();
			
			// Buscando la persona
			Persona p = personaDao.obtener(id);

			// Eliminando los ordenadores de la persona
			List<Ordenador> ordenadores = p.getOrdenadores();
			if (ordenadores != null) for (Ordenador o : ordenadores) 
				ordenadorDao.eliminar(o.getId());

			// Eliminando la persona
			personaDao.eliminar(id); 
			
			transactionManager.commit();
		} catch (AppDaoException e) {
			if (transactionManager != null)
				transactionManager.rollback();
			throw new AppServiceException(e);
		}
	}
	
}
