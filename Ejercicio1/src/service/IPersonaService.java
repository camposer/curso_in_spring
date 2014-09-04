package service;

import java.util.List;

import model.Persona;

public interface IPersonaService {
	public List<Persona> obtenerPersonas();
	public void agregarPersona(Persona p);
	public Persona obtenerPersona(Integer id);
	public void modificarPersona(Persona p);
	public void eliminarPersona(Integer id);
}
