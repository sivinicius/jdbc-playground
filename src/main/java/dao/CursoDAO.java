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

import configuracao.DAO;
import dominio.Status;
import modelo.Curso;

public class CursoDAO implements DAO<Curso>{
	
	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getName());
	private static final String SQL_INSERE = "insert into public.tb_cursos(nome, ativo) values (?, ?)";
	private static final String SQL_ATUALIZA = "update public.tb_cursos set nome = ?, ativo = ? where id = ? ";
	private static final String SQL_DELETE = "delete from public.tb_cursos where id = ?";
	private static final String SQL_BUSCA = "select * from public.tb_cursos where id = ?";
	private static final String SQL_BUSCA_TODOS = "select * from public.tb_cursos";
	
	Connection conexao;
	
	public CursoDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	@Override
	public boolean inserir(Curso entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_INSERE)) {
			statement.setString(1, entidade.getNome());
			statement.setString(2, entidade.isAtivo());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return false;
		}
	}

	@Override
	public boolean atualizar(Curso entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setString(1, entidade.getNome());
			statement.setString(2, entidade.isAtivo());
			statement.setInt(3, entidade.getId());
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
	public Curso buscarPor(Integer id) {
		Curso curso = null;
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA)) {
			statement.setInt(1, id);
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
		List<Curso> cursos = new ArrayList<Curso>();
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
