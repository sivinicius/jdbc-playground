package jdbc_playground;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuracao.FabricaDeConexao;
import dao.ProfessorDAO;
import modelo.Professor;

public class ProfessorDAOTest {

	private Connection conexao;
	private ProfessorDAO professorDao;
	private List<Professor> massaDeTeste;
	
	@Before
	public void setUp() throws SQLException {
		conexao = new FabricaDeConexao().obterConexao();
		conexao.setAutoCommit(false);
		professorDao = new ProfessorDAO(conexao);
		massaDeTeste = Arrays.asList(new Professor("Teste 1"), new Professor("Teste 2"));
		massaDeTeste.forEach(professor -> professorDao.inserir(professor));
	}

	@After
	public void tearDown() throws SQLException {
		conexao.rollback();
		conexao.close();
	}

	@Test
	public void testBuscarTodos() {
		List<Professor> professoresRecuperados = professorDao.buscarTodos();
		assertEquals(massaDeTeste.size(), professoresRecuperados.size());
	}
	
	@Test
	public void testBuscarPorNome() {
		Professor professor = professorDao.buscarPorNome("Teste 1");
		assertNotEquals(null, professor);
	}
	
	@Test
	public void testBuscarPorPalavraChave() {
		List<Professor> professor = professorDao.buscarPorPalavraChave("Teste");
		assertEquals(2, professor.size());
	}

	@Test
	public void testInserir() {
		List<Professor> professoresAntesDoInsert = professorDao.buscarTodos();
		professorDao.inserir(new Professor("Teste Insere"));
		List<Professor> professoresDepoisDoInsert = professorDao.buscarTodos();
		assertNotEquals(professoresAntesDoInsert.size(), professoresDepoisDoInsert.size());
	}

	@Test
	public void testDeletar() {
		professorDao.deletar(professorDao.buscarPorNome("Teste 1"));
		List<Professor> professoresRecuperados = professorDao.buscarTodos();
		assertNotEquals(massaDeTeste.size(), professoresRecuperados.size());
	}
	
	@Test
	public void testAtualizar() {
		Professor professorRecuperado = professorDao.buscarPorNome("Teste 1");
		professorRecuperado.setNome("Alterado");
		professorDao.atualizar(professorRecuperado);
		Professor professorAntigoModificado = professorDao.buscarPorNome("Teste 1");
		assertEquals(null, professorAntigoModificado);
	}

}
