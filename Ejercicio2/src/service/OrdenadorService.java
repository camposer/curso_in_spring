package service;

import java.util.List;

import model.Ordenador;
import model.Persona;
import transaction.ITransactionManager;
import dao.IOrdenadorDao;
import exception.AppDaoException;
import exception.AppServiceException;

public class OrdenadorService implements IOrdenadorService {
	private IOrdenadorDao ordenadorDao;
	private IPersonaService personaService;
	private ITransactionManager transactionManager;
	
	public void setOrdenadorDao(IOrdenadorDao ordenadorDao) {
		this.ordenadorDao = ordenadorDao;
	}

	public void setPersonaService(IPersonaService personaService) {
		this.personaService = personaService;
	}

	public void setTransactionManager(ITransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void init() {
		transactionManager.join(ordenadorDao);
	}
	
	public List<Ordenador> obtenerOrdenadoresOrdenadosPorEdad() {
		try {
			return ordenadorDao
				.obtenerTodosOrdenadosPorEdad();
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public List<Ordenador> obtenerOrdenadores() {
		try {
			return ordenadorDao.obtenerTodos();
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void agregarOrdenador(Ordenador o) {
		try {
			// TODO No entra personaService en el mismo hilo transaccional!!! Resolver...
			transactionManager.begin(); 

			// Obteniendo un objeto persistente, por tanto validando que existe!
			Persona p = personaService.obtenerPersona(
					o.getPersona().getId());
			
			if (p == null)
				throw new AppDaoException(); // Devolver un excepción especializada
			
			o.setPersona(p); // Reemplazando persona anterior por la referencia recién obtenida
			
			ordenadorDao.agregar(o);
			
			transactionManager.commit();
		} catch (AppDaoException e) {
			if (transactionManager != null)
				transactionManager.rollback();
			throw new AppServiceException(e);
		}
	}

	public Ordenador obtenerOrdenador(Integer id) {
		try {
			return ordenadorDao.obtener(id);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void modificarOrdenador(Ordenador o) {
		// TODO Validar si la persona existe en BD
		try {
			transactionManager.begin();
			
			// Obteniendo un objeto persistente, por tanto validando que existe!
			Persona p = personaService.obtenerPersona(
					o.getPersona().getId());
			
			if (p == null)
				throw new AppDaoException(); // Devolver un excepción especializada
			
			o.setPersona(p); // Reemplazando persona anterior por la referencia recién obtenida
			
			ordenadorDao.modificar(o);
			
			transactionManager.commit();
		} catch (AppDaoException e) {
			if (transactionManager != null)
				transactionManager.rollback();
			throw new AppServiceException(e);
		}
	}

	public void eliminarOrdenador(Integer id) {
		// TODO Validar si la persona existe en BD
		try {
			transactionManager.begin();
			ordenadorDao.eliminar(id);
			transactionManager.commit();
		} catch (AppDaoException e) {
			if (transactionManager != null)
				transactionManager.rollback();			
			throw new AppServiceException(e);
		}
	}
	
}
