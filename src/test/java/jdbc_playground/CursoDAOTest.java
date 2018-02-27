package jdbc_playground;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import configuracao.FabricaDeConexao;
import dao.CursoDAO;
import dominio.Status;
import modelo.Curso;

public class CursoDAOTest {
	
	private Connection conexao;
	private CursoDAO cursoDao;
	
	@Before
	public void setUp() throws SQLException {
		conexao = new FabricaDeConexao().obterConexao();
		conexao.setAutoCommit(false);
		cursoDao = new CursoDAO(conexao);
	}
	
	@After
	public void tearDown() throws SQLException {
		conexao.rollback();
		conexao.close();
	}

	@Test
	public void testarSeBuscaTodosOsCursos() {
		List<Curso> cursos = cursoDao.buscarTodos();
		Assert.assertEquals(4, cursos.size());
	}
	
	@Test
	public void seFoiInseridoUmNovoCurso() {
		Curso cursoNovo = new Curso(null, "Teste", Status.ATIVO);
		Integer quantidadeDeCursosAntesDeInserir = cursoDao.buscarTodos().size();
		cursoDao.inserir(cursoNovo);
		Integer quantidadeDeCursosDepoisDeInserir = cursoDao.buscarTodos().size();
		Assert.assertNotEquals(quantidadeDeCursosAntesDeInserir, quantidadeDeCursosDepoisDeInserir);
	}
	
	@Test
	public void seFoiDeletadoCurso() {
		Integer quantidadeDeCursosAntesDeDeletar = cursoDao.buscarTodos().size();
		cursoDao.deletar(cursoDao.buscarTodos().get(1));
		Integer quantidadeDeCursosDepoisDeDeletar = cursoDao.buscarTodos().size();
		Assert.assertNotEquals(quantidadeDeCursosAntesDeDeletar, quantidadeDeCursosDepoisDeDeletar);
	}
	
	
	@Test
	public void seFoiAlteradoCurso() {
		Curso cursoNovo= new Curso(null, "Teste Integracao", Status.ATIVO);
		cursoDao.inserir(cursoNovo);
		Curso cursoAtual = cursoDao.buscarPor(cursoNovo.getNome());
		cursoAtual.setNome("Teste Integracao Atualizado");
		cursoDao.atualizar(cursoAtual);
		Curso cursoAtualizado = cursoDao.buscarPor(cursoNovo.getNome());
		Assert.assertNotEquals(cursoNovo.getNome(), cursoAtualizado.getNome());
	}

}
