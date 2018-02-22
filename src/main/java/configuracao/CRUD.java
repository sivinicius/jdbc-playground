package configuracao;

import java.util.List;

public interface CRUD<T> {
	
	public void inserir(T entidade);
	public void atualizar(T entidade);
	public void deletar(T entidade);
	public T buscarPor(Integer id);
	public List<T> buscarTodos();

}
