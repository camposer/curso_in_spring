package dao;

import java.util.List;

import model.Persona;

public interface IPersonaDao extends IDao<Persona, Integer> {
	public List<Persona> obtenerPersonasOrdenadorPorEdad();
}
