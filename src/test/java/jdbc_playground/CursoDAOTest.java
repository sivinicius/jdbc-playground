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
	public void seBuscaTodosOsCursos() {
		List<Curso> cursos = Arrays.asList(new Curso("Teste 1", Status.ATIVO), new Curso("Teste 2", Status.INATIVO));
		cursos.forEach(curso -> cursoDao.inserir(curso));
		List<Curso> cursosRecuperados = cursoDao.buscarTodos();
		assertEquals(cursos.size(), cursosRecuperados.size());
	}

	@Test
	public void seFoiInseridoUmNovoCurso() {
		List<Curso> cursosAntesDoInsert = cursoDao.buscarTodos();
		cursoDao.inserir(new Curso("Teste", Status.ATIVO));
		List<Curso> cursosDepoisDoInsert = cursoDao.buscarTodos();
		assertNotEquals(cursosAntesDoInsert.size(), cursosDepoisDoInsert.size());
	}

	@Test
	public void seFoiDeletadoCurso() {
		List<Curso> cursos = Arrays.asList(new Curso("Teste 1", Status.ATIVO), new Curso("Teste 2", Status.INATIVO));
		cursos.forEach(curso -> cursoDao.inserir(curso));
		cursoDao.deletar(cursoDao.buscarPor("Teste 1"));
		List<Curso> cursosRecuperados = cursoDao.buscarTodos();
		assertNotEquals(cursos.size(), cursosRecuperados.size());
	}

}
