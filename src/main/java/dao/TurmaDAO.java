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

import configuracao.DAO;
import modelo.Curso;
import modelo.Professor;
import modelo.Turma;

public class TurmaDAO implements DAO<Turma> {

	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getName());
	private static final String SQL_INSERE = "insert into public.tb_turma(data_inicio, data_fim, id_curso, id_professor) values (?, ?, ?, ?)";
	private static final String SQL_ATUALIZA = "update public.tb_professores set data_inicio = ?, data_fim = ?, id_curso = ?, id_professor = ?,  where id = ? ";
	private static final String SQL_DELETE = "delete from public.tb_turmas where id = ?";
	private static final String SQL_BUSCA = "select * from public.tb_turmas where id = ?";
	private static final String SQL_BUSCA_TODOS = "select * from public.tb_turmas";
	private static final String SQL_BUSCA_POR_CURSO = "select t.* from public.tb_turmas as t inner join public.tb_cursos as c on t.id_curso = c.id where c.id = ? ";
	private static final String SQL_BUSCA_POR_PROFESSOR = "select t.* from public.tb_turmas as t inner join public.tb_professores as p on t.id_professor = p.id where p.id = ? ";

	Connection conexao;

	public TurmaDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public boolean inserir(Turma entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_INSERE)) {
			statement.setDate(1, Date.valueOf(entidade.getDataInicio()));
			statement.setDate(2, Date.valueOf(entidade.getDataFim()));
			statement.setInt(3, entidade.getCurso().getId());
			statement.setInt(4, entidade.getProfessor().getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return false;
		}
	}

	@Override
	public boolean atualizar(Turma entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setDate(1, Date.valueOf(entidade.getDataInicio()));
			statement.setDate(2, Date.valueOf(entidade.getDataFim()));
			statement.setInt(3, entidade.getCurso().getId());
			statement.setInt(4, entidade.getProfessor().getId());
			statement.setInt(5, entidade.getId());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return false;
		}
	}

	@Override
	public boolean deletar(Integer id) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_DELETE)) {
			statement.setInt(1, id);
			statement.execute();
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return false;
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
		List<Turma> turmas = new ArrayList<Turma>();
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

	public Turma buscarPor(Curso curso) {
		Turma turma = null;
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_CURSO)) {
			statement.setInt(1, curso.getId());
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

	public Turma buscarPor(Professor professor) {
		Turma turma = null;
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_PROFESSOR)) {
			statement.setInt(1, professor.getId());
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

	private Turma transformarEmTurma(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		LocalDate dataInicio = resultSet.getDate("data_inicio").toLocalDate();
		LocalDate dataFim = resultSet.getDate("data_fim").toLocalDate();
		Curso curso = new Curso();
		Professor professor = new Professor();
		return new Turma(id, dataInicio, dataFim, curso, professor);
	}

}
