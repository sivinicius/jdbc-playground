package modelo;

import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
		this(null, dataInicio, dataFim, curso, professor);
	}

	public Integer getId() {
		return id;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public Curso getCurso() {
		return curso;
	}
		
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Professor getProfessor() {
		return professor;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("ID", this.getId())
				.append("Data Inicio", this.getDataInicio())
				.append("Data Fim", this.getDataFim())
				.append("Curso", this.getCurso().getNome())
				.append("Professor", this.getProfessor().getNome()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		return new EqualsBuilder().append(this.getId(), other.getId()).isEquals();
	}

}
