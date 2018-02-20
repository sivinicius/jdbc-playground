package modelo;

import java.time.LocalDate;

public class Turma {
	
	private Integer id;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private Curso curso;
	private Professor professor;
	
	public Turma() {
		
	}
		
	public Turma(Integer id, LocalDate dataInicio, LocalDate dataFim, Curso curso, Professor professor) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.curso = curso;
		this.professor = professor;
	}
	
	public Turma(LocalDate dataInicio, LocalDate dataFim, Curso curso, Professor professor) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.curso = curso;
		this.professor = professor;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	public Curso getCurso() {
		return curso;
	}
	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	

}
