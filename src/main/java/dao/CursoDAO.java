package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import configuracao.CRUD;
import dominio.Status;
import modelo.Curso;

public class CursoDAO implements CRUD<Curso> {

	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getName());

	private static final String SQL_INSERE = "INSERT INTO public.tb_cursos(nome, ativo) VALUES(?, ?)";	
	private static final String SQL_ATUALIZA = "UPDATE public.tb_cursos SET nome = ?, ativo = ? WHERE id = ? ";	
	private static final String SQL_DELETE = "DELETE FROM public.tb_cursos WHERE id = ?";
	private static final String SQL_BUSCA_POR_ID = "SELECT * FROM public.tb_cursos WHERE id = ?";
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM public.tb_cursos";
	private static final String SQL_BUSCA_POR_NOME = "SELECT * FROM public.tb_cursos WHERE nome LIKE ?";

	Connection conexao;

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
	public void atualizar(Curso entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setString(1, entidade.getNome());
			statement.setString(2, entidade.isAtivo());
			statement.setInt(3, entidade.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
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
	public Curso buscarPor(Integer idCurso) {
		Curso curso = null;
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_ID)) {
			statement.setInt(1, idCurso);
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					curso = transformarEmCurso(resultSet);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return curso;
	}
	
	public Curso buscarPor(String nomeCurso) {
		Curso curso = null;
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_NOME)) {
			statement.setString(1, "%" + nomeCurso + "%");
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					curso = transformarEmCurso(resultSet);
				}
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return curso;
	}

	@Override
	public List<Curso> buscarTodos() {
		List<Curso> cursos = new ArrayList<>();
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_TODOS)) {
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Curso curso = transformarEmCurso(resultSet);
					cursos.add(curso);
				}
				return cursos;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	private Curso transformarEmCurso(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nome = resultSet.getString("nome");
		Status ativo = Status.obterStatus(resultSet.getString("ativo"));
		return new Curso(id, nome, ativo);
	}

}
