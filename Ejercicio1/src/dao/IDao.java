package dao;

import java.util.List;

public interface IDao<T, K> {
	public void agregar(T t);
	public void modificar(T t);
	public void eliminar(K id);
	public T obtener(K id);
	public List<T> obtenerTodos();
}
