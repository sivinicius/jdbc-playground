package principal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import configuracao.DAO;
import configuracao.FabricaDeConexao;
import dao.CursoDAO;
import dominio.Status;
import modelo.Curso;

public class Aplicacao {

	public static void main(String[] args) throws SQLException {
		Connection conexao = new FabricaDeConexao().getConnection();		
		DAO<Curso> cursoDao = new CursoDAO(conexao);
		Curso cursoInsere = new Curso("Teste", Status.ATIVO);
		Curso cursoAltera = new Curso(5, "Teste Alterado", Status.ATIVO);
		
		//Insert
		cursoDao.inserir(cursoInsere);		
		imprimirCursos(cursoDao);
		
		System.out.println("#############################");
		
		cursoDao.atualizar(cursoAltera);
		imprimirCursos(cursoDao);

		System.out.println("#############################");
		
		cursoDao.deletar(cursoAltera.getId());
		imprimirCursos(cursoDao);
	}

	private static void imprimirCursos(DAO<Curso> cursoDao) {
		List<Curso> cursos = cursoDao.buscarTodos();
		for (Curso c : cursos) {
			System.out.println(c);
		}
	}
}
