package util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import dominio.Status;
import modelo.Curso;
import modelo.Professor;
import modelo.Turma;

public class ModelHelper {

	private ModelHelper() {

	}

	public static final Curso obterCursoDe(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nome = resultSet.getString("nome");
		Status ativo = Status.obterStatus(resultSet.getString("ativo"));
		return new Curso(id, nome, ativo);
	}

	public static final Professor obterProfessorDe(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nome = resultSet.getString("nome");
		return new Professor(id, nome);
	}

	public static final Turma obterTurmaDe(ResultSet resultSet) throws SQLException {
		Integer idTurma = resultSet.getInt("id_turma");
		LocalDate dataInicio = resultSet.getDate("data_inicio").toLocalDate();
		LocalDate dataFim = resultSet.getDate("data_fim").toLocalDate();		
		Integer idCurso = resultSet.getInt("id_curso");
		String nomeCurso = resultSet.getString("nome_curso");
		String statusCurso = resultSet.getString("status_curso");
		Integer idProfessor = resultSet.getInt("id_professor");
		String nomeProfessor= resultSet.getString("nome_professor");
		Curso curso = new Curso(idCurso, nomeCurso, Status.obterStatus(statusCurso));
		Professor professor = new Professor(idProfessor, nomeProfessor);		
		return new Turma(idTurma, dataInicio, dataFim, curso, professor);
	}
}