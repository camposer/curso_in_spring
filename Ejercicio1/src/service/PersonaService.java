package service;

import java.util.List;

import model.Persona;
import dao.IPersonaDao;
import exception.AppDaoException;
import exception.AppServiceException;

public class PersonaService implements IPersonaService {
	private IPersonaDao personaDao;
	
	@Override
	public List<Persona> obtenerPersonas() {
		try {
			return personaDao.obtenerTodos();
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	@Override
	public void agregarPersona(Persona p) {
		try {
			personaDao.agregar(p);
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
		// TODO Validar si la persona existe en BD
		try {
			personaDao.eliminar(id);
		} catch (AppDaoException e) {
			throw new AppServiceException(e);
		}
	}

	public void setPersonaDao(IPersonaDao personaDao) {
		this.personaDao = personaDao;
	}

}
