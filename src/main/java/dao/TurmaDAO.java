package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import configuracao.CRUD;
import modelo.Curso;
import modelo.Professor;
import modelo.Turma;

public class TurmaDAO implements CRUD<Turma> {

	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getName());
	private static final String SQL_INSERE = "INSERT INTO tb_turmas(data_inicio, data_fim, id_curso, id_professor) VALUES (?, ?, ?, ?)";
	private static final String SQL_ATUALIZA = "UPDATE tb_turmas SET data_inicio = ?, data_fim = ?, id_curso = ?, id_professor = ? WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM tb_turmas WHERE id = ?";
	private static final String SQL_BUSCA = "SELECT * FROM tb_turmas WHERE id = ?";
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM tb_turmas";
	private static final String SQL_BUSCA_POR_CURSO = "SELECT t.* FROM tb_turmas AS t inner join  tb_cursos AS c on t.id_curso = c.id WHERE c.id = ? ";
	private static final String SQL_BUSCA_POR_PROFESSOR = "SELECT t.* FROM tb_turmas AS t inner join  tb_professores AS p on t.id_professor = p.id WHERE p.id = ? ";

	Connection conexao;

	public TurmaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(Turma entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_INSERE)) {
			statement.setDate(1, Date.valueOf(entidade.getDataInicio()));
			statement.setDate(2, Date.valueOf(entidade.getDataFim()));
			statement.setInt(3, entidade.getCurso().getId());
			statement.setInt(4, entidade.getProfessor().getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

	@Override
	public void atualizar(Turma entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setDate(1, Date.valueOf(entidade.getDataInicio()));
			statement.setDate(2, Date.valueOf(entidade.getDataFim()));
			statement.setInt(3, entidade.getCurso().getId());
			statement.setInt(4, entidade.getProfessor().getId());
			statement.setInt(5, entidade.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

	@Override
	public void deletar(Turma entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_DELETE)) {
			statement.setInt(1, entidade.getId());
			statement.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

	@Override
	public Turma buscarPor(Integer id) {
		Turma turma = null;
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA)) {
			statement.setInt(1, id);
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					turma = transformarEmTurma(resultSet);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return turma;
	}

	@Override
	public List<Turma> buscarTodos() {
		List<Turma> turmas = new ArrayList<>();
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_TODOS)) {
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Turma turma = transformarEmTurma(resultSet);
					turmas.add(turma);
				}
				return turmas;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	public List<Turma> buscarPor(Curso curso) {
		List<Turma> turmas = new ArrayList<>();
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_CURSO)) {
			statement.setInt(1, curso.getId());
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Turma turma = transformarEmTurma(resultSet);
					turmas.add(turma);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return turmas;
	}

	public List<Turma> buscarPor(Professor professor) {
		List<Turma> turmas = new ArrayList<>();
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_PROFESSOR)) {
			statement.setInt(1, professor.getId());
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Turma turma = transformarEmTurma(resultSet);
					turmas.add(turma);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return turmas;
	}

	private Turma transformarEmTurma(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		LocalDate dataInicio = resultSet.getDate("data_inicio").toLocalDate();
		LocalDate dataFim = resultSet.getDate("data_fim").toLocalDate();
		Integer idCurso = resultSet.getInt("id_curso");
		Integer idProfessor = resultSet.getInt("id_professor");
		Curso curso = new CursoDAO(conexao).buscarPor(idCurso);
		Professor professor = new ProfessorDAO(conexao).buscarPor(idProfessor);
		return new Turma(id, dataInicio, dataFim, curso, professor);
	}

}
