package dao;

import static util.ModelHelper.obterProfessorDe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.Professor;

public class ProfessorDAO implements BaseDAO<Professor> {

	private static final Logger LOGGER = Logger.getLogger(ProfessorDAO.class.getName());

	private static final String SQL_INSERE = "INSERT INTO tb_professores(nome) VALUES (?)";
	private static final String SQL_ATUALIZA = "UPDATE tb_professores SET nome = ? WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM tb_professores WHERE id = ?";
	private static final String SQL_BUSCA_POR_ID = "SELECT * FROM tb_professores WHERE id = ?";
	private static final String SQL_BUSCA_POR_NOME = "SELECT * FROM tb_professores WHERE nome like ? ";
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM tb_professores";

	private final Connection conexao;

	public ProfessorDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(Professor entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_INSERE)) {
			statement.setString(1, entidade.getNome());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

	@Override
	public Professor atualizar(Professor entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setString(1, entidade.getNome());
			statement.setInt(2, entidade.getId());
			statement.executeUpdate();
			return entidade;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return null;
		}
	}

	@Override
	public void deletar(Professor entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_DELETE)) {
			statement.setInt(1, entidade.getId());
			statement.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

	@Override
	public Professor buscarPorId(Integer id) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_ID)) {
			statement.setInt(1, id);
			statement.execute();
			return obterResultadoUnicoDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return null;
		}
	}

	public List<Professor> buscarPorNome(String nome) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_NOME)) {
			statement.setString(1, "%" + nome + "%");
			statement.execute();
			return obterListaDeResultadosDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	public List<Professor> buscarTodos() {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_TODOS)) {
			statement.execute();
			return obterListaDeResultadosDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	private Professor obterResultadoUnicoDe(PreparedStatement statement) throws SQLException {
		try (ResultSet resultSet = statement.getResultSet()) {
			while (resultSet.next()) {
				return obterProfessorDe(resultSet);
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return null;
	}

	private List<Professor> obterListaDeResultadosDe(PreparedStatement statement) throws SQLException {
		List<Professor> professores = new ArrayList<>();
		try (ResultSet resultSet = statement.getResultSet()) {
			while (resultSet.next()) {
				professores.add(obterProfessorDe(resultSet));
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return professores;
	}

}
