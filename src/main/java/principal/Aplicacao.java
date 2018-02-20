package principal;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import configuracao.FabricaDeConexao;
import dao.CursoDAO;
import dao.ProfessorDAO;
import dao.TurmaDAO;
import modelo.Curso;
import modelo.Professor;
import modelo.Turma;

public class Aplicacao {

	public static void main(String[] args) throws SQLException {
		Connection conexao = new FabricaDeConexao().getConnection();		
		ProfessorDAO professorDAO = new ProfessorDAO(conexao);
		CursoDAO cursoDAO = new CursoDAO(conexao);
		TurmaDAO turmaDAO = new TurmaDAO(conexao);
		
		Curso curso = cursoDAO.buscarPor(1);
		Professor professor = professorDAO.buscarPor(5);


		List<Turma> turmas = turmaDAO.buscarTodos();
		Turma turma = turmas.get(1);
		turma.setDataFim(LocalDate.now());
		
		turmaDAO.deletar(turma.getId());
		
		System.out.println(turmas);
		
	}

}
