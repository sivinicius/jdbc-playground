package modelo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import dominio.Status;

public class Curso {

	private Integer id;
	private String nome;
	private Status ativo;

	public Curso() {
		ativo = Status.ATIVO;
	}

	public Curso(Integer id, String nome, Status ativo) {
		this.id = id;
		this.nome = nome;
		this.ativo = ativo;
	}

	public Curso(String nome, Status ativo) {
		this(null, nome, ativo);
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String isAtivo() {
		return ativo.getValor();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("ID", this.getId()).append("Nome", this.getNome())
				.append("Ativo", this.isAtivo()).toString();
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
