package dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import configuracao.FabricaDeConexao;
import dao.CursoDAO;
import dao.ProfessorDAO;
import dao.TurmaDAO;
import dominio.Status;
import modelo.Curso;
import modelo.Professor;
import modelo.Turma;

public class TurmaDAOTest {

	private Connection conexao;
	private CursoDAO cursoDao;
	private ProfessorDAO professorDao;
	private TurmaDAO turmaDao;
	
	private List<Curso> massaDeTesteCurso;
	private List<Professor> massaDeTesteProfessores;
	private List<Turma> massaDeTesteTurma;
	
	@Before
	public void setUp() throws SQLException {
		conexao = new FabricaDeConexao().obterConexao();
		conexao.setAutoCommit(false);
		
		professorDao = new ProfessorDAO(conexao);
		massaDeTesteProfessores = Arrays.asList(new Professor("Professor 1"), new Professor("Professor 2"));
		massaDeTesteProfessores.forEach(professor -> professorDao.inserir(professor));
		
		cursoDao = new CursoDAO(conexao);
		massaDeTesteCurso = Arrays.asList(new Curso("Curso 1", Status.ATIVO), new Curso("Curso 2", Status.INATIVO), new Curso("Curso 3", Status.ATIVO));
		massaDeTesteCurso.forEach(curso -> cursoDao.inserir(curso));
		
		turmaDao = new TurmaDAO(conexao);
		massaDeTesteTurma = criarMassaDeTesteParaTurma();
		massaDeTesteTurma.forEach(turma -> turmaDao.inserir(turma));
	}

	private List<Turma> criarMassaDeTesteParaTurma() {
		List<Curso> cursos = cursoDao.buscarTodos();
		List<Professor> professores = professorDao.buscarTodos();
		Turma turma1 = new Turma(LocalDate.now(), LocalDate.of(2018, 8, 1), cursos.get(0), professores.get(0));
		Turma turma2 = new Turma(LocalDate.now(), LocalDate.of(2018, 9, 1), cursos.get(1), professores.get(0));
		Turma turma3 = new Turma(LocalDate.now(), LocalDate.of(2018, 10, 1), cursos.get(2), professores.get(0));
		Turma turma4 = new Turma(LocalDate.now(), LocalDate.of(2018, 11, 1), cursos.get(0), professores.get(1));
		Turma turma5 = new Turma(LocalDate.now(), LocalDate.of(2018, 7, 1), cursos.get(1), professores.get(1));
		return Arrays.asList(turma1, turma2, turma3, turma4, turma5);
	}

	@After
	public void tearDown() throws SQLException {
		conexao.rollback();
		conexao.close();
	}

	@Test
	public void testBuscarTodos() {
		List<Turma> turmasRecuperadas = turmaDao.buscarTodos();
		assertEquals(massaDeTesteTurma.size(), turmasRecuperadas.size());
	}
	
	@Test
	public void testBuscarPorCurso() {
		Curso curso = cursoDao.buscarPorNome("Curso 1");
		List<Turma> turmas = turmaDao.buscarPor(curso);
		assertEquals(2, turmas.size());
	}
		
	@Test
	public void testBuscarPorProfessor() {
		Professor professor = professorDao.buscarPorNome("Professor 1");
		List<Turma> turmas = turmaDao.buscarPor(professor);
		assertEquals(3, turmas.size());
	}
	
	@Test
	public void testInserir() {
		List<Turma> turmasAntesDoInsert = turmaDao.buscarTodos();
		turmaDao.inserir(massaDeTesteTurma.get(0));
		List<Turma> turmasDepoisDoInsert = turmaDao.buscarTodos();
		assertNotEquals(turmasAntesDoInsert.size(), turmasDepoisDoInsert.size());
	}

	@Test
	public void testDeletar() {
		Curso curso = cursoDao.buscarPorNome("Curso 1");
		List<Turma> turmas = turmaDao.buscarPor(curso);
		turmaDao.deletar(turmas.get(0));
		List<Turma> turmasRecuperadas = turmaDao.buscarTodos();
		assertNotEquals(massaDeTesteTurma.size(), turmasRecuperadas.size());
	}
	
	@Test
	public void testAtualizar() {
		Professor professor = professorDao.buscarPorNome("Professor 2");
		List<Turma> turmasRecuperadas = turmaDao.buscarPor(professor);
		Turma turmaParaAlteracao = turmasRecuperadas.get(0);
		turmaParaAlteracao.setProfessor(professorDao.buscarPorNome("Professor 1"));
		turmaDao.atualizar(turmaParaAlteracao);
		List<Turma> turmasAposAlteracao = turmaDao.buscarPor(professor);
		assertNotEquals(turmasRecuperadas.size(), turmasAposAlteracao.size());
	}

}
