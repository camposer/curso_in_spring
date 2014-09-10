package service;

import java.util.List;

import model.Ordenador;

public interface IOrdenadorService {
	public List<Ordenador> obtenerOrdenadoresOrdenadosPorEdad();
	public List<Ordenador> obtenerOrdenadores();
	public void agregarOrdenador(Ordenador o);
	public Ordenador obtenerOrdenador(Integer id);
	public void modificarOrdenador(Ordenador o);
	public void eliminarOrdenador(Integer id);
}
