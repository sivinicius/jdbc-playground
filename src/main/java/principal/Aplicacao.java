package principal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import configuracao.DAO;
import configuracao.FabricaDeConexao;
import dao.ProfessorDAO;
import modelo.Professor;

public class Aplicacao {

	public static void main(String[] args) throws SQLException {
		Connection conexao = new FabricaDeConexao().getConnection();		
		ProfessorDAO professorDAO = new ProfessorDAO(conexao);
		Professor professorInsere = new Professor("Sicrano Silva");
		Professor professorInsere2 = new Professor("Fulano Silva");
		Professor professorInsere3 = new Professor("Catão Silva");
		Professor professorInsere4 = new Professor("Ciprio Silva");
		Professor professorAltera = new Professor(6, "Beltrano Silva");
		
		//Insert
		professorDAO.inserir(professorInsere);		
		professorDAO.inserir(professorInsere2);		
		professorDAO.inserir(professorInsere3);		
		professorDAO.inserir(professorInsere4);		
		imprimirProfessores(professorDAO);
		
		
		System.out.println("#############################");
		
		professorDAO.atualizar(professorAltera);
		imprimirProfessores(professorDAO);

		System.out.println("#############################");
		
		professorDAO.deletar(professorAltera.getId());
		imprimirProfessores(professorDAO);
		
		System.out.println("#############################");
		
		System.out.println(professorDAO.buscarPor("Ciprio"));		
		
		System.out.println("#############################");
		
		System.out.println(professorDAO.buscarPor(2));
	}

	private static void imprimirProfessores(DAO<Professor> professorDAO) {
		List<Professor> professores = professorDAO.buscarTodos();
		for (Professor p : professores) {
			System.out.println(p);
		}
	}
}
