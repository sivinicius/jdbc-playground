package jdbc_playground;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuracao.FabricaDeConexao;
import dao.CursoDAO;
import dominio.Status;
import modelo.Curso;

public class CursoDAOTest {

	private Connection conexao;
	private CursoDAO cursoDao;
	private List<Curso> massaDeTeste;
	
	@Before
	public void setUp() throws SQLException {
		conexao = new FabricaDeConexao().obterConexao();
		conexao.setAutoCommit(false);
		cursoDao = new CursoDAO(conexao);
		massaDeTeste = Arrays.asList(new Curso("Teste 1", Status.ATIVO), new Curso("Teste 2", Status.INATIVO));
		massaDeTeste.forEach(curso -> cursoDao.inserir(curso));
	}

	@After
	public void tearDown() throws SQLException {
		conexao.rollback();
		conexao.close();
	}

	@Test
	public void testBuscarTodos() {
		List<Curso> cursosRecuperados = cursoDao.buscarTodos();
		assertEquals(massaDeTeste.size(), cursosRecuperados.size());
	}
	
	@Test
	public void testBuscarPorNome() {
		Curso curso = cursoDao.buscarPorNome("Teste 1");
		assertNotEquals(null, curso);
	}
	
	@Test
	public void testBuscarPorPalavraChave() {
		List<Curso> cursos = cursoDao.buscarPorPalavraChave("Teste");
		assertEquals(2, cursos.size());
	}

	@Test
	public void testInserir() {
		List<Curso> cursosAntesDoInsert = cursoDao.buscarTodos();
		cursoDao.inserir(new Curso("Teste Insere", Status.ATIVO));
		List<Curso> cursosDepoisDoInsert = cursoDao.buscarTodos();
		assertNotEquals(cursosAntesDoInsert.size(), cursosDepoisDoInsert.size());
	}

	@Test
	public void testDeletar() {
		cursoDao.deletar(cursoDao.buscarPorNome("Teste 1"));
		List<Curso> cursosRecuperados = cursoDao.buscarTodos();
		assertNotEquals(massaDeTeste.size(), cursosRecuperados.size());
	}
	
	@Test
	public void testAtualizar() {
		Curso cursoRecuperado = cursoDao.buscarPorNome("Teste 1");
		cursoRecuperado.setNome("Alterado");
		cursoDao.atualizar(cursoRecuperado);
		Curso cursoAntigoModificado = cursoDao.buscarPorNome("Teste 1");
		assertEquals(null, cursoAntigoModificado);
	}

}
