package dao;

import static util.ModelHelper.obterTurmaDe;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.Curso;
import modelo.Professor;
import modelo.Turma;

public class TurmaDAO implements BaseDAO<Turma> {

	private static final Logger LOGGER = Logger.getLogger(TurmaDAO.class.getName());
	private static final String SQL_INSERE = "INSERT INTO tb_turmas(data_inicio, data_fim, id_curso, id_professor) VALUES (?, ?, ?, ?)";
	private static final String SQL_ATUALIZA = "UPDATE tb_turmas SET data_inicio = ?, data_fim = ?, id_curso = ?, id_professor = ? WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM tb_turmas WHERE id = ?";
	private static final String SQL_BUSCA = "SELECT * FROM tb_turmas WHERE id = ?";
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM tb_turmas";
	private static final String SQL_BUSCA_POR_CURSO = "SELECT t.* FROM tb_turmas AS t inner join  tb_cursos AS c on t.id_curso = c.id WHERE c.id = ? ";
	private static final String SQL_BUSCA_POR_PROFESSOR = "SELECT t.* FROM tb_turmas AS t inner join  tb_professores AS p on t.id_professor = p.id WHERE p.id = ? ";

	private final Connection conexao;

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
	public Turma atualizar(Turma entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setDate(1, Date.valueOf(entidade.getDataInicio()));
			statement.setDate(2, Date.valueOf(entidade.getDataFim()));
			statement.setInt(3, entidade.getCurso().getId());
			statement.setInt(4, entidade.getProfessor().getId());
			statement.setInt(5, entidade.getId());
			statement.executeUpdate();
			return entidade;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return null;
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
	public Turma buscarPorId(Integer id) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA)) {
			statement.setInt(1, id);
			statement.execute();
			return obterResultadoUnicoDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return null;
		}
	}

	@Override
	public List<Turma> buscarTodos() {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_TODOS)) {
			statement.execute();
			return obterListaDeResultadosDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	public List<Turma> buscarPor(Curso curso) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_CURSO)) {
			statement.setInt(1, curso.getId());
			statement.execute();
			return obterListaDeResultadosDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	public List<Turma> buscarPor(Professor professor) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_PROFESSOR)) {
			statement.setInt(1, professor.getId());
			statement.execute();
			return obterListaDeResultadosDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	private Turma obterResultadoUnicoDe(PreparedStatement statement) throws SQLException {
		try (ResultSet resultSet = statement.getResultSet()) {
			while (resultSet.next()) {
				return obterTurmaDe(resultSet);
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return null;
	}

	private List<Turma> obterListaDeResultadosDe(PreparedStatement statement) throws SQLException {
		List<Turma> turmas = new ArrayList<>();
		try (ResultSet resultSet = statement.getResultSet()) {
			while (resultSet.next()) {
				turmas.add(obterTurmaDe(resultSet));
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return turmas;
	}

}
