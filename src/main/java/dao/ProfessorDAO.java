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
import modelo.Professor;

public class ProfessorDAO implements CRUD<Professor>{
	
	private static final Logger LOGGER = Logger.getLogger(CursoDAO.class.getName());
	
	private static final String SQL_INSERE = "INSERT INTO public.tb_professores(nome) VALUES (?)";
	private static final String SQL_ATUALIZA = "UPDATE public.tb_professores SET nome = ? WHERE id = ? ";
	private static final String SQL_DELETE = "DELETE FROM public.tb_professores WHERE id = ?";
	private static final String SQL_BUSCA_POR_ID = "SELECT * FROM public.tb_professores WHERE id = ?";
	private static final String SQL_BUSCA_POR_NOME = "SELECT * FROM public.tb_professores WHERE nome like ? ";
	private static final String SQL_BUSCA_TODOS = "SELECT * FROM public.tb_professores";
	
	Connection conexao;
	
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
	public void atualizar(Professor entidade) {
		try (PreparedStatement statement = conexao.prepareStatement(SQL_ATUALIZA)) {
			statement.setString(1, entidade.getNome());
			statement.setInt(2, entidade.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, e.getMessage());
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
	public Professor buscarPor(Integer id) {
		Professor professor = null;
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_ID)) {
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
		List<Professor> professores = new ArrayList<>();
		try (PreparedStatement statement = conexao.prepareStatement(SQL_BUSCA_POR_NOME)) {
			statement.setString(1, "%" + nome + "%");
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
	
	@Override
	public List<Professor> buscarTodos() {
		List<Professor> professores = new ArrayList<>();
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
