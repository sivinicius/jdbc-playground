package configuracao;

import java.util.List;

public interface DAO<T> {
	
	public boolean inserir(T entidade);
	public boolean atualizar(T entidade);
	public boolean deletar(Integer id);
	public T buscar(Integer id);
	public List<T> buscarTodos();

}
