package dao;

import java.util.List;

public interface BaseDAO<T> {
	
	public void inserir(T entidade);
	public T atualizar(T entidade);
	public void deletar(T entidade);
	public T buscarPorId(Integer numero);
	public List<T> buscarTodos();

}
