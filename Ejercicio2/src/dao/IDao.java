package dao;

import java.util.List;

import javax.persistence.EntityManager;

public interface IDao<T, K> {
	public List<T> obtenerTodos();
	public void agregar(T t);
	public T obtener(K id);
	public void modificar(T t);
	public void eliminar(K id);
	public void setEntityManager(EntityManager em);
}
