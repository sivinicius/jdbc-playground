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
import modelo.Professor;

public class ProfessorDAO implements DAO<Professor>{
	
	
	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getName());
	private static final String SQL_INSERE = "insert into public.tb_professores(nome) values (?)";
	private static final String SQL_ATUALIZA = "update public.tb_professores set nome = ? where id = ? ";
	private static final String SQL_DELETE = "delete from public.tb_professores where id = ?";
	private static final String SQL_BUSCA = "select * from public.tb_professores where id = ?";
	private static final String SQL_BUSCA_NOME = "select * from public.tb_professores where nome like ? ";
	private static final String SQL_BUSCA_TODOS = "select * from public.tb_professores";
	
	Connection conexao;
	
	public ProfessorDAO(Connection conexao) {
		this.conexao = conexao;
	}
	
	@Override
	public boolean inserir(Professor entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_INSERE)) {
			statement.setString(1, entidade.getNome());
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return false;
		}
	}

	@Override
	public boolean atualizar(Professor entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setString(1, entidade.getNome());
			statement.setInt(2, entidade.getId());
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
	public Professor buscarPor(Integer id) {
		Professor professor = null;
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA)) {
			statement.setInt(1, id);
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					professor = transformarEmProfessor(resultSet);	
				}
			}				
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return professor;
	}

	public List<Professor> buscarPor(String nome) {
		List<Professor> professores = new ArrayList<Professor>();
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_NOME)) {
			statement.setString(1, "%" + nome + "%");
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Professor professor = transformarEmProfessor(resultSet);
					professores.add(professor);
				}
			}				
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
		}
		return professores;
	}
	
	@Override
	public List<Professor> buscarTodos() {
		List<Professor> professores = new ArrayList<Professor>();
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_TODOS)) {
			statement.execute();
			try (ResultSet resultSet = statement.getResultSet()) {
				while (resultSet.next()) {
					Professor professor = transformarEmProfessor(resultSet);
					professores.add(professor);
				}
				return professores;
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
			return Collections.emptyList();
		}
	}

	private Professor transformarEmProfessor(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String nome = resultSet.getString("nome");
		return new Professor(id, nome);
	}


}
