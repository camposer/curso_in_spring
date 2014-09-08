package service;

import java.util.List;

import model.Ordenador;
import model.Persona;

public interface IPersonaService {
	public List<Persona> obtenerPersonasOrdenadasPorNombreApellido();
	public List<Persona> obtenerPersonas();
	public void agregarPersona(Persona p, Ordenador o );
	public void agregarPersona(Persona p);
	public Persona obtenerPersona(Integer id);
	public void modificarPersona(Persona p);
	public void eliminarPersona(Integer id);}
