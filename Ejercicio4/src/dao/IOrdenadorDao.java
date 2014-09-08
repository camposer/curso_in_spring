package dao;

import java.util.List;

import model.Ordenador;

public interface IOrdenadorDao extends IDao<Ordenador, Integer> {
	public List<Ordenador> obtenerTodosOrdenadosPorEdad();
}
