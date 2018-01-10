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
		Curso curso = new Curso("Teste", Status.ATIVO);
		
		if(cursoDao.inserir(curso)) {
			List<Curso> cursos = cursoDao.buscarTodos();
			for (Curso c : cursos) {
				System.out.println(c);
			}
		}else {
			System.out.println("Curso não foi inserido");
		}
	}
}
