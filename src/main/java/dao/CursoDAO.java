package dao;

import static util.ModelHelper.obterCursoDe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import modelo.Curso;

public class CursoDAO implements BaseDAO<Curso> {

	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getName());
	private static final String SQL_INSERE = "INSERT INTO tb_cursos(nome, ativo) VALUES(?, ?)";
	private static final String SQL_ATUALIZA = "UPDATE tb_cursos SET nome = ?, ativo = ? WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM tb_cursos WHERE id = ?";
	private static final String SQL_BUSCA_POR_ID = "SELECT * FROM tb_cursos WHERE id = ?";
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM tb_cursos";
	private static final String SQL_BUSCA_POR_NOME = "SELECT * FROM tb_cursos WHERE nome LIKE ?";

	private final Connection conexao;

	public CursoDAO(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(Curso entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_INSERE)) {
			statement.setString(1, entidade.getNome());
			statement.setString(2, entidade.isAtivo());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

	@Override
	public Curso atualizar(Curso entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setString(1, entidade.getNome());
			statement.setString(2, entidade.isAtivo());
			statement.setInt(3, entidade.getId());
			statement.executeUpdate();
			return entidade;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return null;
		}
	}

	@Override
	public void deletar(Curso entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_DELETE)) {
			statement.setInt(1, entidade.getId());
			statement.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
	}

	@Override
	public Curso buscarPorId(Integer numero) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_ID)) {
			statement.setInt(1, numero);
			statement.execute();
			return obterResultadoUnicoDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return null;
		}
	}

	public List<Curso> buscarPorNome(String nomeCurso) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_NOME)) {
			statement.setString(1, "%" + nomeCurso + "%");
			statement.execute();
			return obterListaDeResultadosDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	@Override
	public List<Curso> buscarTodos() {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_TODOS)) {
			statement.execute();
			return obterListaDeResultadosDe(statement);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	private Curso obterResultadoUnicoDe(PreparedStatement statement) throws SQLException {
		try (ResultSet resultSet = statement.getResultSet()) {
			while (resultSet.next()) {
				return obterCursoDe(resultSet);
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return null;
	}

	private List<Curso> obterListaDeResultadosDe(PreparedStatement statement) throws SQLException {
		List<Curso> cursos = new ArrayList<>();
		try (ResultSet resultSet = statement.getResultSet()) {
			while (resultSet.next()) {
				cursos.add(obterCursoDe(resultSet));
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return cursos;
	}

}
